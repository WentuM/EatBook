package com.example.eatbook.ui.sales.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Sale
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.restaurants.list.RestaurantAdapter
import com.example.eatbook.ui.restaurants.list.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class SaleFragment : Fragment(), SaleAdapter.SaleItemHandler {

    @Inject
    lateinit var saleViewModel: SaleViewModel
    private lateinit var application: EatBookApp
    private val saleAdapter = SaleAdapter(this)

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
        search_view.visibility = View.VISIBLE

        rest_list.adapter = saleAdapter

        saleViewModel.sales().observe(viewLifecycleOwner, Observer {
            saleAdapter.submitList(it)
        })

        saleViewModel.getAllSales()
    }

    override fun onClick(idSale: String) {
        var bundle = Bundle()
        bundle.putString("idSale", idSale)
        findNavController().navigate(R.id.action_navigation_home_to_navigation_rest_detail, bundle)
    }
}