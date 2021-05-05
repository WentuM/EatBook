package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.database.dao.RestaurantDao
import com.example.data.database.entity.RestaurantEntity
import com.example.data.mappers.RestaurantConverterImpl
import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.model.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RestaurantRepositoryImpl(
    private val restaurantDao: RestaurantDao,
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
        private const val REST_TABLE_COLUMN_RAITING = "raiting"
        private const val REST_TABLE_COLUMN_IMAGE = "image"
        private const val REST_TABLE_COLUMN_ADDRESS = "address"
    }

    override suspend fun getListRestaurant(): ArrayList<Restaurant> {
        var restaurantConverterImpl = RestaurantConverterImpl()
        var listResult: ArrayList<Restaurant> = ArrayList()
        return suspendCoroutine { continuation ->
            val listRestaurant = firestore.collection(RESTAURANTS_TABLE).get()
                .addOnSuccessListener {
                    for (document in it.documents) {
                        var restaurantMap: HashMap<String, String> = document.data as HashMap<String, String>
                        var restaurantEntity = RestaurantEntity(
                            restaurantMap[REST_TABLE_COLUMN_ID].toString(),
                            restaurantMap[REST_TABLE_COLUMN_TITLE].toString(),
                            restaurantMap[REST_TABLE_COLUMN_DESC].toString(),
                            restaurantMap[REST_TABLE_COLUMN_RAITING]!!.toDouble(),
                            restaurantMap[REST_TABLE_COLUMN_IMAGE].toString(),
                            restaurantMap[REST_TABLE_COLUMN_PRICE]!!.toInt(),
                            restaurantMap[REST_TABLE_COLUMN_ADDRESS].toString()
                        )
                        var restaurantModel =
                            restaurantConverterImpl.dbtoModel(restaurantEntity = restaurantEntity)
                        listResult.add(restaurantModel)
                    }
                    continuation.resume(listResult)
                }.addOnFailureListener {
                    //room db
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun getRestaurantById(id: String): Restaurant {
        return suspendCoroutine { continuation ->
            val restaurant = firestore.collection(RESTAURANTS_TABLE).document(id).get()
                .addOnSuccessListener {
                    var restaurantMap: HashMap<String, String> = it.data as HashMap<String, String>
                    var restaurantEntity = RestaurantEntity(
                        restaurantMap[REST_TABLE_COLUMN_ID].toString(),
                        restaurantMap[REST_TABLE_COLUMN_TITLE].toString(),
                        restaurantMap[REST_TABLE_COLUMN_DESC].toString(),
                        restaurantMap[REST_TABLE_COLUMN_RAITING]!!.toDouble(),
                        restaurantMap[REST_TABLE_COLUMN_IMAGE].toString(),
                        restaurantMap[REST_TABLE_COLUMN_PRICE]!!.toInt(),
                        restaurantMap[REST_TABLE_COLUMN_ADDRESS].toString()
                    )
                    var restaurantConverterImpl = RestaurantConverterImpl()
                    var restaurantModel =
                        restaurantConverterImpl.dbtoModel(restaurantEntity = restaurantEntity)
                    continuation.resume(restaurantModel)
                }
        }
    }
}