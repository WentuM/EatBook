package com.example.data.mappers

import com.example.data.database.entity.SaleEntity
import com.example.domain.model.Sale

interface SaleConverter {

    fun dbtoModel(saleEntity: SaleEntity): Sale

    fun modeltoDb(sale: Sale): SaleEntity
}