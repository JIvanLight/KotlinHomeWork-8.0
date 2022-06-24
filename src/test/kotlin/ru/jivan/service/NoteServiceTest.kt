package ru.jivan.service

import org.junit.Test

import org.junit.Assert.*
import ru.jivan.data.*
import ru.jivan.data.Note.Comment
import ru.jivan.exceptions.NoteNotFoundException
import kotlin.math.exp

class NoteServiceTest {

    @Test
    fun add_expectReturn_3() {

        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val obj2 = Note(
            id = 3,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        noteService.add(obj1)
        val actual = noteService.add(obj1)
        val expectedObj = obj2

        assertEquals(expectedObj, actual)
    }

    @Test
    fun add_correctExecution() {

        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val obj2 = Note(
            id = 3,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        noteService.add(obj1)
        noteService.add(obj1)
        val expectedObj = obj2
        val actual = noteService.notes[2]

        assertEquals(expectedObj, actual)
    }

    @Test
    fun createComment_correctExecution() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val comment = Comment(
            noteId = 1,
            replyTo = 0,
            message = "Message"
        )
        val expectedComment = Comment(
            id = 1,
            noteId = 1,
            replyTo = 0,
            message = "Message"
        )

        noteService.add(obj1)

        val actual = noteService.createComment(1, comment)

        assertEquals(expectedComment, actual)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createComment_correctExecutionIfNoteDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            deleted = true,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val comment = Comment(
            noteId = 1,
            replyTo = 0,
            message = "Message"
        )

        noteService.add(obj1)

        noteService.createComment(1, comment)
    }

    @Test
    fun delete_return_true() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        val actual = noteService.delete(1)

        assertEquals(true, actual)
    }

    @Test
    fun delete_correctExecution() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        noteService.add(obj1)
        noteService.add(obj1)
        noteService.delete(2)
        val actual = noteService.notes[1].deleted

        assertEquals(true, actual)
    }

    @Test
    fun delete_correctExecutionIfNoteDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            deleted = true,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        val actual = noteService.delete(1)

        assertEquals(false, actual)
    }

    @Test
    fun deleteComment_return_true() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val comment = Comment(
            noteId = 1,
            replyTo = 0,
            message = "Message"
        )

        noteService.add(obj1)
        noteService.createComment(1, comment)
        val actual = noteService.deleteComment(1, 1)

        assertEquals(true, actual)
    }

    @Test
    fun deleteComment_correctExecution() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val comment = Comment(
            noteId = 1,
            replyTo = 0,
            message = "Message"
        )

        noteService.add(obj1)
        noteService.createComment(1, comment)
        noteService.createComment(1, comment)
        noteService.createComment(1, comment)
        noteService.deleteComment(1, 2)
        val actual = noteService.notes[0].comments[1].deleted

        assertEquals(true, actual)
    }

    @Test
    fun deleteComment_correctExecutionIfNoteDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            deleted = true,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    id = 1,
                    noteId = 1,
                    replyTo = 0,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)
        val actual = noteService.deleteComment(1, 1)

        assertEquals(false, actual)
    }

    @Test
    fun deleteComment_correctExecutionIfCommentDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    deleted = true,
                    id = 1,
                    noteId = 1,
                    replyTo = 0,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)
        val actual = noteService.deleteComment(1, 1)

        assertEquals(false, actual)
    }

    @Test
    fun edit_return_true() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )
        noteService.add(obj1)

        val actual = noteService.edit(
            noteId = 1,
            title = "text",
            text = "texttexttext",
            privacy = 1,
            commentPrivacy = 1,
            privacyView = " ",
            privacyComment = " "
        )

        assertEquals(true, actual)
    }

    @Test
    fun edit_correctExecution() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val obj2 = Note(
            id = 1,
            title = "text",
            text = "texttexttext",
            privacy = 1,
            commentPrivacy = 1,
            privacyView = "value",
            privacyComment = "value"
        )
        noteService.add(obj1)

        noteService.edit(
            noteId = 1,
            title = "text",
            text = "texttexttext",
            privacy = 1,
            commentPrivacy = 1,
            privacyView = "value",
            privacyComment = "value"
        )
        val actual = noteService.notes[0]
        val expected = obj2

        assertEquals(expected, actual)
    }

    @Test
    fun edit_correctExecutionIfNoteDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val obj2 = Note(
            deleted = true,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        noteService.add(obj2)
        noteService.add(obj1)

        val actual = noteService.edit(
            noteId = 2,
            title = "text",
            text = "texttexttext",
            privacy = 1,
            commentPrivacy = 1,
            privacyView = " ",
            privacyComment = " "
        )

        assertEquals(false, actual)
    }

    @Test
    fun editComment_return_true() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    id = 1,
                    noteId = 1,
                    replyTo = 0,
                    message = "Message"
                )
            )
        )
        noteService.add(obj1)

        val actual = noteService.editComment(
            commentId = 1,
            noteId = 1,
            message = "No message"
        )

        assertEquals(true, actual)
    }

    @Test
    fun editComment_correctExecution() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    id = 1,
                    noteId = 1,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)
        noteService.add(obj1)
        noteService.add(obj1)

        noteService.editComment(
            commentId = 1,
            noteId = 1,
            message = "No message"
        )

        val expected = Comment(
            id = 1,
            noteId = 1,
            message = "No message"
        )

        val actual = noteService.notes[0].comments[0]

        assertEquals(expected, actual)
    }

    @Test
    fun editComment_correctExecutionIfNoteDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    id = 1,
                    noteId = 1,
                    message = "Message"
                )
            )
        )

        val obj2 = Note(
            deleted = true,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    id = 1,
                    noteId = 1,
                    message = "Message"
                )
            )
        )
        noteService.add(obj1)
        noteService.add(obj2)
        noteService.add(obj1)

        val actual = noteService.editComment(
            commentId = 1,
            noteId = 2,
            message = "No message"
        )

        assertEquals(false, actual)
    }

    @Test
    fun editComment_correctExecutionIfCommentDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    id = 1,
                    noteId = 1,
                    message = "Message"
                ),
                Comment(
                    deleted = true,
                    id = 2,
                    noteId = 1,
                    message = "Message"
                ),
                Comment(
                    id = 3,
                    noteId = 1,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)

        val actual = noteService.editComment(
            commentId = 2,
            noteId = 1,
            message = "No message"
        )

        assertEquals(false, actual)
    }

    @Test
    fun get_correctExecution_TypeSortASCENDING() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val obj2 = Note(
            deleted = true,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        noteService.add(obj2)
        noteService.add(obj1)
        noteService.add(obj1)

        val inList = listOf<Int>(1, 2, 4)

        val actual = noteService.get(inList, sort = NoteService.TypeSort.ASCENDING)

        val expected = listOf<Note>(
            Note(
                id = 1,
                title = "TEXT",
                text = "TextTextText TextTextText TextTextText",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = " ",
                privacyComment = " "
            ),
            Note(
                id = 4,
                title = "TEXT",
                text = "TextTextText TextTextText TextTextText",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = " ",
                privacyComment = " "
            )
        )

        println(actual)

        assertEquals(expected, actual)
    }

    @Test
    fun get_correctExecution_TypeSortDESCENDING() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val obj2 = Note(
            deleted = true,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        noteService.add(obj2)
        noteService.add(obj1)
        noteService.add(obj1)

        val inList = listOf(1, 2, 4)

        val actual = noteService.get(inList, sort = NoteService.TypeSort.DESCENDING)

        val expected = listOf<Note>(
            Note(
                id = 4,
                title = "TEXT",
                text = "TextTextText TextTextText TextTextText",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = " ",
                privacyComment = " "
            ),
            Note(
                id = 1,
                title = "TEXT",
                text = "TextTextText TextTextText TextTextText",
                privacy = 0,
                commentPrivacy = 0,
                privacyView = " ",
                privacyComment = " "
            )
        )

        println(actual)

        assertEquals(expected, actual)
    }

    @Test
    fun getById_correctExecution() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        noteService.add(obj1)
        noteService.add(obj1)

        val actual = noteService.getById(3)
        val expected = Note(
            id = 3,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        assertEquals(expected, actual)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getById_correctExecutionIfNoteDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        val obj2 = Note(
            deleted = true,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " "
        )

        noteService.add(obj1)
        noteService.add(obj2)

        val actual = noteService.getById(2)
    }

    @Test
    fun getComments_correctExecution_TypeSortASCENDING() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    id = 1,
                    noteId = 1,
                    message = "Message"
                ),
                Comment(
                    deleted = true,
                    id = 2,
                    noteId = 1,
                    message = "Message"
                ),
                Comment(
                    id = 3,
                    noteId = 1,
                    message = "Message"
                ),
                Comment(
                    id = 4,
                    noteId = 1,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)

        val inList = listOf(1, 2, 4)

        val actual = noteService.getComments(noteId = 1, inList, sort = NoteService.TypeSort.ASCENDING)

        val expected = listOf<Comment>(
            Comment(
                id = 1,
                noteId = 1,
                message = "Message"
            ),
            Comment(
                id = 4,
                noteId = 1,
                message = "Message"
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun getComments_correctExecution_TypeSortDESCENDING() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    id = 1,
                    noteId = 1,
                    message = "Message"
                ),
                Comment(
                    deleted = true,
                    id = 2,
                    noteId = 1,
                    message = "Message"
                ),
                Comment(
                    id = 3,
                    noteId = 1,
                    message = "Message"
                ),
                Comment(
                    id = 4,
                    noteId = 1,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)

        val inList = listOf(1, 2, 4)

        val actual = noteService.getComments(noteId = 1, inList, sort = NoteService.TypeSort.DESCENDING)

        val expected = listOf<Comment>(
            Comment(
                id = 4,
                noteId = 1,
                message = "Message"
            ),
            Comment(
                id = 1,
                noteId = 1,
                message = "Message"
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun restoreComment_expected_true() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    deleted = true,
                    id = 1,
                    noteId = 1,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)

        val actual = noteService.restoreComment(1)

        assertEquals(true, actual)
    }

    @Test
    fun restoreComment_correctExecute() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    deleted = true,
                    id = 1,
                    noteId = 1,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)
        noteService.restoreComment(1)

        val actual = noteService.notes[0].comments[0].deleted

        assertEquals(false, actual)
    }

    @Test
    fun restoreComment_correctExecuteIfNoteDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    id = 1,
                    noteId = 1,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)


        val actual = noteService.restoreComment(1)

        assertEquals(false, actual)
    }

    @Test
    fun restoreComment_correctExecuteIfCommentNoDeleted() {
        val noteService = NoteService()

        val obj1 = Note(
            deleted = true,
            title = "TEXT",
            text = "TextTextText TextTextText TextTextText",
            privacy = 0,
            commentPrivacy = 0,
            privacyView = " ",
            privacyComment = " ",
            comments = mutableListOf(
                Comment(
                    deleted = true,
                    id = 1,
                    noteId = 1,
                    message = "Message"
                )
            )
        )

        noteService.add(obj1)

        val actual = noteService.restoreComment(1)

        assertEquals(false, actual)
    }
}