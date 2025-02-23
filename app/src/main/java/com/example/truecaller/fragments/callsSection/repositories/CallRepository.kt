class CallRepository {
    private val calls = mutableListOf<Call>()

    fun addCall(call: Call) {
        calls.add(call)
    }

    fun getAllCalls(): List<Call> = calls

    fun getCallsByTag(tag: String): List<Call> = calls.filter { it.tag == tag }
}