package com.example.data.mappers

import com.example.data.database.entity.RestaurantEntity
import com.example.domain.model.Restaurant

class RestaurantConverterImpl : RestaurantConverter {
    override fun dbtoModel(restaurantEntity: RestaurantEntity): Restaurant {
        return Restaurant(
            restaurantEntity.id,
            restaurantEntity.title,
            restaurantEntity.description,
            restaurantEntity.rating,
            restaurantEntity.image,
            restaurantEntity.price,
            restaurantEntity.address
        )
    }

    override fun modeltoDb(restaurant: Restaurant): RestaurantEntity {
        return RestaurantEntity(
            restaurant.id,
            restaurant.title,
            restaurant.description,
            restaurant.raiting,
            restaurant.imageRest,
            restaurant.price,
            restaurant.address
        )
    }
}