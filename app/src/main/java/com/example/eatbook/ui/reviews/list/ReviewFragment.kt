package com.example.eatbook.ui.reviews.list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Review
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.reviews.list.model.ReviewListModel
import kotlinx.android.synthetic.main.dialog_review_create.view.*
import kotlinx.android.synthetic.main.fragment_list_review.*
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
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        review_list.adapter = reviewAdapter

        reviewViewModel.reviews().observe(viewLifecycleOwner, Observer {
            reviewAdapter.submitList(it)
            txv_reviews_count.text = it.size.toString()
        })

        reviewViewModel.getAllReviews(idRestaurant, view)


        initField()
        initClick(view)
    }

    override fun onItemClick(idReview: String) {
        val bundle = Bundle()
        bundle.putString("idReview", idReview)
    }

    private fun initField() {

    }

    private fun initClick(view: View) {
        btn_review_write.setOnClickListener {
            if (!reviewViewModel.getCurrentUser()) {
                findNavController().navigate(R.id.action_navigation_review_to_navigation_sign_in)
            } else {
                showReviewDialog(view)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showReviewDialog(view: View) {
        val reviewDialog = AlertDialog.Builder(activity)
        val reviewView = layoutInflater.inflate(R.layout.dialog_review_create, null)
        reviewDialog.setView(reviewView)

        reviewDialog.setTitle("Ваш отзыв")
        reviewDialog.setCancelable(false)
            .setPositiveButton(
                "Оставить отзыв"
            ) { dialogInterface, p1 ->
                reviewViewModel.getUser()

                val sdf = SimpleDateFormat("dd/M/yyyy HH:mm")
                val currentDate: String = sdf.format(Date())
                val textReview: String = reviewView.review_text.text.toString()
                val rating: Double = reviewView.ratingbar_review_create.rating.toDouble()
                val id = UUID.randomUUID().toString()

                reviewViewModel.user().observe(viewLifecycleOwner, Observer {
                    val review: ReviewListModel =
                        ReviewListModel(id, textReview, currentDate, rating, idRestaurant)
                    reviewViewModel.createReview(review, view)
                })

                reviewViewModel.review().observe(viewLifecycleOwner, Observer {
                    Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                })
                dialogInterface?.dismiss()
            }
            .setNegativeButton(
                "Отмена"
            ) { dialogInterface, p1 -> dialogInterface?.cancel() }
        reviewDialog.create()
        reviewDialog.show()
    }
}