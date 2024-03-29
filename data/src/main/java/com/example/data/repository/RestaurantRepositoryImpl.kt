package com.example.data.repository

import android.util.Log
import com.example.data.database.dao.FavouriteRestDao
import com.example.data.database.dao.RestaurantDao
import com.example.data.database.entity.RestaurantEntity
import com.example.data.firebase.response.RestaurantResponse
import com.example.data.firebase.response.ReviewResponse
import com.example.data.mappers.RestaurantConverter
import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.model.Restaurant
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class RestaurantRepositoryImpl(
    private val restaurantDao: RestaurantDao,
    private val favouriteRestDao: FavouriteRestDao,
    private val firestore: FirebaseFirestore,
    private val restaurantConverterImpl: RestaurantConverter
) : RestaurantRepository {

    companion object {
        private const val RESTAURANTS_TABLE = "restaurants"
        private const val REVIEW_TABLE = "reviews"
        private const val REVIEW_TABLE_COLUMN_ID_REST = "idRest"
    }

    override suspend fun getListRestaurant(): List<Restaurant> {
        val listFavouriteRest = favouriteRestDao.getListFavourite()
        Log.d("qweRRRR", listFavouriteRest.toString())

        val restaurantListEntity: List<RestaurantEntity> = try {
            val restaurantListResponse = firestore.collection(RESTAURANTS_TABLE).get().await()
                .toObjects(RestaurantResponse::class.java)
            restaurantListResponse.map { restaurantConverterImpl.fbtoDb(it) }

        } catch (e: Exception) {
            restaurantDao.getAllRestaurant()
        }

        restaurantDao.insertList(restaurantListEntity)

        for (restaurantEntity in restaurantListEntity) {
            if (!(listFavouriteRest.contains(restaurantEntity))) {
                restaurantEntity.likeRest = 0
            }
        }
        return restaurantListEntity.map { restaurantConverterImpl.dbtoModel(it) }
    }

    override suspend fun getRestaurantById(id: String): Restaurant {
        val restaurantResponse: RestaurantResponse?
        var restaurantEntity = RestaurantEntity()
        val reviewResponseList: List<ReviewResponse>

        try {
            reviewResponseList = firestore.collection(REVIEW_TABLE).whereEqualTo(
                REVIEW_TABLE_COLUMN_ID_REST, id
            ).get().await().toObjects(ReviewResponse::class.java)
            var averageRating = 0.0
            for (reviewResponse: ReviewResponse in reviewResponseList) {
                averageRating += reviewResponse.rating
            }
            if (reviewResponseList.isNotEmpty()) {
                averageRating /= reviewResponseList.size
            }
            restaurantResponse = firestore.collection(RESTAURANTS_TABLE).document(id).get().await()
                .toObject(RestaurantResponse::class.java)
            if (restaurantResponse != null) {
                restaurantResponse.rating = averageRating
                restaurantEntity = restaurantConverterImpl.fbtoDb(restaurantResponse)
            }
        } catch (e: Exception) {
            restaurantEntity = restaurantDao.getRestaurantById(id)
        }
        return restaurantConverterImpl.dbtoModel(restaurantEntity)
    }
}