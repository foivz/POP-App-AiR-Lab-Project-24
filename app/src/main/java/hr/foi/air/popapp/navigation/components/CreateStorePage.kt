package hr.foi.air.popapp.navigation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utsman.osmandcompose.*
import hr.foi.air.popapp.ui.components.StyledButton
import hr.foi.air.popapp.ui.components.StyledTextField
import hr.foi.air.popapp.viewmodels.NewStoreViewModel
import org.osmdroid.util.GeoPoint
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CreateStorePage(
    viewModel: NewStoreViewModel = viewModel(),
    onStoreCreated: () -> Unit
) {
    val storeName = viewModel.storeName.observeAsState().value ?: ""
    val errorMessage = viewModel.errorReason.observeAsState().value ?: ""

    val storeLocationMarkerState = rememberMarkerState(
        geoPoint = GeoPoint(0.0, 0.0)
    )
    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(46.307679, 16.338106)
        zoom = 18.5
        speed = 10
    }
    var isStoreSet: Boolean by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Create a Store",
            style = MaterialTheme.typography.bodyMedium,
        )

        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = Color.Red
            )
        }

        StyledTextField(
            label = "Your new store name",
            value = storeName,
            onValueChange = { viewModel.setStoreName(it) },
        )

        Text(
            text = "Hold your finger where you want your store to be.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        OpenStreetMap(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
                .border(2.dp, Color.Black, AbsoluteRoundedCornerShape(10.dp))
                .clip(AbsoluteRoundedCornerShape(10.dp)),
            cameraState = cameraState,
            onMapLongClick = {
                storeLocationMarkerState.geoPoint = it
                isStoreSet = true
                viewModel.setStoreLocation(it.latitude, it.longitude)
            },
            properties = MapProperties(
                minZoomLevel = 17.0,
                maxZoomLevel = 20.0,
                isFlingEnable = false,
                zoomButtonVisibility = ZoomButtonVisibility.NEVER,
                isTilesScaledToDpi = true
            )
        ) {
            Marker(
                state = storeLocationMarkerState,
                visible = isStoreSet,
            )
        }

        StyledButton(
            label = "Create store",
            enabled = isStoreSet && storeName.isNotBlank(),
            onClick = {
                viewModel.createStore(onSuccessfulResponse = onStoreCreated)
            }
        )
    }
}

@Preview
@Composable
fun CreateStorePagePreview() {
    CreateStorePage(onStoreCreated = {})
}