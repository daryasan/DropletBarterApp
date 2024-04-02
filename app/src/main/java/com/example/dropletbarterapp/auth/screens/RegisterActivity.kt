package com.example.dropletbarterapp.auth.screens

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.databinding.ActivityRegisterBinding
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.auth.dto.TokenEntity
import com.example.dropletbarterapp.mainscreens.foryou.screens.MainActivity
import com.example.dropletbarterapp.validators.Toaster
import com.example.dropletbarterapp.validators.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class RegisterActivity : AppCompatActivity(), CoroutineScope {

    private var toaster = Toaster()
    private lateinit var validator: Validator
    private lateinit var binding: ActivityRegisterBinding

    private var job: Job = Job()

    override val coroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Dependencies.initDependencies(this)
        validator = Validator(this)

        binding.editTextRegisterLogin.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            overridePendingTransition(0, 0)
        }

        // sign up
        binding.buttonRegister.setOnClickListener {
            launch {
                val ok = signUp()
                onResult(ok)
            }

        }
    }

    private fun onResult(isSuccessful: Boolean) {
        if (isSuccessful) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(0, 0)
        }
    }

    private suspend fun signUp(): Boolean {

        val password = binding.editTextRegisterPassword.text.toString()
        val login = binding.editTextRegisterLogin.text.toString()
        val firstName = binding.editTextRegisterName.text.toString()
        val lastName = binding.editTextRegisterLastName.text.toString()
        val phone = binding.editTextRegisterPhone.text.toString().toLong()

        val repeatPassword = binding.editTextRepeatPassword.text.toString()
        if (password != repeatPassword) {
            toaster.getToast(applicationContext, "Пароли не совпадают!")
            return false
        }


        if (validator.validateLogin(login, true) && validator.validatePassword(password) &&
            lastName != "" && firstName != "" && phone != 0L
        ) {
            return try {
                val tokenEntity: TokenEntity =
                    Dependencies.authRepository.signUpByEmail(
                        login,
                        password,
                        firstName,
                        lastName,
                        phone
                    )
                Dependencies.tokenService.setTokens(tokenEntity)
                true
            } catch (e: HttpException) {
                toaster.getToast(this, "Вы уже зарегистрированы! Пожалуйста, войдите!")
                false
            } catch (e: SocketTimeoutException) {
                toaster.getToast(
                    this,
                    "Проверьте ваше подключение к интернету!"
                )
                false
            } catch (e: Exception) {
                toaster.getToast(this, "Что-то пошло не так!")
                false
            }
        }
        toaster.getToastWrongLogin(this)
        return false
    }

}