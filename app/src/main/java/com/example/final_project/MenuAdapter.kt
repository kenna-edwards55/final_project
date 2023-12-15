import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.MenuItem
import com.example.final_project.MenuItemAdapter
import com.example.final_project.databinding.MenuItemBinding

class MenuAdapter(private val menuItems: List<MenuItem>) : RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemAdapter.MenuItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MenuItemBinding.inflate(inflater, parent, false)
        return MenuItemAdapter.MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemAdapter.MenuItemViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.bind(menuItem)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }
}

class MenuItemViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(menuItem: MenuItem) {
        binding.menuItem = menuItem
        binding.executePendingBindings()
    }
}
