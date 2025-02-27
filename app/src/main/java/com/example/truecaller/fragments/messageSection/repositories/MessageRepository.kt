class MessageRepository {
    private val messageList = mutableListOf<Message>()

    fun addMessage(call: Message) {
        messageList.add(call)
    }

    fun getAllMessages(): List<Message> = messageList

    fun getMessageByTag(tag: String): List<Message> = messageList.filter { it.tag == tag }
}