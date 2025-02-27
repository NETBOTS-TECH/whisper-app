import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import org.webrtc.*
import java.io.File
import java.io.FileInputStream
import kotlin.math.log
import android.util.Base64
import java.io.IOException


class SocketViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var socket: Socket
    private lateinit var peerConnectionFactory: PeerConnectionFactory
    private lateinit var localAudioSource: AudioSource
    private lateinit var localAudioTrack: AudioTrack
    private lateinit var peerConnection: PeerConnection

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _isRinging = MutableLiveData<Boolean>()
    val isRinging: LiveData<Boolean> get() = _isRinging

    private val _inCall = MutableLiveData<Boolean>()
    val inCall: LiveData<Boolean> get() = _inCall

    private val _transcription = MutableLiveData<String>()
    val transcription: LiveData<String> get() = _transcription

    private val context = application.applicationContext


    init {
        initializeWebRTC()
        connectSocket()
    }

    private fun initializeWebRTC() {
        PeerConnectionFactory.initialize(
            PeerConnectionFactory.InitializationOptions.builder(context)
                .setEnableInternalTracer(true)
                .createInitializationOptions()
        )
        peerConnectionFactory = PeerConnectionFactory.builder().createPeerConnectionFactory()

        localAudioSource = peerConnectionFactory.createAudioSource(MediaConstraints())
        localAudioTrack = peerConnectionFactory.createAudioTrack("audio_track", localAudioSource)

        val iceServers = listOf(
            PeerConnection.IceServer.builder("stun:stun.l.google.com:19302").createIceServer()
        )
        peerConnection = peerConnectionFactory.createPeerConnection(
            iceServers,
            object : PeerConnection.Observer {
                override fun onSignalingChange(p0: PeerConnection.SignalingState?) {
                }

                override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {
                }

                override fun onIceConnectionReceivingChange(p0: Boolean) {
                }

                override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {
                }

                override fun onIceCandidate(candidate: IceCandidate) {
                    val json = JSONObject().apply {
                        put("type", "candidate")
                        put("candidate", candidate.sdp)
                        put("sdpMid", candidate.sdpMid)
                        put("sdpMLineIndex", candidate.sdpMLineIndex)
                    }
                    socket.emit("candidate", json)
                    Log.d("socket", "onIceCandidate: ")
                }

                override fun onIceCandidatesRemoved(p0: Array<out IceCandidate>?) {
                }

                override fun onAddStream(mediaStream: MediaStream) {
                    // Handle remote audio stream
                    _inCall.postValue(true)
                    Log.d("socket", "onAddStream: ")
                }

                override fun onRemoveStream(p0: MediaStream?) {
                }

                override fun onDataChannel(p0: DataChannel?) {
                }

                override fun onRenegotiationNeeded() {
                }

                override fun onAddTrack(p0: RtpReceiver?, p1: Array<out MediaStream>?) {
                }

                // Implement other PeerConnection.Observer methods
            })!!

        peerConnection.addTrack(localAudioTrack)
    }

    private fun connectSocket() {
        try {
            val options = IO.Options().apply {
                reconnection = true
                reconnectionAttempts = Int.MAX_VALUE
                reconnectionDelay = 5000
                transports = arrayOf("websocket", "polling")
            }
//            socket = IO.socket("http://192.168.100.164:3000/", options)
            socket = IO.socket("https://whisper-backend-8aqk.onrender.com/", options)

            socket.on(Socket.EVENT_CONNECT) {
                Log.d("Socket.IO", "Connected to server")
            }

            socket.on(Socket.EVENT_DISCONNECT) {
                Log.d("Socket.IO", "Disconnected from server")
            }

            socket.on("incoming-call") {
                Log.d("Socket.IO", "Incoming call event received")
                _isRinging.postValue(true)
            }

            socket.on("only-you") {
                Log.d("Socket.IO", "Only you are connected")
                _message.postValue("Only you are connected to the app.")
            }

            socket.on("offer") { data ->
                val offer = data[0] as JSONObject
                Log.d("Socket.IO", "Received offer: $offer")
                handleOffer(offer)
            }

            socket.on("answer") { data ->
                val answer = data[0] as JSONObject
                Log.d("Socket.IO", "Received answer: $answer")
                handleAnswer(answer)
            }

            socket.on("candidate") { data ->
                val candidate = data[0] as JSONObject
                Log.d("Socket.IO", "Received candidate: $candidate")
                handleCandidate(candidate)
            }

            socket.on("call-ended") {
                Log.d("Socket.IO", "Call ended event received")
                endCallInternally()
            }

            socket.on("audio-chunk") {
                Log.d("Socket.IO", "Audio chunk received")
            }

            socket.on("transcription") { data ->
                val json = data[0] as JSONObject
                val text = json.optString("text")
                Log.d("Socket.IO", "Transcription received: $text")
                _transcription.postValue(text)
            }

            socket.connect()
        } catch (e: Exception) {
            Log.e("Socket.IO", "Error connecting to server: ${e.message}")
        }
    }

    private fun handleOffer(offer: JSONObject) {
        val sessionDescription =
            SessionDescription(SessionDescription.Type.OFFER, offer.optString("sdp"))
        peerConnection.setRemoteDescription(object : SdpObserver {
            override fun onCreateSuccess(p0: SessionDescription?) {
            }

            override fun onSetSuccess() {
                createAnswer()
                Log.d("socket", "onSetSuccess: ")
            }

            override fun onCreateFailure(p0: String?) {
            }

            override fun onSetFailure(p0: String?) {
            }

            // Implement other SdpObserver methods
        }, sessionDescription)
    }

    private fun createAnswer() {
        peerConnection.createAnswer(object : SdpObserver {
            override fun onCreateSuccess(sessionDescription: SessionDescription) {
                peerConnection.setLocalDescription(object : SdpObserver {
                    override fun onCreateSuccess(p0: SessionDescription?) {
                    }

                    override fun onSetSuccess() {
                        Log.d("socket", "onSetSuccess: ")
                        val json = JSONObject().apply {
                            put("type", "answer")
                            put("sdp", sessionDescription.description)
                        }
                        socket.emit("answer", json)
                    }

                    override fun onCreateFailure(p0: String?) {
                    }

                    override fun onSetFailure(p0: String?) {
                    }

                    // Implement other SdpObserver methods
                }, sessionDescription)
            }

            override fun onSetSuccess() {
            }

            override fun onCreateFailure(p0: String?) {
            }

            override fun onSetFailure(p0: String?) {
            }

            // Implement other SdpObserver methods
        }, MediaConstraints())
    }

    private fun handleAnswer(answer: JSONObject) {
        Log.d("socket", "handleAnswer:-- ")
        val sessionDescription =
            SessionDescription(SessionDescription.Type.ANSWER, answer.optString("sdp"))
        peerConnection.setRemoteDescription(object : SdpObserver {
            override fun onCreateSuccess(p0: SessionDescription?) {
            }

            override fun onSetSuccess() {
                // Answer successfully set
            }

            override fun onCreateFailure(p0: String?) {
            }

            override fun onSetFailure(p0: String?) {
            }

            // Implement other SdpObserver methods
        }, sessionDescription)
    }

    private fun handleCandidate(candidate: JSONObject) {
        val iceCandidate = IceCandidate(
            candidate.optString("sdpMid"),
            candidate.optInt("sdpMLineIndex"),
            candidate.optString("candidate")
        )
        peerConnection.addIceCandidate(iceCandidate)
    }

    fun startCall() {
        Log.d("Socket.IO", "Emitting call-user event")
        socket.emit("call-user")
    }

    fun acceptCall() {
        Log.d("Socket.IO", "Accepting call")
        _isRinging.postValue(false)
        _inCall.postValue(true)

    }

    fun endCall() {
        Log.d("Socket.IO", "Emitting end-call event")
        socket.emit("end-call")
        endCallInternally()
    }

    fun sendAudioFile(file: File) {
        try {
            val fileInputStream = FileInputStream(file)
            val bytes = fileInputStream.readBytes()
            fileInputStream.close()

            // Send the raw audio data as a byte array
            socket.emit("audio-chunk", bytes)

            println("Audio file sent via WebSocket")

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun endCallInternally() {
        peerConnection.close()
        _inCall.postValue(false)
        _isRinging.postValue(false)
        _message.postValue("")
    }

    override fun onCleared() {
        super.onCleared()
        socket.disconnect()
        peerConnection.close()
    }
}