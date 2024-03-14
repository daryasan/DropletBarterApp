package com.example.dropletbarterapp.utils

import com.auth0.android.jwt.JWT

class JwtUtils {

    private val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())


}