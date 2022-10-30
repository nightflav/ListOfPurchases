package com.nightflav.listofpurchases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nightflav.listofpurchases.databinding.SingeItemLayoutBinding
import com.nightflav.listofpurchases.model.Item

interface UserActionListener {
    fun addOneItem(id: String)

    fun removeOneItem(id: String)

    fun deleteItem(id: String)

}

class ItemListAdapter(
    private var userActionListener: UserActionListener
) : RecyclerView.Adapter<ItemListAdapter.ItemsViewHolder>(), View.OnClickListener {

    class ItemsViewHolder(
        val binding: SingeItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root)

    var items: List<Item> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingeItemLayoutBinding.inflate(inflater, parent, false)

        with(binding) {
            btnPlus.setOnClickListener(this@ItemListAdapter)
            btnMinus.setOnClickListener(this@ItemListAdapter)
            btnBin.setOnClickListener(this@ItemListAdapter)
        }

        return ItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            btnPlus.tag = item
            btnMinus.tag = item
            btnBin.tag = item
            holder.itemView.tag = item

            tvSingleItemTitle.text = item.itemTitle
            tvSingleItemCategory.text = item.category
            amountOfItems.text = item.amount.toString()

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onClick(p0: View) {
        val item = p0.tag as Item
        when(p0.id) {
            R.id.btn_plus -> {
                userActionListener.addOneItem(item.id)
                notifyItemChanged(items.indexOf(item))
            }
            R.id.btn_minus -> {
                userActionListener.removeOneItem(item.id)
                notifyItemChanged(items.indexOf(item))
            }
            R.id.btn_bin -> {
                userActionListener.deleteItem(item.id)
                notifyItemRemoved(items.indexOf(item))
            }
        }
    }
}