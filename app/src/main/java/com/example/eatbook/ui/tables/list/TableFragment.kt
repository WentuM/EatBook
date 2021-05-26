package com.example.eatbook.ui.tables.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import kotlinx.android.synthetic.main.fragment_list_table.*
import javax.inject.Inject

class TableFragment : Fragment(), TableAdapter.TableItemHandler {

    @Inject
    lateinit var tableViewModel: TableViewModel
    private val tableAdapter = TableAdapter(this)
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
        EatBookApp.appComponent.tableListComponentFactory()
            .create(this).inject(this)
        val root = inflater.inflate(R.layout.fragment_list_table, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tableViewModel.getAllTables(idRestaurant, view)

        table_list.adapter = tableAdapter

        tableViewModel.tables().observe(viewLifecycleOwner, Observer {
            tableAdapter.submitList(it)
        })
    }

    override fun onClick(idTable: String) {
        var bundle = Bundle()
        bundle.putString("idTable", idTable)
        findNavController().navigate(
            R.id.action_navigation_rest_tables_to_navigation_rest_book,
            bundle
        )
    }
}