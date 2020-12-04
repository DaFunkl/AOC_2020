package days

class Day4 : Day(4) {
    override fun partOne(): Any = countValidPassports(1)

    override fun partTwo(): Any = countValidPassports(2)

    private fun countValidPassports(part: Int): Int {
        var count = 0;
        var newPassport = true;
        var line = "";
        for (l in inputList) {
            if (l.isEmpty() || inputList.last() == l) {
                newPassport = true;
                if (isPassportValidP1(line)) {
                    if (part == 1) count++
                    else if (part == 2 && isPassportValidP2(line)) count++
                }
                continue
            }
            if (newPassport) {
                newPassport = false;
                line = l
            } else {
                line += " $l"
            }
        }
        return count
    }


    private fun isPassportValidP1(line: String): Boolean {
        val keys = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
        return line.isNotEmpty() && keys.map { line.contains(it) }.reduce { acc: Boolean, i -> acc && i }
    }


    private fun isPassportValidP2(line: String): Boolean {
        if (line.isBlank()) return false;
        val attrs = line.split(" ")
        for (atr in attrs) {
            if (atr.isBlank()) {
                continue
            }
            val spl = atr.split(":", limit = 2)
            val key = spl[0].trim()
            val value = spl[1].trim()
            when (key) {
                "byr" -> {
                    val nr = value.toInt()
                    if (nr !in 1920..2002) {
                        return false
                    }
                }
                "iyr" -> {
                    val nr = value.toInt()
                    if (nr !in 2010..2020) {
                        return false
                    }
                }
                "eyr" -> {
                    val nr = value.toInt()
                    if (nr !in 2020..2030) {
                        return false
                    }
                }
                "hgt" -> {
                    if (!(value.endsWith("cm") || value.endsWith("in"))) {
                        println(value)
                        return false
                    }
                    val nr = value.substring(0, value.length - 2).toInt()
                    when (value.substring(value.length - 2)) {
                        "in" -> if (nr !in 59..76) {
                            return false
                        }
                        "cm" -> if (nr !in 150..193) {
                            return false
                        }
                    }
                }
                "hcl" -> {
                    if (!"^#[a-fA-F0-9]{6}\$".toRegex().matches(value)) {
                        return false
                    }
                }
                "ecl" -> {
                    if (!arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value)) {
                        return false
                    }
                }
                "pid" -> {
                    if (!"^[0-9]{9}\$".toRegex().matches(value)) {
                        return false
                    }
                }
                "cid" -> {}
                else -> {
                    return false
                }
            }

        }
        return true;
    }

}