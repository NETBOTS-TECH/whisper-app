import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessageViewModel : ViewModel() {
    private val repository = MessageRepository()

    private val _allMessages = MutableLiveData<List<Message>>()
    val allMessages: LiveData<List<Message>> get() = _allMessages

    private val _primaryMessages = MutableLiveData<List<Message>>()
    val primaryMessages: LiveData<List<Message>> get() = _primaryMessages

    private val _promoMessages = MutableLiveData<List<Message>>()
    val promoMessages: LiveData<List<Message>> get() = _promoMessages

    private val _spamMessages = MutableLiveData<List<Message>>()
    val spamMessages: LiveData<List<Message>> get() = _spamMessages

    init {
        repository.addMessage(
            Message(
                1,
                "hello there, this is primary message",
                "1234567890",
                System.currentTimeMillis(),
                "Primary"
            )
        )
        repository.addMessage(
            Message(
                2,
                "hello there, this is promo message",
                "0987654321",
                System.currentTimeMillis(),
                "Promo"
            )
        )
        repository.addMessage(
            Message(
                3,
                "hello there this is Spam message",
                "1122334455",
                System.currentTimeMillis(),
                "Spam"
            )
        )

        // Update LiveData
        updateMessages()
    }

    private fun updateMessages() {
        _allMessages.value = repository.getAllMessages()
        _primaryMessages.value = repository.getMessageByTag("Primary")
        _promoMessages.value = repository.getMessageByTag("Promo")
        _spamMessages.value = repository.getMessageByTag("Spam")
    }

    fun addMessage(message: Message) {
        repository.addMessage(message)
        updateMessages()
    }
}