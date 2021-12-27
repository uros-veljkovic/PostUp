package com.urkeev14.myapplication.utils.extensions

import android.view.View

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
