package xyz.moratech.android.albotest.features.details.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import xyz.moratech.android.albotest.R
import xyz.moratech.android.albotest.databinding.FragmentBeerDetailsBinding
import xyz.moratech.android.albotest.features.details.domain.models.Beer
import xyz.moratech.android.albotest.features.details.domain.models.IngredientItem
import xyz.moratech.android.albotest.features.details.view.adapters.IngredientsAdapter
import xyz.moratech.android.albotest.features.details.view.viewmodels.MainViewModel

class BeerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBeerDetailsBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val beer = mainViewModel.requestBeer()

        beer?.let { loadBeer(it) } ?: run {
            Toast.makeText(requireContext(), R.string.fr_details_beer_argument_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadBeer(beer: Beer) {
        val ingredients = mutableListOf<IngredientItem>()
        ingredients.addAll(beer.ingredients.malt)
        ingredients.addAll(beer.ingredients.hops)

        Glide.with(this)
            .load(beer.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.beerDetailsBannerImageView)

        binding.beerDetailsTitleTextView.text = beer.name
        binding.beerDetailsDescriptionTextView.text = beer.description
        binding.beerDetailsTagTextView.text = beer.tag
        binding.beerDetailsCreatorTextView.text = beer.contributedBy
        binding.beerDetailsFoodTextView.text = buildFoodList(beer.foodPairing)
        binding.beerDetailsTipsTextView.text = beer.brewersTips
        binding.beerDetailsIngredientsRecyclerView.adapter = IngredientsAdapter(
            ingredients
        )
    }

    private fun buildFoodList(foodPairing: List<String>): String {
        val stringBuilder = StringBuilder()
        foodPairing.forEach {
            stringBuilder.append("- $it")
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}