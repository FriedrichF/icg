package uebung3;

import java.nio.FloatBuffer;

import ogl.vecmath.Vector;

public class VectorImpl implements Vector {
	
	float[] vector = new float[3];

	@Override
	public float x() {
		return vector[0];
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
		// TODO Auto-generated method stub
		return null;
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
	
	public float quadriereVector(Vector v){
		return v.x()*v.x()+v.y()*v.y()+v.z()*v.z();
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
		// TODO Auto-generated method stub
		return null;
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
		return 0;
	}

}
