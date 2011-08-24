package alcoholic.jchess.network;

import java.io.*;
import alcoholic.jchess.core.*;

public class DeselectionEvent implements Event, Serializable
{
	private int row;
	private int col;

	public DeselectionEvent() {}

	public void process( State pState )
	{
		pState.deselect();
	}
}
