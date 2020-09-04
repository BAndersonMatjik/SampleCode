package com.example.core.data

import android.util.Log
import com.example.core.domain.model.Book
import com.example.core.domain.repository.IBookRepository
import com.example.core.utils.Constant
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class BookRepository(
    private val firestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher
) : IBookRepository {

    @ExperimentalCoroutinesApi
    override fun getAllBooks(): Flow<Response<List<Book>>> = channelFlow {
        channel
        val listenerRegistration =
            firestore.collection(Constant.BOOKCOLLECTION).addSnapshotListener { data, error ->
                channel.offer(Response.Loading)
                if (error != null) {
                    channel.offer(Response.Error("Error " + error))
                    channel.close(error)
                } else {
                    if (data != null) {
                        data.toObjects(Book::class.java).apply {
                            channel.offer(Response.Success(this))
                        }
                    } else {
                        channel.offer(Response.Error("Failed To Receive Data"))
                    }
                }
            }

        awaitClose { listenerRegistration }

        channel.invokeOnClose {
            it?.printStackTrace()
        }


    }

    //SYNC data
//    override  fun getAllBooks() = flow<Response<List<Book>>> {
//        emit(Response.Loading())
//        val snapshot = firestore.collection(Constant.BOOKCOLLECTION).get().await()
//
//        val books = snapshot.toObjects<Book>()
//
//        emit(Response.Success(books))
//    }.catch {
//        Log.e("BookRepository", "getAllBooks: "+it.message.toString() )
//        emit(Response.Error(it.message.toString()))
//    }.flowOn(dispatcher)

    override fun insertBook(book: Book) = flow {
        emit(Response.Loading)
        Log.d("BookRepository", "addBook: " + Thread.currentThread())
        delay(2000)
//        val bookReference = firestore.collection(Constant.BOOKCOLLECTION).add(book).await()
        val bookReference = firestore.collection(Constant.BOOKCOLLECTION).document().apply {
            book.idBook = this.id
            this.set(book).await()
        }
        emit(Response.Success(bookReference))
    }.catch {
        emit(Response.Error(it.message.toString()))
    }.flowOn(dispatcher)

    override fun deleteBook(book: Book): Flow<Response<Unit>> = flow{
        emit(Response.Loading)
        delay(1000)
        firestore.collection(Constant.BOOKCOLLECTION).document(book.idBook!!).delete().await()
        emit(Response.Success(Unit))

    }.catch {
        emit(Response.Error(it.message.toString()))
    }.flowOn(dispatcher)

}