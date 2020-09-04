package com.example.core.data

import com.example.core.di.mockFirebaseFirestoreModule
import com.example.core.di.repositoryModule
import com.example.core.domain.model.Book
import com.example.core.domain.repository.IBookRepository
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class BookRepositoryTest:KoinTest {

    private val repository: IBookRepository by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        // Your KoinApplication instance here
        modules(listOf(
            mockFirebaseFirestoreModule,
            repositoryModule
        ))
    }

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }


    @Test
    fun getAllBooksSuccess()= runBlockingTest {
        assertNotNull(repository)
        val books = listOf(
            Book("sd","sdsd",0.0,"kkk","99","190299","kkkkk",1),
            Book("sd","sdsd",0.0,"kkk","99","190299","kkkkk",2)
        )

       val result =  repository.getAllBooks().single()
        when(result){
        is Response.Success -> {
            print(result.value)
        }
        is Response.Loading ->{
            print("loading")
        }
        is Response.Error ->{
            print(result.exception)
        }
    }
    }

    @Test
    fun insertBook() {
    }

}