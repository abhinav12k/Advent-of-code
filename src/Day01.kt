fun main() {

    fun String.getFirstAndLastInteger(): Int {
        return "${first { it.isDigit() }}${last { it.isDigit() }}".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            it.getFirstAndLastInteger()
        }
    }

    val word = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
    )


    fun String.getPossibleWords(index: Int): List<String> {
        return (3..5).map { len ->
            substring(index, (index + len).coerceAtMost(length))
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            it.mapIndexedNotNull { idx, ch ->
                if (ch.isDigit()) ch
                else it.getPossibleWords(idx).firstNotNullOfOrNull { item -> word[item] }
            }.joinToString("").getFirstAndLastInteger()
        }
    }


    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part2(testInput) == 281)

    val input = readInput("Day01_test")
    part1(input).println()
    part2(input).println()
}
