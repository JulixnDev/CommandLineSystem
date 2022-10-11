package dev.julixn.cls.utils

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

fun remove(arr: Array<*>, index: Int): Array<*> {
    if (index < 0 || index >= arr.size) {
        return arr
    }

    val result = arr.toMutableList()
    result.removeAt(index)
    return result.toTypedArray()
}

fun downloadFile(urlString: String, outputFile: File, drawProgress: Boolean): String? {
    var input: InputStream? = null
    var output: OutputStream? = null
    var connection: HttpURLConnection? = null
    try {
        val url = URL(urlString)
        connection = url.openConnection() as HttpURLConnection
        connection.connect()

        if (connection.responseCode !== HttpURLConnection.HTTP_OK) {
            return "Server returned HTTP " + connection.responseCode + " " + connection.responseMessage
        }

        // might be -1: server did not report the length
        val fileLength: Int = connection.contentLength

        // download the file
        input = connection.inputStream
        output = FileOutputStream(outputFile)
        val data = ByteArray(4096)
        var total: Long = 0
        var count: Int
        while (input.read(data).also { count = it } != -1) {
            total += count.toLong()
            if (fileLength > 0)
                drawProgress(
                    100,
                    (total * 100 / fileLength).toInt(),
                    "File-Download",
                    total,
                    fileLength
                )
            output.write(data, 0, count)
        }
    } catch (e: Exception) {
        return e.toString()
    } finally {
        try {
            output?.close()
            input?.close()
        } catch (ignored: IOException) {
        }
        connection?.disconnect()
    }
    return null
}

fun drawProgress(length: Int, progress: Int, label: String, totalLength: Long, fileLength: Int) {
    var fillLength = progress
    var emptyLength = length - progress

    print("\r$label ($progress% | ${totalLength.toDouble() / 1000}KB / ${fileLength.toDouble() / 1000}KB) > [")

    do {
        print("=")
        fillLength--
    } while (fillLength > 0)

    if (emptyLength != 0) {
        do {
            print(" ")
            emptyLength--
        } while (emptyLength > 0)
    }
    print("]")
    Thread.sleep(250)
}