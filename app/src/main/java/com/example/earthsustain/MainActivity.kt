package com.example.earthsustain

import android.app.Fragment
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.earthsustain.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var fragmentManager : FragmentManager
    lateinit var binding : ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(binding.root)


        createNavigation()

    }

    private fun createNavigation(){
        setSupportActionBar(binding.toolbar)

//        when(it.itemId){
//            R.id.nav_home -> showToast("Home")
//            R.id.nav_logout -> showToast("Logout")
//            R.id.nav_profile -> showToast("My Profile")
//            R.id.nav_contact -> showToast("Contact Us")
//            R.id.nav_email -> showToast("Email Us")
//
//        }

        val toggle = ActionBarDrawerToggle(this,binding.drawerLayout, binding.toolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)


        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())


    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.nav_home -> openFragment(HomeFragment())
            R.id.nav_logout -> openFragment(HomeFragment())
            R.id.nav_profile -> openFragment(ProfileFragment())
            R.id.nav_contact -> openFragment(HomeFragment())
            R.id.nav_email -> openFragment(HomeFragment())

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.getOnBackPressedDispatcher().onBackPressed()
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