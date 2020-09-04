package com.example.core.di

import com.example.core.data.BookRepository
import com.example.core.domain.repository.IBookRepository
import com.example.core.domain.usecase.BookInteractor
import com.example.core.domain.usecase.IBookUseCase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val firebaseStoreModule = module {
    single {
        provideFireStoreSetting()
    }

//    single(named(Constant.BOOKCOLLECTION)) {
//        get<FirebaseFirestore>().collection(Constant.BOOKCOLLECTION)
//    }

}
val repositoryModule = module {
    single<IBookRepository> {
        BookRepository(get(),Dispatchers.IO)
    }
}

fun provideFireStoreSetting():FirebaseFirestore{
    val firebaseFirestore = Firebase.firestore
    firebaseFirestore.firestoreSettings = firestoreSettings {
      isPersistenceEnabled = true
        cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
    }
    return firebaseFirestore
}
val useCaseModule = module {
    factory<IBookUseCase> {
        BookInteractor(get<IBookRepository>() as BookRepository)
    }
}