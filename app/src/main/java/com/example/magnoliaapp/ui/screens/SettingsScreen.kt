package com.example.magnoliaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.magnoliaapp.ui.theme.MagnoliaAppTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {

    var darkModeEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Top,

        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Dark Mode",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Switch(
                checked = darkModeEnabled,

                onCheckedChange = {
                    darkModeEnabled = it
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {

    MagnoliaAppTheme {

        SettingsScreen()
    }
}