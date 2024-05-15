package com.devrex.sharedtest

import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object Helper {

    fun readFileResource(fileName: String): String {
        val inputStream = javaClass.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}