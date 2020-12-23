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

        fun remove(): Node{
            prev.next = next
            next.prev = prev
            return this
        }
    }

    override fun partOne(): Any {
        var current = fetchLL(data)
        println(current.toStr())
        for (i in 0 until 10) {
            val removed = mutableListOf<Node>()
            for(r in 0 until 3){
                removed.add(current.next.remove())
            }
            var dest
        }
        return -1
    }

    override fun partTwo(): Any {
        return -1
    }

    fun fetchLL(input: List<Int>): Node {
        var start = Node(input[0])
        var last: Node = start
        for (i in 1 until input.size) {
            var next = Node(input[i])
            next.prev = last
            last.next = next
            last = next
        }
        start.prev = last
        last.next = start
        return start
    }
}