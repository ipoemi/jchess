package alcoholic.jchess.network;

import java.io.*;
import java.net.*;
import javax.swing.*;

import alcoholic.jchess.core.*;

public class ServerManager implements Runnable, NetworkManager
{
	private State state;

	private Thread thread;
	private ServerSocket srvSocket;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	private boolean checkMate = false;
	private boolean endGame = false;

	public ServerManager(State pState)
	{
		state = pState;
	}

	public void start()
	{
		try 
		{
			srvSocket = new ServerSocket(PORT_NUMBER);
			thread = new Thread(this);
			thread.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void run()
	{
		try
		{
			while(socket == null)
			{
				socket = srvSocket.accept();
			}
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.writeObject(state);
			JOptionPane.showMessageDialog(
					null, "Client is connected!", 
					"Information", 
					JOptionPane.INFORMATION_MESSAGE, null);

			while(socket.isConnected())
			{
				((Event) in.readObject()).process(state);
				int loser = state.getLoser();
				if(!endGame)
				{
					if(state.getTurn() == Field.WHITE && 
							!checkMate && state.isCheckMate())
					{
						JOptionPane.showMessageDialog(
								null, "Check mate!!", 
								"Information", 
								JOptionPane.INFORMATION_MESSAGE, null);
						checkMate = true;
					}
					else if(state.getTurn() == Field.BLACK)
						checkMate = false;
					if(loser == Field.WHITE)
					{
						int again = JOptionPane.showConfirmDialog(
								null, "You won! Again game?", 
								"Information",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null);
						if(again == 0)
						{
							state.reset();
						}
						else if(again == 1) endGame = true;
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
					}
				}
				if(socket.isConnected()) out.writeObject(state.clone());
			}
		} 
		catch(Exception e) {}
	}

	public boolean isConnected()
	{
		if(socket == null) return false;
		else return socket.isConnected();
	}

	public void sendState()
	{
		try
		{
			if(out != null) out.writeObject(state.clone());
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
