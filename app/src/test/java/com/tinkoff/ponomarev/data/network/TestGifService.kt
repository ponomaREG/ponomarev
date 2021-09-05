package com.tinkoff.ponomarev.data.network

import com.tinkoff.ponomarev.data.ext.addJsonConverter
import com.tinkoff.ponomarev.util.MockUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.nio.charset.StandardCharsets

/**
 * Класс тестирования для класс-сервиса [GifService] по получению гиф-изображений
 */
class TestGifService {

    /**
     * Мокнутый веб-сервер, выдает json не из сети, а из файла
     */
    private lateinit var mockWebServer: MockWebServer

    /**
     * Сам сервис для тестирования
     */
    private lateinit var gifService: GifService

    /**
     * Первоначальная настройка
     */
    @ExperimentalSerializationApi
    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        gifService = createService(GifService::class.java)
    }

    /**
     * После тестов убиваем сервер
     */
    @After
    fun stop(){
        mockWebServer.shutdown()
    }

    /**
     * Тестирование получения случайного ниф-изображения
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetRandomGif(): Unit = runBlocking {
        enqueueResponse("/GifRandomResponse.json", emptyMap())
        val response = gifService.getRandomGif()
        mockWebServer.takeRequest()
        Assert.assertEquals(MockUtil.mockGifBody(), response)
    }

    /**
     * Тестирование получения гиф-изображений из определенной секции
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testGetGifsBySection(): Unit = runBlocking {
        enqueueResponse("/GifsBySectionResponse.json", emptyMap())
        val response = gifService.getGifsBySection("top", 0)
        mockWebServer.takeRequest()
        Assert.assertEquals(MockUtil.mockGifsResponse(), response)
    }

    /**
     * Создание сервиса
     * @param clazz - класс для создания
     */
    @ExperimentalSerializationApi
    fun <T> createService(clazz: Class<T>): T{
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addJsonConverter()
            .build()
            .create(clazz)
    }

    /**
     * Стави ожидаем ответы в очередь
     * @param filename - название файла json'а
     * @param headers - заголовки
     */
    private fun enqueueResponse(filename: String, headers: Map<String, String>){
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$filename")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for((key, value) in headers){
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }
}