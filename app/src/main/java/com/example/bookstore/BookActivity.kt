package com.example.bookstore

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.core.data.Response
import com.example.core.domain.model.Book
import com.example.core.ui.BookAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_layout.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.random.Random


class BookActivity : AppCompatActivity(),BookAdapter.OnClickListenerBookAdapter {

    private val viewModel by viewModel<BookViewModel>()
    private lateinit var adapter:BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = BookAdapter(this)
        recyclerview.adapter = adapter

        viewModel.books.observe(this, {
            when(it){
                is Response.Loading ->{
                    tv_upload.text = "loading"
                    layoutLoading.visibility = View.VISIBLE
                }
                is Response.Success ->    {
                    tv_upload.text = "success "+it.value.size
                    adapter.swapData(it.value)
                    layoutLoading.visibility =  View.GONE
                }
                is Response.Error ->{
                    tv_upload.text = "error"+it.exception
                }
            }
        })
        val counter :Int = 0
        tv_upload.setOnClickListener {
            viewModel.insertBook(Book("Book"+counter,"Writer"+counter,2.0,"Decription"+counter,"1000","190299","Wohang"+counter,Random.nextInt(0,1000))).observe(
                this,
                 {
                    when (it) {
                        is Response.Loading -> {
                            Log.d(TAG, "onLoading: ")
                            layoutLoading.visibility = View.VISIBLE
                        }
                        is Response.Error -> {
                            Log.d(TAG, "onError: " + it.exception)
                            Toast.makeText(this, it.exception, Toast.LENGTH_SHORT).show()
                        }
                        is Response.Success -> {
                            Log.d(TAG, "onSuccess " + it.value.id + " " + Thread.currentThread())
                            layoutLoading.visibility = View.GONE
                        }
                    }
                })
        }


    }

    companion object {
        private const val TAG = "BookActivity"
    }

    override fun deleteItem(book: Book) {
        viewModel.deleteBook(book).observe(this, {
            when (it) {
                is Response.Loading -> {
                    layoutLoading.visibility = View.VISIBLE
                }
                is Response.Error -> {
                    Toast.makeText(this, it.exception, Toast.LENGTH_SHORT).show()

                }
                is Response.Success -> {
                    layoutLoading.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}