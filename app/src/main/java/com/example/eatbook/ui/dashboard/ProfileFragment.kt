package com.example.eatbook.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.data.firebase.utilits.AUTH
import com.example.domain.RestaurantInteractor
import com.example.domain.UserInteractor
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var application: EatBookApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (AUTH.currentUser == null) {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_sign_in)
        }
        application = activity?.application as (EatBookApp)
        profileViewModel = ViewModelProvider(this, initFactory()).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            //ToDO вынести логику с firebase всю во viewmodel
        AUTH.currentUser?.uid?.let { profileViewModel.onGetUser(it) }
        initFields()
        initClickListener()
    }

    private fun initFields() {
        with(profileViewModel) {
            getUser().observe(viewLifecycleOwner, Observer {
                profile_image.setImageResource(R.drawable.prifile)
                txv_profile_city.text = "Казань"
                txv_profile_email.text = "kesand@mail.ru"
                txv_profile_number.text = it.numberPhone
                txv_profile_fullname.text = it.username
            })
        }
        imgv_profile_edit.visibility = View.VISIBLE
    }

    private fun initFactory(): ViewModelFactory = ViewModelFactory(
        userInteractor = UserInteractor(application.repositoryUser, Dispatchers.IO),
        restaurantInteractor = RestaurantInteractor(
            application.repositoryRestaurant,
            Dispatchers.IO
        )
    )

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
            profileViewModel.onUpdateUserClick(
                edtx_profile_fullname.text.toString(),
                profile_image.toString()
            )
            with(profileViewModel) {
                updateUser().observe(viewLifecycleOwner, Observer {
                    edit(txv_profile_fullname, edtx_profile_fullname)
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        it,
                        Snackbar.LENGTH_LONG
                    ).show()
                })
            }
            enableProfileInfo()
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
}