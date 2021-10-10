package com.news

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.paging.ExperimentalPagingApi
import com.news.ui.MainScreen
import com.news.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.ExperimentalTime

@ExperimentalTime
@AndroidEntryPoint
@ExperimentalPagingApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}