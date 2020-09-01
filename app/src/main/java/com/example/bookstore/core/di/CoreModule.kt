package com.example.bookstore.core.di

import com.example.bookstore.core.data.BookRepository
import com.example.bookstore.core.domain.usecase.BookInteractor
import com.example.bookstore.core.utils.Constant
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val firebaseStoreModule = module {
    single {
        provideFireStoreSetting()
    }

    single(named(Constant.BOOKCOLLECTION)) {
        get<FirebaseFirestore>().collection(Constant.BOOKCOLLECTION)
    }

}
val repositoryModule = module {


    single {
        BookRepository(get(named(Constant.BOOKCOLLECTION)))
    }
}
//not in core
val useCaseModule = module {
    single {
        BookInteractor(get())
    }
}




fun provideFireStoreSetting():FirebaseFirestore{
    val firebaseFirestore = Firebase.firestore
    firebaseFirestore.firestoreSettings = firestoreSettings {
      isPersistenceEnabled = true
        setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
    }
    return firebaseFirestore
}