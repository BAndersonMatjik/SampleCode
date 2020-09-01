package com.example.bookstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.bookstore.core.data.Response
import com.example.bookstore.core.domain.model.Book
import com.example.bookstore.core.domain.usecase.BookInteractor
import com.example.bookstore.core.domain.usecase.IBookUseCase
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class BookActivity : AppCompatActivity() {

    val bookInteractor:BookInteractor by inject<BookInteractor>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = bookInteractor.getAllBooks().asLiveData()

        data.observe(this, Observer {
            when(it){
                is Response.Loading ->{
                    tv_upload.setText("loading")
                }
                is Response.Success ->    {
                    tv_upload.setText("success "+it.value.size)
                }
                is Response.Error ->{
                    tv_upload.setText("error"+it.exception)
                }
            }
        })
        val counter :Int = 0
        tv_upload.setOnClickListener {
            bookInteractor.insertBook(Book("Book"+counter,"Writer"+counter,2.0,"Decription"+counter,"1000","190299","Wohang"+counter,+counter)).asLiveData().observe(
                this,
                Observer {
                    when (it) {
                        is Response.Loading -> {
                            Log.d(Companion.TAG, "onLoading: ")
                        }
                        is Response.Error -> {
                            Log.d(TAG, "onError: "+it.exception)
                        }
                        is Response.Success -> {
                            Log.d(TAG, "onSuccess "+it.value.parent.toString())
                        }
                    }
                })
        }


    }

    companion object {
        private const val TAG = "BookActivity"
    }
}