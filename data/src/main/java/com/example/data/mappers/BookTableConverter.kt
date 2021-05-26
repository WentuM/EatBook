package com.example.data.mappers

import com.example.data.database.entity.BookTableEntity
import com.example.data.database.entity.FullBookTable
import com.example.data.firebase.response.BookTableResponse
import com.example.domain.model.BookTable

interface BookTableConverter {

    fun fbToDb(bookTableResponse: BookTableResponse): BookTableEntity

    fun fbToModel(bookTableResponse: BookTableResponse): BookTable

    fun fullBookTableToModel(fullBookTable: FullBookTable): BookTable

}