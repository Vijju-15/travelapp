    package com.example.travel

    import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import androidx.navigation.NavController
    import androidx.navigation.fragment.NavHostFragment
    import androidx.navigation.ui.setupWithNavController
    import com.google.android.material.bottomnavigation.BottomNavigationView

    class Home : AppCompatActivity() {

        private lateinit var navController: NavController
        private lateinit var navigationView: BottomNavigationView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.main_page)

            // Navigation setup
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
            navController = navHostFragment.navController
            navigationView = findViewById(R.id.navigationView)
            navigationView.setupWithNavController(navController)


        }
    }
