enum class PlayerSymbols(val value: Char) {
    ROCK("X".toCharArray()[0]),
    PAPER("Y".toCharArray()[0]),
    SCISSOR("Z".toCharArray()[0])
}

enum class ElfSymbols(val value: Char) {
    ROCK("A".toCharArray()[0]),
    PAPER("B".toCharArray()[0]),
    SCISSOR("C".toCharArray()[0])
}

enum class PlayerStrategy(val value: Char) {
    WIN("Z".toCharArray()[0]),
    DRAW("Y".toCharArray()[0]),
    LOOSE("X".toCharArray()[0]),
}

fun main(args: Array<String>) {

    val testInput = """
        A Y
        B X
        C Z
    """.trimIndent()

    val input = testInput.filter { it.isLetter() }.toList()

    val rounds = mutableListOf<Pair<Char, Char>>()

    for (i in 0 until (input.size / 2)) {
        val k = i * 2
        rounds.add(Pair(input[k], input[k + 1]))
    }

    val roundsPlayed = rounds.map { it.chooseCorrectWeaponForTotalWin() }

    val scores = roundsPlayed.map { it.calculateScore() }
    println(scores)
    println(scores.sum())
}

fun Char.getWinningMove(): Char = when (this) {
    ElfSymbols.ROCK.value -> PlayerSymbols.PAPER.value
    ElfSymbols.PAPER.value -> PlayerSymbols.SCISSOR.value
    ElfSymbols.SCISSOR.value -> PlayerSymbols.ROCK.value
    else -> throw IllegalStateException("Symbol not found")
}

fun Char.getDrawMove(): Char = when (this) {
    ElfSymbols.ROCK.value -> PlayerSymbols.ROCK.value
    ElfSymbols.PAPER.value -> PlayerSymbols.PAPER.value
    ElfSymbols.SCISSOR.value -> PlayerSymbols.SCISSOR.value
    else -> throw IllegalStateException("Symbol not found")
}


fun Char.getLoosingMove(): Char = when (this) {
    ElfSymbols.ROCK.value -> PlayerSymbols.SCISSOR.value
    ElfSymbols.PAPER.value -> PlayerSymbols.ROCK.value
    ElfSymbols.SCISSOR.value -> PlayerSymbols.PAPER.value
    else -> throw IllegalStateException("Symbol not found")
}

fun Pair<Char, Char>.chooseCorrectWeaponForTotalWin(): Pair<Char, Char> {
    val move = when (this.second) {
        PlayerStrategy.WIN.value -> this.first.getWinningMove()
        PlayerStrategy.DRAW.value -> this.first.getDrawMove()
        PlayerStrategy.LOOSE.value -> this.first.getLoosingMove()
        else -> throw IllegalStateException("Player strat not found")
    }

    return Pair(this.first, move)
}

fun Char.calculateCharScore(): Int = when (val char = this) {
    PlayerSymbols.ROCK.value -> 1
    PlayerSymbols.PAPER.value -> 2
    PlayerSymbols.SCISSOR.value -> 3
    else -> 0
}

fun Pair<Char, Char>.calculateScoreIfWin(): Int {

    val drawScore = 3
    val winnerScore = 6

    return when (this.first) {
        ElfSymbols.ROCK.value -> when (this.second) {
            PlayerSymbols.PAPER.value -> winnerScore
            PlayerSymbols.ROCK.value -> drawScore
            else -> 0
        }
        ElfSymbols.PAPER.value -> when (this.second) {
            PlayerSymbols.SCISSOR.value -> winnerScore
            PlayerSymbols.PAPER.value -> drawScore
            else -> 0
        }
        ElfSymbols.SCISSOR.value -> when (this.second) {
            PlayerSymbols.ROCK.value -> winnerScore
            PlayerSymbols.SCISSOR.value -> drawScore
            else -> 0
        }
        else -> 0
    }
}

fun Pair<Char, Char>.calculateScore() = this.second.calculateCharScore() + this.calculateScoreIfWin()