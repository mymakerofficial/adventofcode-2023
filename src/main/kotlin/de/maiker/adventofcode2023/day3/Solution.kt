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

fun parseLines(lines: List<String>): Pair<List<Symbol>, List<PartNumber>> {
    val symbols = mutableListOf<Symbol>()
    val partNumbers = mutableListOf<PartNumber>()

    lines.forEachIndexed { y, line ->
        val currentDigits = mutableListOf<Char>()
        "$line.".forEachIndexed { x, it ->
            if (it.isDigit()) {
                currentDigits.add(it)
            } else if (currentDigits.isNotEmpty()) {
                partNumbers.add(PartNumber(
                    x = x - currentDigits.size,
                    y = y,
                    length = currentDigits.size,
                    number = currentDigits.joinToString("").toInt()
                ))
                currentDigits.clear()
            }

            if (it.isSymbol()) {
                symbols.add(Symbol(
                    x = x,
                    y = y,
                    char = it,
                ))
            }
        }
    }

    return symbols to partNumbers
}

class Symbol(
    val x: Int,
    val y: Int,
    val char: Char,
) {
    override fun toString() = "Symbol(x=$x, y=$y, char=$char)"

    fun isGear() = char == '*'
}

class PartNumber(
    val x: Int,
    val y: Int,
    val length: Int,
    val number: Int,
) {
    override fun toString() = "Number(x=$x, y=$y, length=$length, number=$number)"

    infix fun isAdjacentTo(symbol: Symbol): Boolean {
        val horizontalRange = (x - 1)..(x + length)
        val verticalRange = (y - 1)..(y + 1)
        return symbol.x in horizontalRange && symbol.y in verticalRange
    }

    infix fun isAdjacentToAny(symbols: List<Symbol>) =
        symbols.any { symbol ->
            this isAdjacentTo symbol
        }
}

fun partOne(lines: List<String>): Int {
    val (symbols, partNumbers) = parseLines(lines)

    val numbers = partNumbers.filter { partNumber ->
        partNumber isAdjacentToAny symbols
    }.map {
        it.number
    }

    return numbers.sum()
}

fun partTwo(lines: List<String>): Int {
    val (symbols, partNumbers) = parseLines(lines)

    val gearRatios = symbols
        .filter { it.isGear() }
        .map { gear ->
            val adjacentPartNumbers = partNumbers.filter { partNumber ->
                partNumber isAdjacentTo gear
            }

            if (adjacentPartNumbers.size != 2) {
                // a symbol is only actually a gear if it is adjacent to exactly two part numbers
                return@map 0
            }

            val (first, second) = adjacentPartNumbers

            val gearRatio = first.number * second.number

            gearRatio
        }

    return gearRatios.sum()
}