package com.example.data.mappers

import com.example.data.database.entity.SaleEntity
import com.example.domain.model.Restaurant
import com.example.domain.model.Sale

class SaleConverterImpl : SaleConverter {
    override fun dbtoModel(saleEntity: SaleEntity): Sale {
        return Sale(
            saleEntity.id,
            saleEntity.title,
            saleEntity.description,
            saleEntity.imageSale,
            saleEntity.idRestaurant,
            saleEntity.titleRestaurant
        )
    }

    override fun modeltoDb(sale: Sale): SaleEntity {
        return SaleEntity(
            sale.id,
            sale.title,
            sale.description,
            sale.imageSale,
            sale.idRestaurant,
            sale.titleRestaurant
        )
    }
}