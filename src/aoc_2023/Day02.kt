package aoc_2023

import java.lang.RuntimeException

data class Round(val blue: Int?, val green: Int?, val red: Int?)
data class Game(val consumesTotalDrawings: Round, val gameId: Int)

private val testInput = """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""".trimIndent()

private val realInput = """
""".trimIndent()

private fun formatInput(input: String): List<String> = input.split("\n")

private fun List<Round>.getMax(gameId: Int): Game {
    val maxBlue = this.maxOf { it.blue ?: 0 }
    val maxRed = this.maxOf { it.red ?: 0 }
    val maxGreen = this.maxOf { it.green ?: 0 }

    return Game(Round(blue = maxBlue, red = maxRed, green = maxGreen), gameId)
}

private fun formatRoundString(game: String): Pair<List<Round>, Int> {

    val indexOfColon = game.indexOf(":")

    val gameId = game.subSequence(5, indexOfColon).toString().toInt() // Every game starts with Game xx:

    val rounds = game.subSequence((indexOfColon + 2)..game.lastIndex).split("; ")

    val roundRounds = rounds.map { round ->
        val counts = mutableMapOf<String, Int?>("green" to null, "red" to null, "blue" to null)

        for (balDraw in round.split(", ")) {
            val (ballCountString: String, color: String) = balDraw.split(" ")
            val ballCount: Int = ballCountString.toInt()

            if (color !in counts) {
                throw RuntimeException("Cound not find color: $color")
            }
            counts[color] = ballCount
        }

        Round(blue = counts["blue"], green = counts["green"], red = counts["red"])
    }

    return Pair(roundRounds, gameId)
}

private fun part1(input: List<String>): Int {

    val allowedGames = input.map { formatRoundString(it) }.map { it.first.getMax(it.second) }.filter { game ->
        (game.consumesTotalDrawings.blue ?: 0) <= 14 &&
                (game.consumesTotalDrawings.green ?: 0) <= 13 &&
                (game.consumesTotalDrawings.red ?: 0) <= 12
    }

    return allowedGames.sumOf { it.gameId }
}

private fun part2(input: List<String>): Int {

    return input.map { formatRoundString(it) }.map { it.first.getMax(it.second) }
        .sumOf {
            val minimums = it.consumesTotalDrawings

            (minimums.blue ?: 1) * (minimums.green ?: 1) * (minimums.red ?: 1)
        }
}

fun main() {

    val result = part2(formatInput(testInput))

    println(result)

}