//package com.example.eatbook.ui.sign.up
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.navigation.findNavController
//import com.example.eatbook.R
//import kotlinx.android.synthetic.main.sign_up_fragment.*
//
//class SignUpFragment : Fragment() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.sign_up_fragment, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        txv_redirect_sign_in.setOnClickListener { view ->
//            view.findNavController().navigate(R.id.action_navigation_sign_up_to_navigation_sign_in)
//        }
//    }
//}