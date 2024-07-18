package mg.geit.marciajennifer

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import mg.geit.marciajennifer.ui.theme.MarciaJenniferTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarciaJenniferTheme {
                CategoryView(
                    seeCategory = { category ->
                        val intent = Intent(this, ProductActivity::class.java)
                            .apply { putExtra("idCategory", category.id) }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

//Composable principale de l' Activité
@Composable
fun CategoryView(seeCategory: (ProductCategory) -> Unit) {
    Scaffold(
        topBar = {
            CategoryHeader("Liste des catégories")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Veuillez cliquer sur l'un des cards",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )

                ShowCategoryList(paddingValues = PaddingValues(8.dp), seeCategory)
            }
        }
    }
}
// Header de l'activité
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryHeader(value: String){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            Color.LightGray,
            titleContentColor = Color.White
        ),
        title = {
            Text(text = value)
        },
    )
}

//Affichage d'une catégorie
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCategoryItem(category: ProductCategory, seeCategory: (ProductCategory) -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 250.dp, height = 250.dp)
            .padding(5.dp)
    ) {
        OutlinedCard(
            onClick = { seeCategory(category) },
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black,

            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = category.imageUrl)
                        .apply {
                            crossfade(true)
                        }.build()
                )
                Image(
                    painter = painter,
                    contentDescription = "CategoryLogo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                )
                Text(
                    text = category.name,
                    fontSize = 15.sp)
            }
        }
    }
}
//Affichage de la liste de catégorie
@Composable
fun ShowCategoryList(paddingValues: PaddingValues, seeCategory: (ProductCategory) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = paddingValues,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxHeight()
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        items(listCategory) {
                category->
            run {
                ShowCategoryItem(category , seeCategory)
                }
            }
        }
    }



@Preview(showBackground = true)
@Composable
fun ShowCategoryPreview() {
    MarciaJenniferTheme {
        CategoryView(seeCategory = {})
    }
}

