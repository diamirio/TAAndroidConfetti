package com.tailoredapps.confetti

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.github.jinatonic.confetti.ConfettiManager
import com.github.jinatonic.confetti.ConfettiSource
import com.github.jinatonic.confetti.ConfettoGenerator
import com.github.jinatonic.confetti.confetto.BitmapConfetto
import com.github.jinatonic.confetti.confetto.Confetto
import java.util.*


/**
 * Displays TailoredApps confetti when fun start(...) is called, until fun stop() is called.
 * If a LifecycleOwner is handed over, fun stop() will be called automatically in onPause.
 */
class TAConfetti(
    lifecycleOwner: LifecycleOwner? = null,
    val confetti: Bitmap? = null
) : LifecycleObserver {
    private var manager: ConfettiManager? = null

    init {
        lifecycleOwner?.lifecycle?.addObserver(this)
    }

    val started: Boolean
        get() = manager != null

    /**
     * Tries to find the root of the [View] to start to display the confetti.
     */
    fun start(view: View) {
        (view.rootView as? ViewGroup)?.let {
            start(it)
        }
    }

    /**
     * Starts to display the confetti in a root [ViewGroup].
     */
    fun start(root: ViewGroup) {
        manager?.terminate()
        manager = root.createConfettiManager().animate()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop() {
        manager?.terminate()
        manager = null
    }

    private fun ViewGroup.createConfettiManager(): ConfettiManager {
        val size = 125

        return ConfettiManager(
            context,
            object : ConfettoGenerator {
                override fun generateConfetto(random: Random?): Confetto {
                    return BitmapConfetto(
                        Bitmap.createScaledBitmap(
                            confetti ?: BitmapFactory.decodeResource(resources, R.drawable.ta_confetti),
                            size,
                            size,
                            false
                        )
                    )
                }
            },
            ConfettiSource(0, -size, this.width, -size),
            this
        ).apply {
            setVelocityX(0f, 175f)
            setVelocityY(350f, 175f)
            setRotationalVelocity(180f, 90f)
            setTouchEnabled(true)
            setNumInitialCount(0)
            setEmissionDuration(ConfettiManager.INFINITE_DURATION)
            setEmissionRate(20f)
        }
    }
}