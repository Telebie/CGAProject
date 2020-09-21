package renderEngine;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;
import shaders.TerrainShader;
import terrains.Terrain;
import textures.ModelTexture;

public class TerrainRenderer {
	
		private TerrainShader shader; 
		
		public TerrainRenderer (TerrainShader shader, Matrix4f projectionMatrix) {
			this.shader = shader;
			shader.start();
			shader.loadProjectionMatrix(projectionMatrix); 
			shader.stop();
		}
		
		public void render(List<Terrain> terrains) {		//Liste aller Terrains die gerendert werden sollen 
			for(Terrain terrain : terrains) {				//Jedes Terrain in der Liste rendern 
				prepareTerrain(terrain);
				loadModelMatrix(terrain);
				GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getIndexcount(), GL11.GL_UNSIGNED_INT, 0);
				unbindTexturedModel(); 
			}
		}
		
		
		private void prepareTerrain(Terrain terrain) {			
			RawModel rawModel = terrain.getModel(); 			//Model selektieren
			GL30.glBindVertexArray(rawModel.getVaoID());
			GL20.glEnableVertexAttribArray(0);					//Attribut Position aktivieren
			GL20.glEnableVertexAttribArray(1);					//Attribut Textur aktivieren
			GL20.glEnableVertexAttribArray(2);					//Attribut Normals aktivieren
			
			ModelTexture texture = terrain.getTexture();											//Textur des Models abrufen um die Variablen zu bekommen
			shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity()); 		//Variablen an den Shader
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);				//Texturebank wo sampler2D vom Shader drin ist
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		}
		
		private void unbindTexturedModel() {					//Wenn alle Entities gerendert wurden
			GL20.glDisableVertexAttribArray(0); 
			GL20.glDisableVertexAttribArray(1); 
			GL20.glDisableVertexAttribArray(2); 
			GL30.glBindVertexArray(0); 
		}
		
		//Transformation 
		private void loadModelMatrix(Terrain terrain) {		//Entity des TexturedModel 
			Matrix4f transMatrix = Transformation.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1); 
			shader.loadTransformationMatrix(transMatrix);
		} 

}
