package com.tinkoff.ponomarev.data.mapper

import com.tinkoff.ponomarev.util.MockUtil
import org.junit.Assert
import org.junit.Test

/**
 * Класс тестирования для класс [GifMapper] для конвертации сущностей
 */
class TestGifMapper {

    /**
     * Иницилизация тестируемого экземпляра
     */
    private val gifMapper: GifMapper = GifMapper()

    /**
     * Тестирование конвертации
     */
    @Test
    fun testFromGifBody(){
        Assert.assertEquals(MockUtil.mockGif(), gifMapper.fromGifBody(MockUtil.mockGifBody()))
    }
}