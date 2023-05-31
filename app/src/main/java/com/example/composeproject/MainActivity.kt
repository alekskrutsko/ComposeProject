package com.example.composeproject

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.ui.theme.ComposeProjectTheme
import com.example.composeproject.viewModel.SharedViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationView(viewModel)
                }
            }
        }
    }
}


@Composable
fun NavigationView(viewModel: SharedViewModel) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                val navController = rememberNavController()

                FirstScreen(navController = navController, viewModel = viewModel, Modifier)


                SecondScreen(viewModel = viewModel, Modifier)


            }
        }
        else ->{
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "first_screen"
            ) {
                composable("first_screen") {
                    FirstScreen(navController, viewModel, Modifier.fillMaxSize())
                }
                composable("second_screen") {
                    SecondScreen(viewModel, Modifier.fillMaxSize())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController, viewModel: SharedViewModel, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (title, editText, button) = createRefs()

        Text(
            text = stringResource(id = R.string.message_sender_fragment),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 60.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(editText.top)
            }
        )

        val message = viewModel.currentMessage.collectAsState().value
//        var message by remember { mutableStateOf("") }
        TextField(
            value = message,
            onValueChange = viewModel::setMessage,
            modifier = Modifier.constrainAs(editText) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(title.bottom, margin = 50.dp)
            }
        )

        Button(
            onClick = {
                viewModel.sendMessage()
                navController.navigate("second_screen")
                      },
            modifier = Modifier.constrainAs(button) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(editText.bottom, margin = 40.dp)
            }
        ) {
            Text(text = stringResource(id = R.string.switch_to_the_receiver_fragment))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(viewModel: SharedViewModel, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (title, editText, button) = createRefs()

        Text(
            text = stringResource(id = R.string.message_receiver_fragment),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 60.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(editText.top)
            }
        )

        val message = viewModel.message.collectAsState().value
        TextField(
            value = message,
            onValueChange = viewModel::setMessage,
            modifier = Modifier.constrainAs(editText) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(title.bottom, margin = 50.dp)
            }
        )

//        Button(
//            onClick = {
//                      },
//            modifier = Modifier.constrainAs(button) {
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//                top.linkTo(editText.bottom, margin = 40.dp)
//            }
//        ) {
//            Text(text = stringResource(id = R.string.update_viewmodel_data))
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeProjectTheme {
//        SecondScreen(rememberNavController(), viewModel)
    }
}