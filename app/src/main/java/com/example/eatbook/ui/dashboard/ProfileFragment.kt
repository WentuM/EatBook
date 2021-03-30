package com.example.eatbook.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.data.firebase.utilits.AUTH
import com.example.data.firebase.utilits.CHILD_ID
import com.example.data.firebase.utilits.NODE_USERS
import com.example.data.firebase.utilits.REF_DATABASE_ROOT
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.UserInteractor
import com.example.eatbook.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userInteractor: UserInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AUTH.currentUser == null) {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_sign_in)
        }
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel.text.observe(viewLifecycleOwner, Observer {
            txv_profile_city.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initClickListener()
    }

    private fun initFields() {
        profile_image.setImageResource(R.drawable.prifile)
//        REF_DATABASE_ROOT.child(NODE_USERS).child(CHILD_ID).
        txv_profile_city.text = "Kazan"
        txv_profile_email.text = "kesand@mail.ru"
        txv_profile_number.text = "89993332211"

//        txv_profile_fullname.text =
        imgv_profile_edit.visibility = View.VISIBLE
    }

    private fun initClickListener() {
        btn_profile_book.setOnClickListener {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                "qweqw",
                Snackbar.LENGTH_LONG
            ).show()
        }
        btn_profile_sign_out.setOnClickListener {
            AUTH.signOut()
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_sign_in)
        }

        imgv_profile_edit.setOnClickListener {
            imgv_profile_edit.visibility = View.INVISIBLE
            imgv_profile_active.visibility = View.VISIBLE
            imgv_profile_cancel.visibility = View.VISIBLE
            enableProfileInfo()
        }

        imgv_profile_cancel.setOnClickListener {
            imgv_profile_edit.visibility = View.VISIBLE
            imgv_profile_active.visibility = View.INVISIBLE
            imgv_profile_cancel.visibility = View.INVISIBLE
            disableProfileInfo()
        }

        imgv_profile_active.setOnClickListener {
            edit(txv_profile_fullname, edtx_profile_fullname)
            edit(txv_profile_city, edtx_profile_city)
            edit(txv_profile_number, edtx_profile_number)
            edit(txv_profile_email, edtx_profile_email)
            enableProfileInfo()
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                "Изменения сохранены",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun enableProfileInfo() {
        txv_profile_number.visibility = View.INVISIBLE
        txv_profile_email.visibility = View.INVISIBLE
        txv_profile_city.visibility = View.INVISIBLE
        edtx_profile_email.visibility = View.VISIBLE
        edtx_profile_number.visibility = View.VISIBLE
        edtx_profile_city.visibility = View.VISIBLE
    }

    private fun disableProfileInfo() {
        txv_profile_number.visibility = View.VISIBLE
        txv_profile_email.visibility = View.VISIBLE
        txv_profile_city.visibility = View.VISIBLE
        edtx_profile_email.visibility = View.INVISIBLE
        edtx_profile_number.visibility = View.INVISIBLE
        edtx_profile_city.visibility = View.INVISIBLE
    }

    private fun edit(etView: TextView, etEdit: EditText) {
        etView.text = etEdit.text.toString()
        etView.visibility = View.VISIBLE
        etEdit.visibility = View.INVISIBLE
    }

    private fun init() {
        userInteractor = UserInteractor(UserRepositoryImpl())
    }
}