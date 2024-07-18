package mg.geit.marciajennifer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mg.geit.marciajennifer.ui.theme.MarciaJenniferTheme


class UpdateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idCategory = intent.getIntExtra("idCategory", 1)
        val idProduct = intent.getIntExtra("idProduct",1)
        setContent {
            MarciaJenniferTheme {
                UpdateMainView(
                    idCategory,
                    idProduct,
                    goToPreviousActivity={
                        val intent  = Intent(this, DetailsProductActivity::class.java)
                            .apply { putExtra("idProduct", idProduct) }
                        startActivity(intent)

                },
                    seeUpdateList = {
                        val intent = Intent(this, ProductActivity::class.java)
                            .apply { putExtra("idCategory", idCategory) }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun UpdateMainView(
    idCategory: Int,
    idProduct: Int,
    goToPreviousActivity : () -> Unit,
    seeUpdateList: () -> Unit,

) {
    val productList = when (idCategory) {
        1 -> sweetProductList
        2 -> saltedProductList
        else -> mutableListOf()
    }
    val product = productList.find { it.id== idProduct } ?: return

    var name by remember { mutableStateOf(product.name) }
    var price by remember { mutableStateOf(product.price.toString()) }
    var quantity by remember { mutableStateOf(product.quantity.toString()) }
    var imageUrl by remember { mutableStateOf(product.imageUrl) }
    var description by remember { mutableStateOf(product.description) }

    Scaffold(
        topBar = {
            Header(value ="MODIFICATION",goToPreviousActivity)
        },

        floatingActionButton = {
            CustomFloatingActionButton(
                text = "MODIFIER",
                color = Color.Green,
                product,
                actionOnProduct = {
                    product ->
                    product.let {
                        val updatedProduct = product.updateProduct(
                            name, imageUrl, description, quantity.toInt(), price.toDouble()
                        )
                        productList[productList.indexOf(product)] = updatedProduct
                    }
                    seeUpdateList()
                }
            )
        }
    )
    {
            innerPadding ->
            FieldsToUpdate(
                product,
                innerPadding,
                onNameChange = { name = it },
                onImageUrlChange = { imageUrl = it},
                onDescriptionChange = { description = it },
                onQuantityChange = { quantity = it},
                onPriceChange = { price = it },
            )
        }
    }


@Composable
fun CustomFloatingActionButton(text: String, color: Color, product: Product?, actionOnProduct: (Product) -> Unit)
{
    ExtendedFloatingActionButton(
        onClick = {
            if (product != null) {
                actionOnProduct(product)
            }
        },
        containerColor = color,
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .padding(35.dp, 0.dp, 0.dp, 16.dp)
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Text(
            text,
            fontSize = 20.sp
        )
    }
}

@Composable
fun FieldsToUpdate(
    product: Product,
    paddingValues: PaddingValues,
    onNameChange: (String) -> Unit,
    onImageUrlChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onPriceChange: (String) -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 5.dp, 0.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .size(width = 350.dp, height = 650.dp)
                .padding(paddingValues)
        ){
            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                item { Column {
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        Text(
                            text = product.name,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    TextField(name = "Nom", data = product.name, onValueChange = onNameChange)
                    TextField(name = "UrlImage", data = product.imageUrl, onValueChange = onImageUrlChange)
                    TextField(name = "Description", data = product.description, onValueChange = onDescriptionChange)
                    TextField(name = "Prix", data = product.price.toString(), onValueChange = onPriceChange)
                    TextField(name = "Quantité", data = product.quantity.toString(), onValueChange = onQuantityChange)
                        }

                    }
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun Preview() {
    UpdateMainView(idCategory = 1, idProduct = 1, goToPreviousActivity = { /*TODO*/ }) {

    }
}