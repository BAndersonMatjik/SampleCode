package com.example.bookstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Book
import com.example.core.domain.usecase.BookInteractor

class BookViewModel(private val bookInteractor: BookInteractor) : ViewModel() {

    var books =bookInteractor.getAllBooks().asLiveData()

    fun insertBook(book: Book) = bookInteractor.insertBook(book).asLiveData()

    fun deleteBook(book:Book) =  bookInteractor.deleteBook(book).asLiveData()
}