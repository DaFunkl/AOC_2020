package days

class Day25 : Day(25) {
    val num1 = inputList[0].toLong()
    val num2 = inputList[1].toLong()
    val mult = 7L
    val divider = 20201227L

    override fun partOne(): Any {
        var i = 1
        var accu = mult
        while(num1 != accu){
            accu = (accu * mult) % divider
            i++
        }
        println(i)
        accu = 1
        for(i in 0 until i ){
            accu = (accu * num2) % divider
        }
        return accu
    }

    override fun partTwo(): Any = "Merry Christmas"
}