package com.smarthings.homework.extensions

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.smarthings.data.common.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

private const val DECIMAL_FORMAT = "#.##"
private const val ICON_BASE_URL = "https://openweathermap.org/img/wn/%s@2x.png"

fun Double.kelvinsToFahrenheit() = (((this - 273.15) * 1.8) + 32).round()

fun Double.kelvinsToCelsius() = (this - 273.15).round()

private fun Double.round(): String {
    return DecimalFormat(DECIMAL_FORMAT).format(this)
}

fun ImageView.load(icon: String) {
    Glide.with(context)
        .load(
            String.format(
                Locale.getDefault(),
                ICON_BASE_URL,
                icon
            )
        )
        .into(this)
}

fun Long.toReadableDate(): String {
    return DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(this * 1000))
}

inline fun ViewModel.launch(
    coroutineContext: CoroutineContext = CoroutineContextProvider().main,
    crossinline block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(coroutineContext) { block() }
}

inline fun <T> LiveData<T>.subscribe(
    owner: LifecycleOwner,
    crossinline onDataReceived: (T) -> Unit
) = observe(owner,
    { onDataReceived(it) })

fun snackBar(message: String, rootView: View) =
    Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

/**
 * Diff util convenience extension
 * Inspired on https://www.youtube.com/watch?v=pO47T8fMZUY
 *
 * @param initialValue delegate initial value
 *
 * @param areContentsTheSame optional function to compare item's content (e.g old.id == new.id, etc..)
 * default comparison is a simple equality between the two items
 *
 * @param areItemsTheSame optional function to compare item's equality
 * default comparison is a simple equality between the two items
 *
 * Read more about [equality](https://kotlinlang.org/docs/reference/equality.html) in Kotlin.
 *
 * @param <VH> A class that extends ViewHolder that will be used by the adapter.
 * @param <T> item list data type
 *
 */
fun <VH : RecyclerView.ViewHolder, T> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T> = emptyList(),
    areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = Delegates.observable(initialValue) { _, old, new ->
    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areItemsTheSame(old[oldItemPosition], new[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areContentsTheSame(old[oldItemPosition], new[newItemPosition])

        override fun getNewListSize(): Int = new.size
        override fun getOldListSize(): Int = old.size
    }).dispatchUpdatesTo(this)
}