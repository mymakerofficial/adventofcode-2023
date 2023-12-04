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

fun hasAdjacentSymbol(lines: Triple<String?, String, String?>, startIndex: Int, endIndex: Int): Boolean {
    val (previous, current, next) = lines

    val marginStartIndex = if (startIndex == 0) 0 else startIndex - 1
    val marginEndIndex = if (endIndex == current.length - 1) endIndex else endIndex + 1

    val previousContains = previous?.slice(marginStartIndex..marginEndIndex)?.any { it.isSymbol() } ?: false
    val currentContains = current.slice(marginStartIndex..marginEndIndex).any { it.isSymbol() }
    val nextContains = next?.slice(marginStartIndex..marginEndIndex)?.any { it.isSymbol() } ?: false

    return previousContains || currentContains || nextContains
}

fun findNumbersWithIndices(line: String): List<Triple<Int /*number*/, Int /*start*/, Int /*end*/>> {
    val digitsWithIndices = line
        .mapIndexed { index, c ->
            index to c
        }
        .filter { (_, c) ->
            c.isDigit()
        }

    val groupedDigitsWithIndices = digitsWithIndices.fold(mutableListOf<MutableList<Pair<Int, Char>>>()) { acc, element ->
        val (index, char) = element
        val last = acc.lastOrNull()

        if (last?.lastOrNull()?.first == index - 1) {
            last.add(index to char)
        } else {
            acc.add(mutableListOf(index to char))
        }

        acc
    }

    return groupedDigitsWithIndices.map {
        val start = it.first().first
        val end = it.last().first

        val number = it.map { (_, char) -> char }.joinToString("").toInt()

        Triple(
            number,
            start,
            end
        )
    }
}

fun partOne(lines: List<String>): String {
    val sums = lines.mapIndexed { index, line ->
        val currentLines = Triple(
            lines.getOrNull(index - 1),
            line,
            lines.getOrNull(index + 1)
        )

        val numbersWithIndices = findNumbersWithIndices(line)

        val numbersWithAdjacentSymbol = numbersWithIndices.filter { (_, start, end) ->
            hasAdjacentSymbol(currentLines, start, end)
        }

        val sum = numbersWithAdjacentSymbol.sumOf { (number, _, _) ->
            number
        }

        sum
    }

    return sums.sum().toString()
}

class Symbol(
    val x: Int,
    val y: Int,
    val char: Char,
) {
    override fun toString() = "Symbol(x=$x, y=$y, char=$char)"

    fun isGear() = char == '*'

    infix fun isAdjacentTo(partNumber: PartNumber) = partNumber isAdjacentTo this
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
}

fun partTwo(lines: List<String>): Int {
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

    gearRatios.forEach { println(it) }

    return gearRatios.sum()
}