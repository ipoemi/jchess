package alcoholic.jchess.scene;

import java.lang.Math;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class CameraManager
{
	private static final int MOUSE_LEFT_BUTTON = 0;
	private static final int MOUSE_RIGHT_BUTTON = 1;

	private int x;
	private int y;
	private int z;

	private float xDistance = 0.0f;
	private float yDistance = 1.0f;
	private float zDistance = -25.0f;

	private float xAngle = 45.0f;
	private float yAngle = 0.0f;

	private void setXAngle( float pXAngle )
	{
		if( pXAngle <= 90 && pXAngle >= 0 ) 
			xAngle = pXAngle;
	}

	private float getXAngle()
	{
		return xAngle;
	}

	private void setYAngle( float pYAngle )
	{
		yAngle = pYAngle;
	}

	private float getYAngle()
	{
		return yAngle;
	}

	private void setXDistance( float pXDistance )
	{
		if( pXDistance <= 16 && pXDistance >=  - 16 ) 
			xDistance = pXDistance;
	}

	private float getXDistance()
	{
		return xDistance;
	}

	private void setYDistance( float pYDistance )
	{
		if( pYDistance <= 16 && pYDistance >=  - 16 ) 
			yDistance = pYDistance;
	}

	private float getYDistance()
	{
		return yDistance;
	}

	private void setZDistance( float pZDistance )
	{
		if( pZDistance <=  - 5 && pZDistance >=  - 50 ) 
			zDistance = pZDistance;
	}

	private float getZDistance()
	{
		return zDistance;
	}

	public CameraManager( boolean reverse )
	{
		if( reverse ) yAngle = 180.0f;
	}

	public void setup()
	{
		GL11.glTranslatef( xDistance, yDistance, 0 );
		GL11.glTranslatef( 0, 0, zDistance );
		GL11.glRotatef( xAngle, 1.0f, 0.0f, 0.0f );
		GL11.glRotatef( yAngle, 0.0f, 1.0f, 0.0f );
	}

	public void listenEvent()
	{
		try
		{
			if( Mouse.isButtonDown( MOUSE_RIGHT_BUTTON ) &&
					!Mouse.isButtonDown( MOUSE_LEFT_BUTTON ) &&
					!Mouse.isGrabbed() ) 
			{
				Mouse.setGrabbed( true );
				x = Mouse.getX();
				y = Mouse.getY();
				z = Mouse.getY();
			}
			else if( !Mouse.isButtonDown( MOUSE_RIGHT_BUTTON ) && 
					!Mouse.isButtonDown( MOUSE_LEFT_BUTTON ) &&
					Mouse.isGrabbed() ) 
			{
				Mouse.setGrabbed( false );
				Mouse.setCursorPosition( x, y );
			}

			if( Mouse.isGrabbed() && 
					!Mouse.isButtonDown( MOUSE_LEFT_BUTTON ) )
			{
				if( x - Mouse.getX() != 0 ) 
				{
					setYAngle( 
							getYAngle() + ( Mouse.getX() - x ) / 3  );
				}
				if( y - Mouse.getY() != 0 ) 
				{
					setXAngle( 
							getXAngle() - ( Mouse.getY() - y ) / 3  );
				}
				Mouse.setCursorPosition( x, y );

				z = Mouse.getY();
			}
			else if( Mouse.isGrabbed() && 
					Mouse.isButtonDown( MOUSE_LEFT_BUTTON ) )
			{
				if( z - Mouse.getY() != 0 )
				{
					setZDistance( 
							getZDistance() - ( Mouse.getY() - z ) / 3  );
				}
				Mouse.setCursorPosition( x, z );
			}

			if( Mouse.hasWheel() )
			{
				if( Mouse.getDWheel() != 0 )
				{
					setZDistance( getZDistance() + Mouse.getDWheel() );
				}
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

}
