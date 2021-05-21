package com.example.data.mappers

import com.example.data.database.entity.TableEntity
import com.example.data.firebase.response.TableResponse
import com.example.domain.model.Table

class TableConverterImpl : TableConverter {
    override fun fbtoDb(tableResponse: TableResponse): TableEntity =
        with(tableResponse) {
            TableEntity(
                id, title, countPlaces, image, idRest
            )
        }

    override fun dbtoModel(tableEntity: TableEntity): Table =
        with(tableEntity) {
            Table(
                id, title, countPlaces, image, idRest
            )
        }
}