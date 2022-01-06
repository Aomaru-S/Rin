package com.rinats.rin.service

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.time.LocalDate

@Service
class GetHolidaysJpApiService {

    private inline fun <reified T: Any> typeRef(): ParameterizedTypeReference<T> = object: ParameterizedTypeReference<T>(){}

    fun getHolidaysJpApi(year: Int): Map<LocalDate, String>? {
        val uri = URI("https://holidays-jp.github.io/api/v1/${year}/date.json")
        val req = RequestEntity<Map<LocalDate, String>>(HttpMethod.GET, uri)
        val res = RestTemplate().exchange(req, typeRef<Map<LocalDate, String>>())
        return res.body
    }
}