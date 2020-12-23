package days

class Day23 : Day(23) {
    val data = inputString.map { Character.getNumericValue(it) }

    data class Node(var value: Int) {
        lateinit var prev: Node
        lateinit var next: Node

        fun toStr(delimiter: String = ", "): String {
            var str = "$value$delimiter"
            var n = next
            while (n.value != value) {
                str += "${n.value}$delimiter"
                n = n.next
            }
            return str
        }

        fun remove(n: Int): Node {
            val prev = this.prev
            var next = this.next
            for (i in 1 until n) {
                next = next.next
            }
            prev.next = next
            next.prev = prev
            return this
        }

        fun append(node: Node, n: Int) {
            val pn = this.next
            this.next = node
            node.prev = this
            var nnp = node
            for (i in 1 until n) nnp = nnp.next
            pn.prev = nnp
            nnp.next = pn
        }

        fun fetch(n: Int): Set<Int> {
            val ret = mutableSetOf<Int>()
            var c = this
            for (i in 0 until n) {
                ret.add(c.value)
                c = c.next
            }
            return ret
        }
    }

    override fun partOne(): Any = solve(data, 100)

    override fun partTwo(): Any = solve(fetchListP2(), 10000000, true)

    private fun makeMap(start: Node): Map<Int, Node> {
        val map: MutableMap<Int, Node> = mutableMapOf()
        var ll = start
        while (!map.containsKey(ll.value)) {
            map[ll.value] = ll
            ll = ll.next
        }
        return map
    }

    private fun solve(data: List<Int>, n: Int, p2: Boolean = false): Any {
        val maxVal: Int = data.maxByOrNull { it }!!
        var current = fetchLL(data)
        val map: Map<Int, Node> = makeMap(current)
        for (i in 1..n) {
            val removed = current.next.remove(3)
            val remSet = removed.fetch(3)
            var dest = current.value - 1
            while (dest <= 0 || remSet.contains(dest)) {
                if (dest <= 0) dest = maxVal
                else dest--
            }
            val appendTo = map[dest]!!
            appendTo.append(removed, 3)
            current = current.next
        }
        if (p2) {
            current = map[1]!!
            return current.next.value.toLong() * current.next.next.value.toLong()
        }
        val spl = current.toStr("").split("1")
        return spl[1] + spl[0]
    }

    private fun fetchListP2(): List<Int> {
        val list = mutableListOf<Int>()
        list.addAll(data)
        var next = list.maxByOrNull { it }!! + 1
        while (list.size < 1000000) {
            list.add(next++)
        }
        return list
    }

    private fun fetchLL(input: List<Int>): Node {
        val start = Node(input[0])
        var last: Node = start
        for (i in 1 until input.size) {
            val next = Node(input[i])
            next.prev = last
            last.next = next
            last = next
        }
        start.prev = last
        last.next = start
        return start
    }
}