package com.example.eatbook.ui.sales.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import kotlinx.android.synthetic.main.fragment_sale_detail.*
import javax.inject.Inject

class SaleDetailFragment : Fragment() {

    @Inject
    lateinit var saleDetailViewModel: SaleDetailViewModel
    private var idSale: String = ""
    private var idRestaurant: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("idSale")?.let {
            idSale = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.saleDetailComponentFactory()
            .create(this)
            .inject(this)
        val root = inflater.inflate(R.layout.fragment_sale_detail, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saleDetailViewModel.getSaleById(idSale, view)
        initFields()
        initClicks()
    }

    private fun initFields() {
        with(saleDetailViewModel) {
            getSale().observe(viewLifecycleOwner, Observer {
                sale_title.text = it.title
                sale_descr.text = it.description
                txv_rest_title.text = it.titleRestaurant
                idRestaurant = it.idRestaurant
                Glide.with(this@SaleDetailFragment).load(it.imageSale).into(imgv_sale)
            })
        }
    }

    private fun initClicks() {
        btn_rest_redirect.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("idRestaurant", idRestaurant)
            findNavController().navigate(R.id.action_navigation_sale_detail_to_navigation_rest_detail)
        }
    }

}