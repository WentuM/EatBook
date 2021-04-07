package com.example.data.firebase.utilits

import com.example.data.firebase.response.UserResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: UserResponse

const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_IMAGE = "image"

const val NODE_RESTAURANTS = "restaurants"
const val REST_ID = "id"
const val REST_TITLE = "title"
const val REST_DESCRIPTION = "description"
const val REST_PRICE = "price"
const val REST_RAITING = "raiting"

const val NODE_PROMOTION = "promotion"
const val PROMOTION_ID = "id"
const val PROMOTION_TITLE = "title"
const val PROMOTION_DESCRIPTION = "description"


fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
}