package days

class Day19 : Day(19) {

    data class Rule(val ref: Int?, val txt: String?)

    private val grammar: MutableMap<Int, List<List<Rule>>> = HashMap()
    private val messages: MutableList<String> = ArrayList()

    override fun partOne(): Any {
        parse()
        val regex = fetchRegex(0, false).toRegex()
        return messages.count { it.matches(regex) }
    }

    override fun partTwo(): Any {
        val regex = fetchRegex(0, true).toRegex()
        return messages.count { it.matches(regex) }
    }

    private fun fetchRegex(rule: Int, part2: Boolean = false): String {
        if (grammar[rule]!![0][0].txt != null) return grammar[rule]!![0][0].txt!!
        var ret = "("
        if (part2 && rule == 11) {
            val reg1 = fetchRegex(grammar[rule]!![0][0].ref!!)
            val reg2 = fetchRegex(grammar[rule]!![0][1].ref!!)
            var reg = reg1 + reg2
            for (i in 1 until 10) {
                ret += "($reg)|"
                reg = reg1 + reg + reg2
            }
            ret += reg
        } else for (opt in grammar[rule]!!) {
            opt.forEach {
                ret += fetchRegex(it.ref!!, part2)
            }
            if (opt != grammar[rule]!!.last()) ret += "|"
        }
        ret += ")"
        if (part2 && rule == 8) ret += "+"
        return ret
    }

    private fun parse() {
        var state = 0
        for (i in inputList) {
            if (i.isBlank()) {
                state++; continue
            }
            when (state) {
                0 -> {
                    var spl = i.split(": ")
                    val idx = spl[0].toInt()
                    val list = mutableListOf<List<Rule>>()
                    spl = spl[1].split(" | ")
                    for (s in spl) {
                        list.add(s.split(" ").map {
                            if (!it.contains('"')) Rule(it.toInt(), null)
                            else Rule(null, it.removeSurrounding("\""))
                        })
                    }
                    grammar[idx] = list
                }
                1 -> messages.add(i)
                else -> throw Exception("Unknown State: $state")
            }
        }
    }
}
