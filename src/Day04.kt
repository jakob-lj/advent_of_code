fun main() {

    val input = readInput("day4_test")

    part1(input)
    part2(input)
}

fun part1(input: List<String>) {

    val data = input.map {
        val compartments = it.split(",")
        val first = compartments[0].split("-")
        val second = compartments[1].split("-")

        Pair(Pair(first[0].toInt(), first[1].toInt()), Pair(second[0].toInt(), second[1].toInt()))
    }.count {
        val first = it.first
        val second = it.second

        (first.first <= second.first && first.second >= second.second) || (first.first >= second.first && first.second <= second.second)
    }

    println(data)
}

fun part2(input: List<String>) {

    val data = input.map {
        val compartments = it.split(",")
        val first = compartments[0].split("-")
        val second = compartments[1].split("-")

        Pair(Pair(first[0].toInt(), first[1].toInt()), Pair(second[0].toInt(), second[1].toInt()))
    }.count {
        val first = it.first
        val second = it.second

        first.second <= second.first && first.first >= second.first || second.first <= first.second && second.second >= first.first
    }

    println(data)
}
