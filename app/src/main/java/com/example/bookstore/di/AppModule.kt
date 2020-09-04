package com.example.bookstore.di

import com.example.bookstore.BookViewModel
import com.example.core.domain.usecase.BookInteractor
import com.example.core.domain.usecase.IBookUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        BookViewModel(get<IBookUseCase>() as BookInteractor)
    }

}
