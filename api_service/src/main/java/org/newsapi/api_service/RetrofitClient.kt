package org.newsapi.api_service

import android.content.Context
import com.ashokvarma.gander.Gander
import com.ashokvarma.gander.GanderInterceptor
import com.ashokvarma.gander.imdb.GanderIMDB
import org.newsapi.common.helper_class.ConnectivityInterceptor
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getClient(context: Context):Retrofit{
        Gander.setGanderStorage(GanderIMDB.getInstance())
       return Retrofit.Builder()
            .client(OkHttpClient().newBuilder().
            addInterceptor(ConnectivityInterceptor(context)).
                addInterceptor {
                    try {
                        it.proceed(it.request())
                    } catch (e:Exception){
                        Response.Builder().code(-2).body(
                            JsonObject().apply {
                                addProperty("error","failure_client")
                            }.asString.toResponseBody("application/json".toMediaType())
                        ).build()
                    }
                }.
            addInterceptor (
                GanderInterceptor(context).showNotification(true)
            ).build())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}