package com.example.jonathan.cleanarchitecture.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.note.util.Screen
import com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.notes.NotesScreen
import com.example.jonathan.cleanarchitecture.notes.ui.theme.NotesAppTheme
import com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.note.NoteScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                NotesApp()
            }
        }
    }
}

@Composable
@ExperimentalAnimationApi
fun NotesApp() {
    Surface(color = MaterialTheme.colors.background) {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()
            val navigateToNote = fun(id: Int, argb: Int) {
                navController.navigate("${Screen.NOTE.route}?noteId=$id&noteColor=$argb")
            }
            NavHost(
                navController = navController,
                startDestination = Screen.NOTES.route
            ) {
                composable(route = Screen.NOTES.route) {
                    NotesScreen(
                        onAddClick = { navigateToNote(-1, -1) },
                        onNoteClick = { note -> navigateToNote(note.id ?: -1, note.color) }
                    )
                }
                composable(
                    route = "${Screen.NOTE.route}?noteId={noteId}&noteColor={noteColor}",
                    arguments = listOf(
                        navArgument(
                            name = "noteId"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(
                            name = "noteColor"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                    )
                ) {
                    val color = it.arguments?.getInt("noteColor") ?: -1
                    NoteScreen(
                        noteColor = color,
                        onNoteSaved = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesAppTheme {
        Greeting("Android")
    }
}