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

        fun remove(n: Int): Node {
            val prev = this.prev
            var next = this.next
            for (i in 1 until n) {
                next = next.next
            }
            prev.next = next
            next.prev = prev
            return this
        }

        fun append(node: Node, n: Int) {
            val pn = this.next
            this.next = node
            node.prev = this
            var nnp = node
            for (i in 1 until n) nnp = nnp.next
            pn.prev = nnp
            nnp.next = pn
        }

        fun fetch(n: Int): Set<Int> {
            val ret = mutableSetOf<Int>()
            var c = this
            for (i in 0 until n) {
                ret.add(c.value)
                c = c.next
            }
            return ret
        }
    }

    override fun partOne(): Any = solve(data, 100)

    override fun partTwo(): Any = solve(fetchListP2(), 10000000, true)


    private fun makeMap(start: Node): Map<Int, Node> {
        val map: MutableMap<Int, Node> = mutableMapOf()
        var ll = start
        while (!map.containsKey(ll.value)) {
            map[ll.value] = ll
            ll = ll.next
        }
        return map
    }

    private fun solve(data: List<Int>, n: Int, p2: Boolean = false): Any {
        val maxVal: Int = data.maxByOrNull { it }!!
        var current = fetchLL(data)
        val map: Map<Int, Node> = makeMap(current)
        for (i in 1..n) {
            val removed = current.next.remove(3)
            val remSet = removed.fetch(3)
            var dest = current.value - 1
            while (dest <= 0 || remSet.contains(dest)) {
                if (dest <= 0) dest = maxVal
                else dest--
            }
            val appendTo = map[dest]!!
            appendTo.append(removed, 3)
            current = current.next
        }
        if (p2) {
            current = map[1]!!
            return current.next.value.toLong() * current.next.next.value.toLong()
        }
        val spl = current.toStr("").split("1")
        return spl[1] + spl[0]
    }

    private fun fetchListP2(): List<Int> {
        val list = mutableListOf<Int>()
        list.addAll(data)
        var next = list.maxByOrNull { it }!! + 1
        while (list.size < 1000000) {
            list.add(next++)
        }
        return list
    }

    private fun fetchLL(input: List<Int>): Node {
        val start = Node(input[0])
        var last: Node = start
        for (i in 1 until input.size) {
            val next = Node(input[i])
            next.prev = last
            last.next = next
            last = next
        }
        start.prev = last
        last.next = start
        return start
    }

    /* -> optimized Java Solution
    package de.monx.tester;

public class AOC20D23 {
	static final String input = "685974213";

	static String part1() {
		int[] data = inToData(-1);
		return solve(data, 100, false);
	}

	static String part2() {
		int[] data = inToData(1_000_000);
		return solve(data, 10_000_000, true);
	}

	static String solve(int[] data, int steps, boolean p2) {
		int current = data[0];
		int maxVal = data[data.length - 1];
		for (int q = 1; q <= steps; q++) {

			int[] narr = new int[3];
			int accu = current;
			for (int i = 0; i < 3; i++) {
				narr[i] = data[accu];
				accu = data[accu];
			}
			data[current] = data[accu];

			int dest = current - 1;
			while (dest <= 0 || contains(narr, dest)) {
				if (dest <= 0) {
					dest = maxVal;
				} else {
					dest--;
				}
			}
			data[narr[2]] = data[dest];
			data[dest] = narr[0];
			current = data[current];
		}

		if (p2) {
			long a = data[1];
			long b = data[data[1]];
			long res = a * b;
			return res + "";
		} else {
			String str = toStr(data, "");
			String[] spl = str.split("1");
			return spl[1] + spl[0];
		}
	}

	static boolean contains(int[] arr, int nr) {
		for (int n : arr) {
			if (n == nr) {
				return true;
			}
		}
		return false;
	}

	static String toStr(int[] arr, String delimiter) {
		int start = arr[0];
		int current = start;
		String ret = current + "";
		while (arr[current] != start) {
			ret += delimiter + arr[current];
			current = arr[current];
		}
		return ret;
	}

	public static void main(String[] args) {
		long start_time = System.nanoTime();
		String p1 = part1();
		long end_time = System.nanoTime();
		double difference = (end_time - start_time) / 1e6;
		System.out.println("Part1: " + p1 + " -> " + difference);
		start_time = System.nanoTime();
		String p2 = part2();
		end_time = System.nanoTime();
		difference = (end_time - start_time) / 1e6;
		System.out.println("Part2: " + p2 + " -> " + difference);
	}

	static int[] inToData(int upTo) {
		int[] ret = new int[input.length() + 2];
		if (upTo > 0) {
			ret = new int[upTo + 2];
		}
		char[] car = input.toCharArray();
		ret[0] = Integer.valueOf(car[0] + "");
		int prev = ret[0];
		ret[ret.length - 1] = ret[0];
		for (int i = 1; i < car.length; i++) {
			int current = Integer.valueOf(car[i] + "");
			if (current > ret[ret.length - 1]) {
				ret[ret.length - 1] = current;
			}
			ret[prev] = current;
			prev = current;
		}
		if (upTo >= 0) {
			for (int current = 10; current <= upTo; current++) {
				if (current > ret[ret.length - 1]) {
					ret[ret.length - 1] = current;
				}
				ret[prev] = current;
				prev = current;
			}
		}
		ret[prev] = ret[0];
		return ret;
	}

}


    */
}