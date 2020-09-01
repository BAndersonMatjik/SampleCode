package com.example.bookstore.core.domain.repository

import com.example.bookstore.core.data.Response
import com.example.bookstore.core.domain.model.Book
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IBookRepository {
    fun getAllBooks(): Flow<Response<List<Book>>>
    fun addBook(book: Book): Flow<Response<DocumentReference>>
}