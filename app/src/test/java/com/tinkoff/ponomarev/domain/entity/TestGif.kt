package com.tinkoff.ponomarev.domain.entity

import com.tinkoff.ponomarev.util.MockUtil
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

/**
 * Класс тестирования для класса-сущности [Gif]
 */
class TestGif {

    /**
     * Тестирование конвертации [Gif.gifURL] из http в https
     */
    @Test
    fun testConvertHttpFieldToHttps(){
        val gif = MockUtil.mockGif()
        assertEquals("https://gifurl.com",gif.gifURLHttps)
    }

    /**
     * Тестирование конвертации [Gif.gifURL] из http в https
     */
    @Test
    fun testConvertHttpFieldToHttpsNull(){
        val gif = MockUtil.mockGifWithNullUrl()
        assertNull(gif.gifURLHttps)
    }
}