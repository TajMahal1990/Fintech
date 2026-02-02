package com.achievemeaalk.freedjf.bank


/*

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bp.antifs.R

class BankAdapter : PagingDataAdapter<BankRelationEntity, BankAdapter.BankViewHolder>(BankDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_banks, parent, false)
        return BankViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val bank = getItem(position)
        if (bank != null) {
            holder.bind(bank)
        }
    }

    class BankViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {

        private val nameTextView: TextView = view.findViewById(R.id.textViewBankName) // пример
        private val packagesCountTextView: TextView = view.findViewById(R.id.textViewBankPackagesCount) // пример
        private val numbersCountTextView: TextView = view.findViewById(R.id.textViewBankNumbersCount) // пример

        fun bind(bank: BankRelationEntity) {

            nameTextView.text = bank.bankEntity.name
            packagesCountTextView.text = bank.packages.size.toString()
            numbersCountTextView.text = bank.numbers.size.toString()
            // Привязываем данные банка к UI-элементам
            // Например, используя view.findViewById() для доступа к TextView
        }
    }

    class BankDiffCallback : DiffUtil.ItemCallback<BankRelationEntity>() {
        override fun areItemsTheSame(oldItem: BankRelationEntity, newItem: BankRelationEntity): Boolean {
            return oldItem.bankEntity.pkId == newItem.bankEntity.pkId // Используйте уникальный идентификатор
        }

        override fun areContentsTheSame(oldItem: BankRelationEntity, newItem: BankRelationEntity): Boolean {
            return oldItem == newItem
        }
    }
}


 */