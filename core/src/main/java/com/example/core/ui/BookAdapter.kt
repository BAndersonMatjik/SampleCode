package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.R
import com.example.core.domain.model.Book
import kotlinx.android.synthetic.main.item_bookf.view.*

class BookAdapter(private val onClickListener:OnClickListenerBookAdapter) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    private var book: List<Book> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_bookf, parent, false),onClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(book[position])
    }

    fun swapData(books: List<Book>) {
        this.book = books
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return this.book.size
    }

    class ViewHolder(view: View,private val customListener: OnClickListenerBookAdapter) : RecyclerView.ViewHolder(view) {
        fun onBindViewHolder(book: Book) {
            itemView.apply {
                tv_name.text = book.bookName
                tv_dateOfIssue.text = book.dateOfIssue
                tv_isbn.text = book.idBook.toString()
                tv_publisher.text = book.publisher
                setOnClickListener {
                    customListener.deleteItem(book)
                }
            }
        }
    }

    companion object {
    }
    interface OnClickListenerBookAdapter{
        fun deleteItem(book: Book)
    }
}