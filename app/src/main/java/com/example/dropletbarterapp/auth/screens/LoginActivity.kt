package com.example.dropletbarterapp.auth.screens

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.auth.dto.TokenEntity
import com.example.dropletbarterapp.databinding.ActivityLoginBinding
import com.example.dropletbarterapp.foryou.screens.MainActivity
import com.example.dropletbarterapp.validators.Toaster
import com.example.dropletbarterapp.validators.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class LoginActivity : AppCompatActivity(), CoroutineScope {

    private var byEmail = true
    private lateinit var validator: Validator
    private val toaster: Toaster = Toaster()
    private lateinit var binding: ActivityLoginBinding

    private var job: Job = Job()

    override val coroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Dependencies.initDependencies(this)
        validator = Validator(this)

        // click listeners
        binding.buttonRegister.setOnClickListener { view: View? ->
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            overridePendingTransition(0, 0)
        }
        binding.buttonLoginByEmail.setOnClickListener { view: View? ->
            binding.editTextLogin.hint = "Электронная почта"
            binding.editTextLogin.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            byEmail = true
        }
        binding.buttonLoginByPhone.setOnClickListener { view: View? ->
            binding.editTextLogin.hint = "Номер телефона"
            binding.editTextLogin.inputType = InputType.TYPE_CLASS_PHONE
            byEmail = false
        }

        // sign in
        binding.buttonLogin.setOnClickListener { view: View? ->
            launch {
                val ok = signIn()
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

    private suspend fun signIn(): Boolean {

        val password = binding.editTextPassword.text.toString()
        val login = binding.editTextLogin.text.toString()
        if (validator.validateLogin(login, byEmail) && validator.validatePassword(password)) {
            return try {
                val tokenEntity: TokenEntity = if (byEmail) {
                    Dependencies.authRepository.signInByEmail(login, password)
                } else {
                    Dependencies.authRepository.signInByPhone(login.toLong(), password)
                }
                Dependencies.tokenService.setTokens(tokenEntity)
                true
            } catch (e: HttpException) {
                toaster.getToast(this, "Неверный логин или пароль!")
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