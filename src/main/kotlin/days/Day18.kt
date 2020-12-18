package days

import java.math.BigDecimal

class Day18 : Day(18) {
    val data = inputList.map {
        it.replace("(", "( ")
            .replace(")", " )")
            .split(" ")
    }

    override fun partOne(): Any = data.sumByLong { solve1(it) }

    override fun partTwo(): Any = data.sumByBigDecimal { solve2(it) }

    fun solve2(formula: List<String>): BigDecimal {
        var stack = ArrayDeque<BigDecimal>()
        var operator = ArrayDeque<String>()
        for (i in formula) {
            when (i) {
                "*", "+", "(" -> operator.add(i)
                ")" -> {
                    var wasRemove = false
                    if (operator.last() == "(") {
                        operator.removeLast()
                        wasRemove = true
                    }
                    while (!(operator.isEmpty() || operator.last() == "(")) {
                        val a = stack.removeLast()
                        val b = stack.removeLast()
                        val op = operator.removeLast()
                        when (op) {
                            "+" -> stack.add(a + b)
                            "*" -> stack.add(a * b)
                        }
                    }
                    if (!wasRemove) operator.removeLast()
                    while (operator.isNotEmpty() && operator.last() == "+") {
                        val a = stack.removeLast()
                        val b = stack.removeLast()
                        val op = operator.removeLast()
                        when (op) {
                            "+" -> stack.add(a + b)
                        }
                    }
                }
                else -> {
                    stack.add(BigDecimal(i))
                    while (operator.isNotEmpty() && operator.last() == "+") {
                        val a = stack.removeLast()
                        val b = stack.removeLast()
                        val op = operator.removeLast()
                        when (op) {
                            "+" -> stack.add(a + b)
                        }
                    }
                }
            }
        }
        while (!(operator.isEmpty() || operator.last() == "(")) {
            val a = stack.removeLast()
            val b = stack.removeLast()
            val op = operator.removeLast()
            when (op) {
                "+" -> stack.add(a + b)
                "*" -> stack.add(a * b)
            }
        }
        println("${formula.joinToString(" ")} => ${stack.last()}")
        return stack.last()
    }

    fun solve1(formula: List<String>): Long {
        var stack = ArrayDeque<Long>()
        var operator = ArrayDeque<String>()
        for (i in formula) {
            when (i) {
                "*", "+", "(" -> operator.add(i)
                ")" -> {
                    operator.removeLast()
                    while (!(operator.isEmpty() || operator.last() == "(")) {
                        val a = stack.removeLast()
                        val b = stack.removeLast()
                        val op = operator.removeLast()
                        when (op) {
                            "+" -> stack.add(a + b)
                            "*" -> stack.add(a * b)
                        }
                    }
                }
                else -> {
                    stack.add(i.toLong())
                    while (!(operator.isEmpty() || operator.last() == "(")) {
                        val a = stack.removeLast()
                        val b = stack.removeLast()
                        val op = operator.removeLast()
                        when (op) {
                            "+" -> stack.add(a + b)
                            "*" -> stack.add(a * b)
                        }
                    }
                }
            }
        }
        return stack.last()
    }

    inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
        var sum = 0L
        for (element in this) {
            sum += selector(element)
        }
        return sum
    }

    inline fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
        var sum: BigDecimal = BigDecimal.ZERO
        for (element in this) {
            sum += selector(element)
        }
        return sum
    }
}
