fun main() {

    val testInput = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()

    val input = testInput.split("\n")

    val maxCalories = input.fold(mutableListOf<Int>()) { acc, s ->
        if (acc.size == 0) {
            acc.add(s.toInt())
        } else if (s == "") {
            acc.add(0)
        } else {
            acc[acc.size - 1] += s.toInt()
        }
        acc
    }.max()

    println(maxCalories)
}
