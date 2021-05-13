package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.SaleDao
import com.example.data.database.entity.RestaurantEntity
import com.example.data.database.entity.SaleEntity
import com.example.data.mappers.RestaurantConverterImpl
import com.example.data.mappers.SaleConverterImpl
import com.example.domain.interfaces.ReviewRepository
import com.example.domain.interfaces.SaleRepository
import com.example.domain.model.Restaurant
import com.example.domain.model.Review
import com.example.domain.model.Sale
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ReviewRepositoryImpl(
    private val saleDao: SaleDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ReviewRepository {

    companion object {
        private const val REVIEW_TABLE = "sales"
        private const val REVIEW_TABLE_TABLE_COLUMN_ID = "id"
        private const val REVIEW_TABLE_TABLE_COLUMN_TITLE = "title"
        private const val REVIEW_TABLE_TABLE_COLUMN_DESC = "description"
        private const val REVIEW_TABLE_TABLE_COLUMN_ID_REST = "id_rest"
    }

    override suspend fun getListReview(): List<Review> {
        var saleConverterImpl = SaleConverterImpl()
        var listResult: ArrayList<Sale> = ArrayList()
        return suspendCoroutine { continuation ->
            firestore.collection(SALES_TABLE).get()
                .addOnSuccessListener {
                    for (document in it.documents) {
                        var saleMap: HashMap<String, String> =
                            document.data as HashMap<String, String>
                        var saleEntity = SaleEntity(
                            saleMap[SALE_TABLE_COLUMN_ID].toString(),
                            saleMap[SALE_TABLE_COLUMN_TITLE].toString(),
                            saleMap[SALE_TABLE_COLUMN_DESC].toString(),
                            saleMap[SALE_TABLE_COLUMN_IMAGE].toString(),
                            saleMap[SALE_TABLE_COLUMN_ID_REST].toString(),
                            saleMap[SALE_TABLE_COLUMN_TITLE_REST].toString()
                        )
                        var saleModel =
                            saleConverterImpl.dbtoModel(saleEntity = saleEntity)
                        listResult.add(saleModel)
                    }
                    continuation.resume(listResult)
                }.addOnFailureListener {
                    //room db
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun getSalesById(id: String): Sale {
        return suspendCoroutine { continuation ->
            firestore.collection(SALES_TABLE).document(id).get()
                .addOnSuccessListener {
                    var saleMap: HashMap<String, String?> = it.data as HashMap<String, String?>
                    var saleEntity = SaleEntity(
                        saleMap[SALE_TABLE_COLUMN_ID].toString(),
                        saleMap[SALE_TABLE_COLUMN_TITLE].toString(),
                        saleMap[SALE_TABLE_COLUMN_DESC].toString(),
                        saleMap[SALE_TABLE_COLUMN_IMAGE].toString(),
                        saleMap[SALE_TABLE_COLUMN_ID_REST].toString(),
                        saleMap[SALE_TABLE_COLUMN_TITLE_REST].toString()
                    )
                    var saleConverterImpl = SaleConverterImpl()
                    var saleModel =
                        saleConverterImpl.dbtoModel(saleEntity)
                    continuation.resume(saleModel)
                }
        }
    }
}