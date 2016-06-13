package myMath;

import java.nio.FloatBuffer;
import java.util.Arrays;

import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import ogl.vecmathimp.VectorImp;

public class MatrixImpl implements Matrix {

	MatrixCore inverseData, data = new MatrixCore();

	public MatrixImpl(float[] vals) {
		data = new MatrixCore(vals);
		inverseData = new MatrixCore(vals);
	}

	public MatrixImpl(float[] mult, float[] mult2) {
		data.setMatrix(mult);
		inverseData.setMatrix(mult2);
	}

	public MatrixImpl(MatrixCore mult, MatrixCore mult2) {
		data = mult;
		inverseData = mult2;
	}

	@Override
	public float[] getValues() {
		return data.getMatrix();
	}

	@Override
	public void setValues(float[] vals) {
		this.data.setMatrix(vals);
	}

	@Override
	public float get(int c, int r) {
		return data.get(c, r);
	}

	@Override
	public Matrix mult(Matrix that) {
		return new MatrixImpl(data.mult(that.getValues()), inverseData.mult(that.invertFull().getValues()));
	}

	@Override
	public boolean equals(Object obj) {
		return equals((Matrix) obj, 0f);
	}

	public String toString() {
		String result = "";
		for (int row = 0; row < 4; row++) {
			for (int column = 0; column < 4; column++) {
				result += data.get(column, row) + "|";
			}
			result += "\n";
		}
		return result;
	}

	@Override
	public Matrix multSlow(Matrix m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector transformPoint(Vector v) {
		return new VectorImp(
				data.getMatrix()[0] * v.x() + data.getMatrix()[4] * v.y() + data.getMatrix()[8] * v.z()
						+ data.getMatrix()[12] * 1.0f,
				data.getMatrix()[1] * v.x() + data.getMatrix()[5] * v.y() + data.getMatrix()[9] * v.z()
						+ data.getMatrix()[13] * 1.0f,
				data.getMatrix()[2] * v.x() + data.getMatrix()[6] * v.y() + data.getMatrix()[10] * v.z()
						+ data.getMatrix()[14] * 1.0f);
	}

	@Override
	public Vector transformDirection(Vector v) {

		return new VectorImp(
				data.getMatrix()[0] * v.x() + data.getMatrix()[4] * v.y() + data.getMatrix()[8] * v.z()
						+ data.getMatrix()[12] * 0.0f,
				data.getMatrix()[1] * v.x() + data.getMatrix()[5] * v.y() + data.getMatrix()[9] * v.z()
						+ data.getMatrix()[13] * 0.0f,
				data.getMatrix()[2] * v.x() + data.getMatrix()[6] * v.y() + data.getMatrix()[10] * v.z()
						+ data.getMatrix()[14] * 0.0f);
	}

	@Override
	public Vector transformNormal(Vector v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix transpose() {
		float[] result = new float[16];

		for (int resultIndex = 0; resultIndex < data.getMatrix().length; resultIndex++) {
			result[resultIndex] = data.getMatrix()[(resultIndex * 4) % 15];
		}

		result[15] = data.getMatrix()[15];

		return new MatrixImpl(result);
	}

	@Override
	public Matrix invertRigid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix invertFull() {
		return new MatrixImpl(inverseData, data);
	}

	@Override
	public float[] asArray() {
		return data.getMatrix();
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
	public Matrix getRotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix getTranslation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Matrix m, float epsilon) {
		if (Arrays.equals(getValues(), m.getValues()))
			return true;
		return false;
	}

}
