package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

@Slf4j
public class ReferenceTest {

	@Test
	public void strongRef() {
		Object obj1 =  new Object();
		Object obj2 = obj1;
		obj1 = null;
		System.gc();
		System.out.println(obj1);
		System.out.println(obj2);
	}

	// -XX:+PrintGCDetails
	@Test
	public void softRef01() {
		Object o = new Object();
		SoftReference<Object> reference = new SoftReference<>(o);
		log.info("o: [{}]", o);
		log.info("reference: [{}]", reference.get());
		o = null;
		System.gc();

		log.info("o: [{}]", o);
		log.info("reference: [{}]", reference.get());
	}

	// -Xms5m -Xmx5m -XX:+PrintGCDetails
	@Test
	public void softRef02() {
		Object o = new Object();
		SoftReference<Object> reference = new SoftReference<>(o);
		log.info("o: [{}]", o);
		log.info("reference: [{}]", reference.get());
		// o = null;
		try {
			byte[] bytes = new byte[10*1024*1024];
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			log.info("o: [{}]", o);
			log.info("reference: [{}]", reference.get());
		}
	}

	// -XX:+PrintGCDetails
	@Test
	public void weakRef() {
		Object o = new Object();
		WeakReference<Object> reference = new WeakReference<>(o);
		log.info("o: [{}]", o);
		log.info("reference: [{}]", reference.get());
		o = null;
		System.gc();

		log.info("o: [{}]", o);
		log.info("reference: [{}]", reference.get());
	}

	// -XX:+PrintGCDetails
	@Test
	public void phantomRef() {
		Object o = new Object();
		ReferenceQueue<Object> queue =new ReferenceQueue<>();
		PhantomReference<Object> reference = new PhantomReference<>(o, queue);
		log.info("o: [{}]", o);
		log.info("reference: [{}]", reference.get());
		log.info("reference in queue: [{}]", queue.poll());
		o = null;
		Object r;
		while ((r = queue.poll()) == null) {
			System.gc();
		}
		log.info("o: [{}]", o);
		log.info("reference: [{}]", reference.get());
		log.info("reference in queue: [{}]", r);
	}

}
