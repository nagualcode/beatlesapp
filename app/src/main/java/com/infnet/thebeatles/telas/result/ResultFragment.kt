package com.infnet.thebeatles.telas.result

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.infnet.thebeatles.R
import com.infnet.thebeatles.databinding.ResultFragmentBinding


class ResultFragment : Fragment() {

    private lateinit var viewModel: ResultViewModel
    private lateinit var viewModelFactory: ResultViewModelFactory
    private lateinit var binding: ResultFragmentBinding
    private var message: String = ""

    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //objeto biding

        val binding: ResultFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.result_fragment,
            container, false
        )

        // gerar viewmodel com viewmodelfactory

        viewModelFactory = ResultViewModelFactory(args.finalScore, args.result)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultViewModel::class.java)
        binding.resultViewModel = viewModel
        binding.lifecycleOwner = this

        //observer do  _eventPlayAgain dentro do viewmodel de resultado

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ResultFragmentDirections.restart())
                viewModel.onPlayAgainFinish()
            }
        })

        viewModel.result.observe(viewLifecycleOwner, Observer { gameResult ->
            if (gameResult) {

                message = getString(R.string.share_text_win, "won", viewModel.score.value)
                binding.scoreTextView.text = message
            } else {
                message = getString(R.string.share_text_lost, "lost", viewModel.score.value)
                binding.scoreTextView.text = message
            }
            setHasOptionsMenu(gameResult)
        })


        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        return binding.root
    }

    // menu the opções

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {

            menu.findItem(R.id.share)?.isVisible = false
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> share()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getShareIntent(): Intent {

        return ShareCompat.IntentBuilder.from(requireActivity())

            .setText(message)
            .setType("text/plain")
            .intent
    }

    // compartilhamento do intent

    private fun share() {
        startActivity(getShareIntent())
    }
}