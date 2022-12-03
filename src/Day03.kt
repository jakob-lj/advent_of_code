data class Compartment(val items: List<Char>)

data class Rucksack(val first: Compartment, val second: Compartment) {
    val items get() = first.items + second.items
}

data class Group(val first: Rucksack, val second: Rucksack, val third: Rucksack)


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

    val groups = rucksacks.reduceToGroups()

    val prioritizationValue = groups.map { it.findDuplicates() }.sumOf { it.sumOf { char -> char.getAocValue() } }
    println(prioritizationValue)
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

    return if (this.isUpperCase()) {
        this.minus("A".toCharArray()[0]) + 27
    } else {
        this.minus("a".toCharArray()[0]) + 1
    }
}

fun List<Rucksack>.reduceToGroups(): List<Group> {

    val groups = mutableListOf<Group>()

    for (i in 0 until this.size / 3) {
        val k = i * 3
        groups.add(
            Group(
                this[k],
                this[k + 1],
                this[k + 2]
            )
        )
    }

    return groups
}

fun Group.findDuplicates(): List<Char> {
    val first = first.items.mergeDuplicates()
    val second = second.items.mergeDuplicates()
    val third = third.items.mergeDuplicates()

    return first.filter {
        first.contains(it) && second.contains(it) && third.contains(it)
    }
}