package com.example.eatbook.ui.favourites.model

data class FavouritesListModel(
    val id: String,
    val title: String,
    val description: String,
    var rating: Double,
    val imageRest: String,
    val price: Int,
    var address: String,
    var likeRest: Int
)
