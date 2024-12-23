package com.example.plantopiapp.adapters
import ChildItem
import ParentItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantopiapp.R

class ExpandableAdapter(private val parentList: List<ParentItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val expandedItems = mutableSetOf<Int>() // Track expanded items

    companion object {
        const val TYPE_PARENT = 0
        const val TYPE_CHILD = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (isParent(position)) TYPE_PARENT else TYPE_CHILD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_PARENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parent, parent, false)
            ParentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false)
            ChildViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ParentViewHolder) {
            val parentItem = parentList[position]
            holder.bind(parentItem, position)
        } else if (holder is ChildViewHolder) {
            val childItem = getChildItem(position)
            holder.bind(childItem)
        }
    }

    override fun getItemCount(): Int {
        return parentList.sumOf { 1 + if (expandedItems.contains(parentList.indexOf(it))) it.children.size else 0 }
    }

    private fun isParent(position: Int): Boolean {
        var count = 0
        for ((index, item) in parentList.withIndex()) {
            if (position == count) return true
            count++
            if (expandedItems.contains(index)) count += item.children.size
        }
        return false
    }

    private fun getChildItem(position: Int): ChildItem {
        var count = 0
        for ((index, item) in parentList.withIndex()) {
            count++
            if (expandedItems.contains(index)) {
                if (position in count until (count + item.children.size)) {
                    return item.children[position - count]
                }
                count += item.children.size
            }
        }
        throw IllegalStateException("Invalid position")
    }

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.parentTitle)
        private val iconImageView: ImageView = itemView.findViewById(R.id.parentIcon)

        fun bind(parentItem: ParentItem, position: Int) {
            titleTextView.text = parentItem.title
            iconImageView.setImageResource(parentItem.iconRes)
            itemView.setOnClickListener {
                if (expandedItems.contains(position)) {
                    expandedItems.remove(position)
                } else {
                    expandedItems.add(position)
                }
                notifyDataSetChanged()
            }
        }
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.childName)
        private val iconImageView: ImageView = itemView.findViewById(R.id.childIcon)

        fun bind(childItem: ChildItem) {
            nameTextView.text = childItem.name
            iconImageView.setImageResource(childItem.iconRes)
        }
    }
}
