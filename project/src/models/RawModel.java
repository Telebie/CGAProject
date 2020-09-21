package models;

public class RawModel {  //Mesh

	private int vao = 0;

    private int indexcount = 0;

	public RawModel(int vao, int indexcount) {
		this.vao = vao;
		this.indexcount = indexcount;
	}

	public int getVaoID() {
		return vao;
	}

	public int getIndexcount() {
		return indexcount;
	}
	
}
