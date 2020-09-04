package com.example.core.domain.model

data class Book(
    var bookName: String?= null,
    var writerBook: String?= null,
    var ratingBook: Double?= null,
    var descriptionBook: String?= null,
    var numberOfPages: String?= null,
    var dateOfIssue: String?= null,
    var publisher: String?= null,
    var isbn: Int?= null
){
    var idBook:String? = null
}