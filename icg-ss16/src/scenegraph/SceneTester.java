package scenegraph;

import icg.math.FactoryImpl;
import ogl.vecmathimp.VectorImp;

public class SceneTester {

	public static void main(String[] args) {
		Knoten knotenT = new Gruppenknoten("T", FactoryImpl.vecmath.translationMatrix(1, 2, 3));
		Knoten knotenR = new Gruppenknoten("R", FactoryImpl.vecmath.rotationMatrix(0, 0, 1, 10));
		Knoten knotenS = new Geometrieknoten("S", FactoryImpl.vecmath.scaleMatrix(1, 1, 1), new VectorImp(0, 0, 1));
		
		knotenT.setChild(knotenR);
		knotenR.setChild(knotenS);
		
//		knotenT.draw(FactoryImpl.vecmath.identityMatrix());
		
		for(int i = 10; i <= 360; i = i+10){
			knotenR.setTransformMatrix(knotenR.getTransformMatrix().mult(FactoryImpl.vecmath.rotationMatrix(0, 0, 1, 10)));
//			knotenT.draw(FactoryImpl.vecmath.identityMatrix());
			knotenT.accept(new Traverser());
		}
		
		
	}
	
}
