package com.example.eatbook

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)
        var imgProfile = findViewById<ImageView>(R.id.profile_image)
        imgProfile.setImageResource(R.drawable.prifile)
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
        btn_profile_book.setOnClickListener {
            Toast.makeText(this, "sdsa", Toast.LENGTH_LONG).show()
        }
        txv_profile_city.text = "Kazan"
        txv_profile_email.text = "kesand@mail.ru"
        txv_profile_number.text = "89993332211"

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
            Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show()
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