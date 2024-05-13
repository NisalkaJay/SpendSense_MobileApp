package com.example.spendsense

import android.widget.ImageView
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.spendsense.databinding.ActivityStartBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the GIF into the ImageView
        Glide.with(this)
            .asGif()
            .load(R.drawable.pig)
            .into(binding.gifImageView)

        // Delay redirection to MainActivity
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500) // 3000 milliseconds = 3 seconds delay
    }
}