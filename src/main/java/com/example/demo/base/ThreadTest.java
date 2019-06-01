package com.example.demo.base;

public class ThreadTest {
	
	public static final Object lock = new Object();
	public static final Object lock2 = new Object();

	public static void main(String[] args) {
//		Thread a = new A();
//		a.start();
		new Thread(mm).start();
		new Thread(nn).start();
		new Thread(qq).start();
	}
	
	public static Runnable mm = new Runnable() {
		
		@Override
		public void run() {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("mm running ...");
			}
		}
	};
	
	public static Runnable nn = new Runnable() {
		
		@Override
		public void run() {
			synchronized (lock) {
				synchronized (lock2) {
					try {
						lock2.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("nn running ...");
					lock.notify();
				}
			}
		}
	};
	
	public static Runnable qq = new Runnable() {
		
		@Override
		public void run() {
			synchronized (lock2) {
				System.out.println("qq running...");
				lock2.notify();
			}
		}
	};

}

class A extends Thread {

	@Override
	public void run() {
		System.out.println("a running...");
		Thread b = new B();
		b.start();
	}
	

}

class B extends Thread {

	@Override
	public void run() {
		System.out.println("b running...");
		Thread c = new C();
		c.start();
	}

}

class C extends Thread {

	@Override
	public void run() {
		System.out.println("c running...");
	}

}
