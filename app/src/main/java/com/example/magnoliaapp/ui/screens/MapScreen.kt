package com.example.magnoliaapp.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.magnoliaapp.model.Magnolia
import com.example.magnoliaapp.ui.theme.MagnoliaAppTheme
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(
    onMagnoliaClick: (Magnolia) -> Unit,
    modifier: Modifier = Modifier,
    magnoliaViewModel: MagnoliaViewModel =
        viewModel(factory = MagnoliaViewModel.Factory)
) {
    // Refactored to call a stateless content Composable
    MapScreenContent(
        uiState = magnoliaViewModel.magnoliaUiState,
        onMagnoliaClick = onMagnoliaClick,
        modifier = modifier,
        onDetailsSuccess = { magnoliaViewModel.getMagnolias() }
    )
}

/**
 * Stateless version of MapScreen for easier testing and previews.
 * This resolves the render issue by allowing the preview to pass state directly
 * without instantiating the ViewModel.
 */
@Composable
fun MapScreenContent(
    uiState: MagnoliaUiState,
    onMagnoliaClick: (Magnolia) -> Unit,
    modifier: Modifier = Modifier,
    onDetailsSuccess: () -> Unit = {}
) {
    when (uiState) {
        is MagnoliaUiState.Loading -> {
            MapLoadingScreen(modifier = modifier)
        }

        is MagnoliaUiState.Error -> {
            MapErrorScreen(modifier = modifier)
        }

        is MagnoliaUiState.Success -> {
            Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

//        Text(
//            text =
//                "Loaded ${uiState.magnolias.size} magnolias\n\n" +
//                        uiState.magnolias.joinToString("\n") {
//                            it.name
//                        }
//        )
                MagnoliaMap(
                    magnolias = uiState.magnolias,
                    onMagnoliaClick = onMagnoliaClick,
                    modifier = modifier
                )
    }
        }

        is MagnoliaUiState.DetailsSuccess -> {
            /*
             * This state is used by MagnoliaDetailsScreen.
             * If we arrive here, simply reload all magnolias.
             */
            MapLoadingScreen(modifier = modifier)
        }
    }
}

@Composable
fun MagnoliaMap(
    magnolias: List<Magnolia>,
    onMagnoliaClick: (Magnolia) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val mapView = rememberMapView(context)

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { mapView },
        update = { view ->
            view.overlays.clear()
            view.controller.setZoom(11.0)
            view.controller.setCenter(GeoPoint(44.4268, 26.1025))

            magnolias.forEach { magnolia ->
                val marker = Marker(view)

                marker.position = GeoPoint(
                    magnolia.latitude,
                    magnolia.longitude
                )

                marker.title = magnolia.name

                marker.setOnMarkerClickListener { _, _ ->
                    onMagnoliaClick(magnolia)
                    true
                }

                view.overlays.add(marker)
            }

            view.invalidate()
        }
    )
}

@Composable
private fun rememberMapView(context: Context): MapView {
    val mapView = remember {
        Configuration.getInstance().userAgentValue = context.packageName

        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
        }
    }

    DisposableEffect(mapView) {
        onDispose {
            mapView.onDetach()
        }
    }

    return mapView
}

@Composable
fun MapLoadingScreen(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator()
    }
}

@Composable
fun MapErrorScreen(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text("Failed to load magnolias")
    }
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {

    MagnoliaAppTheme {
        // Use MapScreenContent instead of MapScreen to avoid ViewModel instantiation in Preview
        MapScreenContent(
            uiState = MagnoliaUiState.Success(emptyList()),
            onMagnoliaClick = {}
        )
    }
}
