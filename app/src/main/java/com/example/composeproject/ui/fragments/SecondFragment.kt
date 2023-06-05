package com.example.composeproject.ui.fragments

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.composeproject.R
import com.example.composeproject.ui.customComponents.CustomButton
import com.example.composeproject.ui.customComponents.CustomText
import com.example.composeproject.ui.customComponents.CustomTextField
import com.example.composeproject.viewModel.SecondFragmentViewModel
import com.example.composeproject.viewModel.SharedViewModel

@Composable
fun SecondScreen(
    navController: NavController,
    viewModel: SharedViewModel,
    secondFragmentViewModel: SecondFragmentViewModel,
    modifier: Modifier,
    orientation: Int
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (title, editText, button) = createRefs()

        CustomText(text = stringResource(id = R.string.message_receiver_fragment),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 60.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(editText.top)
            }
        )

        val message = viewModel.message.collectAsState().value
        val focusRequester = remember { FocusRequester() }
//        if (!viewModel.firstFragmentFocus) focusRequester.requestFocus()
        CustomTextField(text = message,
            modifier = Modifier
                .constrainAs(editText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom, margin = 50.dp)
                }
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) viewModel.firstFragmentFocus = false
                },
            onValueChange = viewModel::setSecondFragmentMessage
        )


        LaunchedEffect(Unit) {
            if (!viewModel.firstFragmentFocus) focusRequester.requestFocus()
        }


        CustomButton(text = stringResource(id = R.string.send_data_and_switch_to_the_sender_fragment),
            modifier =  Modifier.constrainAs(button) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(editText.bottom, margin = 40.dp)
            },
            onClick = {
                viewModel.sendMessageToFirstFragment()
                if (orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    navController.navigate("first_screen")
                }
            })

    }
}