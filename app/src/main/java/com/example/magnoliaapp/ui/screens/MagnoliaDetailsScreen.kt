package com.example.magnoliaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.magnoliaapp.model.Magnolia
import com.example.magnoliaapp.ui.theme.MagnoliaAppTheme

@Composable
fun MagnoliaDetailsScreen(
    magnoliaId: Int,
    modifier: Modifier = Modifier,
    magnoliaViewModel: MagnoliaViewModel =
        viewModel(factory = MagnoliaViewModel.Factory)
) {

    LaunchedEffect(magnoliaId) {

        magnoliaViewModel.getMagnolia(magnoliaId)
    }

    when (val uiState = magnoliaViewModel.magnoliaUiState) {

        is MagnoliaUiState.Loading -> {

            LoadingScreen()
        }

        is MagnoliaUiState.Error -> {

            ErrorScreen()
        }

        is MagnoliaUiState.DetailsSuccess -> {

            MagnoliaDetailsContent(

                magnolia = uiState.magnolia,

                modifier = modifier
            )
        }

        is MagnoliaUiState.Success -> {
            Column {

            Text(
                text = "Loaded ${uiState.magnolias.size} magnolias"
            )

            uiState.magnolias.forEach {

                Text(it.name)
            }
    }
        }
    }
}

@Composable
fun MagnoliaDetailsContent(
    magnolia: Magnolia,
    modifier: Modifier = Modifier
) {

    var visited by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = magnolia.name,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = magnolia.imageUrl
        )

        AsyncImage(
            model = magnolia.imageUrl,

            contentDescription = magnolia.name,

            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),

            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text =
                "Latitude: ${magnolia.latitude}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text =
                "Longitude: ${magnolia.longitude}"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(

            onClick = {

                visited = !visited
            }

        ) {

            Text(

                text =

                    if (visited)
                        "Visited ✓"

                    else
                        "Mark as Visited"
            )
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Failed to load magnolia"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MagnoliaDetailsPreview() {

    MagnoliaAppTheme {

        MagnoliaDetailsContent(

            magnolia = Magnolia(
                id = 1,
                name = "Magnolia Cișmigiu",
                latitude = 44.4352,
                longitude = 26.0910,
                imageUrl = ""
            )
        )
    }
}