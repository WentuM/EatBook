package com.example.eatbook.ui.sign.`in`

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.data.firebase.utilits.AUTH
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.sign_in_fragment.*
import java.util.concurrent.TimeUnit

class SignInFragment : Fragment() {
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var application: EatBookApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        application = activity?.application as (EatBookApp)

        var currentUser = AUTH.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_profile)
        }
        return inflater.inflate(R.layout.sign_in_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        panel_auth_btn.setOnClickListener {
            login()
        }

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(activity, "${e.toString()}", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("qwe1","onCodeSent:   $verificationId")
                storedVerificationId = verificationId
                resendToken = token
                var bundle = Bundle()
                bundle.putString("storedVerificationId", storedVerificationId)
                findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_verify, bundle)

//                var intent = Intent(applicationContext,Verify::class.java)
//                intent.putExtra("storedVerificationId",storedVerificationId)
//                startActivity(intent)
            }
        }
    }

    private fun login() {
        var number = panel_auth_number.text.toString().trim()
//        username = panel_auth_name.text.toString().trim()
        if (!number.isEmpty()) {
            number = "+7$number"
            sendVerificationcode(number)
        } else {
            Toast.makeText(activity, "Введите корректный номер телефона", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(AUTH)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}