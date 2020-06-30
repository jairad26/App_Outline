package com.example.coronavirusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class Clothing : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val mToolbar = findViewById<Toolbar>(R.id.main_page_toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar?.title = "Clothing"

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawable_layout)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { item ->
            userMenuSelector(item)
            false
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawable_layout)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun userMenuSelector(item: MenuItem)
    {
        when(item.itemId)
        {
            R.id.nav_home_menu ->
            {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
            R.id.nav_food ->
            {
                startActivity(Intent(applicationContext, Food::class.java))
                finish()
            }
            R.id.nav_clothing ->
            {
                startActivity(Intent(applicationContext, Clothing::class.java))
                finish()
            }
            R.id.nav_home_necessities ->
            {
                startActivity(Intent(applicationContext, HomeNecessities::class.java))
                finish()
            }
            R.id.nav_medical_equipment ->
            {
                startActivity(Intent(applicationContext, MedicalEquipment::class.java))
                finish()
            }
            R.id.nav_logout ->
            {
                FirebaseAuth.getInstance().signOut()//logout
                startActivity(Intent(applicationContext, Login::class.java))
                finish()
            }
        }
    }

}
