package com.vlavik.urlshortener.service

import com.vlavik.urlshortener.model.Link
import com.vlavik.urlshortener.model.repository.LinkRepository
import com.vlavik.urlshortener.service.impl.DefaultKeyMapperService
import com.vlavik.urlshortener.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class DefaultKeyMapperServiceTest {

    @InjectMocks
    private val service: KeyMapperService = DefaultKeyMapperService()

    @Mock
    lateinit var converter: KeyConverterService

    @Mock
    lateinit var repository: LinkRepository

    private val KEY: String = "aAbBcCdD"

    private val KEY_A: String = "abc"
    private val LINK_A: String = "https://www.google.com"
    private val ID_A: Long = 10_000_000L
    private val LINK_OBJ_A: Link = Link(LINK_A, ID_A)

    private val KEY_B: String = "cde"
    private val LINK_B: String = "https://vk.com"
    private val ID_B: Long = 10_000_001L
    private val LINK_OBJ_B: Link = Link(LINK_B, ID_B)

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(converter.keyToId(KEY_A)).thenReturn(ID_A)
        Mockito.`when`(converter.idToKey(ID_A)).thenReturn(KEY_A)
        Mockito.`when`(converter.keyToId(KEY_B)).thenReturn(ID_B)
        Mockito.`when`(converter.idToKey(ID_B)).thenReturn(KEY_B)

        whenever(repository.findById(Mockito.any())).thenReturn(Optional.empty())
        whenever(repository.save(Link(LINK_A))).thenReturn(LINK_OBJ_A)
        whenever(repository.save(Link(LINK_B))).thenReturn(LINK_OBJ_B)
        whenever(repository.findById(ID_A)).thenReturn(Optional.of(LINK_OBJ_A))
        whenever(repository.findById(ID_B)).thenReturn(Optional.of(LINK_OBJ_B))
    }

    @Test
    fun clientCanAddLink() {
        val keyA = service.add(LINK_A)
        assertEquals(KeyMapperService.Get.Link(LINK_A), service.getLink(keyA))

        val keyB = service.add(LINK_B)
        assertEquals(KeyMapperService.Get.Link(LINK_B), service.getLink(keyB))

        assertNotEquals(keyA, keyB)
    }

    @Test
    fun clientCanNotTakeLinkIfKeyIsNotFoundInService() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }

}