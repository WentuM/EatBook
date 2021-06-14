package com.example.data.mappers

import com.example.data.database.entity.BookTableEntity
import com.example.data.database.entity.FullBookTable
import com.example.data.firebase.response.BookTableResponse
import com.example.domain.model.BookTable

class BookTableConverterImpl : BookTableConverter {
    override fun fbToDb(bookTableResponse: BookTableResponse): BookTableEntity =
        with(bookTableResponse) {
            BookTableEntity(
                id,
                idUser,
                idTable,
                day,
                time,
                countHour,
                countPlaces
            )
        }

    override fun fbToModel(bookTableResponse: BookTableResponse): BookTable =
        with(bookTableResponse) {
            BookTable(
                id,
                idTable,
                day,
                time,
                countHour,
                imageTable,
                nameTable,
                idRestaurant,
                nameRestaurant,
                countPlaces
            )
        }

    override fun fullBookTableToModel(fullBookTable: FullBookTable): BookTable =
        with(fullBookTable) {
            BookTable(
                id,
                idTable,
                day,
                time,
                countHour,
                imageTable,
                nameTable,
                idRestaurant,
                nameRestaurant,
                countPlaces
            )
        }
}