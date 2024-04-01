//package com.example.dropletbarterapp.utils
//
//import com.example.dropletbarterapp.models.Advertisement
//import com.github.kittinunf.fuel.Fuel
//import com.github.kittinunf.fuel.core.FuelManager
//import com.github.kittinunf.fuel.core.Response
//import java.util.*
//
//
//object TagGenerator {
//
//    fun generateTags(advertisement: Advertisement): List<String> {
//        // Подготовка SpaCy
//        FuelManager.instance.basePath = "https://api.spacy.io/v3/text/extract-keywords"
//        val headers = mapOf("Authorization" to "Bearer YOUR_API_KEY")
//
//        // Обработка текста объявления
//        val text = advertisement.name + " " + advertisement.description
//
//        // Запрос к SpaCy
//        val request = Fuel.post().header(headers).body(text).responseObject<SpaCyResponse>()
//
//        // Получение ответа
//        val response: Response<SpaCyResponse> = request.await()
//
//        // Отбор ключевых слов
//        val keywords = response.body()?.tokens ?: emptyList()
//
//        return keywords
//    }
//
//
//}