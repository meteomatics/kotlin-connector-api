package com.example.newkotlinconnector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.newkotlinconnector.databinding.ActivityMainBinding
import com.facebook.stetho.Stetho


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenCreated {
            ///// QUERIES SHOULD BE MADE HERE
            ///// SEE EXAMPLES
        }
    }
}

