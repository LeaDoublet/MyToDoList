package com.example.to_do_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_list.databinding.ActivityUpdateTaskBinding

class UpdateActivity : AppCompatActivity(){

    private lateinit var binding: ActivityUpdateTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}