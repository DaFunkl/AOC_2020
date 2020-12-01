package util

import java.io.File

object InputReader {

    fun getInputAsString(day: Int) = fromResources(day).readText()


    fun getInputAsList(day: Int) = fromResources(day).readLines()

    private fun fromResources(day: Int): File {
        return File(javaClass.classLoader.getResource("input_day_$day.txt").toURI())
    }
}