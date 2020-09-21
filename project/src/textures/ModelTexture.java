package textures;

/*** Repräsentiert die Textur ***/

public class ModelTexture {

	private int textureID;
	
	private float shineDamper = 1;				//wie viele Strahlen die Kamera einfängt
	private float reflectivity = 0; 			//Reflektion des Modells -> wie stark 

	public ModelTexture(int textureID) {
		this.textureID = textureID;
	}

	public int getTextureID() {
		return this.textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDumper(float shineDumper) {
		this.shineDamper = shineDumper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}
	
	
}
