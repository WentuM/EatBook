package com.example.eatbook.ui.tables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import kotlinx.android.synthetic.main.fragment_list_table.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class TableFragment : Fragment(), TableAdapter.TableItemHandler {

    @Inject
    lateinit var tableViewModel: TableViewModel
    private lateinit var application: EatBookApp
    private val tableAdapter = TableAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.tableListComponentFactory()
            .create(this).inject(this)
        val root = inflater.inflate(R.layout.fragment_list_table, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_view.visibility = View.VISIBLE

        table_list.adapter = tableAdapter

        tableViewModel.tables().observe(viewLifecycleOwner, Observer {
            tableAdapter.submitList(it)
        })

        tableViewModel.getAllSales()
    }

    override fun onClick(idTable: String) {
        var bundle = Bundle()
        bundle.putString("idSale", idTable)
    }
}