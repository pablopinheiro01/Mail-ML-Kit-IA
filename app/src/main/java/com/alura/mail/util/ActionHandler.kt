package com.alura.mail.util

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.CalendarContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.alura.mail.R
import com.alura.mail.model.Suggestion
import com.alura.mail.model.SuggestionAction

class ActionHandler {
    fun executeAction(context: Context, suggestion: Suggestion) {
        when (suggestion.action) {
            SuggestionAction.ADDRESS -> {
                openMaps(context, suggestion.text)
            }

            SuggestionAction.DATE_TIME -> {
                addEvent(context, suggestion.text)
            }

            SuggestionAction.EMAIL -> {
                sendEmail(context, suggestion.text)
            }

            SuggestionAction.PHONE_NUMBER -> {
                callPhone(context, suggestion.text)
            }

            SuggestionAction.URL -> {
                openUrl(context, suggestion.text)
            }

            else -> {
                copyToTransferArea(context, suggestion.text)
            }
        }
    }

    fun copyToTransferArea(context: Context, text: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(
            context,
            context.getString(R.string.copied_to_transfer_area, text), Toast.LENGTH_SHORT
        ).show()
    }

    private fun sendEmail(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(text))
            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject))
            putExtra(Intent.EXTRA_TEXT, context.getString(R.string.email_body))
        }
        context.startActivity(intent)
    }

    private fun openMaps(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:0,0?q=$text")
        context.startActivity(intent)
    }

    private fun openUrl(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(text)
        context.startActivity(intent)
    }

    private fun callPhone(context: Context, text: String) {
        val hasCallPermission = context.checkSelfPermission(Manifest.permission.CALL_PHONE)
        if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.CALL_PHONE),
                42
            )
            return
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$text")
            context.startActivity(intent)
        }
    }

    private fun addEvent(context: Context, title: String) {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.Events.EVENT_LOCATION, "Alura Cursos Online")
        context.startActivity(intent)
    }
}