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
        line
            .filter { it.isDigit() }
            .let { listOf(it.first(), it.last()) }
            .joinToString("")
            .toInt()
    }

    return numbers.sum().toString()
}

val stringDigits = listOf(
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine"
)

fun partTwo(lines: List<String>): String {
    val numbers = lines.map { line ->
        val digits = emptyList<String>().toMutableList()
        line.forEachIndexed { index, character ->
            if (character.isDigit()) {
                digits.add(character.toString())
                return@forEachIndexed
            }

            stringDigits.forEachIndexed { digit, stringDigit ->
                if (line.take(index + 1).takeLast(stringDigit.length) == stringDigit) {
                    digits.add((digit + 1).toString())
                }
            }
        }
        listOf(digits.first(), digits.last()).joinToString("").toInt()
    }

    return numbers.sum().toString()
}