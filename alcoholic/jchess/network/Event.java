package alcoholic.jchess.network;

import alcoholic.jchess.core.*;

public interface Event
{
	public void process( State pState );
}
