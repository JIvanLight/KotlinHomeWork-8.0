# KotlinHomeWork-8.0
Во всём проекте опущены поля/параметры onver_id, guid и далее не указываются.

fun get: опущены параметры user_id, offset, count
     изменен тип параметра note_ids c string на List\<Int\>
     изменен тип параметра sort c Int на enum class TypeSort (так как время создания заметки мы не храним, то сортируем по id)     
     функция возвращает List\<Note\>

fun getById: опущен параметр need_wiki

fun getFriendsNotes: документация рекомендует избегать использования данного устаревшего метода. Поэтому не реализуем :)

fun deleteComment и fun editComment: доработана сигнатура, а именно добавлен параметр id заметки, чтобы функция понимала, в какой заметке удалить комментарий.

fun add и fun createComment: вместо ссылки на id возвращают ссылку на объект Note и Comment соответственно.
