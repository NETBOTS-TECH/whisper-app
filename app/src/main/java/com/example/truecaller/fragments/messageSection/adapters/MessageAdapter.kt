package com.example.truecaller.fragments.messageSection.adapters

import Message
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.truecaller.R
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int = messages.size

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val phoneNumber: TextView = itemView.findViewById(R.id.phoneNumber)
        private val timestamp: TextView = itemView.findViewById(R.id.timestamp)
        private val icon: ImageView = itemView.findViewById(R.id.iconText)
        private val messageBody: TextView = itemView.findViewById(R.id.message)

        fun bind(message: Message) {
            if (message.tag == "Spam") {
                phoneNumber.setTextColor(Color.RED)
                timestamp.setTextColor(Color.RED)
                icon.setBackgroundResource(R.drawable.fab_bg_red)
            }

            phoneNumber.text = message.phoneNumber
            messageBody.text = message.message
            timestamp.text =
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(message.timestamp)
        }
    }
}