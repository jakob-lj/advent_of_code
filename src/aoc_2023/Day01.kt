package aoc_2023

class Day01 {
    val testInput = """
    two1nine
    eightwothree
    abcone2threexyz
    xtwone3four
    4nineeightseven2
    zoneight234
    7pqrstsixteen
""".trimIndent()

    val realInput = """""".trimIndent()

    val textToDigits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun String.addFirstAndLastLetter(textualDigit: String): String {
        return "${textualDigit[0]}$this${textualDigit[textualDigit.length - 1]}"
    }

    fun textToDigit(line: String): String {
        var mutableLine = line
        for (textualDigit in textToDigits.keys) {
            mutableLine =
                mutableLine.replace(
                    textualDigit,
                    textToDigits[textualDigit].toString().addFirstAndLastLetter(textualDigit)
                )
        }

        return mutableLine.filter { it.isDigit() }
    }

    enum class Part {
        ONE,
        TWO
    }

    fun part1(part: Part): Int {

        val data = realInput

        val linesWithNumbers = data.split("\n")
            .map { row -> if (part == Part.ONE) row.filter { it.isDigit() } else textToDigit(row) }

        val result = linesWithNumbers.map {
            "${it[0]}${
                it[it.length - 1]
            }".toInt()
        }

        return result.sum()
    }

}

fun main() {
    println(Day01().part1(Day01.Part.TWO))
}
