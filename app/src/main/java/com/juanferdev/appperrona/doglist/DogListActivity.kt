package com.juanferdev.appperrona.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.juanferdev.appperrona.constants.DOG_KEY
import com.juanferdev.appperrona.dogdetail.DogDetailActivity
import com.juanferdev.appperrona.dogdetail.ui.theme.AppPerronaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPerronaTheme {
                DogListScreen(
                    onDogClicked = ::openDogDetailActivity,
                    onNavigationIconClick = ::onNavigationIconClick
                )
            }
        }
    }

    private fun openDogDetailActivity(dog: com.juanferdev.appperrona.core.models.Dog) {
        val intent = Intent(this, DogDetailActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        startActivity(intent)
    }

    private fun onNavigationIconClick() {
        finish()
    }
}
