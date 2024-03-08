package com.example.dropletbarterapp.utils

import android.content.Context
import com.example.dropletbarterapp.auth.dto.TokenEntity

class TokenSharedPreferencesService(context: Context) : TokenService {

    private val sharedPreferences =
        context.getSharedPreferences(TOKEN_FILE_NAME, Context.MODE_PRIVATE)


    override fun setTokens(tokens: TokenEntity) {
        val editor = sharedPreferences.edit()
        editor.putString(ACCESS_TOKEN, tokens.accessToken)
        editor.putString(REFRESH_TOKEN, tokens.refreshToken)
        editor.apply()
    }

    override fun killTokens() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    override fun getAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN, null)
    override fun getRefreshToken(): String? = sharedPreferences.getString(REFRESH_TOKEN, null)

    companion object {
        private const val TOKEN_FILE_NAME = "token_file"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}