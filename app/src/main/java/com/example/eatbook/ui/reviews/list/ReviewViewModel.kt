package com.example.eatbook.ui.reviews.list

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.ReviewInteractor
import com.example.domain.model.Review
import com.example.domain.model.User
import com.example.eatbook.ui.reviews.list.model.ReviewListModel
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewInteractor: ReviewInteractor
) : ViewModel() {

    private val _reviews = MutableLiveData<List<ReviewListModel>>()
    private val _user = MutableLiveData<User>()
    private val _review = MutableLiveData<String>()
    fun user(): LiveData<User> = _user
    fun reviews(): LiveData<List<ReviewListModel>> = _reviews
    fun review(): LiveData<String> = _review

    fun getAllReviews(idRestaurant: String, view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _reviews.value = reviewInteractor.getAllReview(idRestaurant)
                    .map { mapReviewToReviewListModel(it) }
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
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

    fun createReview(review: ReviewListModel, view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _review.value = reviewInteractor.createReview(mapReviewListModelToReview(review))
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    private fun mapReviewToReviewListModel(review: Review): ReviewListModel {
        return with(review) {
            ReviewListModel(
                id, text, dateSend, rating, idRest, nameUser, imageUser
            )
        }
    }

    private fun mapReviewListModelToReview(reviewListModel: ReviewListModel): Review {
        return with(reviewListModel) {
            Review(
                id, text, dateSend, rating, idRest, nameUser, imageUser
            )
        }
    }
}