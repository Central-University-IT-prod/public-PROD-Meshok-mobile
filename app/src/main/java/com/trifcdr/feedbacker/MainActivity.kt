package com.trifcdr.feedbacker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trifcdr.feedbacker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainActViewModel by viewModels()

    private lateinit var bottomNavigation: BottomNavigationView

    private val auth = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigation = binding.bottomNavigationView
        bottomNavigation.setOnItemSelectedListener { item ->
            lifecycleScope.launch {
                vm.isAuth()
                    .collect {
                        val id: Int = item.itemId
                        when (id) {
                            R.id.form -> {
                                try {
                                    if (it) this@MainActivity.findNavController(R.id.nav_host_fragment)
                                        .navigate(R.id.action_profileFargment_to_scanFragment)
                                    else this@MainActivity.findNavController(R.id.nav_host_fragment)
                                        .navigate(R.id.action_registerFragment_to_scanFragment)
                                } catch (_: Exception) {
                                    try {
                                        this@MainActivity.findNavController(R.id.nav_host_fragment)
                                            .navigate(R.id.action_authFragment_to_scanFragment)
                                    } catch (_: Exception) {
                                    }
                                }
                            }

                            R.id.profile ->
                                try {
                                    if (it) this@MainActivity.findNavController(R.id.nav_host_fragment)
                                        .navigate(R.id.action_scanFragment_to_profileFargment)
                                    else this@MainActivity.findNavController(R.id.nav_host_fragment)
                                        .navigate(R.id.action_scanFragment_to_registerFragment)
                                } catch (ignore: Exception) {
                                }
                        }
                    }

            }
            return@setOnItemSelectedListener true
        }

    }

}