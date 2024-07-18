package mg.geit.marciajennifer


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import mg.geit.marciajennifer.ui.theme.MarciaJenniferTheme

class ProductActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val categoryId = intent.getIntExtra("idCategory", 1)
        val productList: MutableList<Product> = when (categoryId) {
            1 -> sweetProductList
            2 -> saltedProductList
            else -> mutableListOf()
        }
        setContent {
            MarciaJenniferTheme {
                ProductView(
                    productList,
                    seeProductDetails = { product ->
                        val intent = Intent(this, DetailsProductActivity::class.java)
                            .apply { putExtra("idProduct", product.id) }
                        startActivity(intent)
                    },
                    goToRegistrationActivity = {
                        val intent = Intent(this, RegistrationActivity:: class.java)
                            .apply { putExtra("idCategory", categoryId)}
                        startActivity(intent)
                        },
                    goToPreviousActivity = {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }

                )
            }
        }
    }
}
// Vue principal de l'Acrivité
@Composable
fun ProductView(
    productList: MutableList<Product>,
    seeProductDetails: (Product) -> Unit,
    goToPreviousActivity: () -> Unit,
    goToRegistrationActivity: () -> Unit,) {
    Scaffold(
        topBar = {
            Header("Food list",goToPreviousActivity)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    goToRegistrationActivity()
                },
                containerColor = Color.LightGray
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add new Product"
                )
            }
        }
    ) { innerPadding ->
        ShowProductList(paddingValues = innerPadding, productList, seeProductDetails)
    }
}


//HEADER DE PRODUCTACTIVITY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    value : String,
    goToPreviousActivity: () -> Unit,

) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            Color.LightGray,
            titleContentColor = Color.White
        ),
        title = {
            Text(text = value)
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    goToPreviousActivity()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Return to MainActivity",
                    modifier = Modifier.padding(end = 5.dp)
                )
            }
        }
    )
}
//Fonction pour afficher un produit
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowProductItem(product: Product, seeProductDetails: (Product) -> Unit) {
    Card(
        onClick = {
            seeProductDetails(product)
        },
        modifier = Modifier
            .size(60.dp)
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = product.name,
                fontSize = 20.sp,
                color = Color.Black)
        }
    }
}
// Fonction pour afficher une liste de produit
@Composable
fun ShowProductList(
    paddingValues: PaddingValues,
    productList: List<Product>,
    seeProductDetails: (Product) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = paddingValues,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxHeight()
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        items(productList) { product ->
            ShowProductItem(product, seeProductDetails)
        }
    }
}

@Preview
@Composable
fun ShowDetailsView()
{
    ProductView(
        productList = saltedProductList,
        seeProductDetails = {},
        goToPreviousActivity = {} ,
        goToRegistrationActivity = {}
    )
}
