package xyz.moratech.android.albotest.features.splash.view.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import xyz.moratech.android.albotest.R
import xyz.moratech.android.albotest.features.details.view.activities.MainActivity
import xyz.moratech.android.albotest.features.extensions.navigateTo

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val DELAY_MILLIS = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.accent)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateTo<MainActivity>()
        }, DELAY_MILLIS)
    }
}