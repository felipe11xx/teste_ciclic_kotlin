package com.example.felipe.pokeclic.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ProgressBar

class BarraDeProgresso() {

    internal fun showProgress(context: Context,show: Boolean, mProgressView: ProgressBar) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = context.resources.getInteger(android.R.integer.config_shortAnimTime)


            mProgressView.visibility = if (show) View.VISIBLE else View.GONE
            mProgressView.animate().setDuration(shortAnimTime.toLong()).alpha(
                (if (show) 1 else 0).toFloat()
            ).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mProgressView.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.visibility = if (show) View.VISIBLE else View.GONE

        }
    }

    companion object {

        val instance = BarraDeProgresso()
    }
}
