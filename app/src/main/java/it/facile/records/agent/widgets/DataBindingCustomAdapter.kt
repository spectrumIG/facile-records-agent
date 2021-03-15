package it.facile.records.agent.widgets

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import androidx.databinding.BindingAdapter
import coil.load
import it.facile.records.agent.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if(!imageUrl.isNullOrEmpty()) {
        view.load(imageUrl) {
            crossfade(true)
            error(R.drawable.ic_beer_mug_empty_svgrepo_com)
        }
    }
}

@BindingAdapter("textForDate")
fun TextView.textForDate(date: String?) {
    this.text = context.getString(R.string.brewed_before, date)
}


@SuppressLint("SetTextI18n")
@BindingAdapter("fieldName", "textForSingleNullable")
fun textAndField(view: TextView, fieldName: String?, text: String?) {
    view.text = fieldName?.capitalize() + ": " + {
        if(text.isNullOrEmpty()) {
            " - "
        } else {
            text
        }
    }.invoke()
}

@BindingAdapter("paragraphName", "formTextListField")
fun formBoldTextListAndField(view: TextView, paragraphName: String, list: List<String?>?) {
    val stringBuilder = SpannableStringBuilder().bold {
        this.insert(0, paragraphName.capitalize())
    }
    if(list.isNullOrEmpty()) {
        stringBuilder.append(" - ")
    } else {
        stringBuilder.append(":")

        list.forEach { stringItem ->
            stringBuilder.append(" $stringItem,")
        }
    }
    view.text = stringBuilder.removeSuffix(",")
}

@BindingAdapter("paragraphName", "formText")
fun formBoldTextAndField(view: TextView, paragraphName: String, text: String?) {
    val stringBuilder = SpannableStringBuilder().bold {
        this.insert(0, paragraphName.capitalize())
    }
    if(text.isNullOrEmpty()) {
        stringBuilder.append(" - ")
    } else {
        stringBuilder.append(":")
        stringBuilder.append(" $text")
    }
    view.text = stringBuilder
}


@BindingAdapter( "formatComplexList")
fun formatComplexList(view: TextView, list: List<Any?>?) {
    val stringBuilder = StringBuilder()
    if(list.isNullOrEmpty()) {
        stringBuilder.insert(0, " - ")
    } else {
        list.forEach { stringItem ->
            stringBuilder.append(" $stringItem,")
        }
    }
    view.text = stringBuilder.removeSuffix(",").toString()
}