package com.example.dropletbarterapp.screens

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.server.api.UserAuthRepository
import com.example.dropletbarterapp.validators.Toaster
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthActivity : AppCompatActivity() {

    private var byEmail = true
    private val toaster = Toaster()
    private val userAuthRepository = UserAuthRepository(
        Retrofit.Builder().baseUrl("http://10.193.63.5:8080")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // resources
        val buttonByEmail = findViewById<Button>(R.id.buttonLoginByEmail)
        val buttonByPhone = findViewById<Button>(R.id.buttonLoginByPhone)
        val login = findViewById<Button>(R.id.buttonLogin)
        val editTextLogin = findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val register = findViewById<Button>(R.id.buttonRegister)

        // click listeners
        register.setOnClickListener { view: View? ->
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            overridePendingTransition(0, 0)
        }
        buttonByEmail.setOnClickListener { view: View? ->
            editTextLogin.hint = "Электронная почта"
            editTextLogin.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            byEmail = true
        }
        buttonByPhone.setOnClickListener { view: View? ->
            editTextLogin.hint = "Номер телефона"
            editTextLogin.inputType = InputType.TYPE_CLASS_PHONE
            byEmail = false
        }
        login.setOnClickListener { view: View? ->
            if (byEmail) {
                val email = editTextLogin.text.toString()
                val password = editTextPassword.text.toString()
                if (toaster.checkNullsAndGetToast(
                        "Заполните все поля!",
                        this@AuthActivity,
                        email,
                        password
                    )
                ) {
                    //userAuthRepository.signInByEmail(email, password)
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                }
            } else {
                val phone = editTextLogin.text.toString().toLong()
                val password = editTextPassword.text.toString()
                if (toaster.checkNullsAndGetToast(
                        "Заполните все поля!",
                        this@AuthActivity,
                        phone.toString(),
                        password
                    )
                ) {
                    //MainActivity.currentUser = userAuthRepository.signInByPhone(phone, password);
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                }
            }
        }
    }


}