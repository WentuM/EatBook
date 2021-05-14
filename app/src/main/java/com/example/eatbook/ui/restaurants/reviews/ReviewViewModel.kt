package com.example.eatbook.ui.restaurants.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.entity.ReviewEntity
import com.example.data.mappers.ReviewConverterImpl
import com.example.domain.interactor.ReviewInteractor
import com.example.domain.model.Review
import com.example.domain.model.User
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewInteractor: ReviewInteractor
) : ViewModel() {

    private val _reviews = MutableLiveData<List<Review>>()
    private val _user = MutableLiveData<User>()
    private val _review = MutableLiveData<String>()
    fun user(): LiveData<User> = _user
    fun reviews(): LiveData<List<Review>> = _reviews
    fun review(): LiveData<String> = _review

    fun getAllReviews(idRestaurant: String) {
        viewModelScope.launch {
            try {
                _reviews.value = reviewInteractor.getAllReview(idRestaurant)
            } catch (e: Exception) {

            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                _user.value = reviewInteractor.getCurrentUser()
            } catch (e: Exception) {

            }
        }
    }

    fun getCurrentUser(): Boolean = reviewInteractor.loggedUser()

    fun createReview(review: Review) {
        viewModelScope.launch {
            try {
                _review.value = reviewInteractor.createReview(review)
            } catch (e: Exception) {

            }
        }
    }


}