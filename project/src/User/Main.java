package User;

import org.lwjgl.opengl.Display;

import models.Camera;
import models.Entity;
import models.Player;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Light;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;

public class Main {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();		//Fenster erstellen 
		Loader loader = new Loader();
		
		
		//Model laden - lädt die Arrays mit den Vertices, IBOs und TexCoords zum einem RawModel
		RawModel characterModel = OBJLoader.loadObjModel("character", loader); 
		RawModel baumModel = OBJLoader.loadObjModel("baum", loader); 
		RawModel birkeModel = OBJLoader.loadObjModel("baum_birke", loader); 
		RawModel kleinBaumModel = OBJLoader.loadObjModel("baum_klein", loader); 
		RawModel tanneModel = OBJLoader.loadObjModel("baum_kleine_tanne", loader); 
		RawModel baumstammModel = OBJLoader.loadObjModel("baumstamm", loader); 
		RawModel steinchenModel = OBJLoader.loadObjModel("steinchen", loader); 
		RawModel kieselModel = OBJLoader.loadObjModel("kiesel", loader); 
		RawModel steinModel = OBJLoader.loadObjModel("stein", loader); 
		RawModel steinQuerModel = OBJLoader.loadObjModel("stein_quer", loader); 
		RawModel grassModel = OBJLoader.loadObjModel("grass", loader); 
		RawModel kesselModel = OBJLoader.loadObjModel("kessel", loader); 
		RawModel bridgeModel = OBJLoader.loadObjModel("bridge", loader); 
		RawModel wasserModel = OBJLoader.loadObjModel("wasser", loader); 
		RawModel besenModel = OBJLoader.loadObjModel("besen", loader); 
		RawModel schlossModel = OBJLoader.loadObjModel("schloss", loader); 
		
		
		//Textur laden
		ModelTexture characterTex = new ModelTexture(loader.loadTexture("character")); 
		ModelTexture besenTex = new ModelTexture(loader.loadTexture("besen"));
		ModelTexture schlossTex = new ModelTexture(loader.loadTexture("schloss"));
		ModelTexture kesselTex = new ModelTexture(loader.loadTexture("kessel"));
		kesselTex.setShineDumper(100);
		kesselTex.setReflectivity(100);
		ModelTexture naturTex = new ModelTexture(loader.loadTexture("natur"));
		naturTex.setShineDumper(10);
		naturTex.setReflectivity(1);
		
		//Model und Textur verbinden 
		TexturedModel characterTexturedModel = new TexturedModel(characterModel, characterTex);
		TexturedModel baumTexturedModel = new TexturedModel(baumModel, naturTex); 
		TexturedModel birkeTexturedModel = new TexturedModel(birkeModel, naturTex); 
		TexturedModel kleinBaumTexturedModel = new TexturedModel(kleinBaumModel, naturTex); 
		TexturedModel tanneTexturedModel = new TexturedModel(tanneModel, naturTex); 
		TexturedModel baumstammTexturedModel = new TexturedModel(baumstammModel, naturTex); 
		TexturedModel steinchenTexturedModel = new TexturedModel(steinchenModel, naturTex); 
		TexturedModel kieselTexturedModel = new TexturedModel(kieselModel, naturTex); 
		TexturedModel steinTexturedModel = new TexturedModel(steinModel, naturTex); 
		TexturedModel steinQuerTexturedModel = new TexturedModel(steinQuerModel, naturTex); 
		TexturedModel grassTexturedModel = new TexturedModel(grassModel, naturTex); 
		TexturedModel kesselTexturedModel = new TexturedModel(kesselModel, kesselTex); 
		TexturedModel bridgeTexturedModel = new TexturedModel(bridgeModel, naturTex); 
		TexturedModel wasserTexturedModel = new TexturedModel(wasserModel, naturTex); 
		TexturedModel besenTexturedModel = new TexturedModel(besenModel, besenTex); 
		TexturedModel schlossTexturedModel = new TexturedModel(schlossModel, schlossTex); 
		
		
		/* Entities */
		List<Entity> entities = new ArrayList<Entity>();
		
		entities.add(new Entity(tanneTexturedModel, new Vector3f(7,0,-14), 0, 0, 0, 0.09f));
		entities.add(new Entity(steinTexturedModel, new Vector3f(4.5f,0,-17), 0, 90, 0, 0.12f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(5,0,-20), 0, 0, 0, 0.12f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(-5,0,-35), 0, -90, 0, 0.1f));
		entities.add(new Entity(steinTexturedModel, new Vector3f(-4,0,-40), 0, -90, 0, 0.2f));
		entities.add(new Entity(kleinBaumTexturedModel, new Vector3f(-5,0,-48), 0, 0, 0, 0.2f));
		entities.add(new Entity(kleinBaumTexturedModel, new Vector3f(-5,0,-60), 0, 0, 0, 0.15f));
		entities.add(new Entity(kleinBaumTexturedModel, new Vector3f(-6,0,-65), 0, 0, 0, 0.2f));
		entities.add(new Entity(kesselTexturedModel, new Vector3f(5.5f,0,-60), 0, 0, 0, 0.12f));
		entities.add(new Entity(baumstammTexturedModel, new Vector3f(0,0,-65), 0, 0, 0, 0.15f));
		
		entities.add(new Entity(steinQuerTexturedModel, new Vector3f(-5,0,-110), 0, 0, 0, 0.15f)); 
		entities.add(new Entity(steinQuerTexturedModel, new Vector3f(-2,0,-110), 0, 180, 0, 0.15f)); 
		entities.add(new Entity(baumTexturedModel, new Vector3f(1,0,-115), 0, 0, 0, 0.1f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(3,0,-118), 0, 0, 0, 0.1f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(5,0,-110), 0, 90, 0, 0.1f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(6,0,-119), 0, 180, 0, 0.1f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(3,0,-107), 0, 0, 0, 0.09f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(4,0,-112), 0, 0, 0, 0.11f));
		
		entities.add(new Entity(tanneTexturedModel, new Vector3f(-6.5f,0,-150), 0, 0, 0, 0.09f));
		entities.add(new Entity(steinTexturedModel, new Vector3f(6,0,-155.5f), 0, 90, 0, 0.15f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(1,0.7f,-160), 0, 0, 90, 0.1f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(-1,0.5f,-160), 0, 0, -90, 0.1f));
		
		entities.add(new Entity(baumstammTexturedModel, new Vector3f(4.5f,0,-210), 0, 0, 0, 0.15f));
		entities.add(new Entity(steinQuerTexturedModel, new Vector3f(-5,0,-210), 0, 0, 0, 0.25f));
		entities.add(new Entity(steinTexturedModel, new Vector3f(-1,0,-208), 0, 90, 0, 0.25f));
		entities.add(new Entity(steinTexturedModel, new Vector3f(-2.5f,0,-206), 0, 180, 0, 0.13f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(-5,0,-205), 0, 0, 0, 0.09f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(-6.5f,0,-204), 0, 0, 0, 0.06f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(-5.5f,0,-203), 0, 0, 0, 0.04f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(-4,0,-216), 0, 0, 0, 0.18f));

		entities.add(new Entity(kesselTexturedModel, new Vector3f(5.5f,0,-250), 0, 0, 0, 0.12f));
		entities.add(new Entity(kesselTexturedModel, new Vector3f(-5.5f,0,-250), 0, 0, 0, 0.12f));
		entities.add(new Entity(bridgeTexturedModel, new Vector3f(0,0,-260), 0, 0, 0, 0.5f));
		entities.add(new Entity(wasserTexturedModel, new Vector3f(-12,-0.316f,-260), 0, 0, 0, 0.32f));
		entities.add(new Entity(wasserTexturedModel, new Vector3f(0,-0.316f,-260), 0, 0, 0, 0.32f));
		entities.add(new Entity(wasserTexturedModel, new Vector3f(12,-0.316f,-260), 0, 0, 0, 0.32f));
		
		entities.add(new Entity(steinQuerTexturedModel, new Vector3f(5.5f,0,-300), 0, 180, 0, 0.15f)); 
		entities.add(new Entity(steinTexturedModel, new Vector3f(1,0,-300), 0, 0, 0, 0.25f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(-6,0,-288), 0, 0, 0, 0.12f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(-5,0,-293), 0, 90, 0, 0.1f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(-2,0,-297), 0, 0, 0, 0.1f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(-6,0,-297), 0, 0, 0, 0.1f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(-4,0,-301), 0, 90, 0, 0.1f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(-5,0,-308), 0, 0, 0, 0.12f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(-4,0,-313), 0, 0, 0, 0.1f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(-5,0,-320), 0, 0, 0, 0.1f));
		
		entities.add(new Entity(besenTexturedModel, new Vector3f(-5,0.6f,-370), 0, 0, 0, 0.3f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(7,0,-348), 0, 0, 0, 0.07f));
		entities.add(new Entity(steinQuerTexturedModel, new Vector3f(4,0,-355), 0, -40, 0, 0.25f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(0,0,-360), 0, 0, 0, 0.1f));
		entities.add(new Entity(birkeTexturedModel, new Vector3f(1,0.25f,-391), -78, 0, -28, 0.1f)); 	//umgekippt

		entities.add(new Entity(tanneTexturedModel, new Vector3f(7,0,-415), 0, 0, 0, 0.08f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(6,0,-420), 0, 20, 0, 0.1f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(7,0,-430), 0, 0, 0, 0.08f));
		
		entities.add(new Entity(baumstammTexturedModel, new Vector3f(0,0,-450), 0, 0, 0, 0.15f));
		entities.add(new Entity(steinTexturedModel, new Vector3f(-6,0,-445), 0, -70, 0, 0.2f));
		entities.add(new Entity(tanneTexturedModel, new Vector3f(7,0,-445), 0, 40, 0, 0.1f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(5,0,-450), 0, 180, 0, 0.1f));
		entities.add(new Entity(baumTexturedModel, new Vector3f(-5,0,-450), 0, 0, 0, 0.1f));

		entities.add(new Entity(kesselTexturedModel, new Vector3f(5.5f,0,-500), 0, 0, 0, 0.12f));
		entities.add(new Entity(kesselTexturedModel, new Vector3f(-5.5f,0,-500), 0, 0, 0, 0.12f));

		entities.add(new Entity(schlossTexturedModel, new Vector3f(0,-0.9f,-520), 0, 0, 0, 1));

		
		//Gras
		Random random = new Random();
		for(int i=0;i<500;i++){ 
			entities.add(new Entity(grassTexturedModel, new Vector3f(random.nextFloat()*50 - 25,0, random.nextFloat() * -250), 0, random.nextFloat()*90, 0, 0.11f)); 
			entities.add(new Entity(grassTexturedModel, new Vector3f(random.nextFloat()*50 - 25,0, -270 + random.nextFloat() * -250), 0, random.nextFloat()*90, 0, 0.08f)); 
		}
		//Kiesel an der Brücke
		for(int i=0;i<200;i++){ 
			entities.add(new Entity(kieselTexturedModel, new Vector3f(random.nextFloat()*50 - 25,0, (4*random.nextFloat()) -254), 0, random.nextFloat()*90, 0, 0.08f)); 
			entities.add(new Entity(kieselTexturedModel, new Vector3f(random.nextFloat()*50 - 25,0, (4*random.nextFloat()) -269), 0, random.nextFloat()*90, 0, 0.08f)); 
		}
		for(int i=0;i<50;i++){ 
			entities.add(new Entity(steinchenTexturedModel, new Vector3f(random.nextFloat()*50 - 25,0, (4*random.nextFloat()) -254), 0, random.nextFloat()*90, 0, 0.06f)); 
			entities.add(new Entity(steinchenTexturedModel, new Vector3f(random.nextFloat()*50 - 25,0, (4*random.nextFloat()) -269), 0, random.nextFloat()*90, 0, 0.06f)); 
		}
		for(int i=0;i<20;i++){ 
			entities.add(new Entity(birkeTexturedModel, new Vector3f(random.nextFloat()* 10 -2 , 0, -360 + random.nextFloat() * -30), 0, 0, 0, 0.12f)); 
		}
		
		
		//Bäume am Rand
		for(int j=1; j<5; j++) {
			for(int i=0; i<100; i++) {
				float x = -5 + (-6*j);
				float y = 0;
				float z = -3 + (-5*i); 
				entities.add(new Entity(kleinBaumTexturedModel, new Vector3f(x,y,z), 0, 0, 0, 0.3f));
			}
		}
		for(int j=1; j<5; j++) {
			for(int i=0; i<100; i++) {
				float x = 5 + (6*j);
				float y = 0;
				float z = -3 + (-5*i); 
				entities.add(new Entity(kleinBaumTexturedModel, new Vector3f(x,y,z), 0, 0, 0, 0.3f));
			}
		}
		
		
		/* Terrain */
		List<Terrain> terrains = new ArrayList<Terrain>(); 
		Terrain terrain = new Terrain(0,-1, loader, new ModelTexture(loader.loadTexture("wiese")));
		Terrain terrain2 = new Terrain(-1,-1, loader, new ModelTexture(loader.loadTexture("wiese")));
		terrains.add(terrain);
		terrains.add(terrain2);
		
		
		/*** Character ***/
		Player player = new Player(characterTexturedModel, new Vector3f(0,0, -5), 0, 180, 0, 1);
		
		
		Light light = new Light(new Vector3f(50,2000,1000), new Vector3f(1,1,1));
		Camera camera = new Camera(player);
		MasterRenderer renderer = new MasterRenderer(camera);
		
		
		
		
		/***Game Logic***/ 
		while(!Display.isCloseRequested()) {  	//Solange Display nicht geschlossen wird, ist es aktiv 
			
			camera.move();
			player.move();
			
			//renderer.renderShadowMap(entities, light); 
			
			renderer.processEntity(player);
			
			for(Terrain t : terrains) {
				renderer.processTerrain(t);
			}
			
			for(Entity entity : entities) {
				renderer.processEntity(entity); 
			}
			
			renderer.render(light, camera); 
			DisplayManager.updateDisplay();	
		}
		
		
		//Cleanup
		renderer.cleanup();
		loader.cleanup();
		DisplayManager.closeDisplay();

	}

}
