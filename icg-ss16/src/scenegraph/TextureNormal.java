package scenegraph;

public class TextureNormal {
	
	private String texturePath;
	private boolean isNormal;
	
	public TextureNormal(String texturePath, boolean isNormal){
		this.texturePath = texturePath;
		this.isNormal = isNormal;
	}

	public String getTexturePath() {
		return texturePath;
	}

	public void setTexturePath(String texturePath) {
		this.texturePath = texturePath;
	}

	public boolean isNormal() {
		return isNormal;
	}

	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}
	

}
