//package com.example.eatbook.ui
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.domain.RestaurantInteractor
//import com.example.domain.UserInteractor
//import com.example.eatbook.ui.dashboard.ProfileViewModel
//import com.example.eatbook.ui.restaurants.list.RestaurantViewModel
//import com.example.eatbook.ui.sign.`in`.VerifyViewModel
//import java.lang.IllegalArgumentException
//
//class ViewModelFactory(
//    private val userInteractor: UserInteractor,
//    private val restaurantInteractor: RestaurantInteractor
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//        when {
//            modelClass.isAssignableFrom(VerifyViewModel::class.java) -> {
//                VerifyViewModel(userInteractor) as? T
//                    ?: throw IllegalArgumentException("Unknown ViewModel class")
//            }
//            modelClass.isAssignableFrom(RestaurantViewModel::class.java) -> {
//                RestaurantViewModel(restaurantInteractor) as? T
//                    ?: throw IllegalArgumentException("Unknown ViewModel class")
//            }
//            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
//                ProfileViewModel(userInteractor) as? T
//                    ?: throw IllegalArgumentException("Unknown ViewModel class")
//            }
//            else -> {
//                throw IllegalArgumentException("Unknown ViewModel class")
//            }
//        }
//}
