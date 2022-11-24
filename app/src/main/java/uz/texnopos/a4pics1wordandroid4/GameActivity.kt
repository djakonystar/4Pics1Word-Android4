package uz.texnopos.a4pics1wordandroid4

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import uz.texnopos.a4pics1wordandroid4.data.Constants
import uz.texnopos.a4pics1wordandroid4.data.Question
import uz.texnopos.a4pics1wordandroid4.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var questions: List<Question>
    private var currentQuestionId = -1
    private val optionLetters = mutableListOf<TextView>()
    private val answerLetters = mutableListOf<TextView>()
    private val currentAnswers = mutableListOf<Pair<Int, String>>()
    private val currentOptions = mutableListOf<Char>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questions = Constants.getQuestions()

        binding.apply {
            optionLetters.addAll(
                listOf(
                    tvOption1, tvOption2, tvOption3, tvOption4, tvOption5, tvOption6,
                    tvOption7, tvOption8, tvOption9, tvOption10, tvOption11, tvOption12
                )
            )

            answerLetters.addAll(
                listOf(
                    tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
                    tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8
                )
            )
        }

        currentQuestionId++
        setQuestion()

        binding.apply {
            tvOption1.setOnClickListener { optionClick(it as TextView) }
            tvOption2.setOnClickListener { optionClick(it as TextView) }
            tvOption3.setOnClickListener { optionClick(it as TextView) }
            tvOption4.setOnClickListener { optionClick(it as TextView) }
            tvOption5.setOnClickListener { optionClick(it as TextView) }
            tvOption6.setOnClickListener { optionClick(it as TextView) }
            tvOption7.setOnClickListener { optionClick(it as TextView) }
            tvOption8.setOnClickListener { optionClick(it as TextView) }
            tvOption9.setOnClickListener { optionClick(it as TextView) }
            tvOption10.setOnClickListener { optionClick(it as TextView) }
            tvOption11.setOnClickListener { optionClick(it as TextView) }
            tvOption12.setOnClickListener { optionClick(it as TextView) }

            tvAnswer1.setOnClickListener { answerClick(it as TextView) }
            tvAnswer2.setOnClickListener { answerClick(it as TextView) }
            tvAnswer3.setOnClickListener { answerClick(it as TextView) }
            tvAnswer4.setOnClickListener { answerClick(it as TextView) }
            tvAnswer5.setOnClickListener { answerClick(it as TextView) }
            tvAnswer6.setOnClickListener { answerClick(it as TextView) }
            tvAnswer7.setOnClickListener { answerClick(it as TextView) }
            tvAnswer8.setOnClickListener { answerClick(it as TextView) }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        val currentQuestion = questions[currentQuestionId]

        binding.apply {
            currentAnswers.clear()
            updateAnswer()
            showContinue(false)

            tvLevel.text = (currentQuestionId + 1).toString()

            ivPic1.setImageResource(currentQuestion.images[0])
            ivPic2.setImageResource(currentQuestion.images[1])
            ivPic3.setImageResource(currentQuestion.images[2])
            ivPic4.setImageResource(currentQuestion.images[3])

            val options = currentQuestion.answer.toMutableList()

            repeat(12 - options.size) {
                options.add(Random.nextInt(1040, 1072).toChar())
            }
            options.shuffle()
            currentOptions.clear()
            currentOptions.addAll(options)

            options.forEachIndexed { index, letter ->
                optionLetters[index].text = letter.toString()
            }

            answerLetters.forEach {
                it.isVisible = true
            }

            for (i in currentQuestion.answer.length until answerLetters.size) {
                answerLetters[i].isVisible = false
            }
        }
    }

    private fun showContinue(show: Boolean) {
        binding.apply {
            bgOverlay.isVisible = show
            ivShine.isVisible = show
            btnNext.isVisible = show
            tvNext.isVisible = show
        }
    }

    private fun optionClick(optionTV: TextView) {
        val currentQuestion = questions[currentQuestionId]

        val index = optionLetters.indexOf(optionTV)
        val letter = currentOptions[index]

        currentAnswers.add(Pair(index, letter.toString()))
        updateAnswer()
        optionTV.text = ""

        if (currentQuestion.answer.length == currentAnswers.size) {
            if (currentQuestion.answer == currentAnswers.map { it.second }.joinToString("")) {
                showContinue(true)
            } else {
                optionLetters.forEach { option ->
                    option.isClickable = false
                }
            }
        }
    }

    private fun answerClick(answerTV: TextView) {
        answerTV.text = ""
        val index = answerLetters.indexOf(answerTV)
        val pair = currentAnswers[index]

        optionLetters[pair.first].text = pair.second
        currentAnswers.removeAt(index)
    }

    private fun updateAnswer() {
        currentAnswers.forEachIndexed { index, letter ->
            answerLetters[index].text = letter.second
        }
    }
}
