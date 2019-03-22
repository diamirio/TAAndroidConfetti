package com.tailoredapps.confetti

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


/**
 * Attaches a click listener to the [View] that fires the confetti after [clicksToRain] clicks.
 */
fun View.attachConfettiClick(taConfetti: TAConfetti, clicksToRain: Int = 100) {
    var clicks = 0
    setOnClickListener {
        clicks++
        if (clicks >= clicksToRain && !taConfetti.started) {
            taConfetti.start(this)
        }
    }
}


/**
 * Convenience function to create a [TAConfetti] for an [AppCompatActivity].
 */
fun AppCompatActivity.confetti(): Lazy<TAConfetti> = lazy { TAConfetti(this) }


/**
 * Convenience function to create a [TAConfetti] for a [Fragment].
 */
fun Fragment.confetti(): Lazy<TAConfetti> = lazy { TAConfetti(this) }
