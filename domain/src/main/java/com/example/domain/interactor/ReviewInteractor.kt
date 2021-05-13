package com.example.domain.interactor

import com.example.domain.interfaces.ReviewRepository
import com.example.domain.interfaces.SaleRepository
import com.example.domain.model.Review
import com.example.domain.model.Sale
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ReviewInteractor(
    private val reviewRepository: ReviewRepository,
    private val context: CoroutineContext
) {
    suspend fun getReviewById(id: String): Review =
        withContext(context) {
            reviewRepository.getReviewById(id)
        }

    suspend fun getAllReview(): ArrayList<Review> =
        withContext(context) {
            reviewRepository.getListReview() as ArrayList<Review>
        }
}