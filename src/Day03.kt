data class Compartment(val items: List<Char>)

data class Rucksack(val first: Compartment, val second: Compartment)


fun main() {

    val testInput = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()

    val input = testInput.split("\n").map { it.filter { inner -> inner.isLetter() } }

    val rucksacks = input.map {
        val charArray = it.toCharArray().toList()
        Rucksack(
            Compartment(charArray.subList(0, charArray.size / 2).mergeDuplicates()),
            Compartment(charArray.subList(charArray.size / 2, charArray.size).mergeDuplicates())
        )
    }


    val existsInBothCompartments: List<List<Char>> = rucksacks.map {
        (it.first.items + it.second.items).findDuplicates()
    }

    val sum = existsInBothCompartments.sumOf { it.sumOf { char -> char.getAocValue() } }

    println(sum)
}

fun List<Char>.mergeDuplicates(): List<Char> {
    val seen = mutableListOf<Char>()

    this.forEach {
        if (!seen.contains(it)) {
            seen.add(it)
        }
    }

    return seen
}

fun List<Char>.findDuplicates(): List<Char> {
    val seen = mutableListOf<Char>()
    val duplicates = mutableListOf<Char>()

    this.forEach {
        if (!seen.contains(it)) {
            seen.add(it)
        } else {
            if (!duplicates.contains(it)) {
                duplicates.add(it)
            }
        }
    }
    return duplicates
}

fun Char.getAocValue(): Int {

    if (this.isUpperCase()) {
        return this.minus("A".toCharArray()[0]) + 27
    } else {
        return this.minus("a".toCharArray()[0]) + 1
    }
}
