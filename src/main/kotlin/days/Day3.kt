package days

class Day3 : Day(3) {

    override fun partOne(): Any = countTrees(3, 1)

    override fun partTwo(): Any = arrayOf(
        Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2)
    ).map { countTrees(it.first, it.second) }.reduce { acc, i -> acc * i }


    fun countTrees(right: Int, down: Int): Long {
        var posX = 0;
        var posY = 0;
        var trees: Long = 0;

        while (posX < inputList.size) {
            if (inputList[posX][posY] == '#') {
                trees++
            }
            posX += down
            posY = (posY + right) % inputList[0].length
        }
        return trees
    }
}