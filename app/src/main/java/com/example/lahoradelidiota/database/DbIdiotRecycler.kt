package com.example.lahoradelidiota.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityDbIdiotRecyclerBinding

class DbIdiotRecycler : AppCompatActivity() {

    private lateinit var binding: ActivityDbIdiotRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDbIdiotRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Toolbar
        val dbtoolbar = binding.dbToolbar
        dbtoolbar.title = "Base de datos idiota"
        dbtoolbar.setNavigationIcon(R.drawable.baseline_login_24)
        dbtoolbar.setNavigationOnClickListener {

            startActivity(Intent(this, PantallaIdiota::class.java))
        }

    }

}