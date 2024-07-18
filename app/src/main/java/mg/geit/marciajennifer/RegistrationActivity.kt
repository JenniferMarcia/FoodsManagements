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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mg.geit.marciajennifer.ui.theme.MarciaJenniferTheme

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idCategory = intent.getIntExtra("idCategory", 1)

        setContent {
            MarciaJenniferTheme {
                RegistrationMainView(idCategory) {
                    val intent = Intent(this, ProductActivity::class.java)
                        .apply { putExtra("idCategory", idCategory) }
                    startActivity(intent)
                }
            }
        }
    }
}
//Vue principale de l'Activité
@Composable
fun RegistrationMainView(idCategory: Int, seeUpdateList:() -> Unit) {
    val productList = when (idCategory) {
        1 -> sweetProductList
        2 -> saltedProductList
        else -> mutableListOf()
    }
    val product = productList.find { it.idCategory == idCategory } ?: return

    var nameProduct by remember { mutableStateOf(product.name) }
    var imageUrl by remember { mutableStateOf(product.imageUrl) }
    var descriptionProduct by remember { mutableStateOf(product.description) }
    var priceProduct by remember { mutableStateOf(product.price) }
    var quantityProduct by remember { mutableStateOf(product.quantity) }

    Scaffold(
        topBar = {
            CategoryHeader("Création de nouveau Produit")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val newProduct = Product.create(idCategory,nameProduct,imageUrl,descriptionProduct,quantityProduct,priceProduct)
                    productList.add(newProduct)
                    seeUpdateList()
                   }

                ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Product")
            }
        }
    ) {
            innerPadding ->
        DisplayFields(
            paddingValues = innerPadding,
            onNameChange = { nameProduct = it },
            onImageUrlChange = { imageUrl = it },
            onDescriptionChange = { descriptionProduct = it },
            onQuantityChange = { quantityProduct = it.toInt() },
            onPriceChange = { priceProduct = it.toDouble() }
        )
    }
}
// Formulaire d'ajout
@Composable
fun DisplayFields(
    paddingValues: PaddingValues,
    onNameChange: (String) -> Unit,
    onImageUrlChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onPriceChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 30.dp, 0.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(width = 350.dp, height = 550.dp)
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                item {
                    Column {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(30.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Veuillez remplir les champs",
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        TextField(name = "Nom du produit ", data = "", onValueChange = onNameChange)
                        TextField(name = "ImageUrl ", data = "", onValueChange = onImageUrlChange)
                        TextField(name = "Description ", data = "", onValueChange = onDescriptionChange)
                        TextField(name = "Quantite du produit", data = "", onValueChange = onQuantityChange)
                        TextField(name = "Prix du produit ", data = "", onValueChange = onPriceChange)
                    }
                }
            }
        }
    }
}
//Champ à afficher
@Composable
fun TextField(name: String,
              data: String,
              onValueChange: (String) -> Unit
){
    Column(
        modifier = Modifier
            .padding(16.dp, 5.dp, 0.dp, 0.dp)
            .fillMaxSize()
    ){
        TextFieldWithIcon(name, data, onValueChange)
    }
}
//Label des champs
@Composable
fun TextFieldWithIcon(
    name: String,
    data: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue(data)) }
    OutlinedTextField(
        value = text,
        textStyle = LocalTextStyle.current,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "EditIcon",
                tint = Color.Black
            )
        },
        onValueChange = {
            text = it
            onValueChange(it.text)
        },
        modifier = Modifier.width(320.dp),
        label = {
            Text(
                text = name,
                fontWeight = FontWeight.Bold
            )
        },
        colors = OutlinedTextFieldDefaults.colors(Color.Black)
    )
}

@Preview(showBackground = true)
@Composable
fun ShowPreview(){
    RegistrationMainView(
        idCategory = 1,
        seeUpdateList= {}
    )
}