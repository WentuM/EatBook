package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.database.dao.FavouriteRestDao
import com.example.data.database.dao.RestaurantDao
import com.example.data.database.entity.FavouriteRestEntity
import com.example.data.database.entity.RestaurantEntity
import com.example.data.firebase.response.RestaurantResponse
import com.example.data.mappers.RestaurantConverterImpl
import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.model.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class RestaurantRepositoryImpl(
    private val restaurantDao: RestaurantDao,
    private val favouriteRestDao: FavouriteRestDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : RestaurantRepository {

    companion object {
        private const val RESTAURANTS_TABLE = "restaurants"
        private const val REST_TABLE_COLUMN_ID = "id"
        private const val REST_TABLE_COLUMN_TITLE = "title"
        private const val REST_TABLE_COLUMN_DESC = "description"
        private const val REST_TABLE_COLUMN_PRICE = "price"
        private const val REST_TABLE_COLUMN_RATING = "rating"
        private const val REST_TABLE_COLUMN_IMAGE = "image"
        private const val REST_TABLE_COLUMN_ADDRESS = "address"
    }

    override suspend fun getListRestaurant(): List<Restaurant> {
        val listFavouriteRest: List<RestaurantEntity> = favouriteRestDao.getListFavourite()
        val list = favouriteRestDao.getAllFavouriteId()
        Log.d("qeqweweq", list.toString())
        Log.d("qweFFFF", listFavouriteRest.toString())
        val restaurantConverterImpl = RestaurantConverterImpl()
        var listResult: List<Restaurant> = emptyList()
        var restaurantListEntity: List<RestaurantEntity> = emptyList()
        restaurantListEntity = try {
            val restaurantListResponse = firestore.collection(RESTAURANTS_TABLE).get().await()
                .toObjects(RestaurantResponse::class.java)
            restaurantListResponse.map { restaurantConverterImpl.fbtoDb(it) }
        } catch (e: Exception) {
            Log.d("qweF", e.toString())
            restaurantDao.getAllRestaurant()
        }
        restaurantDao.insertList(restaurantListEntity)
        Log.d("qweeqeqw", restaurantListEntity.toString())
        for (restaurantEntity in restaurantListEntity) {
            if ((listFavouriteRest.contains(restaurantEntity))) {
                restaurantEntity.likeRest = 0
            }
        }
        listResult = restaurantListEntity.map { restaurantConverterImpl.dbtoModel(it) }
        return listResult
    }

    override suspend fun getRestaurantById(id: String): Restaurant {
        var restaurantConverterImpl = RestaurantConverterImpl()
        var restaurantResponse: RestaurantResponse? = RestaurantResponse()
        var restaurantEntity = RestaurantEntity()
        var idFavourite: String = ""
        try {
            restaurantResponse = firestore.collection(RESTAURANTS_TABLE).document(id).get().await().toObject(RestaurantResponse::class.java)
            if (restaurantResponse != null) {
                restaurantEntity = restaurantConverterImpl.fbtoDb(restaurantResponse)
            }
        } catch (e: Exception) {
            restaurantEntity = restaurantDao.getRestaurantById(id)
        }
        val existRestaurantId: FavouriteRestEntity = favouriteRestDao.getFavouriteEntity(restaurantEntity.id)
//        idFavourite = favouriteRestDao.getBooleanId(restaurantEntity.id)
        if (existRestaurantId == null) {
            restaurantEntity.likeRest = 0
        }
        var restaurantResult = restaurantConverterImpl.dbtoModel(restaurantEntity)
        return restaurantResult
    }
}