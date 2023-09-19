package com.example.earthsustain

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
import com.google.android.material.navigation.NavigationView

class EventActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawerLayout: DrawerLayout
    lateinit var fragmentManager : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_navigation)

        createNavigation(savedInstanceState)

    }

    private fun createNavigation(savedInstanceState: Bundle?){
        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)


        val toggle = ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.open, R.string.close)
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
                showToast("Home Clicked")
                openFragment(HomeFragment()) }
            R.id.nav_logout -> {
                showToast("Logout Clicked")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_profile -> {
                showToast("Profile Clicked")
                openFragment(ProfileFragment())
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