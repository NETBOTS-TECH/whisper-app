import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.truecaller.R
import java.text.SimpleDateFormat
import java.util.Locale

class CallAdapter(private val calls: List<Call>) :
    RecyclerView.Adapter<CallAdapter.CallViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call, parent, false)
        return CallViewHolder(view)
    }

    override fun onBindViewHolder(holder: CallViewHolder, position: Int) {
        val call = calls[position]
        holder.bind(call)
    }

    override fun getItemCount(): Int = calls.size

    class CallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val phoneNumber: TextView = itemView.findViewById(R.id.phoneNumber)
        private val timestamp: TextView = itemView.findViewById(R.id.timestamp)

        fun bind(call: Call) {
            phoneNumber.text = call.phoneNumber
            timestamp.text =
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(call.timestamp)
        }
    }
}