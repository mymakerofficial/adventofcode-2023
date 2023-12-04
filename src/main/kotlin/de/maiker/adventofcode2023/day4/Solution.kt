package de.maiker.adventofcode2023.day4

import java.io.File
import kotlin.math.pow

fun main() {
    val lines = File("src/main/resources/day4.txt").readLines()

    val partOne = partOne(lines)
    println("part one: $partOne")

    val partTwo = partTwo(lines)
    println("part two: $partTwo")
}

class Card(
    private val winningNumbers: Set<Int>,
    private val playerNumbers: Set<Int>,
) {
    override fun toString() = "Card(winningNumbers=$winningNumbers, playerNumbers=$playerNumbers, winningPoints()=${winningPoints()}, score()=${score()}"

    private fun winningPoints(): Int {
        return (winningNumbers intersect playerNumbers).size
    }

    // first winning number gives 1 point, each additional winning number doubles the score
    fun score(): Int {
        return 2.0.pow(winningPoints() - 1).toInt()
    }
}

fun parseLine(line: String): Card {
    val (winningStringPart, playerStringPart) = line.split(":").last().split("|")

    val winning = winningStringPart
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toSet()

    val player = playerStringPart
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toSet()

    return Card(winning, player)
}

fun partOne(lines: List<String>): Int {
    val cards = lines.map { parseLine(it) }
    return cards.sumOf { it.score() }
}

fun partTwo(lines: List<String>): Int {
    TODO()
}