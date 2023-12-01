package de.maiker.adventofcode2023.day1

import java.io.File

fun main() {
    val lines = File("src/main/resources/day1.txt").readLines()

    val partOne = partOne(lines)
    println("part one: $partOne")

    val partTwo = partTwo(lines)
    println("part two: $partTwo")
}

fun partOne(lines: List<String>): String {
    val numbers = lines.map { line ->
        val first = line.first { it.isDigit() }.digitToInt()
        val last = line.last { it.isDigit() }.digitToInt()

        first * 10 + last
    }

    return numbers.sum().toString()
}

val digits = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun findAtIndex(line: String, index: Int): Int? {
    if (line[index].isDigit()) {
        return line[index].digitToInt()
    }

    digits.forEach { (digitAsWord, digit) ->
        if (line.take(index + 1).takeLast(digitAsWord.length) == digitAsWord) {
            return digit
        }
    }

    return null
}

fun find(line: String, index: Int, dir: Int): Int {
    return findAtIndex(line, index) ?: find(line, index + dir, dir)
}

fun leftFind(line: String) = find(line, 0, 1)

fun rightFind(line: String) = find(line, line.length - 1, -1)

fun partTwo(lines: List<String>): String {
    val numbers = lines.map {
        val first = leftFind(it)
        val last = rightFind(it)

        first * 10 + last
    }

    return numbers.sum().toString()
}