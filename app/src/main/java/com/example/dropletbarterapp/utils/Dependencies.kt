package com.example.dropletbarterapp.utils

import android.content.Context
import android.location.Geocoder
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.auth.AuthRepository
import com.example.dropletbarterapp.auth.RuntimeAuthRepository
import com.example.dropletbarterapp.mainscreens.advertisements.api.ads.AdvertisementRepository
import com.example.dropletbarterapp.mainscreens.advertisements.api.ads.RuntimeAdvertisementRepository
import com.example.dropletbarterapp.mainscreens.profile.api.query.RuntimeQueryRepository
import com.example.dropletbarterapp.mainscreens.advertisements.api.favourites.FavouritesRepository
import com.example.dropletbarterapp.mainscreens.advertisements.api.favourites.RuntimeFavouritesRepository
import com.example.dropletbarterapp.mainscreens.advertisements.api.purchases.PurchasesRepository
import com.example.dropletbarterapp.mainscreens.advertisements.api.purchases.RuntimePurchasesRepository
import com.example.dropletbarterapp.mainscreens.advertisements.api.shared.RuntimeSharedUsagesRepository
import com.example.dropletbarterapp.mainscreens.advertisements.api.shared.SharedUsageRepository
import com.example.dropletbarterapp.mainscreens.profile.api.query.QueryRepository
import com.example.dropletbarterapp.mainscreens.profile.api.user.RuntimeUserRepository
import com.example.dropletbarterapp.mainscreens.profile.api.user.UserRepository
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object Dependencies {

    private lateinit var applicationContext: Context
    lateinit var geocoder: Geocoder

    fun initDependencies(context: Context) {
        applicationContext = context
        geocoder = Geocoder(applicationContext, Locale("ru", "RU"))
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(applicationContext.getString(R.string.api_url))
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
    }

    val authRepository: AuthRepository by lazy { RuntimeAuthRepository(retrofit) }
    val userRepository: UserRepository by lazy { RuntimeUserRepository(retrofit) }
    val advertisementRepository: AdvertisementRepository by lazy {
        RuntimeAdvertisementRepository(
            retrofit
        )
    }
    val queryRepository: QueryRepository by lazy { RuntimeQueryRepository(retrofit) }
    val favouritesRepository: FavouritesRepository by lazy { RuntimeFavouritesRepository(retrofit) }
    val purchasesRepository: PurchasesRepository by lazy { RuntimePurchasesRepository(retrofit) }
    val sharedUsageRepository: SharedUsageRepository by lazy {
        RuntimeSharedUsagesRepository(
            retrofit
        )
    }
    val tokenService: TokenService by lazy { TokenSharedPreferencesService(applicationContext) }
}