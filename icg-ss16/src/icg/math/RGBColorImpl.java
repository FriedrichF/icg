package icg.math;

import java.nio.FloatBuffer;

import ogl.vecmath.Color;

public class RGBColorImpl implements Color {

	float r;
	float g;
	float b;

	public RGBColorImpl(float r, float g, float b) {
		if (r > 1)
			this.r = 1;
		else if (r < 0)
			this.r = 0;
		else
			this.r = r;
		
		if (r > 1)
			this.g = 1;
		else if (g < 0)
			this.g = 0;
		else
			this.g = g;
		
		if (b > 1)
			this.b = 1;
		else if (b < 0)
			this.b = 0;
		else
			this.b = b;
	}

	@Override
	public float getR() {
		return r;
	}

	@Override
	public float getG() {
		return g;
	}

	@Override
	public float getB() {
		return b;
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
		float[] array = {r, g, b};
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
	public int toAwtColor() {
		return new java.awt.Color(r, g, b).getRGB();
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
	
	public String toString(){
		return "RGB("+this.r+","+this.g+","+this.b+")";
	}

}
