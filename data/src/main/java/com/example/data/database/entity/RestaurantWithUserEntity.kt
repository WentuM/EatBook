//package com.example.data.database.entity
//
//import androidx.room.Embedded
//import androidx.room.Junction
//import androidx.room.Relation
//
//data class RestaurantWithUserEntity(
//    @Embedded val userEntity: UserEntity,
//    @Relation(
//        parentColumn = "idUser",
//        entityColumn = "idRest",
//        entity = RestaurantEntity::class,
//        associateBy = Junction(
//            value = FavouriteRestEntity::class,
//            parentColumn = "idUser",
//            entityColumn = "idRest"
//        )
//    ) val restIdUser: List<RestaurantEntity>
//)