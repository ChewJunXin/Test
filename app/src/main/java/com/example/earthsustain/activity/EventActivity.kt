package com.example.earthsustain.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.earthsustain.MainActivity
import com.example.earthsustain.fragment.ProfileFragment
import com.example.earthsustain.R
import com.example.earthsustain.fragment.DonateFragment
import com.example.earthsustain.fragment.EditProfileFragment
import com.example.earthsustain.fragment.EventFragment
import com.example.earthsustain.fragment.HomeFragment
import com.example.earthsustain.fragment.LoginFragment
import com.example.earthsustain.fragment.PasswordFragment
import com.example.earthsustain.fragment.RecoverPasswordFragment
import com.example.earthsustain.fragment.SignupFragment
import com.google.android.material.navigation.NavigationView

class EventActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawerLayout: DrawerLayout
    lateinit var fragmentManager : FragmentManager
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_navigation)

        //Show the navigation bar
        createNavigation(savedInstanceState)

        // Retrieve the data from fragment to perform specific tasks
        val programme = intent.getStringExtra("programme")

        val donation = intent.getStringExtra("donation")

        // Check if data is not null
        if (programme != null) {
            // Call the event function
            toolbar.title = "Programme"
        }

        if (donation != null) {
            // Call the donation function
            toolbar.title = "Donation"
            openFragment(DonateFragment())
        }
    }

    private fun createNavigation(savedInstanceState: Bundle?){
        drawerLayout = findViewById(R.id.drawer_layout)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "Event"

        val navigationView = findViewById<NavigationView>(R.id.nav_view)


        val toggle = ActionBarDrawerToggle(this,drawerLayout, toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)


        if (savedInstanceState == null){
            fragmentManager = supportFragmentManager
            val fragmentToOpen = when {
                intent.getBooleanExtra("openProfile", false) -> ProfileFragment()
                intent.getBooleanExtra("openEditProfile", false) -> EditProfileFragment()
                intent.getBooleanExtra("openChangePassword", false) -> PasswordFragment()
                else -> HomeFragment()
            }
            openFragment(fragmentToOpen)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean{

        when(item.itemId){
            R.id.nav_home -> {
                showToast("Dashboard Clicked")
                openFragment(HomeFragment())
                toolbar.title = "DashBoard"
            }
            R.id.nav_logout -> {
                showToast("Logout Clicked")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_profile -> {
                showToast("Profile Clicked")
                openFragment(ProfileFragment())
                toolbar.title = "Profile"
            }
            R.id.nav_contact -> {
                showToast("Contact Clicked")
                openFragment(HomeFragment())
            }
            R.id.nav_email -> {
                showToast("Email Clicked")
                openFragment(HomeFragment())
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            getOnBackPressedDispatcher().onBackPressed()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun openFragment(fragment : androidx.fragment.app.Fragment){
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}