package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.models.Query
import com.example.dropletbarterapp.utils.Dependencies
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class QueriesViewModel : ViewModel() {

    fun getQueries(): MutableList<Query> {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        var queries: List<Query>
        try {
            runBlocking {
                queries = Dependencies.queryRepository.findQueryForUser(
                    Dependencies.tokenService.getAccessToken().toString(),
                    jwt.getClaim("id").asString()!!.toLong()
                )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                queries = Dependencies.queryRepository.findQueryForUser(
                    Dependencies.tokenService.getAccessToken().toString(),
                    jwt.getClaim("id").asString()!!.toLong()
                )
            }
        }


        return removeClosedQueries(queries)

    }

    private fun removeClosedQueries(queries: List<Query>): MutableList<Query> {
        val open = mutableListOf<Query>()
        for (q in queries) {
            if (q.status == 0) {
                open.add(q)
            }
        }
        return open.toSet().toMutableList()
    }


}