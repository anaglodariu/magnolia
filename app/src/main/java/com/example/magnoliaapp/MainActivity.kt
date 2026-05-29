package com.example.magnoliaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.magnoliaapp.ui.theme.MagnoliaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MagnoliaAppTheme {
                MagnoliaApp()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    MagnoliaApp(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MagnoliaAppPreview() {
    MagnoliaAppTheme {
        MagnoliaApp()
    }
}