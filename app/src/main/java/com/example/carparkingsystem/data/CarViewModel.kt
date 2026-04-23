package com.example.carparkingsystem.data

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carparkingsystem.models.CarModel
import com.example.carparkingsystem.navigation.ROUTE_DASHBOARD
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class CarViewModel : ViewModel() {

    private val cloudinaryUrl = "https://api.cloudinary.com/v1_1/djy683idb/image/upload"
    private val uploadPreset = "june_folder"

    private val _cars = mutableStateListOf<CarModel>()
    val cars: List<CarModel> = _cars

    // ── UPLOAD / ADD ─────────────────────────────────────────────────────────
    fun uploadCar(
        imageUri: Uri?,
        plateNumber: String,
        vehicleType: String,
        driverName: String,
        phoneNumber: String,
        entryTime: String,
        carColor: String,
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageUrl = imageUri?.let { uploadToCloudinary(context, it) }

                val ref = FirebaseDatabase.getInstance()
                    .getReference("Cars")
                    .push()

                val car = CarModel(
                    id = ref.key,
                    plateNumber = plateNumber,
                    vehicleType = vehicleType,
                    driverName = driverName,
                    phoneNumber = phoneNumber,
                    entryTime = entryTime,
                    carColor = carColor,
                    imageUrl = imageUrl
                )

                ref.setValue(car).await()

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Car saved successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_DASHBOARD)
                }
            } catch (e: Exception) {
                Log.e("UPLOAD_ERROR", e.message ?: "Unknown error")
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to save car", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // ── FETCH ─────────────────────────────────────────────────────────────────
    fun fetchCar(context: Context) {
        val ref = FirebaseDatabase.getInstance().getReference("Cars")
        ref.get().addOnSuccessListener { snapshot ->
            _cars.clear()
            for (child in snapshot.children) {
                val car = child.getValue(CarModel::class.java)
                car?.let {
                    it.id = child.key
                    _cars.add(it)
                }
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to load cars", Toast.LENGTH_LONG).show()
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    fun deleteCar(carId: String, context: Context) {
        val ref = FirebaseDatabase.getInstance()
            .getReference("Cars")
            .child(carId)

        ref.removeValue()
            .addOnSuccessListener {
                _cars.removeIf { it.id == carId }
                Toast.makeText(context, "Car deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to delete car", Toast.LENGTH_SHORT).show()
            }
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    fun updateCar(
        carId: String,
        plateNumber: String,
        vehicleType: String,
        driverName: String,
        phoneNumber: String,
        entryTime: String,
        carColor: String,
        imageUri: Uri?,
        existingImageUrl: String?,
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageUrl = if (imageUri != null) {
                    uploadToCloudinary(context, imageUri)
                } else {
                    existingImageUrl
                }

                val ref = FirebaseDatabase.getInstance()
                    .getReference("Cars")
                    .child(carId)

                val updatedCar = CarModel(
                    id = carId,
                    plateNumber = plateNumber,
                    vehicleType = vehicleType,
                    driverName = driverName,
                    phoneNumber = phoneNumber,
                    entryTime = entryTime,
                    carColor = carColor,
                    imageUrl = imageUrl
                )

                ref.setValue(updatedCar).await()

                val index = _cars.indexOfFirst { it.id == carId }
                if (index != -1) _cars[index] = updatedCar

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Car updated successfully", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            } catch (e: Exception) {
                Log.e("UPDATE_ERROR", e.message ?: "Unknown error")
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to update car", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // ── CLOUDINARY ────────────────────────────────────────────────────────────
    private fun uploadToCloudinary(context: Context, uri: Uri): String {
        val inputStream: InputStream =
            context.contentResolver.openInputStream(uri)
                ?: throw Exception("Cannot read image")

        val fileBytes = inputStream.readBytes()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                "image.jpg",
                fileBytes.toRequestBody("image/*".toMediaTypeOrNull())
            )
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder()
            .url(cloudinaryUrl)
            .post(requestBody)
            .build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw Exception("Image upload failed")

        val responseBody = response.body?.string()
        val imageUrl = Regex("\"secure_url\":\"(.*?)\"")
            .find(responseBody ?: "")
            ?.groupValues?.get(1)

        return imageUrl ?: throw Exception("No image URL returned")
    }
}
