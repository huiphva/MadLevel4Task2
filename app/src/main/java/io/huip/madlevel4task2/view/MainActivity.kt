package io.huip.madlevel4task2.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import io.huip.madlevel4task2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Rock, paper, scissors."

        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handles menu button clicks (top right of screen!)
    // https://developer.android.com/guide/topics/ui/menus
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnGameHistory -> {
                navController.navigate(R.id.action_gameFragment_to_gameHistoryFragment)
                return true
            }
            android.R.id.home -> {
                navController.navigate(R.id.action_gameHistoryFragment_to_gameFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}