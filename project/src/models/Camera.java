package models;

import org.joml.Vector3f;
import org.lwjgl.input.Mouse;

/*** Repräsentiert die virtuelle Kamera ***/

public class Camera {
	
	//Variablen die geändert werden können
	private float distanceFromPlayer = 15;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,25,0); 	//Anfangsposition der Kamera
	private float pitch = 15;							//rotation up/down	-> kamera Position hoch/runter
	private float yaw; 									//rotation left/right
	private float roll;									//rotation um z-Achse 
	
	private Player player;								//Kamera folgt dem Player, also braucht es Infos des Players
	
	
	public Camera (Player player){		
		this.player = player; 
	}
	
	//Aufruf in jedem Frame 
	public void move() {
		cameraZoom();									//Eingaben des Players
		cameraPitch();
		cameraAroundPlayer();
		float horizontalDistance = horizontalDistance();
		float verticalDistance = verticalDistance();
		cameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	
	
	// Berechnungen der Position
	
	private void cameraPosition(float hDistance, float vDistance) {
		float theta = player.getRotY() + angleAroundPlayer; 						//Winkel
		float offsetX = (float) (hDistance * Math.sin(Math.toRadians(theta))); 
		float offsetZ = (float) (hDistance * Math.cos(Math.toRadians(theta))); 
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ; 
		position.y = player.getPosition().y + vDistance;
	}
	
	private float horizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float verticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	
	
	
	// Player Eingabe Abfrage
	private void cameraZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;  				//rein zommen, wenn Rad runter
	}
	
	private void cameraPitch() {
		if(Mouse.isButtonDown(1)) {		//Wenn Button gedrückt wird = true (0=left/1=right)
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void cameraAroundPlayer() {
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	
	
	
	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}
	
	
	
}
