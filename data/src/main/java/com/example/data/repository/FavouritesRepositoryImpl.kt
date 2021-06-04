package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.database.dao.FavouriteRestDao
import com.example.data.database.dao.RestaurantDao
import com.example.data.database.entity.FavouriteRestEntity
import com.example.data.database.entity.RestaurantEntity
import com.example.data.firebase.response.RestaurantResponse
import com.example.data.mappers.RestaurantConverter
import com.example.data.mappers.RestaurantConverterImpl
import com.example.domain.interfaces.FavouritesRepository
import com.example.domain.model.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FavouritesRepositoryImpl(
    private val favouriteRestDao: FavouriteRestDao,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val restaurantConverterImpl: RestaurantConverter
) : FavouritesRepository {

    companion object {
        private const val FAVOURITE_RESTAURANTS = "favourite_restaurants"
        private const val RESTAURANTS_TABLE = "restaurants"
        private const val USER_TABLE = "users"
    }

    override suspend fun getFavouriteRestaurant(): List<Restaurant> {
        var resultList: List<Restaurant>
        val restaurantListResponse: List<RestaurantResponse>
        val userId: String? = firebaseAuth.currentUser?.uid
        if (userId != null) {
            try {
                restaurantListResponse = firestore.collection(USER_TABLE).document(userId)
                    .collection(FAVOURITE_RESTAURANTS)
                    .get().await().toObjects(RestaurantResponse::class.java)
                val restaurantListEntity =
                    restaurantListResponse.map { restaurantConverterImpl.fbtoDb(it) }
                resultList = restaurantListEntity.map { restaurantConverterImpl.dbtoModel(it) }
            } catch (e: Exception) {
                resultList = favouriteRestDao.getListFavourite()
                    .map { restaurantConverterImpl.dbtoModel(it) }
            }
        } else {
            resultList = favouriteRestDao.getListFavourite()
                .map { restaurantConverterImpl.dbtoModel(it) }

        }
        Log.d("qweRRR", resultList.toString())
        return resultList
    }

    override suspend fun setLikeForRestaurant(idRestaurant: String): String {
        var result = ""
        val userId = firebaseAuth.currentUser?.uid
        val idFavouriteRest = userId + idRestaurant
        var restaurantEntity: RestaurantEntity? = RestaurantEntity()
        var restaurantResponse: RestaurantResponse? = RestaurantResponse()
        val favouriteRestaurantEntity = FavouriteRestEntity(idRestaurant)
        //проверка, что пользователь авторизирован
        if (userId != null) {
            //проверяю, присутствует ли уже этот ресторан в моей таблице избранных
            try {
                restaurantResponse = firestore.collection(USER_TABLE).document(userId)
                    .collection(FAVOURITE_RESTAURANTS).document(idFavouriteRest)
                    .get().await().toObject(RestaurantResponse::class.java)
            } catch (e: Exception) {
                result = e.toString()
            }
            //если такого ресторана нет в таблице
            if (restaurantResponse == null) {

                //достаю необходимый мне ресторан из общей таблицы
                restaurantResponse =
                    firestore.collection(RESTAURANTS_TABLE).document(idRestaurant).get().await()
                        .toObject(RestaurantResponse::class.java)
                if (restaurantResponse != null) {
                    restaurantEntity = restaurantConverterImpl.fbtoDb(restaurantResponse)
                }

                //добавляю в таблицу избранных новый ресторан(который лайкнули)
                try {
                    if (restaurantEntity != null) {
                        firestore.collection(USER_TABLE).document(userId)
                            .collection(FAVOURITE_RESTAURANTS).document(idFavouriteRest)
                            .set(restaurantEntity).await()
                        //сохраняю в локальную базу айди ресторана в таблице избранных
                        favouriteRestDao.insert(favouriteRestaurantEntity)
                        result = "Ресторан добавлен в избранное"
                    }
                } catch (e: Exception) {
                    result = e.toString()
                }
                //если такой ресторан есть, значит (дизлайк), нужно удалить его из таблицы
            } else {
                result = try {
                    firestore.collection(USER_TABLE).document(userId)
                        .collection(FAVOURITE_RESTAURANTS).document(idFavouriteRest)
                        .delete().await()
                    favouriteRestDao.delete(favouriteRestaurantEntity)
                    "Ресторан удалён из избранного"
                } catch (e: Exception) {
                    e.toString()
                }
            }
            //ситуация, когда пользователь не авторизирован
        } else {
            val existRestaurantId: FavouriteRestEntity? = favouriteRestDao.getFavouriteEntity(idRestaurant)
            result = if (existRestaurantId != null) {
                favouriteRestDao.delete(favouriteRestaurantEntity)
                "Ресторан удалён из избранного"
            } else {
                favouriteRestDao.insert(favouriteRestaurantEntity)
                "Ресторан добавлен в избранное"
            }
        }
        return result
    }
}