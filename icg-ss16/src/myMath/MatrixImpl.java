package myMath;

import java.nio.FloatBuffer;
import java.util.Arrays;

import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import ogl.vecmathimp.VectorImp;

public class MatrixImpl implements Matrix {

	MatrixCore inverseData, data;

	public MatrixImpl(float[] vals) {
		data = new MatrixCore(vals);
		inverseData = new MatrixCore(vals);
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
		return new MatrixImpl(data.mult(((MatrixImpl) that).data), ((MatrixImpl) that).inverseData.mult(inverseData));
	}

	@Override
	public boolean equals(Object obj) {
		return equals((Matrix) obj, 0f);
	}

	public String toString() {
		String result = "";
		for (int i = 0; i < data.getMatrix().length; i++) {
			if (i % 4 == 0)
				result += "\n";
			result += data.getMatrix()[i] + "|";
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
				data.getMatrix()[0] * v.x() + data.getMatrix()[1] * v.y() + data.getMatrix()[2] * v.z()
						+ data.getMatrix()[3] * 1.0f,
				data.getMatrix()[4] * v.x() + data.getMatrix()[5] * v.y() + data.getMatrix()[6] * v.z()
						+ data.getMatrix()[7] * 1.0f,
				data.getMatrix()[8] * v.x() + data.getMatrix()[9] * v.y() + data.getMatrix()[10] * v.z()
						+ data.getMatrix()[11] * 1.0f);
	}

	@Override
	public Vector transformDirection(Vector v) {

		return new VectorImp(
				data.getMatrix()[0] * v.x() + data.getMatrix()[1] * v.y() + data.getMatrix()[2] * v.z()
						+ data.getMatrix()[3] * 0.0f,
				data.getMatrix()[4] * v.x() + data.getMatrix()[5] * v.y() + data.getMatrix()[6] * v.z()
						+ data.getMatrix()[7] * 0.0f,
				data.getMatrix()[8] * v.x() + data.getMatrix()[9] * v.y() + data.getMatrix()[10] * v.z()
						+ data.getMatrix()[11] * 0.0f);
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
		return new MatrixImpl(inverseData.getMatrix());
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
		if (Arrays.equals(this.asArray(), m.asArray()))
			return true;
		return false;
	}

}
