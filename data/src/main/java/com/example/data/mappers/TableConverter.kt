package com.example.data.mappers

import com.example.data.database.entity.ReviewEntity
import com.example.data.database.entity.TableEntity
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.ReviewResponse
import com.example.data.firebase.response.TableResponse
import com.example.domain.model.Review
import com.example.domain.model.Table

interface TableConverter {

    fun fbtoDb(tableResponse: TableResponse): TableEntity

    fun dbtoModel(tableEntity: TableEntity): Table
}