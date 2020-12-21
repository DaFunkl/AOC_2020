package days

class Day21 : Day(21) {
    val data: List<Pair<List<String>, List<String>>> = inputList.map {
        Pair(
            it.split(" (contains ")[0].split(" "),
            it.split(" (contains ")[1].replace(")", "").split(", ")
        )
    }
    private val dictionary = mutableMapOf<String, String>()

    override fun partOne(): Any {
        val idxMap: MutableMap<String, MutableList<Int>> = fetchIdxMap()
        val known = mutableSetOf<String>()
        val possibly = mutableMapOf<String, List<String>>()

        for ((k, v) in idxMap) {
            possibly[k] = data[v[0]].first.toList().filter { !known.contains(it) }
            for (vi in 1 until v.size) {
                possibly[k] = possibly[k]!!.filter { data[v[vi]].first.contains(it) }
                if (possibly[k]!!.size == 1) break
            }
            if (possibly[k]!!.size == 1) {
                val found = possibly[k]!![0]
                dictionary[k] = found
                known.add(found)
                for ((k2, v2) in possibly) {
                    possibly[k2] = v2.filter { it != found }
                }
                possibly.remove(k)
            }
        }

        var didChange = true
        while (didChange) {
            didChange = false
            for ((k, v) in idxMap) {
                if (possibly[k] == null) continue
                for (vi in 0 until v.size) {
                    val preSize = possibly[k]!!.size
                    possibly[k] = possibly[k]!!.filter { data[v[vi]].first.contains(it) }
                    if (preSize != possibly[k]!!.size) didChange = true
                    if (possibly[k]!!.size == 1) break
                }
                if (possibly[k]!!.size == 1) {
                    val found = possibly[k]!![0]
                    dictionary[k] = found
                    known.add(found)
                    for ((k2, v2) in possibly) {
                        possibly[k2] = v2.filter { it != found }
                    }
                    possibly.remove(k)
                    didChange = true
                }
            }
        }
        return data.sumBy { it.first.count { !known.contains(it) } }
    }

    private fun fetchIdxMap(): MutableMap<String, MutableList<Int>> {
        val map: MutableMap<String, MutableList<Int>> = mutableMapOf()
        for ((idx, p) in data.withIndex()) {
            for (k in p.second) {
                if (map[k] == null) map[k] = mutableListOf(idx)
                else map[k]?.add(idx)
            }
        }
        return map
    }

    override fun partTwo(): Any = dictionary.keys.sorted().map { dictionary[it] }.joinToString(",")
}