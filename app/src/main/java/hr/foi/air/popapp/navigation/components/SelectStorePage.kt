package hr.foi.air.popapp.navigation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utsman.osmandcompose.*
import hr.foi.air.popapp.R
import hr.foi.air.popapp.ui.components.StoreCard
import hr.foi.air.popapp.ui.components.StyledButton
import hr.foi.air.popapp.viewmodels.StoresViewModel
import hr.foi.air.popapp.ws.models.responses.Store
import org.osmdroid.util.GeoPoint

@Composable
fun SelectStorePage(
    viewModel: StoresViewModel = viewModel(),
    onStoreSelected: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        var selectedStore by remember { mutableStateOf<Store?>(null) }
        val cameraState = rememberCameraState {
            geoPoint = GeoPoint(46.307679, 16.338106)
            zoom = 18.5
            speed = 10
        }

        val stores by viewModel.stores.observeAsState()
        viewModel.fetchStores()
        val storeMarkers = mutableListOf<Pair<MarkerState, Store>>()

        stores?.let {
            for (store in it) {
                storeMarkers.add(
                    rememberMarkerState(
                        geoPoint = GeoPoint(
                            store.latitude!!,
                            store.longitude!!
                        )
                    ) to store
                )
            }
        }

        Text(
            text = "Select a Store",
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = "Select a store to which you belong by tapping on it.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        OpenStreetMap(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
                .border(2.dp, Color.Black, AbsoluteRoundedCornerShape(10.dp))
                .clip(AbsoluteRoundedCornerShape(10.dp)),
            cameraState = cameraState,
            properties = MapProperties(
                minZoomLevel = 17.0,
                maxZoomLevel = 20.0,
                isFlingEnable = false,
                zoomButtonVisibility = ZoomButtonVisibility.NEVER,
                isTilesScaledToDpi = true
            )
        ) {
            storeMarkers.forEach { (markerState, store) ->
                Marker(
                    state = markerState,
                    infoWindowContent = {
                        StoreCard(store = store)
                        storeMarkers.forEach { (ms) -> if (ms != markerState) ms.marker!!.closeInfoWindow() }
                    },
                    onClick = {
                        selectedStore = store
                        true
                    },
                    icon = LocalContext.current.resources.getDrawable(
                        R.drawable.baseline_storefront_24,
                        null
                    )
                )
            }
        }

        StyledButton(
            label = selectedStore?.name ?: "Select a store",
            enabled = selectedStore != null,
            onClick = {
                // TODO: assign a store on backend
                onStoreSelected()
            }
        )
    }
}

@Preview
@Composable
fun SelectStorePagePreview() {
    SelectStorePage(onStoreSelected = {})
}
