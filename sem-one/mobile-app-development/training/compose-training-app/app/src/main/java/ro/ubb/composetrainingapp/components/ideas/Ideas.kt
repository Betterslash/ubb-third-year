package ro.ubb.composetrainingapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ro.ubb.composetrainingapp.model.Idea
import ro.ubb.composetrainingapp.model.MockDatasource

@Composable
fun Ideas(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ideas",
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("ideaEdit") }) {
                Text(text = "Add Idea")
            }
        }
    ){
        Column{
            LazyColumn {
                items(MockDatasource.ideas){
                    Idea(idea = it, navController = navController)
                }
            }
        }
    }

}

@Composable
fun Idea(idea: Idea, navController: NavController){
    Card(modifier = Modifier
        .padding(8.dp)
        .height(50.dp)
        .width(180.dp)
        .clickable {
            navController.navigate("ideaEdit/${idea.id}")
        })
    {
        Row {
            Text(text = idea.id + " " + idea.title)
        }
    }
}