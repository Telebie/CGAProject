package renderEngine;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.Shader;
import textures.ModelTexture;

public class EntityRenderer {

	private Shader shader;
	
	
	public EntityRenderer(Shader shader, Matrix4f projectionMatrix) {		
		this.shader = shader;
		 
		shader.start();						//ProjectionMatrix direkt in den Shader laden 
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop(); 
	}
	

	
	
	public void render(Map<TexturedModel, List<Entity>> entities) {
		for(TexturedModel model:entities.keySet()) {	//Durch alle Keys der HashMap 
			prepareTexturedModel(model); 
			
			List<Entity> batch = entities.get(model); 	//Alle Entities holen, die das TexturedModel nutzen 
			for(Entity entity:batch) {					//Für jede Entity, Vorbereiten 
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getIndexcount(), GL11.GL_UNSIGNED_INT, 0);	//final render
			}
			
			unbindTexturedModel();  
		}
	}
	
	
	
	// => wird nur einmal aufgerufen und nicht für jede gleiche Entity 
		private void prepareTexturedModel(TexturedModel model) {		
			RawModel rawModel = model.getRawModel(); 			//Model selektieren
			GL30.glBindVertexArray(rawModel.getVaoID());
			GL20.glEnableVertexAttribArray(0);					//Attribut Position aktivieren
			GL20.glEnableVertexAttribArray(1);					//Attribut Textur aktivieren
			GL20.glEnableVertexAttribArray(2);					//Attribut Normals aktivieren
			
			ModelTexture texture = model.getTexture();												//Textur des Models abrufen um die Variablen zu bekommen
			shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity()); 		//Variablen an den Shader
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);				//Texturebank wo sampler2D vom Shader drin ist
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
		}
		
		private void unbindTexturedModel() {					//Wenn alle Entities gerendert wurden
			GL20.glDisableVertexAttribArray(0); 
			GL20.glDisableVertexAttribArray(1); 
			GL20.glDisableVertexAttribArray(2); 
			GL30.glBindVertexArray(0); 
		}
		
		private void prepareInstance(Entity entity) {		//Per Entity des TexturedModel wird vorbereitet 
			Matrix4f transMatrix = Transformation.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale()); 
			shader.loadTransformationMatrix(transMatrix);
		}
	
	
}

