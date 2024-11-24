package hr.foi.air.popapp.navigation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.utsman.osmandcompose.MapProperties
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import hr.foi.air.popapp.ui.components.StyledButton
import org.osmdroid.util.GeoPoint

@Composable
fun SelectStorePage(
    onStoreSelected: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        val isStoreSelected by remember { mutableStateOf(false) }
        val cameraState = rememberCameraState {
            geoPoint = GeoPoint(46.307679, 16.338106)
            zoom = 18.5
            speed = 10
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
        )

        StyledButton(
            label = "I've selected my store",
            enabled = isStoreSelected,
            onClick = { }
        )
    }
}

@Preview
@Composable
fun SelectStorePagePreview() {
    SelectStorePage(onStoreSelected = {})
}
