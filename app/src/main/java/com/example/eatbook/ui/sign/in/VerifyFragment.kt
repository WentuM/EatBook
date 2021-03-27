package com.example.eatbook.ui.sign.`in`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eatbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_verify_number.*

class VerifyFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

//        val storedVerificationId=intent.getStringExtra("storedVerificationId")

//        Reference
//        val verify=findViewById<Button>(R.id.verifyBtn)
//        val otpGiven=findViewById<EditText>(R.id.id_otp)
        arguments?.getString("storedVerificationId")?.let {
            storedVerificationId = it
        }
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
        activity?.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, "Успешный вход в аккаунт", Toast.LENGTH_LONG).show()
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(activity, "Неправильный код", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }
}