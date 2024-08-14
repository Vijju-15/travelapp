package com.example.travel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class home_page: Fragment() {


    private lateinit var mumbai: ImageButton
    private lateinit var hyderabad: ImageButton
    private lateinit var kerala: ImageButton
    private lateinit var delhi: ImageButton
    private lateinit var kolkata: ImageButton
    private lateinit var jammu: ImageButton
    private lateinit var vizag: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mumbai = view.findViewById(R.id.mumbai)
        hyderabad = view.findViewById(R.id.hyderabad)
        kerala = view.findViewById(R.id.kerala)
        delhi = view.findViewById(R.id.delhi)
        kolkata = view.findViewById(R.id.kolkata)
        jammu = view.findViewById(R.id.jammu)
        vizag = view.findViewById(R.id.vizag)

        mumbai.setOnClickListener { openViewerActivity("mumbai") }
        hyderabad.setOnClickListener { openViewerActivity("hyderabad") }
        kerala.setOnClickListener { openViewerActivity("kerala") }
        delhi.setOnClickListener { openViewerActivity("delhi") }
        kolkata.setOnClickListener { openViewerActivity("kolkata") }
        jammu.setOnClickListener { openViewerActivity("jammu") }
        vizag.setOnClickListener { openViewerActivity("vizag") }
    }

    private fun openViewerActivity(folderId: String) {
        val intent = Intent(activity, Places_viewer::class.java).apply {
            putExtra("folderId", folderId)
        }
        startActivity(intent)
    }
}
