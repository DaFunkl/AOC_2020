package tester

object Tester {

    @JvmStatic
    fun main(args: Array<String>) {
        onelinerParser()
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
