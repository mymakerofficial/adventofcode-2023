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

fun parseLine(line: String): Pair<Int, List<Pair<String, Int>>> {
    val id = line.getFirstNumber()

    val events = line
        .replace(",", "")
        .replace(";", "")
        .dropWhile { it != ':' }
        .drop(2)
        .split(" ")
        .windowed(2, 2)
        .map { (amount, color) ->
            color to amount.toInt()
        }

    return id to events
}

fun partOne(lines: List<String>): String {
    val sum = lines
        .map {parseLine(it) }
        .map { (id, events) ->
            val invalid = events.any { (color, amount) ->
                legalAmounts[color]!! < amount
            }

            id to !invalid
        }.sumOf { (id, valid) ->
            if (valid) {
                id
            } else {
                0
            }
        }

    return sum.toString()
}

fun partTwo(lines: List<String>): String {
    val maxs = lines
        .map {parseLine(it) }
        .map { (_, events) ->
            val groups = events
                .groupBy { (color, _) -> color }
                .map { (_, events) -> events.map { (_, amount) -> amount } }

            val maxs = groups.map { it.max() }

            maxs.reduce { acc, i -> acc * i }
        }

    return maxs.sum().toString()
}