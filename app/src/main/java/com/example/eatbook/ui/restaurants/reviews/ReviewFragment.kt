package com.example.eatbook.ui.restaurants.reviews

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Review
import com.example.domain.model.User
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.sales.list.SaleAdapter
import com.example.eatbook.ui.sales.list.SaleViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_review_create.view.*
import kotlinx.android.synthetic.main.fragment_list_review.*
import kotlinx.android.synthetic.main.fragment_list_sale.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ReviewFragment : Fragment(), ReviewAdapter.ReviewItemHandler {

    @Inject
    lateinit var reviewViewModel: ReviewViewModel
    private lateinit var application: EatBookApp
    private val reviewAdapter = ReviewAdapter(this)
    private var idRestaurant: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("idRestaurant")?.let {
            idRestaurant = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.reviewListComponentFactory()
            .create(this).inject(this)
        val root = inflater.inflate(R.layout.fragment_list_review, container, false)
        reviewViewModel.getAllReviews(idRestaurant)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        review_list.adapter = reviewAdapter

        reviewViewModel.reviews().observe(viewLifecycleOwner, Observer {
            reviewAdapter.submitList(it)
            txv_reviews_count.text = it.size.toString()
        })


        initField()
        initClick()
    }

    override fun onItemClick(idReview: String) {
        var bundle = Bundle()
        bundle.putString("idReview", idReview)
    }

    private fun initField() {

    }

    private fun initClick() {
        btn_review_write.setOnClickListener {
            if (!reviewViewModel.getCurrentUser()) {
                findNavController().navigate(R.id.action_navigation_review_to_navigation_sign_in)
            } else {
                showReviewDialog()
            }
        }
    }

    private fun showReviewDialog() {
        val reviewdialog = AlertDialog.Builder(activity)
        var reviewView = layoutInflater.inflate(R.layout.dialog_review_create, null)
        reviewdialog.setView(reviewView)

        reviewdialog.setTitle("Ваш отзыв")
        reviewdialog.setCancelable(false)
            .setPositiveButton("Оставить отзыв", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    reviewViewModel.getUser()

                    val sdf = SimpleDateFormat("dd/M/yyyy HH:mm")
                    val currentDate: String = sdf.format(Date())
                    var textReview: String = reviewView.review_text.text.toString()
                    var rating: Double = reviewView.ratingbar_review_create.rating.toDouble()
                    var id = UUID.randomUUID().toString()

                    reviewViewModel.user().observe(viewLifecycleOwner, Observer {
                        var review: Review =
                            Review(id, textReview, currentDate, rating, idRestaurant)
                        reviewViewModel.createReview(review)
                    })

                    reviewViewModel.review().observe(viewLifecycleOwner, Observer {
                        Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                    })
                    p0?.dismiss()
                }

            })
            .setNegativeButton("Отмена", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.cancel()
                }

            })
        reviewdialog.create()
        reviewdialog.show()
    }
}