package com.example.bookstore.core.domain.usecase

import com.example.bookstore.core.data.BookRepository
import com.example.bookstore.core.data.Response
import com.example.bookstore.core.domain.model.Book
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

class BookInteractor(private val bookRepository: BookRepository):IBookUseCase {
    override fun getAllBooks(): Flow<Response<List<Book>>> {
        return bookRepository.getAllBooks()
    }

    override fun insertBook(book: Book): Flow<Response<DocumentReference>> {
        return bookRepository.addBook(book)
    }

}