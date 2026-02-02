package com.achievemeaalk.freedjf.sms


/*

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bp.antifs.R
import com.bp.antifs.pushnotification.PushNotificationEntity
import java.time.format.DateTimeFormatter


class SmsAdapter(): PagingDataAdapter<SmsRelationEntity, SmsAdapter.SmsViewHolder>(
    DiffCallback
) {

    companion object {
        private val DiffCallback = object :
            DiffUtil.ItemCallback<SmsRelationEntity>() {
            override fun areItemsTheSame(
                oldItem: SmsRelationEntity,
                newItem: SmsRelationEntity
            ): Boolean {
                return oldItem.smsEntity.uuid == newItem.smsEntity.uuid
            }

            override fun areContentsTheSame(
                oldItem: SmsRelationEntity,
                newItem: SmsRelationEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class SmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uuidTextView: TextView = itemView.findViewById(R.id.uuid)
        val createdAtTextView: TextView = itemView.findViewById(R.id.created_at)
        val senderTextView: TextView = itemView.findViewById(R.id.sender)
        val textTextView: TextView = itemView.findViewById(R.id.text)
        val bankTextView: TextView = itemView.findViewById(R.id.bank)
        val sendToServerTextView: TextView = itemView.findViewById(R.id.send_to_server)
    }

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) {
        val smsRelationEntity: SmsRelationEntity? = getItem(position)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        holder.createdAtTextView.text = smsRelationEntity?.smsEntity?.createdAt?.toLocalDateTime()?.format(formatter)
        holder.uuidTextView.text = smsRelationEntity?.smsEntity?.uuid
        holder.senderTextView.text = smsRelationEntity?.smsEntity?.sender
        holder.textTextView.text = smsRelationEntity?.smsEntity?.text
        holder.bankTextView.text = smsRelationEntity?.bankEntity?.name
        if(!smsRelationEntity?.smsEntity?.sendToServer!!) {
            holder.sendToServerTextView.text = holder.itemView.context.resources.getString(R.string.send_to_server_in_process)
            holder.sendToServerTextView.setTextColor(holder.itemView.context.resources.getColor(R.color.md_theme_error, null))
        } else {
            holder.sendToServerTextView.text =
                smsRelationEntity.smsEntity.sendToServerAt?.toLocalDateTime()?.format(formatter)
            holder.sendToServerTextView.setTextColor(holder.itemView.context.resources.getColor(R.color.md_theme_secondary, null))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sms, parent, false)
        return SmsViewHolder(view)
    }
}

 */