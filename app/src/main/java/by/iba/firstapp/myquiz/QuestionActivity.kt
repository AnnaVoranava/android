package by.iba.firstapp.myquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val question=intent.getStringExtra(IntentKeys.KEY_QUESTION)//TODO
        val answer0=intent.getStringExtra(IntentKeys.KEY_ANSWER0)
        val answer1=intent.getStringExtra(IntentKeys.KEY_ANSWER1)
        val correctIndex=intent.getIntExtra(IntentKeys.KEY_CORRECT_INDEX,-1)
        Log.d("MyTag","question: $question, answer0: $answer0, answer1: $answer1, correctIndex: $correctIndex")   }
}

