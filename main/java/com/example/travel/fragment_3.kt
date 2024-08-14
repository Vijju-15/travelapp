package com.example.travel

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class fragment_3 : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var searchBar: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        searchBar = view.findViewById(R.id.searchBar)
        searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val placeName = searchBar.text.toString().trim()
                if (placeName.isNotEmpty()) {
                    searchForPlace(placeName)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add your map customization logic here
        val sydney = LatLng(-34.92873, 138.60077)
        mMap.addMarker(MarkerOptions().position(sydney).title("Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnZoomIn = view.findViewById<Button>(R.id.btnZoomIn)
        val btnZoomOut = view.findViewById<Button>(R.id.btnZoomOut)
        val btnOptions = view.findViewById<ImageButton>(R.id.btnOptions)

        btnZoomIn.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomIn())
        }

        btnZoomOut.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomOut())
        }

        btnOptions.setOnClickListener {
            showOptionsMenu()
        }
    }

    private fun showOptionsMenu() {
        val popupMenu = PopupMenu(requireContext(), view?.findViewById(R.id.btnOptions))
        popupMenu.menuInflater.inflate(R.menu.menu_map_options, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_normal -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                R.id.menu_satellite -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                R.id.menu_hybrid -> mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                R.id.menu_terrain -> mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
            true
        }

        popupMenu.show()
    }

    private fun searchForPlace(placeName: String) {
        val geoCoder = Geocoder(requireContext())
        try {
            val addresses = geoCoder.getFromLocationName(placeName, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    mMap.clear() // Clear existing markers
                    mMap.addMarker(MarkerOptions().position(latLng).title(placeName))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                } else {
                    Toast.makeText(requireContext(), "Place not found", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}


