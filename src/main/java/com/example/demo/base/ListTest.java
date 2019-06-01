package com.example.demo.base;

import java.util.*;

public class ListTest {
	
	public static void main(String[] args) throws InterruptedException {
		// List<String> list = new LinkedList<String>();
		// Set<String> set = new HashSet<String>();
		// for(int i = 0; i < 10; i++) {
		//
		// set.add(i + "");
		// list.add("a");
		// list.add("b");
		// list.add("c");
		// list.add("d");
		// list.add("e");
		// list.add("f");
		// list.add("g");
		// list.add("h");
		// list.add("i");
		// list.add("j");
		// list.add("k");
		// list.add("l");
		// list.add("m");
		// list.add("n");
		// list.add("o");
		// list.add("p");
		// list.add("q");
		// list.add("r");
		// list.add("s");
		// list.add("t");
		// list.add("u");
		// list.add("v");
		// list.add("w");
		// list.add("x");
		// list.add("y");
		// list.add("z");
		// set.add("a");
		// set.add("b");
		// set.add("c");
		// set.add("d");
		// set.add("e");
		// set.add("f");
		// set.add("g");
		// set.add("h");
		// set.add("i");
		// set.add("j");
		// set.add("k");
		// set.add("l");
		// set.add("m");
		// set.add("n");
		// set.add("o");
		// set.add("p");
		// set.add("q");
		// set.add("r");
		// set.add("s");
		// set.add("t");
		// set.add("u");
		// set.add("v");
		// set.add("w");
		// set.add("x");
		// set.add("y");
		// set.add("z");
		// }
		// for (String string : set) {
		// 	System.out.print(string);
		// }
		// System.out.println();
		// for (String string : list) {
		// 	System.out.print(string);
		// }
		ArrayList<String> list = new ArrayList<String>();
		list.add("a");
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals("a")) {
				list.remove(i--);
			}
		}
		System.out.println(list.toString());
		
		List<AA> as = new ArrayList<>();
		as.add(new AA());
		as.add(new AA());
		as.add(new AA());
		as.add(new AA());
		System.out.println(as.toString());
		System.out.println(""+Runtime.getRuntime().availableProcessors());
		while (true) {
			Thread.sleep(5000);
			System.out.println("A");
		}
	}
}

class AA {
	private int x;
	private int y;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
