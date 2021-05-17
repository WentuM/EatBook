package com.example.domain.interactor

import com.example.domain.interfaces.ReviewRepository
import com.example.domain.interfaces.SaleRepository
import com.example.domain.interfaces.UserRepository
import com.example.domain.model.Review
import com.example.domain.model.Sale
import com.example.domain.model.User
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ReviewInteractor(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val context: CoroutineContext
) {

    suspend fun getAllReview(idRestaurant: String): List<Review> =
        withContext(context) {
            reviewRepository.getListReview(idRestaurant)
        }

    suspend fun createReview(review: Review): String =
        withContext(context) {
            reviewRepository.createReviewByUser(review)
        }

    suspend fun getCurrentUser(): User =
        withContext(context) {
            userRepository.getCurrentUser()
        }

    fun loggedUser(): Boolean =
        userRepository.loggedUser()
}