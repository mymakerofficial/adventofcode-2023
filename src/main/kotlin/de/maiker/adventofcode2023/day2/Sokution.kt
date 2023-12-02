package de.maiker.adventofcode2023.day2

import java.io.File

fun main() {
    val lines = File("src/main/resources/day2.txt").readLines()

    val partOne = partOne(lines)
    println("part one: $partOne")

    val partTwo = partTwo(lines)
    println("part two: $partTwo")
}

val legalAmounts = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

fun String.getFirstThat(predicate: (Char) -> Boolean): String {
    return this.dropWhile { !predicate(it) }.takeWhile(predicate)
}

fun String.getFirstNumber(): Int {
    return this.getFirstThat { it.isDigit() }.toInt()
}

fun String.cleanUp(): String {
    return this
        .filterNot { it == ',' || it == ';' }
        .dropWhile { it != ':' }
        .drop(2)
}

fun parseLine(line: String): Pair<Int, List<Pair<String, Int>>> {
    val id = line.getFirstNumber()

    val events = line
        .cleanUp()
        .split(" ")
        .windowed(2, 2)
        .map { (amount, color) ->
            color to amount.toInt()
        }

    return id to events
}

fun List<Pair<String, Int>>.isValidGame(): Boolean {
    return !this.any { (color, amount) ->
        legalAmounts[color]!! < amount
    }
}

fun partOne(lines: List<String>): String {
    val sum = lines
        .map {parseLine(it) }
        .map { (id, events) ->
            id to events.isValidGame()
        }.sumOf { (id, valid) ->
            if (valid) id else 0
        }

    return sum.toString()
}

fun List<Pair<String, Int>>.groupByColor(): Map<String, List<Pair<String, Int>>> {
    return this.groupBy { (color, _) -> color }
}

fun Map<String, List<Pair<String, Int>>>.dropColorInformation(): List<List<Int>> {
    return this.map { (_, events) ->
        events.map { (_, amount) -> amount }
    }
}

fun List<Int>.product(): Int {
    return this.reduce { acc, i -> acc * i }
}

fun partTwo(lines: List<String>): String {
    val products = lines
        .map { parseLine(it) }
        .map { (_, events) ->
            val groups = events
                .groupByColor()
                .dropColorInformation()

            val maxs = groups.map { it.max() }

            maxs.product()
        }

    return products.sum().toString()
}