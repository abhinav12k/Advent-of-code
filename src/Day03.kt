typealias SchematicRow = List<Element>

class Day03 {

    fun part1(input: List<String>): Int {
        val extractedElements: SchematicRow = input.mapIndexed { index, s -> extractElements(s, index) }.flatten()
        val numbers = extractedElements.filterIsInstance(Number::class.java)
        val symbols = extractedElements.filterIsInstance(Symbol::class.java)

        return numbers.sumOf { number ->
            if (symbols.any { it.column in number.expandedColumn && it.row in number.expandedRow }) number.value else 0
        }
    }

    fun part2(input: List<String>): Int {
        val extractedElements: SchematicRow = input.mapIndexed { index, s -> extractElements(s, index) }.flatten()
        val numbers = extractedElements.filterIsInstance(Number::class.java)
        val gearSymbols = extractedElements.filterIsInstance(Symbol::class.java).filter { it.value == '*' }

        val gears = gearSymbols.map { symbol ->
            numbers.filter { symbol.row in it.expandedRow && symbol.column in it.expandedColumn }
        }.filter { it.size == 2 }

        return gears.sumOf { it[0].value * it[1].value }
    }

    private fun extractElements(input: String, row: Int): List<Element> = buildList {
        var numberStartIdx = -1
        var currentNumber = ""
        input.forEachIndexed { index, c ->
            if (c.isDigit()) {
                currentNumber += c
                if (numberStartIdx == -1) numberStartIdx = index
            } else {
                if (c != '.') add(Symbol(c, index, row))
                if (currentNumber.isNotEmpty()) {
                    add(Number(currentNumber, numberStartIdx, index - 1, row))
                    currentNumber = ""
                    numberStartIdx = -1
                }
            }
        }
        if (currentNumber.isNotEmpty()) {
            add(Number(currentNumber, numberStartIdx, input.length - 1, row))
            currentNumber = ""
            numberStartIdx = -1
        }
    }

}

fun main() {
    val day3 = Day03()
    val input = readInput("Day03_test")
    day3.part1(input).println()
    day3.part2(input).println()
}

sealed class Element
data class Number(val value: Int, val xRange: IntRange, val row: Int) : Element() {
    val expandedColumn = xRange.first - 1..xRange.last + 1
    val expandedRow = row - 1..row + 1
}

fun Number(number: String, start: Int, end: Int, row: Int) = Number(number.toInt(), start..end, row)

data class Symbol(val value: Char, val column: Int, val row: Int) : Element()