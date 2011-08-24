package alcoholic.jchess.network;

import java.io.*;
import java.net.*;

import alcoholic.jchess.core.*;

public interface NetworkManager
{
	public static final int PORT_NUMBER = 45123;
	public boolean isConnected();
}
