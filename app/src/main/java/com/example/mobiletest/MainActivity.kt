package com.example.mobiletest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.mobiletest.manager.BookingException
import com.example.mobiletest.manager.BookingManager
import com.example.mobiletest.ui.theme.MobileTestTheme
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bookingManager: BookingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 确保每次显示时都获取最新数据
        loadBookingData()
    }

    private fun loadBookingData() {
        lifecycleScope.launch {
            try {
                val bookingData = bookingManager.getBookingData()
                // 在控制台打印数据
                Log.d("BookingDemo", "Booking Data: $bookingData")
            } catch (e: BookingException) {
                Log.e("BookingDemo", "Error getting booking data", e)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


