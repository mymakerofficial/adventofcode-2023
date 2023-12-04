package de.maiker.adventofcode2023.lib

fun String.getFirstThat(predicate: (Char) -> Boolean): String {
    return this.dropWhile { !predicate(it) }.takeWhile(predicate)
}

fun String.getFirstNumber(): Int {
    return this.getFirstThat { it.isDigit() }.toInt()
}