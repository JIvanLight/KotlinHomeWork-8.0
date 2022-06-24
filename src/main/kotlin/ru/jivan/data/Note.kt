package ru.jivan.data

data class Note(
    val title: String,
    val text: String,
    val privacy: Int,
    val commentPrivacy: Int,
    val privacyView: String,
    val privacyComment: String,
    val comments: MutableList<Comment> = mutableListOf(),
    val deleted: Boolean = false,
    val id: Int = 0
){
    data class Comment(
        val noteId: Int,
        val message: String,
        val replyTo: Int = 0,
        val deleted: Boolean = false,
        val id: Int = 0
    )
}
