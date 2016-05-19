package icg.rendering;

import java.awt.image.BufferedImage;

import ogl.vecmath.*;

abstract class RotatingTriangleBase {
	protected Matrix localTrafo = MyMathFactory.translationMatrix(MyMathFactory.vector(0, 0, 0));
	
	/**
	 * returns the rotation axis
	 * @return the rotation axis
	 */
	public abstract Vector getRotationAxis();
	
	/**
	 * set the new rotation axis
	 * @param axis the new rotation axis
	 */
	public abstract void setRotationAxis(Vector axis);
	
	/**
	 * Renders the triangle into a given image. The default implementation calls the pixelColorAt method for each pixel.
	 * Override this function for improved performance
	 * @param img the image to render to
	 */
	public abstract void fillImage(BufferedImage img);
	
	/**
	 * returns the local transformation (which may be null)
	 * @return a matrix representing the local transformation
	 */
	public Matrix getTransform(){
		return localTrafo;
	}
	
	/**
	 * set the viewing distance (camera position)
	 * @param zPos the viewing distance
	 */
	public abstract void setViewDistance(float zPos);
	
	/**
	 *  updates the local transformation
	 */
	public void setTransform(Matrix m){
		localTrafo = m;
	}
}
