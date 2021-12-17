package com.infnet.thebeatles.telas.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.infnet.thebeatles.R
import com.infnet.thebeatles.databinding.RulesFragmentBinding

class RulesFragment : Fragment() {

    private lateinit var binding: RulesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.rules_fragment, container
            , false
        )

        return binding.root
    }
}