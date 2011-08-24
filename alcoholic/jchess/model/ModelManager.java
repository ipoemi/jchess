package alcoholic.jchess.model;

import java.io.*;
import java.nio.IntBuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.glu.GLU;
import org.lwjgl.input.*;
import org.lwjgl.BufferUtils;

import alcoholic.jchess.core.*;
import alcoholic.jchess.model.*;
import alcoholic.jchess.scene.*;
import alcoholic.jchess.network.*;

public class ModelManager
{
	private static final int BUFFER_LENGTH = 64;
	private static final int MOUSE_LEFT_BUTTON = 0;

	private static final float UNIT_OF_ROOM = 2.0f;
	private static final float START_X = -3.5f * UNIT_OF_ROOM;
	private static final float START_Y = 3.5f * UNIT_OF_ROOM;

	private int currentRow;
	private int currentCol;

	private CameraManager camera;
	private NetworkManager network;
	private State state;
	private BoardModel board;
	private MarkerModel[] markers;
	private boolean server;

	private int selectedRow = -1;
	private int selectedCol = -1;

	private boolean selectionFlag = false;

	private void processSelection( int pX, int pY )
	{
		try
		{
			float aspect;
			int hits;
			IntBuffer selectBuff = 
				BufferUtils.createIntBuffer( BUFFER_LENGTH * 4 );
			int[] selection = new int[BUFFER_LENGTH];
			int[] viewport = new int[4];

			GL11.glSelectBuffer( selectBuff );
			viewport[0] = viewport[1] = 0;
			viewport[2] = Display.getDisplayMode().getWidth();
			viewport[3] = Display.getDisplayMode().getHeight();
			aspect = ( float ) viewport[2] / ( float ) viewport[3];

			GL11.glMatrixMode( GL11.GL_PROJECTION );
			GL11.glPushMatrix();
			GL11.glRenderMode( GL11.GL_SELECT );

			GL11.glLoadIdentity();
			GLU.gluPickMatrix( pX, pY, 1, 1, viewport );
			GLU.gluPerspective( 45.0f, aspect, 0.1f, 100.0f);

			GL11.glClear( 
				GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
			GL11.glMatrixMode( GL11.GL_MODELVIEW );
			camera.setup();
			board.draw();
			GL11.glLoadIdentity();
			Display.update();

			hits = GL11.glRenderMode( GL11.GL_RENDER );
			selectBuff.get( selection );
			currentRow = -1;
			currentCol = -1;
			if( hits == 1 )
			{
				if( selection[3] != 0 )
				{
					currentCol = ( ( selection[3] - 1 ) / 2 ) / 8;
					currentRow = ( ( selection[3] - 1 ) / 2 ) % 8;
				}
			}
			GL11.glMatrixMode( GL11.GL_PROJECTION );
			GL11.glPopMatrix();
			GL11.glMatrixMode( GL11.GL_MODELVIEW );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	public ModelManager( boolean pServer, 
			State pState, NetworkManager pNetwork )
	{
		server = pServer;
		state = pState;
		network = pNetwork;
		camera = new CameraManager( !pServer );
		board = new BoardModel();
		markers = new MarkerModel[7];
		
		board.load( "resource/model/Board.alc", 
				"resource/texture/Board.jpg" );

		for( int i = 1; i < 7; i++ )
		{
			markers[i] = new MarkerModel();
		}

		markers[Field.PAWN].load( "resource/model/Pawn.alc" );
		markers[Field.ROOK].load( "resource/model/Rook.alc" );
		markers[Field.KNIGHT].load( "resource/model/Knight.alc" );
		markers[Field.BISHOP].load( "resource/model/Bishop.alc" );
		markers[Field.QUEEN].load( "resource/model/Queen.alc" );
		markers[Field.KING].load( "resource/model/King.alc" );
	}

	public void draw()
	{
		Field[][] field = state.getField();
		int[] whiteDead = state.getWhiteDead();
		int[] blackDead = state.getBlackDead();
		int whiteCnt = state.getWhiteDeadCnt();
		int blackCnt = state.getBlackDeadCnt();
		camera.setup();

		for( int i = 0; i < whiteCnt; i++ )
		{
			GL11.glPushMatrix();
			GL11.glColor3f( 1.0f, 1.0f, 1.0f );
			GL11.glTranslatef( START_X + UNIT_OF_ROOM * i, 
					0.0f, START_Y - UNIT_OF_ROOM * 8 );
			GL11.glRotatef( 180.0f, 0.0f, 1.0f, 0.0f );
			markers[whiteDead[i]].draw();
			GL11.glPopMatrix();
		}

		for( int i = 0; i < blackCnt; i++ )
		{
			GL11.glPushMatrix();
			GL11.glColor3f( 0.3f, 0.3f, 0.3f );
			GL11.glTranslatef( 
					START_X + UNIT_OF_ROOM * ( 7 - i ), 
					0.0f, START_Y + UNIT_OF_ROOM );
			markers[blackDead[i]].draw();
			GL11.glPopMatrix();
		}

		for( int i = 0; i < 8; i++ )
		{
			for( int j = 0; j < 8; j++ )
			{
				if( field[i][j].color == Field.WHITE )
				{
					GL11.glPushMatrix();
					if( field[i][j].highlight )
						GL11.glColor4f( 1.0f, 1.0f, 1.0f, 0.3f );
					else if( i == state.getSelectedCol() &&
							j == state.getSelectedRow() )
						GL11.glColor4f( 0.0f, 1.0f, 1.0f, 0.3f );
					else
						GL11.glColor3f( 1.0f, 1.0f, 1.0f );
					GL11.glTranslatef( START_X + UNIT_OF_ROOM * j, 
							0.0f, START_Y - UNIT_OF_ROOM * i );
					if( state.getTurn() == Field.WHITE && server &&
							currentRow == j && currentCol == i &&
							!Mouse.isGrabbed() )
						GL11.glTranslatef( 0.0f, 1.0f, 0.0f );
					markers[field[i][j].kind].draw();
					GL11.glPopMatrix();
				}
				else if( field[i][j].color == Field.BLACK )
				{
					GL11.glPushMatrix();
					if( field[i][j].highlight )
						GL11.glColor4f( 1.0f, 1.0f, 1.0f, 0.3f );
					else if( i == state.getSelectedCol() &&
							j == state.getSelectedRow() )
						GL11.glColor4f( 0.0f, 1.0f, 1.0f, 0.3f );
					else
						GL11.glColor3f( 0.3f, 0.3f, 0.3f );
					GL11.glTranslatef( START_X + UNIT_OF_ROOM * j, 
							0.0f, START_Y - UNIT_OF_ROOM * i );
					if( state.getTurn() == Field.BLACK && !server &&
							currentRow == j && currentCol == i &&
							!Mouse.isGrabbed() )
						GL11.glTranslatef( 0.0f, 1.0f, 0.0f );
					GL11.glRotatef( 180.0f, 0.0f, 1.0f, 0.0f );
					markers[field[i][j].kind].draw();
					GL11.glPopMatrix();
				}
				if( state.isSelected() )
				{
					int color = state.getTurn();
					if( color == Field.WHITE &&
						server && field[i][j].highlight )
					{
						GL11.glPushMatrix();
						GL11.glColor4f( 1.0f, 1.0f, 1.0f, 0.3f );
						GL11.glTranslatef( START_X + UNIT_OF_ROOM * j, 
								0.0f, START_Y - UNIT_OF_ROOM * i );
						markers[state.getSelectedKind()].draw();
						GL11.glPopMatrix();
					}
					else if( color == Field.BLACK &&
							!server && field[i][j].highlight )
					{
						GL11.glPushMatrix();
						GL11.glColor4f( 0.3f, 0.3f, 0.3f, 0.3f );
						GL11.glTranslatef( START_X + UNIT_OF_ROOM * j, 
								0.0f, START_Y - UNIT_OF_ROOM * i );
						markers[state.getSelectedKind()].draw();
						GL11.glPopMatrix();
					}
				}
			}
		}
		board.draw();
	}

	public void setState( State pState )
	{
		if( pState != null ) state = pState;
	}

	public void listenEvent()
	{
		camera.listenEvent();
		processSelection( Mouse.getX(), Mouse.getY() );
		if( selectedRow != currentRow || selectedCol != currentCol )
			selectionFlag = false;
		if( state.getLoser() == Field.NONE )
		{
			if( network.isConnected() && server && 
					state.getTurn() == Field.WHITE )
			{
				ServerManager serverManager = ( ServerManager ) network;
				if( Mouse.isButtonDown( MOUSE_LEFT_BUTTON ) &&
						serverManager.isConnected() )
				{
					if( state.isSelected() )
					{
						if( state.getSelectedRow() == currentRow &&
								state.getSelectedCol() == currentCol &&
								!selectionFlag )
						{
							state.deselect();
							selectionFlag = true;
						}
						else
						{
							state.moveTo( currentRow, currentCol );
							serverManager.sendState();
						}
					}
					else if( !selectionFlag )
					{
						state.select( currentRow, currentCol );
						selectedRow = currentRow;
						selectedCol = currentCol;
						selectionFlag = true;
					}
				}
			}
			else if( network.isConnected() && !server && 
					state.getTurn() == Field.BLACK )
			{
				ClientManager clientManager = ( ClientManager ) network;
				if( Mouse.isButtonDown( MOUSE_LEFT_BUTTON ) )
				{
					if( state.isSelected() )
					{
						if( state.getSelectedRow() == currentRow &&
								state.getSelectedCol() == currentCol &&
								!selectionFlag )
						{
							clientManager.sendEvent( 
									new DeselectionEvent() );
							selectionFlag = true;
						}
						else
						{
							clientManager.sendEvent( 
									new MovementEvent( 
										currentRow, currentCol ) );
						}
					}
					else if( !selectionFlag )
					{
						clientManager.sendEvent( new
							SelectionEvent( currentRow, currentCol ) );
						selectedRow = currentRow;
						selectedCol = currentCol;
						selectionFlag = true;
					}
				}
			}
		}
	}
}
