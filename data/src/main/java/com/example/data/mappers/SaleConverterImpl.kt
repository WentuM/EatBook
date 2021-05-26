package com.example.data.mappers

import com.example.data.database.entity.SaleEntity
import com.example.data.firebase.response.SaleResponse
import com.example.domain.model.Restaurant
import com.example.domain.model.Sale

class SaleConverterImpl : SaleConverter {
    override fun fbtoModel(saleResponse: SaleResponse): Sale =
        with(saleResponse) {
            Sale(
                id, title, description, imageSale, idRestaurant, titleRestaurant
            )
        }


    override fun dbtoModel(saleEntity: SaleEntity): Sale =
        with(saleEntity) {
            Sale(
                id,
                title,
                description,
                imageSale,
                idRestaurant,
                titleRestaurant
            )
        }

    override fun modeltoDb(sale: Sale): SaleEntity =
        with(sale) {
            SaleEntity(
                id,
                title,
                description,
                imageSale,
                idRestaurant,
                titleRestaurant
            )
        }

    override fun fbtoDb(saleResponse: SaleResponse): SaleEntity =
        with(saleResponse) {
            SaleEntity(id, title, description, imageSale, idRestaurant, titleRestaurant)
        }

}