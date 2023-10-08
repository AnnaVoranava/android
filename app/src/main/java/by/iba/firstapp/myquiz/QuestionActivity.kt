package by.iba.firstapp.myquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import by.iba.firstapp.myquiz.R.id.btn_quiz_start

class QuestionActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var questionView: TextView
    lateinit var answer1View: TextView
    lateinit var answer2View: TextView
    lateinit var card1View: CardView
    lateinit var card2View: CardView
    private var result=false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        submitButton = findViewById(R.id.btn_submit)
        questionView = findViewById(R.id.tv_question)
        answer1View = findViewById(R.id.tv_answer_1)
        answer2View = findViewById(R.id.tv_answer_2)
        card1View = findViewById(R.id.cv_answer_1)
        card2View = findViewById(R.id.cv_answer_2)
        submitButton.isEnabled=false


        val question = intent.getStringExtra(IntentKeys.KEY_QUESTION)//TODO
        val answer0 = intent.getStringExtra(IntentKeys.KEY_ANSWER0)
        val answer1 = intent.getStringExtra(IntentKeys.KEY_ANSWER1)
        val correctIndex = intent.getIntExtra(IntentKeys.KEY_CORRECT_INDEX, -1)
        Log.d(
            QuestionActivity::class.java.simpleName,
            "question: $question, answer0: $answer0, answer1: $answer1, correctIndex: $correctIndex"
        )

        questionView.text = question?.let { it }?:"Error"
        answer1View.text=answer0
        answer2View.text=answer1

        answer1View.setOnClickListener{
            card1View.elevation=0F
            card2View.elevation=10F
            result = correctIndex == 0
            submitButton.isEnabled=true
        }
        answer2View.setOnClickListener{
            card1View.elevation=10F
            card2View.elevation=0F
            result = correctIndex == 1
            submitButton.isEnabled=true
        }
        submitButton.setOnClickListener {
            intent= Intent(applicationContext, MainActivity::class.java)
            intent.putExtra(IntentKeys.KEY_RESULT, result)
            setResult(Activity.RESULT_OK, intent)
            finish()

        }
        Log.d("Lifecycle", "QuestionActivity onCreate")
    }
    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "MainActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "QuestionActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "QuestionActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "QuestionActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "QuestionActivity onDestroy")
    }
}

