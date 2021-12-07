package ro.ubb.composetrainingapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ro.ubb.composetrainingapp.model.Idea
import ro.ubb.composetrainingapp.model.MockDatasource

@Composable
fun IdeaEdit(navController: NavController, idea : Idea){
    var ideaStateTitle by remember {
        mutableStateOf(idea.title)
    }
    var ideaStateText by remember {
        mutableStateOf(idea.text)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = idea.title, fontWeight = FontWeight.Bold)
                }
            )
        },
        floatingActionButton = {FloatingActionButton(onClick = {navController.navigate("ideas"); idea.title = ideaStateTitle; idea.text=ideaStateText; if(idea.id==""){
            idea.id = MockDatasource.ideas.last().id.plus(1)
            MockDatasource.ideas.add(idea)
        } }) {
            Text(text = "Back")
        }},
        content = {
            Column {
                OutlinedTextField(modifier = Modifier.padding(5.dp), value = ideaStateTitle,
                    onValueChange = {ideaStateTitle = it},
                    label = { Text("Title") })
                OutlinedTextField(modifier = Modifier.padding(5.dp), value = ideaStateText,
                    onValueChange = {ideaStateText = it},
                    label = { Text("Text") })
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    IdeaEdit(navController = rememberNavController(), idea = Idea("", "" ,""))
}