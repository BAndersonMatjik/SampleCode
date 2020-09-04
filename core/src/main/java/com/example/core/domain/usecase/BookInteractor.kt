package com.example.core.domain.usecase

import com.example.core.data.BookRepository
import com.example.core.data.Response
import com.example.core.domain.model.Book
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

class BookInteractor(private val bookRepository: BookRepository):IBookUseCase {
    override fun getAllBooks(): Flow<Response<List<Book>>> {
        return bookRepository.getAllBooks()
    }

    override  fun insertBook(book: Book): Flow<Response<DocumentReference>> {
        return bookRepository.insertBook(book)
    }

    override fun deleteBook(book: Book): Flow<Response<Unit>> {
        return bookRepository.deleteBook(book)
    }
}