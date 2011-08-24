package alcoholic.jchess.util;

public class AlcHeader
{
	public int descNum = 0xC1;
	public byte desc[] = new byte[32]; // char 'A' 'L' 'C'
	public int version = 1;
	public int numFaces;
	public int numVertices;
	public int offsetFaces = 48;
	public int offsetVertices;
}
