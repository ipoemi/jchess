package alcoholic.jchess.network;

import java.io.*;
import java.net.*;
import javax.swing.*;

import alcoholic.jchess.core.*;

public class ClientManager implements NetworkManager, Runnable
{
	private String ipAddress;
	private State state;

	private Thread thread;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	boolean checkMate = false;
	boolean endGame = false;

	public ClientManager(String pIpAddress) 
	{
		ipAddress = pIpAddress;
	}
	
	public void start()
	{
		try 
		{
			socket = new Socket(ipAddress, PORT_NUMBER);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			state = (State) in.readObject();
			thread = new Thread(this);
			thread.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void run()
	{
		try
		{
			while(socket.isConnected()) 
			{
				if(!endGame)
				{
					state = (State) in.readObject();
					int loser = state.getLoser();
					if(state.getTurn() == Field.BLACK &&
							!checkMate && state.isCheckMate())
					{
						JOptionPane.showMessageDialog(
								null, "Check mate!!", "Information", 
								JOptionPane.INFORMATION_MESSAGE, null);
						checkMate = true;
					}
					else if(state.getTurn() == Field.WHITE)
						checkMate = false;
					if(loser == Field.WHITE)
					{
						int again = JOptionPane.showConfirmDialog(
								null, "You lose! Again game?", 
								"Information",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null);
						if(again == 0)
						{
							state.reset();
						}
						else if(again == 1) endGame = true;
						sendEvent(new EndGameEvent());
					}
					else if(loser == Field.BLACK)
					{
						int again = JOptionPane.showConfirmDialog(
								null, "You lose! Again game?", 
								"Information",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null);
						if(again == 0)
						{
							state.reset();
						}
						else if(again == 1) endGame = true;
						sendEvent(new EndGameEvent());
					}
				}
			}
		}
		catch(Exception e) {}
	}

	public boolean isConnected()
	{
		if(socket == null) return false;
		else return socket.isConnected();
	}

	public State getState()
	{
		return state;
	}

	public void sendEvent(Event pEvent)
	{
		try
		{
			if(out != null) out.writeObject(pEvent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
