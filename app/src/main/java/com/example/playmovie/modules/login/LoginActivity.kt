package com.example.playmovie.modules.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.playmovie.R
import com.example.playmovie.utils.UserPreference

class LoginActivity : AppCompatActivity() {

    lateinit var userPreference : UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userPreference = UserPreference(this)
        val btn_login : Button = findViewById(R.id.btn_login)
        val et_username : EditText = findViewById(R.id.et_username)

        btn_login.setOnClickListener {
            if (et_username.text.isNullOrEmpty()) {
                et_username.error = "Please Input Username"
            } else {
                userPreference.setNamaUser(et_username.text.toString())
                userPreference.setStatusUser(true)

                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}