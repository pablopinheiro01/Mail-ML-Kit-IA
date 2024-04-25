package com.alura.mail.util

import android.content.Context
import java.io.File


class FileUtil(private val context: Context) {
    fun getSizeModel(modelName: String): String {
        val modelFile =
            File(context.filesDir.parent, "no_backup/com.google.mlkit.translate.models/$modelName")

        return formatBytesToMB(getFolderSize(modelFile))
    }

    private fun getFolderSize(folder: File): Long {
        var size: Long = 0
        val files = folder.listFiles() ?: return 0

        for (file in files) {
            size += if (file.isFile) {
                file.length()
            } else if (file.isDirectory) {
                getFolderSize(file)
            } else {
                0
            }
        }
        return size
    }

    private fun formatBytesToMB(bytes: Long): String {
        return "${bytes / 1024 / 1024} MB"
    }
}