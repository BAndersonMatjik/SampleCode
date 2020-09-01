package com.example.bookstore.core.domain.usecase

import com.example.bookstore.core.data.Response
import com.example.bookstore.core.domain.model.Book
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

interface IBookUseCase {
    fun getAllBooks(): Flow<Response<List<Book>>>
    fun insertBook(book: Book):Flow<Response<DocumentReference>>
}