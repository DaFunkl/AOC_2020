package tester

object Tester {

    @JvmStatic
    fun main(args: Array<String>) {
//        onelinerParser()
        println(1 in 5..3)
        println(1 in 3..5)
        println(4 in 5..3)
        println(4 in 3..5)
        println(3 in 5..3)
        println(3 in 3..5)
        println(5 in 5..3)
        println(5 in 3..5)


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
