package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 800;
	private static final int fps = 120;
	
	private static long lastFrameTime;	//Zeit am ende des letzten Frames
	private static float delta;			//
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2) //Version von OpenGL 3,2
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Little Mage");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		//Wo im Display kann das Spiel gerendert werden
		GL11.glViewport(0, 0, WIDTH, HEIGHT); 
		lastFrameTime = getCurrentTime(); 
	}
	
	public static void updateDisplay() {
		Display.sync(fps); 
		Display.update();
		
		long currentFrameTime = getCurrentTime();				//RenderZeit
		delta = (currentFrameTime - lastFrameTime) /1000f;		//Wie lange der letzte Frame gebraucht hat um zu rendern - in Sekunden
		lastFrameTime = currentFrameTime;						//bereit für nächste Kalkulation
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
	
	private static long getCurrentTime() {		//in ms -> *1000
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

}
