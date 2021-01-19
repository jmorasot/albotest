package xyz.moratech.android.albotest.features.extensions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T : AppCompatActivity> AppCompatActivity.navigateTo() {
    this.finish()
    startActivity(Intent(this, T::class.java))
}