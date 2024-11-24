package hr.foi.air.popapp.navigation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import hr.foi.air.popapp.ui.components.ProductCard
import hr.foi.air.popapp.viewmodels.ProductsViewModel
import hr.foi.air.popapp.ws.models.responses.Product
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductPage(viewModel: ProductsViewModel = viewModel()) {
    val products by viewModel.products.observeAsState()
    viewModel.fetchProducts()

    if (products?.isEmpty() == true) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        LazyColumn {
            items(products as List<Product>) {
                ProductCard(product = it)
            }
        }
    }
}