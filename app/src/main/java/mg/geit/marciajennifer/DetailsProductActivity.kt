package mg.geit.marciajennifer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import mg.geit.marciajennifer.ui.theme.MarciaJenniferTheme


class DetailsProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val id = intent.getIntExtra("idProduct", 1)
        val product = when {
            sweetProductList.any { it.id == id } -> sweetProductList.first { it.id == id }
            saltedProductList.any { it.id == id } -> saltedProductList.first { it.id == id }
            else -> null
        }
        val productList =  when (product?.idCategory) {
            1 -> sweetProductList
            2 -> saltedProductList
            else -> { mutableListOf()
            }
        }
        setContent {
            MarciaJenniferTheme {
                if (product != null) {
                    DetailsActivityView(
                        product = product,
                        goToUpdateActivity = {
                            val intent = Intent(this, UpdateActivity::class.java)
                                .apply{ putExtra("idProduct",product.id)}
                                .apply { putExtra("idCategory",product.idCategory) }
                            startActivity(intent)
                        },
                        actionOnProduct = {
                            productList.remove(product)
                            val intent = Intent(this, ProductActivity::class.java)
                            startActivity(intent)
                        },
                        goToPreviousActivity = {
                            val intent = Intent(this, ProductActivity::class.java)
                                .apply { putExtra("idCategory", product.idCategory)}
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}
//Composable principale de l'Activité
@Composable
fun DetailsActivityView(
    product: Product?,
    goToUpdateActivity: () -> Unit,
    actionOnProduct: () -> Unit,
    goToPreviousActivity : () -> Unit,
){
    Scaffold (
        topBar = {
            if (product != null) {
                Header(product.name,goToPreviousActivity)
            }
        },
    ){
            innerPadding ->
        if (product != null) {
            DisplayDetails(innerPadding = innerPadding, product ,goToUpdateActivity ,actionOnProduct)
        }
    }
}
//Affichage des informations sur le produit
@Composable
fun DisplayDetails(
    innerPadding: PaddingValues,
    product: Product?,
    goToUpdateActivity: () -> Unit,
    actionOnProduct: () -> Unit,
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(width = 350.dp, height = 600.dp)
                .padding((innerPadding))
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(1))
            {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp, 15.dp, 0.dp, 0.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Image du produit
                        val painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = product?.imageUrl)
                                .apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                    transformations(CircleCropTransformation())
                                }).build()
                        )
                        Image(
                            painter = painter,
                            contentDescription = "Image Product",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                        )
                        //Afficahe de ses informations
                        if (product != null) {
                            DisplayText(title = "-Identifiant: ", " ${product.id}")

                            DisplayText(title = "-Description: ", "${product.description}")

                            DisplayText(title = "-Quantité: ", " ${product.quantity}")

                            DisplayText(title = "-Prix: ", " ${product.price}$")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            //modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            //Bouton de modification
                            Column {
                                OutlinedButton(
                                    onClick = { goToUpdateActivity()},
                                    border = BorderStroke(1.dp, Color.Black),
                                    shape = RoundedCornerShape(15.dp),
                                    modifier = Modifier
                                        .padding(top = 20.dp,end=5.dp)
                                        .height(40.dp)
                                        .fillMaxHeight()
                                ) {
                                    Icon(
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = "Update product"
                                        )

                                    Text(
                                        text = "MODIFIER",
                                        fontSize = 13.sp
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                            }  //Bouton de supprésion
                                Column {
                                    OutlinedButton(
                                        onClick = { actionOnProduct()},
                                        border = BorderStroke(1.dp, Color.Black),
                                        shape = RoundedCornerShape(15.dp),
                                        modifier = Modifier
                                            .padding(top = 20.dp)
                                            .height(40.dp)
                                            .fillMaxHeight()
                                    ) {
                                        Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "Delete product"
                                            )
                                        Text(
                                            text = "SUPPRIMER",
                                            fontSize = 13.sp
                                        )
                                    }
                                }

                        }
                    }
                }
            }
        }
    }
}

//Affichage de texte
@Composable
fun DisplayText(title: String,
                data: String){
    Column(
        modifier = Modifier
            .padding(6.dp, 3.dp, 6.dp, 2.dp)
    ){
        Text(
            text = "$title : $data ",
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black,
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun DisplayDetailsPreview() {
    MarciaJenniferTheme {
        DetailsActivityView(
            goToUpdateActivity = {},
            goToPreviousActivity = {},
            product = Product.create(2,"Pizza 4 fromages",
                "https://www.cuisineaz.com/recettes/pizza-quatre-fromages-81578.aspx",
                "La pizza quatre fromages est un type de pizza dont la particularité est l'utilisation d'une combinaison de quatre sortes de fromages, généralement fondus ensemble. Plat populaire, c'est un classique des menus des pizzerias",
                8,
                499.99),
            actionOnProduct = {}
        )
    }
}

