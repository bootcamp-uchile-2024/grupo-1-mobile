import androidx.recyclerview.widget.DiffUtil
import com.example.plantopiapp.dataclases.Carrito

class CarritoDiffCallback(
    private val oldList: List<Carrito>,
    private val newList: List<Carrito>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
