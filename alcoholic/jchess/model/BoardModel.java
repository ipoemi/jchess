package alcoholic.jchess.model;

import java.io.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;

public class BoardModel
{
	private static final float UNIT_OF_ROOM = 2.0f;

	private static final float START_X = -3.5f * UNIT_OF_ROOM;
	private static final float START_Y = 3.5f * UNIT_OF_ROOM;

	private int modelList = -1;

	public BoardModel() {}

	public void load( String pAlcFile, String pTexFile )
	{
		ModelLoader loader = new ModelLoader();
		modelList = loader.loadBoard( pAlcFile, pTexFile );
	}

	public void draw()
	{
		if( modelList == -1 ) 
			System.out.println( "model isn't loaded yet" );
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		GL11.glColor3f( 1.0f, 1.0f, 1.0f );
		GL11.glCallList( modelList );
		GL11.glDisable( GL11.GL_TEXTURE_2D );
	}
}
