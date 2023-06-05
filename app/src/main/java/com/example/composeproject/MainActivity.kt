package com.example.composeproject

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.ui.fragments.FirstScreen
import com.example.composeproject.ui.fragments.SecondScreen
import com.example.composeproject.ui.theme.ComposeProjectTheme
import com.example.composeproject.viewModel.FirstFragmentViewModel
import com.example.composeproject.viewModel.SecondFragmentViewModel
import com.example.composeproject.viewModel.SharedViewModel
import com.google.accompanist.adaptive.FoldAwareConfiguration
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.calculateDisplayFeatures

class MainActivity : ComponentActivity() {
    private val viewModel: SharedViewModel by viewModels()
    private val firstFragmentViewModel: FirstFragmentViewModel by viewModels()
    private val secondFragmentViewModel: SecondFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    NavigationView(viewModel)
                    val orientation = LocalConfiguration.current.orientation
                    val navController = rememberNavController()
                    when (orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> {
                            TwoPane(
                                first = {
                                    FirstScreen(
                                        navController = navController,
                                        viewModel = viewModel,
                                        firstFragmentViewModel = firstFragmentViewModel,
                                        modifier = Modifier,
                                        orientation = orientation
                                    )
                                },
                                second = {
                                    SecondScreen(
                                        navController = navController,
                                        viewModel = viewModel,
                                        secondFragmentViewModel = secondFragmentViewModel,
                                        modifier = Modifier,
                                        orientation = orientation
                                    )
                                },
                                strategy = HorizontalTwoPaneStrategy(0.5f),
                                displayFeatures = calculateDisplayFeatures(activity = this@MainActivity),
                                modifier = Modifier.absoluteOffset(30.dp),
                                foldAwareConfiguration = FoldAwareConfiguration.HorizontalFoldsOnly,
                            )
                        }

                        else -> {
                            val startDestination = if (viewModel.firstFragmentFocus) "first_screen"
                            else "second_screen"
                            NavHost(
                                navController = navController,
                                startDestination = startDestination
                            ) {
                                composable("first_screen") {
                                    FirstScreen(
                                        navController = navController,
                                        viewModel = viewModel,
                                        firstFragmentViewModel = firstFragmentViewModel,
                                        modifier = Modifier.fillMaxSize(),
                                        orientation = orientation
                                    )
                                }
                                composable("second_screen") {
                                    SecondScreen(
                                        navController = navController,
                                        viewModel = viewModel,
                                        secondFragmentViewModel = secondFragmentViewModel,
                                        modifier = Modifier.fillMaxSize(),
                                        orientation = orientation
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