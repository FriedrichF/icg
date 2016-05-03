package uebung2;

import java.nio.FloatBuffer;

import ogl.vecmath.Vector;

public class Point implements Vector {

	float x, y, z;

	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public float x() {
		// TODO Auto-generated method stub
		return this.x;
	}

	@Override
	public float y() {
		// TODO Auto-generated method stub
		return this.y;
	}

	@Override
	public float z() {
		// TODO Auto-generated method stub
		return this.z;
	}

	@Override
	public Vector add(Vector v) {
		return ogl.vecmathimp.FactoryDefault.vecmath.vector(v.x() + x(), v.y() + y(), v.z() + z());
	}

	@Override
	public Vector sub(Vector v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector mult(float s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector mult(Vector v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector normalize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float dot(Vector v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector cross(Vector v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float[] asArray() {
		float[] array = { x, y, z };
		return array;
	}

	@Override
	public FloatBuffer asBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fillBuffer(FloatBuffer buf) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(Vector o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return asArray().length;
	}

}
