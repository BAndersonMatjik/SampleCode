package com.example.core.di

import com.example.core.data.BookRepository
import com.example.core.domain.repository.IBookRepository
import com.example.core.domain.usecase.BookInteractor
import com.example.core.domain.usecase.IBookUseCase
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module
import org.mockito.Mockito.mock


val mockFirebaseFirestoreModule = module {
    single<FirebaseFirestore>{
        mock(FirebaseFirestore::class.java)
    }
}
val mockBookRepositoryModule = module {
    single<IBookRepository> {
        mock(BookRepository::class.java)
    }
}
