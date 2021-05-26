package com.example.eatbook.ui.profile

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.UserInteractor
import com.example.domain.model.Restaurant
import com.example.domain.model.User
import com.example.eatbook.ui.favourites.model.FavouritesListModel
import com.example.eatbook.ui.profile.model.UserItemModel
import kotlinx.android.synthetic.main.fragment_list_my_book_table.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(
    private val userInteractor: UserInteractor
) : ViewModel() {

    private val mUpdateUser: MutableLiveData<String> = MutableLiveData()
    private val mGetUser: MutableLiveData<UserItemModel> = MutableLiveData()
    private val signOut: MutableLiveData<String> = MutableLiveData()

    fun updateUser(): LiveData<String> = mUpdateUser
    fun getUser(): LiveData<UserItemModel> = mGetUser
    fun signOutUser(): LiveData<String> = signOut

    fun onUpdateUserClick(newUsername: String) {
        viewModelScope.launch {
            try {
                mUpdateUser.value = userInteractor.updateUser(newUsername)
            } catch (e: Exception) {

            }
        }
    }

    fun getCurrentUser(): Boolean = userInteractor.loggedUser()

    fun onGetUser(view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                mGetUser.value = mapUserToUserItemModel(userInteractor.getCurrentUser())
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    fun onSignOut() {
        viewModelScope.launch {
            signOut.value = userInteractor.signOutUser()
        }
    }

    private fun mapUserToUserItemModel(user: User): UserItemModel {
        return with(user) {
            UserItemModel(
                id, phone, username, image
            )
        }
    }
}