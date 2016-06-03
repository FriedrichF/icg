package de.mb.uebungen.test;

import org.junit.Assert;
import org.junit.Test;

import de.mb.uebungen.VectorImpl;
import de.mb.uebungen.VectorImpl;
import ogl.vecmath.Vector;

public class VectorImplTest {

	@Test
	public void testLength() {
		Vector v1 = new VectorImpl(1, 1, 1);
		Assert.assertEquals(Math.sqrt(3), v1.length(), 0.01);

		Vector v2 = new VectorImpl(1, 2, 1);
		Assert.assertEquals(Math.sqrt(6), v2.length(), 0.01);

		Vector v3 = new VectorImpl(5, 5, 5);
		Assert.assertEquals(Math.sqrt(75), v3.length(), 0.01);

		Vector v4 = new VectorImpl(9, 8, 3);
		Assert.assertEquals(Math.sqrt(154), v4.length(), 0.01);

	}

	@Test
	public void testCompareTo() {
		Vector v1 = new VectorImpl(1, 1, 1);
		Assert.assertEquals(0, v1.compareTo(v1));

		Vector v2 = new VectorImpl(3, 3, 3);
		Vector v22 = new VectorImpl(5, 5, 5);
		Assert.assertEquals(-3, v2.compareTo(v22));

		Vector v3 = new VectorImpl(5, 5, 5);
		Vector v33 = new VectorImpl(3, 3, 3);
		Assert.assertEquals(3, v3.compareTo(v33));

		Vector v4 = new VectorImpl(9, 8, 3);
		Vector v44 = new VectorImpl(7, 7, 7);
		Assert.assertEquals(0, v4.compareTo(v44));
	}

	@Test
	public void testSize() {
		Assert.assertEquals(3, new VectorImpl(1, 2, 3).size());
	}

	@Test
	public void testNormalize() {
		Assert.assertEquals(new VectorImpl(1f / 3f, 2f / 3f, 2f / 3f), new VectorImpl(1, 2, 2).normalize());
	}

	@Test
	public void testAdd() {

		Vector v1 = new VectorImpl(1, 2, 3);
		Vector v2 = new VectorImpl(7, 8, -9);

		Vector v3 = v1.add(v2);
		Vector v4 = new VectorImpl(8, 10, -6);
		
		Assert.assertEquals(v4, v3);
		
	}
	
	@Test
	public void testMult() {

		Vector v1 = new VectorImpl(1, 2, 3);
		Vector v2 = new VectorImpl(7, 8, -9);

		Vector v3 = v1.mult(v2);
		Vector v4 = new VectorImpl(7, 16, -27);
		
		Assert.assertEquals(v4, v3);
		
		float value = 4;

		v3 = v1.mult(value);
		v4 = new VectorImpl(4, 8, 12);
		
		Assert.assertEquals(v4, v3);
		
	}
	
	
	@Test
	public void testSub() {

		Vector v1 = new VectorImpl(1, 2, 3);
		Vector v2 = new VectorImpl(7, 8, -9);

		Vector v3 = v1.sub(v2);
		Vector v4 = new VectorImpl(-6, -6, 12);
		
		Assert.assertEquals(v4, v3);
		
	}
	

}
