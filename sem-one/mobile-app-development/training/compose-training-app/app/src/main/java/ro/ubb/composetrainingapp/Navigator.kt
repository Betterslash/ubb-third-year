package ro.ubb.composetrainingapp

import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ro.ubb.composetrainingapp.components.auth.Login
import ro.ubb.composetrainingapp.model.MockDatasource

@Composable
fun Navigator(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("login"){
            Login(navController = navController)
        }
        composable("ideas") {
            Ideas(navController = navController)
        }
        composable("ideaEdit/{ideaId}",arguments = listOf(navArgument("ideaId") { type = NavType.StringType })){
                backStackEntry ->
            val ideaId : String? = backStackEntry.arguments?.getString("ideaId")
            if(ideaId != ""){
                IdeaEdit(navController = navController, idea = MockDatasource.ideas.first { e -> e.id== ideaId })
            }
        }
        composable("ideaEdit"){
            IdeaEdit(navController = navController, idea = ro.ubb.composetrainingapp.model.Idea("","",""))
        }
    }
}