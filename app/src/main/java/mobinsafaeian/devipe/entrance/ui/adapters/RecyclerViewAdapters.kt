package mobinsafaeian.devipe.entrance.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mobinsafaeian.devipe.entrance.R
import mobinsafaeian.devipe.entrance.databinding.RecyclerViewItemBinding
import mobinsafaeian.devipe.entrance.model.connections.responses.RollCall

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var listItems: ArrayList<RollCall>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<RecyclerViewItemBinding>(LayoutInflater.from(parent.context) , R.layout.recycler_view_item , parent , false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = if (listItems.isNullOrEmpty()) 0 else listItems?.size!!

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        val currentItem = listItems!![position]
        holder.itemBinding.item = currentItem
    }

    fun setData(listItems: ArrayList<RollCall>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }

    fun reset() {
        this.listItems?.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val itemBinding: RecyclerViewItemBinding): RecyclerView.ViewHolder(itemBinding.root)
}