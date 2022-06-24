package ru.jivan.service

import ru.jivan.data.Note
import ru.jivan.data.Note.Comment
import ru.jivan.exceptions.NoteNotFoundException

class NoteService {
    val notes: MutableList<Note> = mutableListOf()
    private var lastIdNote = 0
    private var lastIdComment = 0

    enum class TypeSort {
        ASCENDING, DESCENDING
    }

    fun add(note: Note): Note {
        lastIdNote++
        val noteWithId = note.copy(id = lastIdNote)
        notes.add(noteWithId)
        return noteWithId
    }

    fun createComment(noteId: Int, comment: Comment): Comment {
        for (element in notes) {
            if (element.deleted) continue
            if (element.id == noteId) {
                lastIdComment++
                val commentWithId = comment.copy(id = lastIdComment, noteId = noteId)
                element.comments.add(commentWithId)
                return commentWithId
            }
        }
        throw NoteNotFoundException("Заметка с id $noteId не существует")
    }

    fun delete(noteId: Int): Boolean {
        for ((index, element) in notes.withIndex()) {
            if (element.id == noteId && !element.deleted) {
                val deletedNote = element.copy(deleted = true)
                notes[index] = deletedNote
                return true
            }
        }
        return false
    }

    fun deleteComment(noteId: Int, commentId: Int): Boolean {
        for (elementNote in notes) {
            if (elementNote.id == noteId && !elementNote.deleted) {
                for ((index, elementComment) in elementNote.comments.withIndex()) {
                    if (elementComment.id == commentId && !elementComment.deleted) {
                        val deletedComment = elementComment.copy(deleted = true)
                        elementNote.comments[index] = deletedComment
                        return true
                    }
                }
            }
        }
        return false
    }

    fun edit(
        noteId: Int,
        title: String,
        text: String,
        privacy: Int,
        commentPrivacy: Int,
        privacyView: String,
        privacyComment: String
    ): Boolean {
        for ((index, element) in notes.withIndex()) {
            if (element.deleted) continue
            if (element.id == noteId) {
                val editedNote = element.copy(
                    title = title,
                    text = text,
                    privacy = privacy,
                    commentPrivacy = commentPrivacy,
                    privacyView = privacyView,
                    privacyComment = privacyComment
                )
                notes[index] = editedNote
                return true
            }
        }
        return false
    }

    fun editComment(commentId: Int, noteId: Int, message: String): Boolean {
        for (elementNote in notes) {
            if (elementNote.deleted) continue
            if (elementNote.id == noteId) {
                for ((index, elementComment) in elementNote.comments.withIndex()) {
                    if (elementComment.id == commentId && !elementComment.deleted) {
                        val editedComment = elementComment.copy(message = message)
                        elementNote.comments[index] = editedComment
                        return true
                    }
                }
            }
        }
        return false
    }

    fun get(noteIds: List<Int>, sort: TypeSort = TypeSort.ASCENDING): List<Note> {
        val outList: MutableList<Note> = mutableListOf()
        for (element in notes) {
            if (element.deleted) continue
            for (elementId in noteIds) {
                if (elementId == element.id) {
                    outList.add(element)
                }
            }
        }
        when (sort) {
            TypeSort.ASCENDING -> outList.sortBy { it.id }
            TypeSort.DESCENDING -> outList.sortWith(Comparator { first, second ->
                if (first.id > second.id) -1
                else if (first.id < second.id) 1
                else 0
            })
        }
        return outList
    }

    fun getById(noteId: Int): Note {
        for (element in notes) {
            if (element.deleted) continue
            if (element.id == noteId) return element
        }
        throw NoteNotFoundException("Заметка с id $noteId не существует")
    }

    fun getComments(noteId: Int, commentIds: List<Int>, sort: TypeSort = TypeSort.ASCENDING): List<Comment> {
        val outList: MutableList<Comment> = mutableListOf()
        var foundNote: Note? = null
        for (elementNote in notes) {
            if (elementNote.deleted) continue
            if (elementNote.id == noteId) {
                foundNote = elementNote
                break
            }
        }
        if (foundNote != null) {
            for (elementComment in foundNote.comments) {
                if (elementComment.deleted) continue
                for (elementId in commentIds) {
                    if (elementId == elementComment.id) {
                        outList.add(elementComment)
                    }
                }
            }
        } else return outList

        when (sort) {
            TypeSort.ASCENDING -> outList.sortBy { it.id }
            TypeSort.DESCENDING -> outList.sortWith { first, second ->
                if (first.id > second.id) -1
                else if (first.id < second.id) 1
                else 0
            }
        }
        return outList
    }

    fun restoreComment(commentId: Int): Boolean {
        for (elementNote in notes) {
            if (elementNote.deleted) continue
            for ((index, elementComment) in elementNote.comments.withIndex()) {
                if (elementComment.id == commentId && elementComment.deleted) {
                    val restoredComment = elementComment.copy(deleted = false)
                    elementNote.comments[index] = restoredComment
                    return true
                }
            }
        }
        return false
    }
}