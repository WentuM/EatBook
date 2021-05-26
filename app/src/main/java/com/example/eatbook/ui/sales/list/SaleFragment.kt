package com.example.eatbook.ui.sales.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import kotlinx.android.synthetic.main.fragment_list_sale.*
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

        sale_list.adapter = saleAdapter

        saleViewModel.sales().observe(viewLifecycleOwner, Observer {
            saleAdapter.submitList(it)
        })

        saleViewModel.getAllSales(view)
    }

    override fun onClick(idSale: String) {
        var bundle = Bundle()
        bundle.putString("idSale", idSale)
        findNavController().navigate(R.id.action_navigation_sale_to_navigation_sale_detail, bundle)
    }
}