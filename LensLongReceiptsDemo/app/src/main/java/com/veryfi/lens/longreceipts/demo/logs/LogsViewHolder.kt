package com.veryfi.lens.longreceipts.demo.logs

import androidx.recyclerview.widget.RecyclerView
import com.veryfi.lens.longreceipts.demo.databinding.LogsListItemBinding
import com.veryfi.lens.longreceipts.demo.logs.Log

class LogsViewHolder(private val itemBinding: LogsListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun onBind(log: Log) {
        itemBinding.logsTitle.text = log.title
        itemBinding.logsMessage.text = log.message
    }
}