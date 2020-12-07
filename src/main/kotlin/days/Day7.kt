package days

class Day7 : Day(7) {


    override fun partOne(): Any {

        inputList.map { it.split(" bags contain ") }
            .associate {
                it[0] to it[1]
                    .split(", ").map {
                        val spl = it.split(" ")
                        if(spl[0]=="no") null
                        else {
//                            Pair(spl[0].toInt(), spl.joinTo(" "))
                        }
                    }
            }

        return -1
    }

    override fun partTwo(): Any {


        return -1
    }


}
