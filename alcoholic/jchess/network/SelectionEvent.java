package alcoholic.jchess.network;

import java.io.*;
import alcoholic.jchess.core.*;

public class SelectionEvent implements Event, Serializable
{
	private int row;
	private int col;

	public SelectionEvent( int pRow, int pCol )
	{
		row = pRow;
		col = pCol;
	}

	public void process( State pState )
	{
		pState.select( row, col );
	}
}
