package com.reyndev.simpliweather

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setText")
fun setText(textView: TextView, text: String?) {
    textView.text = text
}