package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcompose.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "first_screen") {
        composable("first_screen") {
            FirstScreen(onNavigateToSecondScreen = { navController.navigate("second_screen") })
        }
        composable("second_screen") {
            SecondScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(onNavigateToSecondScreen: () -> Unit) {

    val state = rememberLazyListState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Home Goods") })
            },
            bottomBar = { },
            floatingActionButton = { ScrollToTopButton(state) }
        ) { innerPadding ->

            DecorationsList(
                modifier = Modifier.padding(innerPadding),
                DecorationsList.list,
                state,
                onNavigateToSecondScreen
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(onNavigateBack: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Home Goods") })
            },
            bottomBar = { }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("CHECKOUT SCREEN")
                Button(onClick = onNavigateBack) {
                    Text("Back")
                }
            }
        }

    }
}

@Composable
fun DecorationsList(
    modifier: Modifier = Modifier,
    decorations: List<Decoration>,
    state: LazyListState,
    onNavigateToSecondScreen: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = state
    ) {
        item { Text(text = "Christmas Decorations") }
        items(decorations) { decoration ->
            DecorationItem(
                decoration.id,
                decoration.description,
                decoration.price,
                onNavigateToSecondScreen
            )
        }
    }
}

@Composable
fun DecorationItem(
    id: Int,
    decoration: String,
    price: Double,
    onNavigateToSecondScreen: () -> Unit
) {

    var count by rememberSaveable { mutableIntStateOf(0) }
    var isInCart by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary),
        shadowElevation = 8.dp,
        tonalElevation = 8.dp
    ) {
        Row {
            Image(
                painter = painterResource(id = id),
                contentDescription = "Any",
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .border(BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(decoration)
                Text("$ $price")
                if (!isInCart) {
                    ElevatedButton(
                        onClick = { isInCart = true },
                        // modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_shopping_cart),
                            contentDescription = "Add to cart"
                        )
                    }
                } else {
                    Row {
                        OutlinedButton(onClick = { if (count > 0) count-- else isInCart = false }) {
                            Text("-", color = MaterialTheme.colorScheme.onPrimary)
                        }
                        Text(text = count.toString(), modifier = Modifier.padding(10.dp))
                        OutlinedButton(onClick = { count++ }) {
                            Text("+", color = MaterialTheme.colorScheme.onPrimary)
                        }
                        ElevatedButton(
                            onClick = { if (count > 0) onNavigateToSecondScreen() },
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_shopping_cart_checkout),
                                contentDescription = "Checkout"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScrollToTopButton(state: LazyListState) {
    val coroutineScope = rememberCoroutineScope()

    FloatingActionButton(
        onClick = {
            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }
        },
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_upward),
            contentDescription = "Scroll to Top"
        )
    }
}


// add dark and light preview
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    //val image = painterResource(R.drawable.a)
    AppTheme {
//        DecorationsList(
//            listOf(
//                Decoration("first", R.drawable.a),
//                Decoration("second", R.drawable.b),
//                Decoration("third", R.drawable.c)
//            )
//        )
    }
}