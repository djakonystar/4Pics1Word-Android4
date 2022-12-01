package uz.texnopos.a4pics1wordandroid4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import uz.texnopos.a4pics1wordandroid4.data.Constants
import uz.texnopos.a4pics1wordandroid4.data.Question
import uz.texnopos.a4pics1wordandroid4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var questions: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CheckLifecycle", "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questions = Constants.getQuestions()
        prefs = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)

        binding.btnPlay.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val questionId = prefs.getInt(Constants.LEVEL, 0)
        setQuestion(questionId)
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion(questionId: Int) {
        val currentQuestion = questions[questionId]

        binding.apply {
            val cycle = prefs.getInt(Constants.CYCLE, 0)
            tvLevel.text = (cycle * questions.size + questionId + 1).toString()

            ivPic1.setImageResource(currentQuestion.images[0])
            ivPic2.setImageResource(currentQuestion.images[1])
            ivPic3.setImageResource(currentQuestion.images[2])
            ivPic4.setImageResource(currentQuestion.images[3])
        }
    }
}
