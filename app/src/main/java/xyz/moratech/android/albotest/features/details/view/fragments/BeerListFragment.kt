package xyz.moratech.android.albotest.features.details.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.moratech.android.albotest.R
import xyz.moratech.android.albotest.databinding.FragmentBeerListBinding
import xyz.moratech.android.albotest.features.details.domain.models.Beer
import xyz.moratech.android.albotest.features.details.view.adapters.BeerSelectionListener
import xyz.moratech.android.albotest.features.details.view.adapters.BeersAdapter
import xyz.moratech.android.albotest.features.details.view.viewmodels.DetailsViewModel
import xyz.moratech.android.albotest.features.details.view.viewmodels.MainViewModel

class BeerListFragment : Fragment(), BeerSelectionListener {

    private lateinit var binding: FragmentBeerListBinding

    private val detailsViewModel: DetailsViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val beersAdapter = BeersAdapter(mutableListOf(), this)

    private val scrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val scrolledPosition =
                    (binding.beerListRecyclerView.layoutManager!! as LinearLayoutManager)
                        .findLastVisibleItemPosition()
                detailsViewModel.loadMoreData(
                    scrolledPosition = scrolledPosition + 1,
                    currentListSize = beersAdapter.itemCount
                )
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBeerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.beerListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.beerListRecyclerView.adapter = beersAdapter
        binding.beerListRecyclerView.addOnScrollListener(scrollListener)
        binding.beerListRewindButton.setOnClickListener { scrollToTop() }

        binding.beerListSwipeToRefreshLayout.setOnRefreshListener {
            detailsViewModel.clearAndFetch()
        }

        detailsViewModel.beersLiveData.observe(viewLifecycleOwner) {
            hideLoading()
            beersAdapter.attachMore(it)
            binding.beerListEmptyTextView.isVisible = false
            binding.beerListRewindButton.isVisible = true
        }

        detailsViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        detailsViewModel.errorLiveData.observe(viewLifecycleOwner) {
            hideLoading()
            Toast.makeText(
                requireContext(),
                getString(R.string.beers_error, it),
                Toast.LENGTH_SHORT
            ).show()

            if (beersAdapter.itemCount == 0) {
                showEmptyView()
            }
        }

        detailsViewModel.fetchBeers()
    }

    private fun showEmptyView() {
        binding.beerListEmptyTextView.isVisible = true
        binding.beerListRewindButton.isVisible = false
    }

    private fun showLoading() {
        binding.beerLoadingCardView.isVisible = true
        binding.beerListRecyclerView.removeOnScrollListener(scrollListener)
    }

    private fun hideLoading() {
        binding.beerLoadingCardView.isVisible = false
        binding.beerListSwipeToRefreshLayout.isRefreshing = false
        binding.beerListRecyclerView.addOnScrollListener(scrollListener)
    }

    private fun scrollToTop() {
        binding.beerListRecyclerView.smoothScrollToPosition(0)
    }

    override fun invoke(beer: Beer) {
        mainViewModel.saveSelectedBeer(beer)
        findNavController().navigate(R.id.navListToDetails)
    }



}