package com.example.bookstore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.di.useCaseModule
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class BookViewModelTest:KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val viewModel:BookViewModel by inject()

    @Before
    fun setUp() {
        startKoin{
            modules(
                useCaseModule
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getBooks() {
        assertNotNull(viewModel)
    }

    @Test
    fun insertBook() {
    }
}