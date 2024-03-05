package com.example.dropletbarterapp.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivityMainBinding
import com.example.dropletbarterapp.databinding.ActivityRegisterBinding
import com.example.dropletbarterapp.models.User
import com.example.dropletbarterapp.screens.MainActivity.Companion.currentUser
import com.example.dropletbarterapp.server.api.UserAuthRepository
import com.example.dropletbarterapp.validators.Toaster
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RegisterActivity : AppCompatActivity(), CoroutineScope {

    private var byEmail = true
    private val userAuthRepository = UserAuthRepository(
        Retrofit.Builder().baseUrl("http://10.193.60.168:8080/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
    )
    private var toaster = Toaster()

    //private val sharedPreferences =
    //applicationContext.getSharedPreferences(TOKEN_FILE_NAME, Context.MODE_PRIVATE)

    private lateinit var binding: ActivityRegisterBinding

//    var buttonByEmail: Button? = null
//    var register: Button? = null
//    var editTextLogin: EditText? = null
//    var buttonByPhone: Button? = null
//    var editTextPassword: EditText? = null
//    var editTextRepeatPassword: EditText? = null

    private var job: Job = Job()

    override val coroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(binding.root)

//        buttonByEmail = findViewById(R.id.buttonRegisterByEmail)
//        register = findViewById(R.id.buttonRegister)
//        editTextLogin = findViewById(R.id.editTextRegisterLogin)
//        buttonByPhone = findViewById(R.id.buttonRegisterByPhone)
//        editTextPassword = findViewById(R.id.editTextRegisterPassword)
//        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword)

        binding.buttonRegisterByEmail.setOnClickListener {
            binding.editTextRegisterLogin.hint = "Электронная почта"
            binding.editTextRegisterLogin.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            byEmail = true
        }
        binding.buttonRegisterByPhone.setOnClickListener {
            binding.editTextRegisterLogin.hint = "Номер телефона"
            binding.editTextRegisterLogin.inputType = InputType.TYPE_CLASS_PHONE
            byEmail = false
        }
        binding.buttonRegister.setOnClickListener {
            launch {
                val user = signUp()
                onResult(user)
            }

        }
    }

    fun onResult(user: User) {
        currentUser = user
        startActivity(Intent(applicationContext, MainActivity::class.java))
        overridePendingTransition(0, 0)
    }

    private suspend fun signUp(): User {

        val password = binding.editTextRegisterPassword.text.toString()
        if (byEmail) {
            val email = binding.editTextRegisterLogin.text.toString()
            val repeatPassword = binding.editTextRepeatPassword.text.toString()
            if (password != repeatPassword) {
                toaster.getToast(applicationContext, "Пароли не совпадают!")
            }
            if (toaster.checkNullsAndGetToast(
                    "Заполните все поля!",
                    this@RegisterActivity,
                    email,
                    password
                )
            ) return userAuthRepository.signUpByEmail(email, password)

        } else {
            val phone = binding.editTextRegisterLogin.text.toString().toLong()
            if (toaster.checkNullsAndGetToast(
                    "Заполните все поля!",
                    this@RegisterActivity,
                    phone.toString(),
                    password
                )
            ) return userAuthRepository.signUpByPhone(phone, password)
        }
        throw IOException("!")
    }

}