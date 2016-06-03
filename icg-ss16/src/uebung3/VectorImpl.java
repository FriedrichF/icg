package uebung3;

import java.nio.FloatBuffer;
import java.util.Arrays;

import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class VectorImpl implements Vector {

	float[] vector = new float[3];

	public VectorImpl(float x, float y, float z) {
		this.vector[0] = x;
		this.vector[1] = y;
		this.vector[2] = z;
	}

	public VectorImpl(Vector v) {
		this.vector[0] = v.x();
		this.vector[1] = v.y();
		this.vector[2] = v.z();
	}

	@Override
	public float x() {
		return vector[0];
	}
	
	@Override
	public boolean equals(Object obj) {
		if (Arrays.equals(this.asArray(), ((Vector) (obj)).asArray()))
			return true;
		return false;
	}

	@Override
	public float y() {
		return vector[1];
	}

	@Override
	public float z() {
		return vector[2];
	}

	@Override
	public Vector add(Vector v) {
		return new VectorImpl(x() + v.x(), y() + v.y(), z() + v.z());
	}

	@Override
	public Vector sub(Vector v) {
		return new VectorImpl(x() - v.x(), y() - v.y(), z() - v.z());
	}

	@Override
	public Vector mult(float s) {
		return new VectorImpl(x() * s, y() * s, z() * s);
	}

	@Override
	public Vector mult(Vector v) {
		return new VectorImpl(x() * v.x(), y() * v.y(), z() * v.z());
	}

	@Override
	public float length() {
		return (float) Math.sqrt(quadriereVector());
	}

	@Override
	public Vector normalize() {
		// TODO Auto-generated method stub
		return null;
	}

	public float quadriereVector() {
		return x() * x() + y() * y() + z() * z();
	}

	@Override
	public float dot(Vector v) {
		return x() * v.x() + y() * v.y() + z() * v.z();
	}

	@Override
	public Vector cross(Vector v) {
		return new VectorImpl(y() * v.z() - z() * v.y(), z() * v.x() - x() * v.z(), x() * v.y() - y() * v.x());
	}

	@Override
	public float[] asArray() {
		return vector;
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
		return (int) (length() - o.length());
	}

	@Override
	public int size() {
		return vector.length;
	}

	public String toString() {
		return x() + "," + y() + "," + z();
	}

}
