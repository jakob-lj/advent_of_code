fun main() {

    val input = readInput("day4")

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
