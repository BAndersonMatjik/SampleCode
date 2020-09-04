package com.example.core.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.core.data.BookRepository
import com.example.core.data.Response
import com.example.core.di.mockBookRepositoryModule
import com.example.core.domain.model.Book
import com.example.core.domain.repository.IBookRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class BookInteractorTest:KoinTest {
    private val repository:IBookRepository by inject()
    private lateinit var bookInteractor:IBookUseCase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockObserver: Observer<Response<List<Book>>>

    @Captor
    private lateinit var captor: ArgumentCaptor<Response<List<Book>>>

    private val books = listOf(
        Book("sd","sdsd",0.0,"kkk","99","190299","kkkkk",1),
        Book("sd","sdsd",0.0,"kkk","99","190299","kkkkk",2)
    )


    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(listOf(
            mockBookRepositoryModule
        ))
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        bookInteractor  = BookInteractor(repository as BookRepository)
    }


    @Test
    fun checkDataIsFlowLoading() = runBlockingTest{

        val flow = flow {
            emit(Response.Loading)
            delay(200)
            emit(Response.Success(books))
        }

        `when`(repository.getAllBooks()).thenReturn(flow)

        val liveData = bookInteractor.getAllBooks().asLiveData()
        liveData.observeForever(mockObserver)

        verify(mockObserver, times(1)).onChanged(captor.capture())
        assertNotNull(captor.value)

        verify(mockObserver, times(1)).onChanged(argThat {
            when(it){
                is Response.Loading -> {
                    return@argThat true
                }
                else -> {
                    return@argThat false
                }
            }
        })
    }
}