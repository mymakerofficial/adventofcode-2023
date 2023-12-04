package de.maiker.adventofcode2023.day4

import de.maiker.adventofcode2023.lib.getFirstNumber
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
    private val number: Int,
    private val winningNumbers: Set<Int>,
    private val playerNumbers: Set<Int>,
) {
    override fun toString() = "Card(number=$number, winningNumbers=$winningNumbers, playerNumbers=$playerNumbers, winningCount()=${winningCount()}, points()=${points()}), winsCards()=${winsCards()})"

    private fun winningCount(): Int {
        return (winningNumbers intersect playerNumbers).size
    }

    // first winning number gives 1 point, each additional winning number doubles the points
    fun points(): Int {
        return 2.0.pow(winningCount() - 1).toInt()
    }

    fun winsCards(): List<Int> {
        return 0.until(winningCount()).map { number + it + 1 }
    }
}

fun parseLine(line: String): Card {
    val (firstPart, secondPart) = line.split(":").last().split("|")

    val gameNumber = line.getFirstNumber()

    val winning = firstPart
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toSet()

    val player = secondPart
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toSet()

    return Card(gameNumber, winning, player)
}

fun partOne(lines: List<String>): Int {
    val cards = lines.map { parseLine(it) }
    return cards.sumOf { it.points() }
}

fun partTwo(lines: List<String>): Int {
    val originalCards = lines.map { parseLine(it) }

    originalCards.forEach { println(it) }

    TODO()
}