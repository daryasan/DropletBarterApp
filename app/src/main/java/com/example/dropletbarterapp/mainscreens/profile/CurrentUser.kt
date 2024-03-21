//package com.example.dropletbarterapp.profile
//
//import com.auth0.android.jwt.JWT
//import com.example.dropletbarterapp.utils.Dependencies
//import com.example.dropletbarterapp.models.User
//import kotlinx.coroutines.*
//import retrofit2.HttpException
//import kotlin.coroutines.CoroutineContext
//
//object CurrentUser : CoroutineScope {
//
//    var user: User? = null
//
//    fun initUser() {
//        runBlocking {
//            launch {
//                getUser()
//            }
//        }
//    }
//
//    fun clear() {
//        user = null
//    }
//
//    private suspend fun getUser() {
//        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
//        try {
//            user = Dependencies.userRepository.findUserById(
//                Dependencies.tokenService.getAccessToken().toString(),
//                jwt.getClaim("id").asString()?.toLong() ?: -1
//            )
//        } catch (e: HttpException) {
//            throw e
//        }
//
//    }
//
//    private var job: Job = Job()
//
//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Default + job
//
//
//}