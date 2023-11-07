package com.smartgeek.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Arrays
import java.util.Timer


class MainActivity : AppCompatActivity() {

    private var gameActivity: Boolean = true
    private var activePlayerNo = 0
    private var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

    // State meanings:
    //    0 - X
    //    1 - O
    //    2 - Null
    // put all win positions in a 2D array
    private var winPositions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )
    private var count = 0
    private lateinit var textNotification: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textNotification = findViewById(R.id.tv_text1)
    }

    @SuppressLint("SetTextI18n")
    fun playerTap(view: View) {
        val img = view as ImageView
        val tappedImage = img.tag.toString().toInt()

        if (!gameActivity) {
            gameReset(view)
            gameActivity = true
        }

        if (count == 9) {
            gameActivity = false
        }

        gameState[tappedImage] = activePlayerNo
        img.translationY = -1000f

        if (activePlayerNo == 0) {
            img.setImageResource(R.drawable.icon_x)
            textNotification.text = "O player's turn"
            activePlayerNo = 1
            count++
        } else {
            img.setImageResource(R.drawable.icon_o)
            textNotification.text = "X player's turn"
            activePlayerNo = 0
            count++
        }
        img.animate().translationYBy(1000f).duration = 300

        var flag = 0
        // Check if any player has won if counter is > 4 as min 5 taps are
        // required to declare a winner
        if (count >= 4) {
            for (winPosition in winPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2
                ) {
                    flag = 1
                    // Somebody has won! - Find out who!
                    gameActivity = false
                    val winnerStr: String = if (gameState[winPosition[0]] == 0) {
                        "X has won"
                    } else {
                        "O has won"
                    }
                    // Update the winner announcement in text view
                    textNotification.text = winnerStr

                    Handler(Looper.getMainLooper()).postDelayed({
                        gameReset(view)
                    }, 5000)
                }
            }
            // set the status if the match draw
            if (count == 9 && flag == 0) {
                textNotification.text = "Match Draw"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun gameReset(view: View?) {
        gameActivity = true
        activePlayerNo = 0
        //set all position to Null
        Arrays.fill(gameState, 2)
        // remove all the images from the boxes inside the grid
        (findViewById<View>(R.id.iv_0) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.iv_1) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.iv_2) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.iv_3) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.iv_4) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.iv_5) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.iv_6) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.iv_7) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.iv_8) as ImageView).setImageResource(0)
        textNotification.text = "X's Turn - Tap to play"
    }

}