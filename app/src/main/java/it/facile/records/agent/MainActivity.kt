package it.facile.records.agent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import it.facile.records.agent.databinding.ActivityMainBinding

/**
 *  Main Activity. I stick with Google Single Activity pattern since with Navigation components I found to be a good solution.
 * */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setupNavigation() {
        val toolbar = binding.toolbar
        toolbar.title = getString(R.string.title)
        setSupportActionBar(toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(toolbar, navController)

    }
}
