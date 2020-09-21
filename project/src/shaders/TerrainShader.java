package shaders;

import org.joml.Matrix4f;

import models.Camera;
import renderEngine.Light;
import renderEngine.Transformation;

public class TerrainShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.glsl";
	
	private int location_transMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_shineDamper;				//Licht Kegel der Refektion
	private int location_reflectivity;				//Wie stark die Reflektion des Modells ist 
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {	//in VertexShader
		super.bindAttribute(0, "position");			//0=Position in VertexShader / Attribute Nummer (Loader)
		super.bindAttribute(1, "textureCoords");	//Textur Koordinaten 
		super.bindAttribute(2, "normal"); 			//Normals
	}

	@Override  //Location der Uniforms aus dem ShaderCode in Variable speichern 
	protected void getAllUniformLocations() {
		location_transMatrix = super.getUniformLocation("transMatrix"); 
		location_projectionMatrix = super.getUniformLocation("projectionMatrix"); 
		location_viewMatrix = super.getUniformLocation("viewMatrix"); 
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColour = super.getUniformLocation("lightColour"); 
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
	}
	
	
	//upload Transformation Matrix to Uniform Variable in the Shader Code
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transMatrix, matrix);
	}
	
	//upload Light data to Uniform Variable in the Shader Code
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
	}
	
	//upload damper and reflectivity in the Shader
	public void loadShineVariables(float damper, float refectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, refectivity);
	}
	
	//Upload Projection Matrix to Uniform Variable in the Shader Code
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);  
	}
	
	//upload Camera view 
	public void loadViewMatrix(Camera camera) {	
		Matrix4f viewMatrix = Transformation.createViewMatrix(camera);  
		super.loadMatrix(location_viewMatrix, viewMatrix); 
	}
	
	
}
