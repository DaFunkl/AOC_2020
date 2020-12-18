package days

class Day18 : Day(17) {

    val data = arrayOf<Array<Array<Boolean>>>()
    val size = 21
    val zero = 11

    override fun partOne(): Any {
        parse()

        return -1
    }

    override fun partTwo(): Any {
        return -1
    }

    fun countActivity(x: Int, y: Int, z: Int): Int {
        var count = 0
        for(xx in x-1..x+1){
            if(xx < 0 || xx >= size) continue
            for(yy in y-1..y+1){
                if(yy < 0 || yy >= size) continue
                for(zz in z-1..z+1){
                    if(zz < 0 || zz >= size) continue
                    if(xx == x && yy == y && zz == z) continue
                    if(data[xx][yy][zz]) count++
                }
            }
        }
        return count
    }

    fun parse() {
        inputList.withIndex().forEach { (x, r) ->
            r.withIndex().forEach { (y, c) ->
                if (c == '#') data[x + zero][y + zero][zero] = true
            }
        }
    }
}
