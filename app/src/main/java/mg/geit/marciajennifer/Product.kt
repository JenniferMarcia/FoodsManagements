package mg.geit.marciajennifer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Product(
    val id: Int,
    val idCategory: Int,
    var name: String,
    var imageUrl: String,
    var description: String,
    var quantity: Int,
    var price: Double
) {
    companion object {
        private var startId = 0

        private fun nextId(): Int {
            return ++startId
        }

        fun create(
            idCategory: Int,
            name: String,
            imageUrl: String,
            description: String,
            quantity: Int,
            price: Double
        ): Product {
            return Product(nextId(), idCategory, name, imageUrl, description, quantity, price)
        }
    }

    fun updateProduct(
        newName: String,
        newImageUrl: String,
        newDescription: String,
        newQuantity: Int,
        newPrice: Double
    ): Product {
        return Product(id, idCategory, newName, newImageUrl, newDescription, newQuantity, newPrice)
    }

}

