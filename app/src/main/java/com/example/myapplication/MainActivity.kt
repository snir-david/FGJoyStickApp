package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MainActivity", "app Started")
        val vm = ViewModelProvider(this).get(ViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main
        ).apply {
        this.lifecycleOwner = this@MainActivity
        this.viewModel = vm
        }
        vm.binding = binding
    }

}