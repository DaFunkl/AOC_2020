package days

class Day14 : Day(14) {

    override fun partOne(): Any {
        val values = mutableMapOf<Int, Long>()
        var zeroMask = 0L
        var oneMask = 0L
        inputList.forEach {
            when {
                it.startsWith("mask = ") -> {
                    val mask = it.split(" = ")[1]
                    zeroMask = mask.replace("X", "0").toLong(2)
                    oneMask = mask.replace("X", "1").toLong(2)
                }
                it.startsWith("mem[") -> {
                    val spl = it.split("] = ")
                    val address = spl[0].substring(4).toInt()
                    var value = spl[1].toLong()
                    value = value or zeroMask
                    value = value and oneMask
                    values[address] = value
                }
                else -> throw Exception("Unknown Expression: $it")
            }
        }
        return values.asSequence().map { it.value }.sum()
    }

    override fun partTwo(): Any {
        val values = mutableMapOf<String, Long>()
        var xMask = "0"
        inputList.forEach {
            when {
                it.startsWith("mask = ") -> xMask = it.split(" = ")[1]
                it.startsWith("mem[") -> {
                    val spl = it.split("] = ")
                    val address = spl[0].substring(4).toLong()
                    val value = spl[1].toLong()
                    fetchAddresses((address).toString(2), xMask)
                        .forEach { idx -> values[idx] = value }
                }
                else -> throw Exception("Unknown Expression: $it")
            }
        }
        return values.asSequence().map { it.value }.sum()
    }

    private fun fetchAddresses(address: String, mask: String): List<String> {
        val list = mutableListOf<String>()
        var current = address
        while (current.length < mask.length) {
            current = "0$current"
        }
        val cArr = current.toCharArray()
        for ((idx, c) in mask.withIndex()) {
            when (c) {
                '1' -> cArr[idx] = '1'
                'X' -> cArr[idx] = 'X'
            }
        }
        val todos = mutableListOf(String(cArr))
        while (todos.isNotEmpty()) {
            current = todos[0]; todos.removeAt(0)
            if (!current.contains("X")) {
                list.add(current)
            } else {
                todos.add(current.replaceFirst("X", "0"))
                todos.add(current.replaceFirst("X", "1"))
            }
        }
        return list
    }
}
