package alcoholic.jchess.network;

import java.io.*;
import alcoholic.jchess.core.*;

public class MovementEvent implements Event, Serializable
{
	private int row;
	private int col;

	public MovementEvent( int pRow, int pCol )
	{
		row = pRow;
		col = pCol;
	}

	public void process( State pState )
	{
		pState.moveTo( row, col );
	}
}
