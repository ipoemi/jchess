package alcoholic.jchess;

import java.io.*;
import javax.swing.*;
import alcoholic.jchess.core.*;
import alcoholic.jchess.scene.*;
import alcoholic.jchess.model.*;
import alcoholic.jchess.network.*;

public class JChess
{
	public static void main( String[] args )
	{
		try
		{
			int server = JOptionPane.showConfirmDialog(
					null, "Do you want to be Server?",
					"Choose Server or Client",
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, null );
			String ip = null;
			if( server == 1 )
				ip = JOptionPane.showInputDialog(
						"Input IP address you want to connect" );
			if( server == 0 )
			{
				State state = new State();
				ServerManager serverManager = new ServerManager( state );
				WindowManager window= new WindowManager( true, false );
				window.create();
				SceneManager sceneManager = new SceneManager();
				ModelManager modelManager = 
					new ModelManager( true, state, serverManager );
				serverManager.start();

				while( true )
				{
					sceneManager.begin();
					modelManager.draw();
					sceneManager.end();

					window.listenEvent();
					modelManager.listenEvent();
				}
			}
			else if( server == 1 && ip != null )
			{
				ClientManager clientManager = new ClientManager( ip );
				WindowManager window= new WindowManager( false, false );
				window.create();
				SceneManager sceneManager = new SceneManager();
				ModelManager modelManager = 
					new ModelManager( false, new State(), clientManager );
				clientManager.start();

				while( true )
				{
					sceneManager.begin();
					modelManager.draw();
					sceneManager.end();

					window.listenEvent();
					modelManager.listenEvent();
					modelManager.setState( clientManager.getState() );
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}
}
