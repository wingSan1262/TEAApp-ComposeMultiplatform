package base.infra

import base.base_entity.TeaRemoteResponse
import data.local.LocalPreferenceImpl.Companion.BEARER_TOKEN
import getPreferenceManager
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.ParametersBuilder
import io.ktor.http.contentType

suspend inline fun <reified K : TeaRemoteResponse<*>> HttpClient.doGetTeaWithField(
    url : String,
    crossinline setYourQuery : (ParametersBuilder)->Unit
) : K {
    return doGetWithField(url, setYourQuery)
}

suspend inline fun <reified K> HttpClient.doGetWithField(
    url : String,
    crossinline setYourQuery : (ParametersBuilder)->Unit = {}
) : K {
    try {
        get(url){
            url { setYourQuery(this.parameters) }
            setTeaAppHeader()
        }.let {
            return it.body()
        }
    } catch (error : Throwable){
        Napier.d("Error GET $error")
        throw error
    }
}

suspend inline fun <reified T : TeaRemoteResponse<*>, reified Body> HttpClient.doTeaPost(
    url: String,
    body: Body?
): T {
    return doPost(url, body)
}

suspend inline fun <reified T, reified Body> HttpClient.doPost(
    url: String,
    body: Body?
): T {
    try {
        post(url) {
            contentType(ContentType.Application.Json)
            setTeaAppHeader()
            body?.let {
                setBody(it)
            }
        }.let { return it.body() }
    } catch (error: Throwable) {
        Napier.d("Error POST $error")
        throw error
    }
}

suspend inline fun <reified T : TeaRemoteResponse<*>, reified Body> HttpClient.doTeaPut(
    url: String,
    body: Body?
): T {
    return doPut(url, body)
}

suspend inline fun <reified T, reified Body> HttpClient.doPut(
    url: String,
    body: Body?
): T {
    try {
        put(url) {
            contentType(ContentType.Application.Json)
            setTeaAppHeader()
            body?.let {
                setBody(it)
            }
        }.let { return it.body() }
    } catch (error: Throwable) {
        Napier.d("Error POST $error")
        throw error
    }
}


fun HttpRequestBuilder.setTeaAppHeader(){
    val preferenceManager = getPreferenceManager()
    header(HttpHeaders.Authorization, "Bearer ${
        preferenceManager.getString(BEARER_TOKEN, "tes")
    }")
}