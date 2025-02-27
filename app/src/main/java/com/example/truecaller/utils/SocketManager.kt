import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException
import kotlin.math.log

class SocketManager {
    private var socket: Socket? = null

    init {
        try {
            socket = IO.socket("http://192.168.100.164:3000/")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            Log.e("socket", ":e.printStackTrace() ${e.printStackTrace()}")
        }
    }

    fun connect() {
        socket?.connect()
        Log.e("socket", "connect: ${socket?.id()}")
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun isConnected(): Boolean {
        return socket?.connected() ?: false
    }
}