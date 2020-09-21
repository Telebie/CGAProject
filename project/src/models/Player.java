package models;

import org.joml.Vector3f;
import org.lwjgl.input.Keyboard;

import renderEngine.DisplayManager;

public class Player extends Entity{

	private static final float RUN_SPEED = 20;		//Distance/Seconds
	private static final float TURN_SPEED = 160;  	//Degree/Second
	private static final float GRAVITY = -45;		//Erdanziehung
	private static final float JUMP_POWER = 17;		//Sprunghöhe
	private static final float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;			//Keine Bewegung am Anfang
	private float currentTurnSpeed = 0;
	private float jumpSpeed = 0; 			//Geschwindigkeit des Sprungs
	private boolean jumps = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		
	}
	
	public void move() {
		checkInputs();
		super.setRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.setPosition(dx, 0, dz);
		jumpSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.setPosition(0, jumpSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if(super.getPosition().y < TERRAIN_HEIGHT) {	//Ist die Position unter dem Terrain Niveau, wird der Player zurück auf das Terrain gesetzt  
			jumpSpeed = 0; 
			jumps = false;								//wenn Player wieder auf dem Boden ist 
			super.getPosition().y = TERRAIN_HEIGHT; 
		}
	}
	
	private void jump() {
		if(!jumps) {		//wenn Player in der Luft, dann soll er nicht nochmal springen können 
			this.jumpSpeed = JUMP_POWER;
			jumps = true;
		}
	}
	
	private void checkInputs() {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
//			this.currentSpeed = -RUN_SPEED;
			this.currentSpeed = 0;
		}
//		else {
//			this.currentSpeed = 0;
//		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		}else {
			this.currentTurnSpeed = 0; 
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump(); 
		}
		
	}

}
