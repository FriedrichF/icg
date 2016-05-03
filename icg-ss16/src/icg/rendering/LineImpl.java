package icg.rendering;

import java.util.ArrayList;

import icg.math.RGBColorImpl;
import ogl.vecmath.*;

public class LineImpl extends Line {
	/**
	 * constructor, sets vertices of this line
	 * 
	 * @param p1
	 *            the first vertex
	 * @param p2
	 *            the second vertex
	 */
	public LineImpl(Vector p1, Vector p2) {
		super(p1, p2);
	}

	/**
	 * @param fx
	 *            the x coordinate of the pixel
	 * @param fy
	 *            the y coordinate of the pixel
	 * @param width
	 *            the width of the image
	 * @param height
	 *            the height of the image
	 * @return a color if the pixel is on the line, null otherwise
	 */
	public Color pixelColorAt(int fx, int fy, int width, int height) {
		float y = (((p2.y() - p1.y()) / (p2.x() - p1.x())) * (fx - p1.x()) + p1.y());

		if (y == fy && (fy > p1.y() && fy < p2.y()))
			return new RGBColorImpl(0, 0, 0);

		return new RGBColorImpl(1, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see icg.rendering.Line#getPixels(int, int)
	 */
	@Override
	public ArrayList<Pixel> getPixels(int width, int height) {
		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
		
		int x0 = (int) p1.x();
		int x1 = (int) p2.x();
		int y0 = (int) p1.y();
		int y1 = (int) p2.y();
		
		int dx = (int) Math.abs(x1 - x0), sx = x0 < x1 ? 1 : -1;
		int dy = (int) -Math.abs(y1 - y0), sy = y0 < y1 ? 1 : -1;
		int err = dx + dy, e2; /* error value e_xy */

		int i = 0;
		while (true) {
			i++;
			pixels.add(new Pixel((int) x0, (int) y0, new RGBColorImpl(1, 1, 1)));
			if (x0 == x1 && y0 == y1)
				break;
			e2 = 2 * err;
			if (e2 > dy) {
				err += dy;
				x0 += sx;
			} /* e_xy+e_x > 0 */
			if (e2 < dx) {
				err += dx;
				y0 += sy;
			} /* e_xy+e_y < 0 */
		}
		
		return pixels;
	}
}
