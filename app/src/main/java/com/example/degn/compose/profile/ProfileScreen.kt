package com.example.degn.compose.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.degn.R
import com.example.degn.compose.utils.CircularProgress
import com.example.degn.compose.utils.CustomEmailField
import com.example.degn.compose.utils.Title
import com.example.degn.viewModels.profile.ProfileViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.imageUri.value = uri
        uri?.let {
            val file = viewModel.uriToFile(context, it)
            if (file != null) {
                // Convert the file to JPG format if necessary
                val jpgFile = viewModel.convertToJpgIfNecessary(context, file)
                val requestBody = jpgFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val multipartBody =
                    MultipartBody.Part.createFormData("file", jpgFile.name, requestBody)

                viewModel.updateProfilePicture(multipartBody)
            }
        }
    }


    LaunchedEffect(Unit) {
        viewModel.getProfile()
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 45.dp)
            ) {
                Title(title = "Profile") { onBackPress.invoke() }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .size(95.dp)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.imageUri.value != null) {
                    AsyncImage(
                        model = viewModel.imageUri.value,
                        contentDescription = "Selected Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else if (viewModel.image.value.isNotEmpty()) {
                    AsyncImage(
                        model = viewModel.image.value,
                        contentDescription = "Profile Picture from URL",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.edit_profile),
                        contentDescription = "Default Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            CustomEmailField(
                value = viewModel.username.value,
                onValueChange = { viewModel.username.value = it },
                placeholder = "Enter Username",
                onEnterPressed = { viewModel.updateUserProfile() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomEmailField(
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it },
                placeholder = "Enter email"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Member since ${viewModel.joinedAt}",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )
        }
        if (viewModel.isLoading.value) {
            CircularProgress()
        }
    }
}
