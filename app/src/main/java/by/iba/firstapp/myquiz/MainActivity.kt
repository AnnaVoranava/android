package by.iba.firstapp.myquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import kotlin.jvm.internal.Intrinsics.Kotlin

class MainActivity : AppCompatActivity() {
    private lateinit var quizItemList: List<QuizItem>
    private lateinit var startButton: Button
    private var currentQuizItem: QuizItem? = null
    private var activeQuiz: MutableMap<QuizItem, AnswerType>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quizItemList = createQuestionList()

        startButton = findViewById(R.id.btn_quiz_start)
        startButton.setOnClickListener {
            checkNextOrFinish()
        }

    }


    private fun checkNextOrFinish() {
        if (activeQuiz == null) {
            startNewQuiz()
        }
        activeQuiz!!.forEach() { (key, value) ->
            if (value == AnswerType.UNDEFINED) currentQuizItem = key
        }
        if (currentQuizItem == null) {
            showResult()
        } else {
            val newIntent = Intent(applicationContext, QuestionActivity::class.java)
            newIntent.putExtra(IntentKeys.KEY_QUESTION, currentQuizItem!!.question)//TODO
            newIntent.putExtra(IntentKeys.KEY_ANSWER0, currentQuizItem!!.answer.get(0))
            newIntent.putExtra(IntentKeys.KEY_ANSWER1, currentQuizItem!!.answer.get(1))
            newIntent.putExtra(IntentKeys.KEY_CORRECT_INDEX, currentQuizItem!!.correctId)
            startActivity(newIntent)
            val activityLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val incomingIntent = result.data
                        val isCorrect =
                            incomingIntent?.getBooleanExtra(IntentKeys.KEY_RESULT, false) ?: false
                        activeQuiz?.put(
                            currentQuizItem!!,
                            if (isCorrect) AnswerType.CORRECT else AnswerType.INCORRECT

                        )
                        currentQuizItem = null
                        checkNextOrFinish()
                    }
                }
        }
    }

    private fun showResult() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "MainActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "MainActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "MainActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "MainActivity onDestroy")
    }

    private fun startNewQuiz() {
        activeQuiz = mutableMapOf()
        for (index in 0..3) {
            activeQuiz!!.put(quizItemList.get(index), AnswerType.UNDEFINED)
        }
    }
}


data class QuizItem(val question: String, val answer: List<String>, val correctId: Int)

fun createQuestionList(): List<QuizItem> {
    val quizItemList: MutableList<QuizItem> = mutableListOf()
    quizItemList.add(QuizItem("Котлин - статический язык?", listOf("Да", "Нет"), 0))
    quizItemList.add(QuizItem("Сколько примитивных типов в Котлин?", listOf("0", "8"), 1))
    quizItemList.add(QuizItem("Является ли Котлин функциональным языком?", listOf("Да", "Нет"), 1))
    quizItemList.add(QuizItem("Чем являются функции в Котлин?", listOf("Объект", "Переменная"), 0))
    return quizItemList
}

enum class AnswerType {
    CORRECT, INCORRECT, UNDEFINED
}

class IntentKeys {

    companion object {
        val KEY_QUESTION: String = "question"
        val KEY_ANSWER0: String = "answer0"
        val KEY_ANSWER1: String = "answer1"
        val KEY_CORRECT_INDEX: String = "correctIndex"
        val KEY_RESULT = "KEY_RESULT"

    }
}