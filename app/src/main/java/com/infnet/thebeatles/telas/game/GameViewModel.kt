package com.infnet.thebeatles.telas.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Collections.shuffle

class GameViewModel : ViewModel() {

    // classe para as questões:

    data class Question(
        val text: String,
        val answers: List<String>
    )

    // liveData encapsula para a lógica do gameFragment:

    private var _currentQuestion = MutableLiveData<String>()
    val currentQuestion: LiveData<String>
        get() = _currentQuestion

    private var _answers = MutableLiveData<List<String>>()
    val answers: LiveData<List<String>>
        get() = _answers

    private var _questionIndex = MutableLiveData<Int>()
    val questionIndex: LiveData<Int>
        get() = _questionIndex


    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    //um LiveData para o final do jogo:

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    //LiveData para o resultado de vitória:
    private val _eventGameResult = MutableLiveData<Boolean>()
    val eventGameResult: LiveData<Boolean>
        get() = _eventGameResult


    private lateinit var questionsList: MutableList<Question>
    private lateinit var rightAnswer: String
    private lateinit var resps: List<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        defineQuestion()
        nextQuestion()
        _score.value = 0
        _eventGameFinish.value = false
        _eventGameResult.value = false
    }


    // botão de próxima questão:

    private fun nextQuestion() {
        if (questionsList.isNotEmpty()) {

            _currentQuestion.value = questionsList[0].text
            resps = questionsList[0].answers
            rightAnswer = questionsList[0].answers[0]
            shuffle(resps)
            _answers.value = resps
            questionsList.removeAt(0)
        } else {
            //fim do jogo
            _eventGameFinish.value = true
        }
    }

    // método para checar respostas:

    fun onAnsweredQuestion(answerString: String) {
        //toast.show()
        if (answerString == rightAnswer) {
            _score.value = (score.value)?.plus(1)
            /**
             * check the score
             */
            if (_score.value!! >= 5) {
              //vitória:
                _eventGameResult.value = true
                _eventGameFinish.value = true
            } else {
                nextQuestion()
            }

        } else {

            nextQuestion()
        }

    }

    //começar novamente:

    fun onGameFinishComplete() {

        _eventGameResult.value = false

        _eventGameFinish.value = false
    }

    // Questões hardcoded (item 0 da lista é a resposta certa):

    private fun defineQuestion() {

        questionsList = mutableListOf(
            Question(
                text = "Como se chamava o produtor da banda The Beatles??",
                answers = listOf("George Martin", "Rick Rubin", "Liminha", "Butch Vig")
            ),
            Question(
                text = "Qual foi o último album que os Beatles gravaram?",
                answers = listOf("Abbey Road", "Let It Be", "White Album", "Sgt. Pepper")
            ),
            Question(
                text = "Qual destas músicas foi composta por George Harrison?",
                answers = listOf("Todas citadas", "Something", "Here Comes The Sun", "I Me Mine")
            ),
            Question(
                text = "Qual foi o primeiro sucesso dos Beattles a alcançar o no. 1 das paradas britânicas?",
                answers = listOf("Please, Please me", "Twist And Shout", "Help!", "I Wanna Hold Your Hand")
            ),
            Question(
                text = "Quem foi a inspiração para Paul Mccartney compor Hey Jude?",
                answers = listOf(
                    "O filho de John Lennon (julian Lennon)",
                    "Sua namorada (Linda Mccartney)",
                    "Sua mãe (Mary Mccartney)",
                    "O empresário da banda (Brian Epstein)"
                )
            ),
            Question(
                text = "Nome da casa noturna de Liverpool onde os Beatles ficaram famosos?",
                answers = listOf("Cavern Club", "The Pub", "Albert Hall", "Maida Valley")
            ),
            Question(
                text = "Qual Beatle canta a música With A Little Help From My Friends (do album Sgt. Pepper)?",
                answers = listOf(
                    "Ringo Starr",
                    "Paul Mccartney",
                    "John Lennon",
                    "George Harrison"
                )
            ),
            Question(
                text = "Qual o nome do tecladista que das gravações do album Let It Be e do concerto no telhado?",
                answers = listOf("Billy Preston", "Stevie Wonder", "Ray Charles", "Sid Barret")
            ),
            Question(
                text = "Qual Beatle toca bateria na gravação de Back In The URSS?",
                answers = listOf(
                    "Paul Mccartney",
                    "Ringo Starr",
                    "John Lennon",
                    "George Harrison"
                )
            ),
            Question(
                text = "Qual era o nome dado a música Yesterday , antes dela receber sua letra definitiva?",
                answers = listOf("Scrambled Eggs", "Mother Mary", "Dig A Pony", "Blackbird")
            )
        )
        questionsList.shuffle()
    }


}