package com.tailoredapps.confetti

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.Preference

/**
 * Convenience function to create a [TAConfetti] for an [AppCompatActivity].
 */
fun AppCompatActivity.confetti(): Lazy<TAConfetti> = lazy { TAConfetti(this) }

/**
 * Convenience function to create a [TAConfetti] for a [Fragment].
 */
fun Fragment.confetti(): Lazy<TAConfetti> = lazy { TAConfetti(this) }

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
 * Attaches a click listener to the [Preference] that fires the confetti on root after [clicksToRain] clicks.
 */
fun Preference.attachConfettiClick(taConfetti: TAConfetti, root: ViewGroup, clicksToRain: Int = 100) {
    var clicks = 0
    setOnPreferenceClickListener {
        clicks++
        if (clicks >= clicksToRain && !taConfetti.started) {
            taConfetti.start(root)
        }
        false
    }
}

/**
 * Attaches a click listener to the [Preference] that tries to fire the confetti on the root of view after
 * [clicksToRain] clicks.
 */
fun Preference.attachConfettiClick(taConfetti: TAConfetti, view: View, clicksToRain: Int = 100) {
    var clicks = 0
    setOnPreferenceClickListener {
        clicks++
        if (clicks >= clicksToRain && !taConfetti.started) {
            taConfetti.start(view)
        }
        false
    }
}
