package com.infnet.thebeatles.telas.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.infnet.thebeatles.R
import com.infnet.thebeatles.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    //objeto para biding
    private lateinit var binding: GameFragmentBinding

    // instância GameViewModel para o viewModel

    private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // inflando o fragmento:

        binding = DataBindingUtil.inflate(
            inflater, R.layout.game_fragment, container
            , false
        )

        Log.i("GameFragment", "Called ViewModelProviders.of! ")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        binding.answerButton.setOnClickListener { v: View? ->
            val checkedId = binding.questionsRadioGroup.checkedRadioButtonId
            val radio: RadioButton = binding.questionsRadioGroup.findViewById(checkedId)
            val toast = Toast.makeText(activity, "Game Won!", Toast.LENGTH_SHORT)
            viewModel.onAnsweredQuestion(radio.text.toString())

        }
        // observer para o final do jogo:

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasWon ->
            if (hasWon) {
                gameFinished(viewModel.eventGameResult)
                viewModel.onGameFinishComplete()
            }
        })
        // observer para a pontuação
        viewModel.score.observe(viewLifecycleOwner, Observer { updateScore ->
            binding.scoreCount.text = updateScore.toString()

        })

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        return binding.root
    }

    // vitória:

    private fun gameFinished(endResult: LiveData<Boolean>) {
        val currentScore = viewModel.score.value ?: 0
        val currentResult = viewModel.eventGameResult.value ?: false

        // não fez pontos suficientes:
        val action = GameFragmentDirections.actionGameFragmentToResultFragment(
            currentScore,
            currentResult
        )
        NavHostFragment.findNavController(this).navigate(action)
    }


}