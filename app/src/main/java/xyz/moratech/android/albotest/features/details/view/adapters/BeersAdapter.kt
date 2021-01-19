package xyz.moratech.android.albotest.features.details.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import xyz.moratech.android.albotest.databinding.ItemBeerBinding
import xyz.moratech.android.albotest.features.details.domain.models.Beer
import java.text.DateFormatSymbols
typealias BeerSelectionListener = ((beer: Beer) -> Unit)


class BeersAdapter(
    private val items: MutableList<Beer>,
    private val listener: BeerSelectionListener
) : RecyclerView.Adapter<BeersAdapter.BeersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
        val view = ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeersViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun attachMore(items: List<Beer>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class BeersViewHolder(
        private val binding: ItemBeerBinding,
        listener: (beer: Beer) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val months = DateFormatSymbols().months

        init {
            itemView.setOnClickListener {
                listener.invoke(itemView.tag as Beer)
            }
        }

        fun bind(beer: Beer) {
            itemView.tag = beer

            binding.beerNameTextView.text = beer.name
            binding.beerTagTextView.text = beer.tag
            binding.beerContributorTextView.text = beer.contributedBy
            binding.beerFromTextView.text = parseDate(beer.firstBrewedDate)
            Glide.with(itemView)
                .load(beer.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.beerPhotoImageView)
        }

        private fun parseDate(date: String): String {
            val dateSections = date.split("/")
            return "${months[dateSections[0].toInt() - 1]}, ${dateSections[1]}".capitalize()
        }
    }
}
