package scenegraph;

import icg.math.FactoryImpl;
import ogl.vecmathimp.VectorImp;

public class SceneTester {

	public static void main(String[] args) {
		Knoten knotenT = new Gruppenknoten("T", FactoryImpl.vecmath.translationMatrix(1, 2, 3));
		Knoten knotenR = new Gruppenknoten("R", FactoryImpl.vecmath.rotationMatrix(1, 0, 0, 00));
		Knoten knotenS = new Geometrieknoten("S", FactoryImpl.vecmath.scaleMatrix(0, 0, 0), new VectorImp(1, 1, 1));
		
		knotenT.setChild(knotenR);
		knotenR.setChild(knotenS);
		
		knotenT.draw(FactoryImpl.vecmath.identityMatrix());
	}
}
