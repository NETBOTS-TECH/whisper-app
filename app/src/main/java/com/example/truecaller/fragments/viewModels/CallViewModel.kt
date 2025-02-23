import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CallViewModel : ViewModel() {
    private val repository = CallRepository()

    private val _allCalls = MutableLiveData<List<Call>>()
    val allCalls: LiveData<List<Call>> get() = _allCalls

    private val _primaryCalls = MutableLiveData<List<Call>>()
    val primaryCalls: LiveData<List<Call>> get() = _primaryCalls

    private val _promoCalls = MutableLiveData<List<Call>>()
    val promoCalls: LiveData<List<Call>> get() = _promoCalls

    private val _spamCalls = MutableLiveData<List<Call>>()
    val spamCalls: LiveData<List<Call>> get() = _spamCalls

    init {
        repository.addCall(Call(1, "John", "1234567890", System.currentTimeMillis(), "Primary"))
        repository.addCall(Call(2, "Promo Call", "0987654321", System.currentTimeMillis(), "Promo"))
        repository.addCall(Call(3, "Spam Call", "1122334455", System.currentTimeMillis(), "Spam"))

        // Update LiveData
        updateCalls()
    }

    private fun updateCalls() {
        _allCalls.value = repository.getAllCalls()
        _primaryCalls.value = repository.getCallsByTag("Primary")
        _promoCalls.value = repository.getCallsByTag("Promo")
        _spamCalls.value = repository.getCallsByTag("Spam")
    }

    fun addCall(call: Call) {
        repository.addCall(call)
        updateCalls()
    }
}