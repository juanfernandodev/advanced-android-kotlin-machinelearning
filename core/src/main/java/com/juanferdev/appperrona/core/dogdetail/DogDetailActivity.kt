package com.juanferdev.appperrona.core.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.juanferdev.appperrona.core.dogdetail.ui.theme.AppPerronaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            AppPerronaTheme {
                DogDetailScreen { messageId ->
                    messageId?.let {
                        Toast.makeText(this, messageId, Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }


        }
    }
}


