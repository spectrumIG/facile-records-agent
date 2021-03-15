package it.facile.records.agent.library.android.entity

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun String.isValidUrl(): Boolean {
    val regex = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|png)".toRegex()
    return regex.matches(this)
}

fun Context.hideKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
        hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Context.showKeyboard() {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
        toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}
