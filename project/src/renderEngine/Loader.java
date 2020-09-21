package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

public class Loader {	//Modelle in den Speicherladen 
	
	// Lists for all cleanUp 
	private List<Integer> vaos = new ArrayList<Integer>(); 
	private List<Integer> vbos = new ArrayList<Integer>(); 
	private List<Integer> ibos = new ArrayList<Integer>(); 
	private List<Integer> textures = new ArrayList<Integer>(); 
	
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {	//Positionen der Vertices des Modells 
		
		int vaoID = createVAO(); 						//VAO erstellen 
		bindIndicesBuffer(indices); 					//IBO erstellen
		storeDataInAttributeList(0,3, positions); 		//VBO position
		storeDataInAttributeList(1,2, textureCoords); 	//VBO texture 
		storeDataInAttributeList(2,3, normals); 	//VBO texture 
		unbindVAO();
		return new RawModel(vaoID, indices.length); 
		
	}
	
	public int loadTexture(String fileName) {	//Texturen laden
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/texture/"+fileName+".png"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);				//Aufruf der lower resolution Textures Mipmap
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.5f);		//niedrige negative Zahl stellt die Textur etwas detaillierter dar
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID); //TextureID in die Liste
		return textureID;
	}
	
	
	/***VAO***/
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID); 
		GL30.glBindVertexArray(vaoID);
		return vaoID; 
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	
	/***IBO***/
	private void bindIndicesBuffer(int[] indices) {
		int iboID = GL15.glGenBuffers();	
		ibos.add(iboID); 																	//Generiertes IBO zur Liste hinzufügen 
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboID); 							//IBO binden
		IntBuffer buffer = storeDataInIntBuffer(indices); 									//Int Array IBO in INTBuffer umwandeln 
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); 		
	}
	
	
	private IntBuffer storeDataInIntBuffer(int[] data) {	//Data in IBO speichern 
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer; 
	}
	
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {	//Data in VBO speichern 
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer; 
	}
	
	
	/***VBO***/
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {	//attributeNumber=position/texture , coordinate=wie viele Koordinaten hat ein Attribut 
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID); 
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0,0);	//
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); 	//cleanup
	}
	
	
	// Delete all IDs in the ArrayLists 
	public void cleanup() {
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo); 
		}
		for(int texture:textures) {
			GL11.glDeleteTextures(texture);
		}
	}

}
