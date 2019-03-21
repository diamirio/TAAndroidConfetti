package com.tailoredapps.confetti

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.github.jinatonic.confetti.ConfettiManager
import com.github.jinatonic.confetti.ConfettiSource
import com.github.jinatonic.confetti.ConfettoGenerator
import com.github.jinatonic.confetti.confetto.BitmapConfetto


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
        val size: Int = resources.getDimensionPixelSize(R.dimen.big_confetti_size)
        val generator = ConfettoGenerator {
            BitmapConfetto(
                Bitmap.createScaledBitmap(
                    confetti ?: BitmapFactory.decodeResource(resources, R.drawable.ta_confetti),
                    size,
                    size,
                    false
                )
            )
        }
        val velocitySlow: Int = resources.getDimensionPixelOffset(R.dimen.default_velocity_slow)
        val velocityNormal: Int = resources.getDimensionPixelOffset(R.dimen.default_velocity_normal)

        return ConfettiManager(
            context,
            generator,
            ConfettiSource(0, -size, this.width, -size),
            this
        ).apply {
            setVelocityX(0f, velocitySlow.toFloat())
            setVelocityY(velocityNormal.toFloat(), velocitySlow.toFloat())
            setRotationalVelocity(180f, 90f)
            setTouchEnabled(true)
            setNumInitialCount(0)
            setEmissionDuration(ConfettiManager.INFINITE_DURATION)
            setEmissionRate(20f)
        }
    }
}