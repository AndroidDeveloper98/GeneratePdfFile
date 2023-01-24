package com.innovative.generatepdffile.utility

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by sumit on 24-01-2023
 */

infix fun View.onClick(click : () -> Unit){
    setOnClickListener { click() }
}

fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit
) = launch {
    onPreExecute()
    val result = withContext(Dispatchers.IO) {
        // runs in background thread without blocking the Main Thread
        doInBackground()
    }
    onPostExecute(result)
}