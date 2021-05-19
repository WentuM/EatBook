package com.example.data.mappers

import com.example.data.database.entity.BookTableEntity
import com.example.data.database.entity.ReviewEntity
import com.example.data.firebase.response.BookTableResponse
import com.example.data.firebase.response.ReviewResponse
import com.example.domain.model.BookTable

class BookTableConverterImpl : BookTableConverter {
    override fun fbToDb(bookTableResponse: BookTableResponse): BookTableEntity =
        with(bookTableResponse) {
            BookTableEntity(
                id,
                idUser,
                idTable, day, time, countHour
            )
        }

    override fun dbToModel(bookTableEntity: BookTableEntity): BookTable =
        with(bookTableEntity) {
            BookTable(
                idTable, day, time, countHour
            )
        }

}