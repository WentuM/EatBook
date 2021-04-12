package com.example.eatbook.ui.sign.`in`

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.domain.RestaurantInteractor
import com.example.domain.UserInteractor
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.ViewModelFactory
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_verify_number.*
import kotlinx.coroutines.Dispatchers

class VerifyFragment : Fragment() {
    lateinit var storedVerificationId: String
    private lateinit var username: String
    private lateinit var verifyViewModel: VerifyViewModel
    private lateinit var application: EatBookApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val storedVerificationId=intent.getStringExtra("storedVerificationId")

//        Reference
//        val verify=findViewById<Button>(R.id.verifyBtn)
//        val otpGiven=findViewById<EditText>(R.id.id_otp)
        arguments?.getString("storedVerificationId")?.let {
            storedVerificationId = it
            Log.d("qwe5", it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        application = activity?.application as (EatBookApp)
        verifyViewModel = ViewModelProvider(this, initFactory()).get(VerifyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_verify_number, container, false)
        return root
    }

    private fun initFactory(): ViewModelFactory = ViewModelFactory(
        userInteractor = UserInteractor(application.repositoryUser, Dispatchers.IO),
        restaurantInteractor = RestaurantInteractor(
            application.repositoryRestaurant,
            Dispatchers.IO
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSubcribes()
        initListener()
    }

    private fun initSubcribes() {
        with(verifyViewModel) {
            verify().observe(viewLifecycleOwner, Observer {
                if (it == "Успешный вход в аккаунт") {
                    Toast.makeText(
                        activity,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_navigation_verify_to_navigation_profile)
                } else {
                    Toast.makeText(
                        activity,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_navigation_verify_to_navigation_profile)
                }
            })
        }
    }

    private fun initListener() {
        verifyBtn.setOnClickListener {
            var otp = id_otp.text.toString().trim()
            if (!otp.isEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId, otp
                )
                verifyViewModel.onVerifyClick(credential)
            } else {
                Toast.makeText(activity, "Введите ваш код", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        var result = userInteractor.authUser(credential)
//        if (result == "Успешный вход в аккаунт") {
//            Toast.makeText(
//                activity,
//                result,
//                Toast.LENGTH_LONG
//            ).show()
//            findNavController().navigate(R.id.action_navigation_verify_to_navigation_profile)
//        } else {
//            Toast.makeText(
//                activity,
//                result,
//                Toast.LENGTH_LONG
//            ).show()
//            findNavController().navigate(R.id.action_navigation_verify_to_navigation_profile)
//        }
    }

//    private fun init() {
//        userInteractor = UserInteractor(UserRepositoryImpl())
//    }
}