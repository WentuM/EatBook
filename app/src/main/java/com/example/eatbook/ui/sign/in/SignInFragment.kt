package com.example.eatbook.ui.sign.`in`

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.sign_in_fragment.*
import ru.tinkoff.decoro.Mask
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.PhoneNumberUnderscoreSlotsParser
import ru.tinkoff.decoro.slots.PredefinedSlots
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignInFragment : Fragment() {
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    @Inject
    lateinit var signInViewModel: SignInViewModel
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
        EatBookApp.appComponent.signInComponentFactory()
            .create(this)
            .inject(this)
        if (signInViewModel.getCurrentUser()) {
            findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_profile)
        }
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val slots = PhoneNumberUnderscoreSlotsParser().parseSlots("+7(___)___-__-__")
        val inputMask = MaskImpl.createTerminated(slots)
        inputMask.insertFront(panel_auth_number.text.toString())
        panel_auth_btn.setOnClickListener {
            login()
        }

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(activity, "$e", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                var bundle = Bundle()
                bundle.putString("storedVerificationId", storedVerificationId)
                findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_verify, bundle)
            }
        }
    }

    private fun login() {
        var number = panel_auth_number.text.toString().trim()
        if (!number.isEmpty()) {
            number = "+7$number"
            sendVerificationcode(number)
        } else {
            Toast.makeText(activity, "Введите корректный номер телефона", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = activity?.let {
            PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(it) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }
}