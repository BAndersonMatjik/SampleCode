package com.example.bookstore.core.data

import android.util.Log
import com.example.bookstore.core.domain.model.Book
import com.example.bookstore.core.domain.repository.IBookRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class BookRepository(private val bookCollection: CollectionReference) : IBookRepository {

    override fun getAllBooks() = flow<Response<List<Book>>> {
        emit(Response.Loading())
        val snapshot = bookCollection.get().await()
        val books = snapshot.toObjects<Book>()

        emit(Response.Success(books))
    }.catch {
        Log.e("BookRepository", "getAllBooks: "+it.message.toString() )
        emit(Response.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override fun addBook(book: Book)= flow<Response<DocumentReference>>{
        emit(Response.Loading())

        val bookReference = bookCollection.add(book).await()

        emit(Response.Success(bookReference))
    }.catch {
        emit(Response.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}