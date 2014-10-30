package org.xnat.test.jdk8;

import java.util.stream.IntStream;

public class Test {
	public static void main(String[] args) {
		new Thread(() -> {
			System.out.println("lambda实现的线程");
		}).start();
		int[] arr = IntStream.range(0, 10000).filter(p -> p%2 == 0).toArray();
	}
}
