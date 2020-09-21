package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import models.RawModel;

/*** Lädt OBJ File, nimmt die Daten und gibt ein RawModel aus ***/

public class OBJLoader {
	
	
	public static RawModel loadObjModel(String file, Loader loader) {		//Loader um die vertices in ein VAO zu laden
		
		FileReader fileReader = null;	//Datei öffnen 
		try {
			fileReader = new FileReader(new File("res/models/"+file+".obj"));
		} catch (FileNotFoundException e) {
			System.out.println("Coultn't load obj file!");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fileReader); 			//Um Datei lesen zu können 
		String line;	//aktuelle Zeile der Datei
		List<Vector3f> vertices = new ArrayList<Vector3f>();				//Position Coords
		List<Vector2f> textures = new ArrayList<Vector2f>();				//Textur Daten
		List<Vector3f> normals = new ArrayList<Vector3f>();					//Normalen Coords für das Licht
		List<Integer> indices = new ArrayList<Integer>();
		
		float[] verticesArray = null; 										//Daten zusammenfassen
		float[] normalsArray = null; 
		float[] texturesArray = null;
		int[] indicesArray = null;
		
		try {
			
			while(true) { //Endlosschleife um aus der Datei zu lesen 
				
				line = reader.readLine();
				String[] currentLine = line.split(" "); 
				
				if(line.startsWith("v ")) {
					
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));	//currentLine[0]=Buchstabe
					vertices.add(vertex);
					
				}else if(line.startsWith("vt ")) {
					
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
					
				}else if(line.startsWith("vn ")) {
					
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
					
				}else if(line.startsWith("f ")) {
					//wenn faces anfangen, können die Größen der Arrays festgelegt und weiter gemacht werden 
					texturesArray = new float[vertices.size() *2];  //für jedes Dreieck eine Textur mit vec2 
					normalsArray = new float[vertices.size() *3];
					break;
					
				}
				
			}
			
			/*Faces*/
			while(line != null) {					//Informationen über jedes Dreieck, wenn line nicht leer ist 
				if(!line.startsWith("f ")) {		//wenn line nicht mit f anfängt soll trotzdem gelesen werden, aber wieder von vorne anfangen  
					line = reader.readLine();
					continue;
				}
				
				String[] currentLine = line.split(" "); 
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				//Koordinaten in die jeweiligen Arrays an richtiger Position speichern
				processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);
				line = reader.readLine(); 
				
			}
			reader.close(); //einlesen beendet 
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		
		//Größen der restlichen Arrays festlegen
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];
		
		
		/*** List in Array konvertieren ***/
		int vertexPointer = 0;
		for(Vector3f vertex:vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i=0; i<indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		
		/*** VBOs ins VAO laden ***/
		return loader.loadToVAO(verticesArray, texturesArray, normalsArray, indicesArray); //lädt die Arrays mit den Vertices, IBOs und TexCoords zum RawModel
		
	}
	
	
	
	/*** Textur und Normalen Coords in die Arrays speichern/sortieren ***/
	
	//Für jedes Vertex aufrufen - vertexData: welche Texture Coords & Normalenvektor zu welcher Vertex Position gehört 
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {		//vertexData(vertex1/2/3 aus Faces)↑ 
		
		//Index der Vertex Position 
		int currentVertexPointer = Integer.parseInt(vertexData[0]) -1;  // OBJ File startet bei 1 und Array bei 0 
		indices.add(currentVertexPointer); 								
		
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) -1); 
		textureArray[currentVertexPointer*2] = currentTex.x; 
		textureArray[currentVertexPointer*2+1] = 1 - currentTex.y; 	//OpenGL startet oben links in der Textur und Blender unten links  
		
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) -1);
		normalsArray[currentVertexPointer*3] = currentNorm.x; 
		normalsArray[currentVertexPointer*3+1] = currentNorm.y; 
		normalsArray[currentVertexPointer*3+2] = currentNorm.z; 
		
	}

}



















