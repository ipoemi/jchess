package alcoholic.jchess;

import java.io.*;
import alcoholic.jchess.core.*;
import alcoholic.jchess.scene.*;
import alcoholic.jchess.model.*;
import alcoholic.jchess.network.*;

public class JChessDebug
{
	public static void main( String[] args )
	{
		/*
		try
		{
			boolean server;

			BufferedReader input =
				new BufferedReader( new InputStreamReader( System.in ) );
			System.out.println( 
					"Input IP address of server " +
					"(If you want to be server, just press Enter key) : " );
			String ip = input.readLine();
			if( ip.equals( "" ) ) 
			{
				server = true;
			}
			else server = false;

			if( server )
			{
				State state = new State();
				ServerManager serverManager = new ServerManager( state );
				WindowManager window= new WindowManager( true, false );
				window.create();
				SceneManager sceneManager = new SceneManager();
				ModelManager modelManager = 
					new ModelManager( true, state, serverManager );
				serverManager.start();

				while( !state.isLost() )
				{
					sceneManager.begin();
					modelManager.draw();
					sceneManager.end();

					window.listenEvent();
					modelManager.listenEvent();
				}
				serverManager.stop();
			}
			else
			{
				ClientManager client = new ClientManager( ip );
				client.start();
				WindowManager window= new WindowManager( false, false );
				window.create();
				SceneManager sceneManager = new SceneManager();
				while( client.getState() == null ) Thread.sleep( 1000 );
				ModelManager modelManager = 
					new ModelManager( false, client.getState(), client );

				while( !client.getState().isLost() )
				{
					sceneManager.begin();
					modelManager.draw();
					sceneManager.end();

					window.listenEvent();
					modelManager.listenEvent();
					modelManager.setState( client.getState() );
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
		*/
		try
		{
			State state = new State();
			BufferedReader in = 
				new BufferedReader( new InputStreamReader( System.in ) );
			String inp;
			int row, col;
			row = -1;
			col = -1;
			while( true )
			{
				while( !state.select( row, col ) )
				{
					state.printAll();
					inp = in.readLine();
					row = Integer.parseInt(inp);
					inp = in.readLine();
					col = Integer.parseInt(inp);
				}
				row = -1;
				col = -1;
				while( !state.moveTo( row, col ) )
				{
					state.printAll();
					inp = in.readLine();
					row = Integer.parseInt(inp);
					inp = in.readLine();
					col = Integer.parseInt(inp);
					if( state.getSelectedRow() == row && 
							state.getSelectedCol() == col )
					{
						state.deselect();
						break;
					}
				}
				row = -1;
				col = -1;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}
}
