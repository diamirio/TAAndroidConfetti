package com.tailoredapps.confetti.example

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.tailoredapps.confetti.TAConfetti
import com.tailoredapps.confetti.attachConfettiClick
import com.tailoredapps.confetti.confetti
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val confetti: TAConfetti by confetti()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTest.attachConfettiClick(confetti, clicksToRain = 10)
    }
}
