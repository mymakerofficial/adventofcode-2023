package de.maiker.adventofcode2023.day3

import java.io.File

fun main() {
    val lines = File("src/main/resources/day3.txt").readLines()

    val partOne = partOne(lines)
    println("part one: $partOne")

    val partTwo = partTwo(lines)
    println("part two: $partTwo")
}

fun Char.isSymbol(): Boolean {
    return !this.isDigit() && !this.isLetter() && this != '.'
}

fun blup(lines: Triple<String?, String, String?>, startIndex: Int, endIndex: Int): Boolean {
    val (previous, current, next) = lines

    val marginStartIndex = if (startIndex == 0) 0 else startIndex - 1
    val marginEndIndex = if (endIndex == current.length - 1) endIndex else endIndex + 1

    val previousContains = previous?.slice(marginStartIndex..marginEndIndex)?.any { it.isSymbol() } ?: false
    val currentContains = current.slice(marginStartIndex..marginEndIndex).any { it.isSymbol() }
    val nextContains = next?.slice(marginStartIndex..marginEndIndex)?.any { it.isSymbol() } ?: false

    return previousContains || currentContains || nextContains
}

fun partOne(lines: List<String>): String {
    lines.forEachIndexed { index, line ->
        val currentLines = Triple(
            lines.getOrNull(index - 1),
            line,
            lines.getOrNull(index + 1)
        )

        // TODO: find start and end indices of each number in the line
    }

    return ""
}

fun partTwo(lines: List<String>): String {
    return ""
}