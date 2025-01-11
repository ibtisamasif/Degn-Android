import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.degn.R
import com.example.degn.data.Screens
import com.example.degn.viewModels.authentication.AuthenticationViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: AuthenticationViewModel = koinViewModel(),
    navController: NavController
) {

    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f, animationSpec = tween(
                durationMillis = 1500, easing = FastOutSlowInEasing
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .alpha(alpha.value)
    ) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Splash Logo")
    }

    LaunchedEffect(true) {
        delay(2000)
        if(viewModel.isUserLogin()){
            navController.navigate(Screens.EmailScreen.route)
        }
        else navController.navigate(Screens.SliderScreen.route)
    }

}
