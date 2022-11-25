package uz.texnopos.a4pics1wordandroid4

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import uz.texnopos.a4pics1wordandroid4.data.Constants
import uz.texnopos.a4pics1wordandroid4.data.Question
import uz.texnopos.a4pics1wordandroid4.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var questions: List<Question>
    private var currentQuestionId = -1
    var clickedImageId = -1
    private val optionLetters = mutableListOf<TextView>()
    private val answerLetters = mutableListOf<TextView>()
    private val currentAnswers = mutableListOf<Pair<String, TextView>>()
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



        optionLetters.forEach { optionTV ->
            optionTV.addTextChangedListener {
                val letter = it.toString()
                optionTV.isEnabled = letter.isNotEmpty()
            }
        }

        answerLetters.forEach { answerTV ->
            answerTV.addTextChangedListener {
                val letter = it.toString()
                answerTV.isEnabled = letter.isNotEmpty()
            }
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

            btnNext.setOnClickListener {
                currentQuestionId++
                setQuestion()
            }

            btnBack.setOnClickListener {
                finish()
            }

            ivPic1.setOnClickListener {
                clickedImageId = 0
                bigImage.setImageResource(questions[currentQuestionId].images[0])
                bigImage.visibility = View.VISIBLE
                bigImage.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@GameActivity,R.anim.animation_up_one
                    )
                )
            }

            ivShine.startAnimation(
                AnimationUtils.loadAnimation(
                    this@GameActivity,R.anim.rotate_anim
                )
            )


            bigImage.setOnClickListener {
                when(clickedImageId){
                    0-> {
                        bigImage.startAnimation(AnimationUtils.loadAnimation(
                            this@GameActivity,R.anim.animation_down_one
                        ))
                        Handler().postDelayed({
                            bigImage.visibility = View.GONE
                        },200L)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        val currentQuestion = questions[currentQuestionId]

        binding.apply {
            currentAnswers.clear()
            updateAnswer(currentQuestion)
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
                it.isClickable = true
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

            answerLetters.forEach {
                it.isClickable = false
            }
        }
    }

    private fun optionClick(optionTV: TextView) {
        val currentQuestion = questions[currentQuestionId]

        val index = optionLetters.indexOf(optionTV)
        val letter = currentOptions[index]

        val pairIndex = currentAnswers.indexOfFirst { it.first == "" }
        if (pairIndex == -1) {
            currentAnswers.add(Pair(letter.toString(), optionTV))
        } else {
            currentAnswers[pairIndex] = Pair(letter.toString(), optionTV)
        }
        updateAnswer(currentQuestion)
        optionTV.text = ""
    }

    private fun answerClick(answerTV: TextView) {
        answerTV.text = ""
        val index = answerLetters.indexOf(answerTV)
        val pair = currentAnswers[index]

        pair.second.text = pair.first
        currentAnswers[index] = Pair("", pair.second)
        updateAnswer(questions[currentQuestionId])
    }

    private fun updateAnswer(question: Question) {
        if (currentAnswers.isEmpty()) {
            answerLetters.forEach {
                it.text = ""
            }
            optionLetters.forEach { option ->
                option.isClickable = true
            }
            return
        }

        currentAnswers.forEachIndexed { index, letter ->
            answerLetters[index].text = letter.first
        }

        if (question.answer.length == currentAnswers.filter { it.first.isNotEmpty() }.size) {
            if (question.answer == currentAnswers.map { it.first }.joinToString("")) {
                showContinue(true)
            }
            optionLetters.forEach { option ->
                option.isClickable = false
            }
        } else {
            optionLetters.forEach { option ->
                option.isClickable = true
            }
        }
    }
}
