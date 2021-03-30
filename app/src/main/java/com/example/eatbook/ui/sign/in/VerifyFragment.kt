package com.example.eatbook.ui.sign.`in`

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.data.firebase.utilits.*
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.UserInteractor
import com.example.eatbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_verify_number.*

class VerifyFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    private lateinit var username: String
    private lateinit var userInteractor: UserInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

//        val storedVerificationId=intent.getStringExtra("storedVerificationId")

//        Reference
//        val verify=findViewById<Button>(R.id.verifyBtn)
//        val otpGiven=findViewById<EditText>(R.id.id_otp)
        arguments?.getString("storedVerificationId")?.let {
            storedVerificationId = it
            Log.d("qwe5", it)
        }
        arguments?.getString("username")?.let {
            username = it
            Log.d("qwe6", it)

        }
        init()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        application = activity?.application as (CharacterApplication)
//        detailCharacterViewModel =
//            ViewModelProvider(this, initFactory()).get(DetailCharacterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_verify_number, container, false)
//        arguments?.getInt("id")?.let {
//            idCharacter = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyBtn.setOnClickListener {
            var otp = id_otp.text.toString().trim()
            if (!otp.isEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId, otp
                )
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(activity, "Введите ваш код", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        var result = userInteractor.authUser(credential)
        if (result == "Успешный вход в аккаунт") {
            Toast.makeText(
                activity,
                result,
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_navigation_verify_to_navigation_profile)
        } else {
            Toast.makeText(
                activity,
                result,
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_navigation_verify_to_navigation_profile)
        }
    }

    private fun init() {
        userInteractor = UserInteractor(UserRepositoryImpl())
    }
}