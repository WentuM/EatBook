package com.example.data.mappers

import com.example.data.database.entity.RestaurantEntity
import com.example.data.firebase.response.RestaurantResponse
import com.example.domain.model.Restaurant

class RestaurantConverterImpl : RestaurantConverter {
    override fun dbtoModel(restaurantEntity: RestaurantEntity): Restaurant {
        return with(restaurantEntity) {
            Restaurant(
                id,
                title,
                description,
                rating,
                imageRest,
                price,
                address,
                likeRest
            )
        }
    }

    override fun modeltoDb(restaurant: Restaurant): RestaurantEntity {
        return with(restaurant) {
            RestaurantEntity(
                id,
                title,
                description,
                rating,
                imageRest,
                price,
                address,
                likeRest
            )
        }
    }

    override fun fbtoDb(restaurantResponse: RestaurantResponse): RestaurantEntity {
        return with(restaurantResponse) {
            RestaurantEntity(
                id, title, description, rating, imageRest, price, address, 1
            )
        }
    }
}