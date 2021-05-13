package com.example.eatbook.ui.restaurants.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.sales.list.SaleAdapter
import com.example.eatbook.ui.sales.list.SaleViewModel
import kotlinx.android.synthetic.main.fragment_list_sale.*
import javax.inject.Inject

class ReviewFragment : Fragment(), ReviewAdapter.ReviewItemHandler {

    @Inject
    lateinit var reviewViewModel: ReviewViewModel
    private lateinit var application: EatBookApp
    private val reviewAdapter = ReviewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.salesListComponentFactory()
            .create(this).inject(this)
        val root = inflater.inflate(R.layout.fragment_list_sale, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sale_list.adapter = reviewAdapter

        reviewViewModel.reviews().observe(viewLifecycleOwner, Observer {
            reviewAdapter.submitList(it)
        })

        saleViewModel.getAllSales()
    }

    override fun onItemClick(idReview: String) {
        var bundle = Bundle()
        bundle.putString("idSale", idReview)
    }
}