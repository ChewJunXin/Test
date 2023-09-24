package com.example.earthsustain.activity

import android.content.Intent
import android.net.Uri
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
import com.example.earthsustain.fragment.CreateEventFragment
import com.example.earthsustain.fragment.DonateFragment
import com.example.earthsustain.fragment.EditEventFragment
import com.example.earthsustain.fragment.EventFragment
import com.example.earthsustain.fragment.HomeFragment
import com.example.earthsustain.fragment.JoinEventFragment
import com.example.earthsustain.fragment.LoginFragment
import com.example.earthsustain.fragment.ProgFragment
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

        val create = intent.getStringExtra("create")

        val edit = intent.getStringExtra("edit")

        val joined = intent.getStringExtra("joined")

        val joinevent = intent.getStringExtra("joinevent")


        // Check if data is not null
        if (programme != null || joinevent != null) {
            // Call the programme activity
            toolbar.title = "Programme"
            openFragment(ProgFragment())
        }

        if (create != null) {
            // Call the create event activity
            toolbar.title = "Create Event"
            openFragment(CreateEventFragment())
        }

        if (edit != null) {
            // Call the create event activity
            toolbar.title = "Edit Event"
            openFragment(EditEventFragment())
        }

        if (joined != null) {
            // Call the view joined events activity
            toolbar.title = "My Joined Events"
            openFragment(JoinEventFragment())
        }

        if (donation != null) {
            // Call the donation activity
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
            openFragment(EventFragment())
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
                contactFunction()
            }
            R.id.nav_email -> {
                showToast("Email Clicked")
                emailFunction()
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

    private fun contactFunction(){
        val phoneNumber = "01112345678"

        val formattedPhoneNumber = phoneNumber.replace(" ", "").replace("-", "")

        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$formattedPhoneNumber")

        startActivity(intent)

    }

    private fun emailFunction(){
        val emailAddress = "earthsustain2023@gmail.com"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$emailAddress")

        startActivity(intent)

    }
}