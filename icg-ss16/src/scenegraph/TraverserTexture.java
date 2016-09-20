package scenegraph;

import java.util.ArrayList;
import java.util.List;

public class TraverserTexture extends Traverser {
	
	private int textureID = 0;
	List<TextureNormal> textures = new ArrayList<TextureNormal>();

	public TraverserTexture(){
		textureID = 0;
	}
	
	public List<TextureNormal> getTextures() {
		return textures;
	}

	@Override
	public void visit(Gruppenknoten k) {
		for (Knoten child : k.getChilds()) {
			child.accept(this);
		}
	}

	@Override
	public void visit(Geometrieknoten k) {
		textures.add(new TextureNormal(k.getTexturePath(), false));
		
		k.setTextureIndex(textureID);
		textureID++;
		
		k.setNormalIndex(-1);
		
		if(!k.getNormalPath().equals("")){
			textures.add(new TextureNormal(k.getNormalPath(), true));
			k.setNormalIndex(textureID);
			textureID++;
		}
	}

	@Override
	public void visit(Kameraknoten kameraknoten) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Lichtknoten lichtknoten) {
		// TODO Auto-generated method stub

	}
}
