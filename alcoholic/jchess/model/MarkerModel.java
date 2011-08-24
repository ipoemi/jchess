package alcoholic.jchess.model;

import java.io.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;

public class MarkerModel
{
	private int modelList = -1;

	public MarkerModel() {}

	public void load( String pFileName )
	{
		ModelLoader loader = new ModelLoader();
		modelList = loader.loadMarker( pFileName );
	}

	public void draw()
	{
		if( modelList == -1 ) 
			System.out.println( "model isn't loaded yet" );
		GL11.glCallList( modelList );
	}
}
