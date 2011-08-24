package alcoholic.jchess.scene;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.LWJGLException;

public class WindowManager
{
	private String windowTitle;

	private DisplayMode displayMode;

	private boolean fullscreen;

	public WindowManager( boolean pServer, boolean pFullscreen )
	{
		if(  pServer  )
			windowTitle = "JChess - Alcoholic Team Project ( Server )";
		else
			windowTitle = "JChess - Alcoholic Team Project ( Client )";
		fullscreen = pFullscreen;
		try
		{
			Display.setFullscreen( fullscreen );
			DisplayMode d[] = Display.getAvailableDisplayModes();
			for( int i = 0; i < d.length; i++ ) 
			{
				if( d[i].getWidth() == 800 && d[i].getHeight() == 600 ) 
				{
					displayMode = d[i];
					break;
				}
			}
			Display.setDisplayMode( displayMode );
			Display.setTitle( windowTitle );
		}
		catch( Exception e ) 
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	public void create()
	{
		try
		{
			Display.create();
		}
		catch(  Exception e  )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	public void toggleFullscreen()
	{
		try
		{
			fullscreen = !fullscreen;
			Display.setFullscreen( fullscreen );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	public void setFullscreen( boolean pFullscreen )
	{
		try
		{
			fullscreen = pFullscreen;
			Display.setFullscreen( fullscreen );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	public void destroyWindow()
	{
		try
		{
			Display.destroy();
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	public void listenEvent()
	{
		try
		{
			if( Display.isCloseRequested() ||
					Keyboard.isKeyDown( Keyboard.KEY_ESCAPE ) ||
					Keyboard.isKeyDown( Keyboard.KEY_Q ) )
			{
				destroyWindow();
				System.exit( 0 );
			}
			if( Keyboard.isKeyDown( Keyboard.KEY_F ) )
			{
				toggleFullscreen();
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

}
