package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import models.Camera;
import models.Entity;
import models.TexturedModel;
import shaders.Shader;
import shaders.TerrainShader;
import shadow.ShadowMasterRenderer;
import terrains.Terrain;

/*** Code für alle Renderer  ***/

public class MasterRenderer {
	
		// Konstanten für die ProjectionMatrix 
		public static final float FOV = 70;
		public static final float NEAR_PLANE = 0.1f;
		public static final float FAR_PLANE = 1000f;
		
		private Matrix4f projectionMatrix;

		private Shader shader = new Shader(); 
		private EntityRenderer renderer; 
		
		private TerrainRenderer terrainRenderer;
		private TerrainShader terrainShader = new TerrainShader(); 
		
		private ShadowMasterRenderer shadowRenderer;
		
		private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
		private List<Terrain> terrains = new ArrayList<Terrain>();
		
		
		public MasterRenderer(Camera camera) {
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
			createProjectionMatrix();			//ProjectionMatrix soll als erstes gerendert werden und durch den Konstruktor wird es immer als erstes geladen und muss nicht nochmal aufgerufen werden 
			terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
			renderer = new EntityRenderer(shader, projectionMatrix); 	//Rendern nach Laden der ProjectionMatrix
			//this.shadowRenderer = new ShadowMasterRenderer(camera); 
		}
		
		
		public void render(Light light, Camera camera) {
			prepare(); 	//Renderer vorbereiten -> all clean up
			shader.start(); 
			shader.loadLight(light);
			shader.loadViewMatrix(camera); 
			renderer.render(entities); 
			shader.stop();
			
			terrainShader.start();
			terrainShader.loadLight(light);
			terrainShader.loadViewMatrix(camera);
			terrainRenderer.render(terrains); 
			terrainShader.stop();
			
			terrains.clear();
			entities.clear(); 	//Clear HashMap of Entities 
		}
		
		
		
		
		/*** Entity ***/ 
		//Put Entities into the HashMap - in die richtige Liste sortieren
		public void processEntity(Entity entity) {
			TexturedModel entityModel = entity.getModel();			//Welches TexturedModel nutzt die Entity
			List<Entity> batch = entities.get(entityModel); 
			if(batch != null) { 									//Existiert bereits ein batch
				batch.add(entity); 		
			}else {													//Neuer Batch 
				List<Entity> newBatch = new ArrayList<Entity>(); 
				newBatch.add(entity); 
				entities.put(entityModel, newBatch); 
			}
		}
	
		
		
		
		/*** Terrains ***/
		// Terrains zur List hinzufügen
		public void processTerrain(Terrain terrain) {
			terrains.add(terrain); 
		}
		
		
		/*** ShadowMapping ***/
		public int getShadowMaptexture() {
			return shadowRenderer.getShadowMap();
		}
		
		public void renderShadowMap(List<Entity> entityList, Light light) {
			for(Entity entity : entityList) {
				shadowRenderer.render(entities, light);
				entities.clear();
			}
		}
		
		
		
		
		
		/*** Für alle Renderer ***/
		
		public void prepare() {
			GL11.glEnable(GL11.GL_DEPTH_TEST); //Depth-Test: welches Dreieck vor welchem ist
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  	//Cleanup Colour & Depth Test Buffer
			GL11.glClearColor(0.49f, 89f, 0.98f, 1);
		}
		
	
		public void cleanup() {
			shader.cleanup();
			terrainShader.cleanup();
			shadowRenderer.cleanUp();
		}
		
		
		/*** Projection Matrix ***/
		private void createProjectionMatrix() {
			float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
			float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
			float x_scale = y_scale / aspectRatio;
			float frustum_length = FAR_PLANE - NEAR_PLANE;
			
			projectionMatrix = new Matrix4f();
			projectionMatrix.m00(x_scale); 
			projectionMatrix.m11(y_scale); 
			projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length)); 
			projectionMatrix.m23(-1);
			projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
			projectionMatrix.m33(0);
		}
	
}
