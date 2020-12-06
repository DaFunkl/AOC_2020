package days

class Day6 : Day(6) {
    override fun partOne(): Any{
        var groupAnswers = HashSet<Char>()
        var count = 0;
        for(line in inputList){
            if (line.isBlank() || line == inputList.last()){
                println(groupAnswers.size)
                count += groupAnswers.size;
                groupAnswers = HashSet<Char>()
            } else {
                line.forEach { groupAnswers.add(it) }
            }
        }
        return count;
    }

    override fun partTwo(): Any {
        return -1
    }
}
