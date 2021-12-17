package com.infnet.thebeatles.telas.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.infnet.thebeatles.R
import com.infnet.thebeatles.databinding.TitleFragmentBinding


class TitleFragment : Fragment() {

    //instancia o objeto biding

    private lateinit var binding: TitleFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // infla o fragmento

        binding = DataBindingUtil.inflate(
            inflater, R.layout.title_fragment
            , container, false
        )

        // listener par ao botao de jogar

        binding.playButton.setOnClickListener {
            findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        return binding.root
    }
}