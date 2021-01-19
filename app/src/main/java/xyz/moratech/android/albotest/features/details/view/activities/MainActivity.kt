package xyz.moratech.android.albotest.features.details.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.moratech.android.albotest.R
import xyz.moratech.android.albotest.databinding.ActivityMainBinding
import xyz.moratech.android.albotest.features.details.view.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.navigationLiveData.observe(this) { isRootFragment ->
            if (isRootFragment) {
                dismissBack()
            } else {
                showBack()
            }
        }

        binding.mainToolbar.setNavigationOnClickListener {
            onBackPressed()
            mainViewModel.saveSelectedBeer(null)
        }
    }

    private fun dismissBack() {
        binding.mainToolbar.setTitle(R.string.act_main_title)
        binding.mainToolbar.navigationIcon = null
    }

    private fun showBack() {
        binding.mainToolbar.setTitle(R.string.app_name)
        binding.mainToolbar.setNavigationIcon(R.drawable.ic_back)
    }
}