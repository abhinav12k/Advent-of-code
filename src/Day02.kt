class Day02 {

    private val MAX_RED_CUBES = 12
    private val MAX_GREEN_CUBES = 13
    private val MAX_BLUE_CUBES = 14

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { index, str ->
            if (isValidGameInput(str.split(";"))) {
                sum += (index + 1)
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { str ->
            getPowerOfSet(str)
        }
    }

    private fun getPowerOfSet(input: String): Int {
        var maxRed = 1
        var maxBlue = 1
        var maxGreen = 1
        input.split(";").forEach {
            maxRed = getCubes(it, "red").coerceAtLeast(maxRed)
            maxGreen = getCubes(it, "green").coerceAtLeast(maxGreen)
            maxBlue = getCubes(it, "blue").coerceAtLeast(maxBlue)
        }
        return maxRed * maxBlue * maxGreen
    }

    private fun isValidGameInput(input: List<String>): Boolean {
        println(input)
        input.forEach {
            val red = getCubes(it, "red")
            val green = getCubes(it, "green")
            val blue = getCubes(it, "blue")

            println("Red - $red\nGreen - $green\nBlue - $blue")
            println("-------------------")

            if(red > MAX_RED_CUBES) return false
            if(green > MAX_GREEN_CUBES) return false
            if(blue > MAX_BLUE_CUBES) return false
        }
        return true
    }

    private fun getCubes(input: String, key: String): Int {
        var idx = input.indexOf(key)
        return if (idx - 2 >= 0 && input[idx-2].isDigit()) {
            idx -= 2
            var multiplier = 1
            var digit = 0
            while (idx >= 0) {
                if (input[idx].isDigit()) {
                    digit += input[idx].digitToInt() * multiplier
                    multiplier *= 10
                } else {
                    break
                }
                idx--
            }
            digit
        } else {
            0
        }
    }

}


fun main() {
    val day2 = Day02()
    val input = readInput("Day02_test")
    day2.part1(input).println()
    day2.part2(input).println()
}