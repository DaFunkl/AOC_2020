package days

class Day2 : Day(2) {

    override fun partOne(): Any {
        return inputList.map { PwPolicy(it, 1) }.count { it.valid }
    }

    override fun partTwo(): Any {
        return inputList.map { PwPolicy(it, 2) }.count { it.valid }
    }

    class PwPolicy {
        var min: Int = 0
        var max: Int = 0
        var c: Char = '@'
        var pw: String = ""
        var valid = false

        constructor(line: String, part: Int) {
            var spl = line.split(":")
            pw = spl[1].trim()
            spl = spl[0].split(" ")
            c = spl[1].trim()[0]
            spl = spl[0].split("-")
            min = spl[0].trim().toInt()
            max = spl[1].trim().toInt()
            valid = isValid(part)
        }

        fun isValid(part: Int): Boolean = when (part) {
            1 -> pw.count { it == c } in min..max
            2 -> (pw[min - 1] == c) xor (pw[max - 1] == c)
            else -> false
        }
    }
}