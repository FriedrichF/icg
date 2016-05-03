package icg.math;

import java.nio.FloatBuffer;

import ogl.vecmath.Color;

public class YUVColorImpl implements Color {

	float y;
	float u;
	float v;

	public YUVColorImpl(float y, float u, float v) {
		this.y = y;
		this.u = u;
		this.v = v;
	}

	public YUVColorImpl(RGBColorImpl rgbColor) {
		this.y = (float) ((0.257 * rgbColor.getR()) + (0.504 * rgbColor.getG()) + (0.098 * rgbColor.getB()) + 16);
		this.v = (float) ((0.439 * rgbColor.getR()) - (0.368 * rgbColor.getG()) - (0.071 * rgbColor.getB()) + 128);
		this.u = (float) (-(0.148 * rgbColor.getR()) - (0.291 * rgbColor.getG()) + (0.439 * rgbColor.getB()) + 128);
	}

	@Override
	public float getR() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getG() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getB() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBlack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Color add(Color c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color modulate(float s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color modulate(Color c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color clip() {
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
	public int toAwtColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(Color o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
