package tester

object Tester {

    @JvmStatic
    fun main(args: Array<String>) {
//        onelinerParser()
//        for (i in 2..10) {
//            println("$i -> ${fibonacci(i)}")
//        }

        val list = mutableListOf<Int>()
        for(i in 0 .. 1000000){
            list.add(i)
        }
        println(list.last())

    }

    fun fibonacci(n: Int): Int {
        var first = 0
        var second = 0
        var third = 1

        for (i in 1..n) {
            val next_value = first + second + third
            first = second
            second = third
            third = next_value
        }

        return third
    }

    fun onelinerParser() {
        val txtLine = "abc 123"
        val pattern = "([a-z]+) (.\\d+)".toRegex()

        var str = "";
        var nr = -1;
        pattern.findAll(txtLine).forEachIndexed { i, v ->
            when (i) {
                0 -> str = v.value
                1 -> nr = v.value.toInt()
            }
        }

        println("$str $nr");
    }


}
