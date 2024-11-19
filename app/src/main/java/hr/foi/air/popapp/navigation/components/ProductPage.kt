package hr.foi.air.popapp.navigation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import hr.foi.air.popapp.context.Auth
import hr.foi.air.popapp.core.network.ResponseListener
import hr.foi.air.popapp.core.network.models.ErrorResponseBody
import hr.foi.air.popapp.core.network.models.SuccessfulResponseBody
import hr.foi.air.popapp.ui.components.ProductCard
import hr.foi.air.popapp.ws.models.responses.Product
import hr.foi.air.popapp.ws.request_handlers.ProductsRequestHandler

@Composable
fun ProductPage() {
    val (products, setProducts) = remember { mutableStateOf<MutableList<Product>>(mutableListOf()) }

    val productsRequestHandler = ProductsRequestHandler(Auth.loggedInUserData!!.jwt)

    productsRequestHandler.sendRequest(object : ResponseListener {
        override fun <T> onSuccessfulResponse(response: SuccessfulResponseBody<T>) {
            println(response)
            val productsArray = response.data
            setProducts((productsArray as Array<Product>).toMutableList())
        }

        override fun onErrorResponse(response: ErrorResponseBody) {
            println(response)
        }

        override fun onNetworkFailure(t: Throwable) {
            println("Error contacting network...")
        }
    })

    if (products.isEmpty()) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        LazyColumn {
            items(products) {
                ProductCard(product = it)
            }
        }
    }
}