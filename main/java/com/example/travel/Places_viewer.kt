package com.example.travel

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException

class Places_viewer : AppCompatActivity() {
    private lateinit var main_image: ImageView
    private lateinit var image1: ImageButton
    private lateinit var image2: ImageButton
    private lateinit var image3: ImageButton
    private lateinit var image4: ImageButton
    private lateinit var description: TextView
    private lateinit var descriptionInput: EditText
    private lateinit var saveButton: Button
    private lateinit var folderId: String
    private val storageReference = FirebaseStorage.getInstance().reference
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer)

        // Initialize the views
        main_image = findViewById(R.id.main_image)
        image1 = findViewById(R.id.image1)
        image2 = findViewById(R.id.image2)
        image3 = findViewById(R.id.image3)
        image4 = findViewById(R.id.image4)
        description = findViewById(R.id.description)
        descriptionInput = findViewById(R.id.description_input)
        saveButton = findViewById(R.id.save_button)

        // Retrieve the folderId passed via intent
        folderId = intent.getStringExtra("folderId") ?: return

        // Load images from Firebase
        loadImagesFromFirebase()

        // Load description from Firestore
        loadDescription()

        // Save description on button click
        saveButton.setOnClickListener {
            saveDescription()
        }
    }

    private fun loadImagesFromFirebase() {
        val folderRef = storageReference.child(folderId)
        val imageButtons = listOf(image1, image2, image3, image4)

        folderRef.listAll()
            .addOnSuccessListener { result ->
                result.items.forEachIndexed { index, item ->
                    if (index < imageButtons.size) {
                        item.downloadUrl.addOnSuccessListener { uri ->
                            Glide.with(this).load(uri).into(imageButtons[index])
                            imageButtons[index].setOnClickListener {
                                Glide.with(this).load(uri).into(main_image)
                            }

                            // Set the first image as the main image by default
                            if (index == 0) {
                                Glide.with(this).load(uri).into(main_image)
                            }
                        }.addOnFailureListener { exception ->
                            handleFailure(exception)
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                handleFailure(exception)
            }
    }

    private fun loadDescription() {
        db.collection("descriptions").document(folderId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    description.text = document.getString("content")
                } else {
                    description.text = ""
                }
            }
            .addOnFailureListener { exception ->
                showToast("Failed to load description")
                Log.e("Places_viewer", "Firestore error", exception)
            }
    }

    private fun saveDescription() {
        val content = descriptionInput.text.toString()
        if (content.isNotEmpty()) {
            val descriptionData = hashMapOf("content" to content)
            db.collection("descriptions").document(folderId).set(descriptionData)
                .addOnSuccessListener {
                    showToast("Description saved")
                    description.text = content
                }
                .addOnFailureListener { exception ->
                    showToast("Failed to save description")
                    Log.e("Places_viewer", "Firestore error", exception)
                }
        } else {
            showToast("Description cannot be empty")
        }
    }

    private fun handleFailure(exception: Exception) {
        if (exception is StorageException) {
            when (exception.errorCode) {
                StorageException.ERROR_OBJECT_NOT_FOUND -> showToast("Image not found")
                StorageException.ERROR_BUCKET_NOT_FOUND -> showToast("Bucket not found")
                StorageException.ERROR_PROJECT_NOT_FOUND -> showToast("Project not found")
                StorageException.ERROR_QUOTA_EXCEEDED -> showToast("Quota exceeded")
                StorageException.ERROR_NOT_AUTHORIZED -> showToast("Not authorized to access")
                else -> showToast("An error occurred")
            }
        } else {
            showToast("An unexpected error occurred")
        }
        Log.e("Places_viewer", "Firebase error", exception)
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
