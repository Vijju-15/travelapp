/*package com.example.travel

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class fragment_2 : Fragment() {

    private val REQUEST_CODE_PERMISSIONS = 100
    private val REQUEST_CODE_TAKE_PICTURE = 101
    private val REQUEST_CODE_PICK_IMAGE = 102

    private lateinit var photoFile: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_2, container, false)

        // Find the floating action button
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add_image_button)

        // Set a click listener for the button
        fab.setOnClickListener {
            // Request permissions
            val permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE_PERMISSIONS)
            // Open the gallery
            openGallery()
            // Save the image file


        }

        return view
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted(grantResults)) {
                // Permissions granted, open gallery
                openGallery()
            } else {
                // Permissions denied, show a message to the user
                Toast.makeText(requireContext(), "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun allPermissionsGranted(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun openGallery() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhotoIntent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
            // The image has been captured and saved to the photoFile
            // You can now access the image file and use it as needed
            // For example, if you need to display the captured image in an ImageView:
            val takenImageBitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
            // Now you can use takenImageBitmap to set it in an ImageView or any other view
        } else if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            // Image selected from gallery
            val selectedImageUri: Uri? = data?.data
            // Do something with the selected image URI
            // For example, if you need to display the selected image in an ImageView:
            val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri!!)
            val selectedImageBitmap = BitmapFactory.decodeStream(inputStream)
            // Now you can use selectedImageBitmap to set it in an ImageView or any other view
        }
    }
}
*/