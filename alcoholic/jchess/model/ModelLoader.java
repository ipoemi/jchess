package alcoholic.jchess.model;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.devil.IL;
import org.lwjgl.devil.ILU;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.*;
import org.lwjgl.LWJGLException;

import alcoholic.jchess.util.*;

public class ModelLoader
{
	private Vector3f[] positions;
	private Vector3f[] normals;
	private Face[] faces;
	private AlcHeader alcHeader  =  new AlcHeader();

	private void loadVertices( String pFileName )
	{
		try
		{
			RandomAccessFile alcFile  =  
				new RandomAccessFile( pFileName, "r" );
			alcHeader.descNum  =  alcFile.readInt();
			alcHeader.desc[0]  =  alcFile.readByte();
			alcHeader.desc[1]  =  alcFile.readByte();
			alcHeader.desc[2]  =  alcFile.readByte();
			// skip description section
			for( int i  =  3; i < 32; i++ ) alcFile.readByte();
			alcHeader.version  =  alcFile.readInt();
			alcHeader.numFaces  =  alcFile.readInt();
			alcHeader.numVertices  =  alcFile.readInt();
			alcHeader.offsetFaces  =  alcFile.readInt();
			alcHeader.offsetVertices  =  alcFile.readInt();

			if ( ( char ) alcHeader.desc[0] != 'A' || 
					( char ) alcHeader.desc[1] != 'L' ||
					alcHeader.desc[2] != 'C' || 
					alcHeader.descNum != 0xC1 ||
					alcHeader.version != 1 )
			{
				System.out.println( "Wrong .alc file" );
				System.exit( 1 );
			}

			faces  =  new Face[alcHeader.numFaces];
			System.out.println( pFileName + " Faces" );
			for( int i  =  0; i < alcHeader.numFaces; i++ )
			{
				faces[i]  =  
					new Face( alcFile.readInt(), 
							alcFile.readInt(), 
							alcFile.readInt() );
			}

			positions  =  new Vector3f[alcHeader.numVertices];
			normals  =  new Vector3f[alcHeader.numVertices];
			for( int i  =  0; i < alcHeader.numVertices; i++ )
			{
				positions[i] 
					 =  new Vector3f( alcFile.readFloat(), 
									alcFile.readFloat(), 
									alcFile.readFloat() );

				normals[i] 
					 =  new Vector3f( alcFile.readFloat(), 
									alcFile.readFloat(), 
									alcFile.readFloat() );
			}
			alcFile.close();
		}
		catch ( Exception e ) 
		{
			e.printStackTrace(); 
			System.exit( 1 );
		}
	}

	public int loadMarker( String pFileName )
	{
		int retValue  =  -1;
		try 
		{
			System.out.println( "Loading " + pFileName + " ..." );
			loadVertices( pFileName );

			float x, y, z;
			int currentFace;

			retValue  =  GL11.glGenLists( 1 );

			GL11.glNewList( retValue, GL11.GL_COMPILE );
			GL11.glBegin( GL11.GL_TRIANGLES );
			for( int i  =  0; i < faces.length; i++ ) 
			{
				currentFace  =  faces[i].getV1();
				x  =  normals[currentFace].getX();
				y  =  normals[currentFace].getY();
				z  =  normals[currentFace].getZ();
				GL11.glNormal3f( x, y, z );

				x  =  positions[currentFace].getX();
				y  =  positions[currentFace].getY();
				z  =  positions[currentFace].getZ();
				GL11.glVertex3f( x , y, z );

				currentFace  =  faces[i].getV2( );
				x  =  normals[currentFace].getX( );
				y  =  normals[currentFace].getY( );
				z  =  normals[currentFace].getZ( );
				GL11.glNormal3f( x, y, z  );

				x  =  positions[currentFace].getX();
				y  =  positions[currentFace].getY();
				z  =  positions[currentFace].getZ();
				GL11.glVertex3f( x, y, z );

				currentFace  =  faces[i].getV3();
				x  =  normals[currentFace].getX();
				y  =  normals[currentFace].getY();
				z  =  normals[currentFace].getZ();
				GL11.glNormal3f( x, y, z );

				x  =  positions[currentFace].getX();
				y  =  positions[currentFace].getY();
				z  =  positions[currentFace].getZ();
				GL11.glVertex3f( x, y, z );
			}
			GL11.glEnd();
			GL11.glEndList();
		}
		catch( Exception e ) 
		{ 
			e.printStackTrace(); 
			System.exit( 1 ); 
		}
		return retValue;

	}

	public int loadBoard( String pAlcFile, String pTexFile )
	{
		int retValue  =  -1;
		try 
		{
			GL11.glEnable( GL11.GL_TEXTURE_2D );
			System.out.println( "Loading " + pAlcFile + " ..." );
			loadVertices( pAlcFile );

			IntBuffer image  =  BufferUtils.createIntBuffer( 1 );
			IntBuffer texture  =  BufferUtils.createIntBuffer( 1 );

			IL.create();
			IL.ilGenImages( image );
			IL.ilBindImage( image.get( 0 ) );
			IL.ilLoad( IL.IL_JPG, pTexFile );
			IL.ilConvertImage( IL.IL_RGB, IL.IL_BYTE );

			ByteBuffer imageData  =  BufferUtils.createByteBuffer( 
					IL.ilGetInteger( IL.IL_IMAGE_WIDTH ) * 
						IL.ilGetInteger( IL.IL_IMAGE_HEIGHT ) * 3 );

			IL.ilCopyPixels( 0, 0, 0, IL.ilGetInteger( IL.IL_IMAGE_WIDTH ), 
					IL.ilGetInteger( IL.IL_IMAGE_HEIGHT ), 1, IL.IL_RGB, 
					IL.IL_BYTE, imageData );

			GL11.glGenTextures( texture );
			GL11.glBindTexture( GL11.GL_TEXTURE_2D, texture.get( 0 ) );
			GL11.glTexParameteri( GL11.GL_TEXTURE_2D, 
						GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR );
			GL11.glTexParameteri( GL11.GL_TEXTURE_2D, 
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR );
			GL11.glTexImage2D( GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 
					IL.ilGetInteger( IL.IL_IMAGE_WIDTH ), 
					IL.ilGetInteger( IL.IL_IMAGE_HEIGHT ), 
					0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, imageData );

			retValue  =  GL11.glGenLists( 1 );

			GL11.glNewList( retValue, GL11.GL_COMPILE );

			GL11.glBegin( GL11.GL_TRIANGLES );
			for( int i = 0; i < 16; i++ ) 
			{
				int[] currentFace  =  new int[3];
				float x, y, z;

				currentFace[0]  =  faces[i].getV1();
				currentFace[1]  =  faces[i].getV2();
				currentFace[2]  =  faces[i].getV3();

				for ( int j = 0; j < 3; j++ )
				{
					x = normals[currentFace[j]].getX();
					y = normals[currentFace[j]].getY();
					z = normals[currentFace[j]].getZ();
					GL11.glNormal3f( x, y, z );

					x = positions[currentFace[j]].getX();
					y = positions[currentFace[j]].getY();
					z = positions[currentFace[j]].getZ();
					GL11.glVertex3f( x, y, z );
				}
			}
			GL11.glEnd();

			GL11.glInitNames();
			GL11.glPushName( 0 );
			for( int i = 16; i < 144; i++ ) 
			{
				int[] currentFace  =  new int[3];
				float x, y, z;

				currentFace[0]  =  faces[i].getV1();
				currentFace[1]  =  faces[i].getV2();
				currentFace[2]  =  faces[i].getV3();

				GL11.glLoadName( i - 15 );
				GL11.glBegin( GL11.GL_TRIANGLES );
				for ( int j = 0; j < 3; j++ )
				{
					x = normals[currentFace[j]].getX();
					y = normals[currentFace[j]].getY();
					z = normals[currentFace[j]].getZ();
					GL11.glNormal3f( x, y, z );

					x = positions[currentFace[j]].getX();
					y = positions[currentFace[j]].getY();
					z = positions[currentFace[j]].getZ();

					GL11.glTexCoord2f( x / 16.0f + 0.5f,
							1.0f - z / 16.0f + 0.5f );
					GL11.glVertex3f( x, y, z );
				}
				GL11.glEnd();
			}
			GL11.glPopName();

			GL11.glBegin( GL11.GL_TRIANGLES );
			for( int i = 144; i < faces.length; i++ ) 
			{
				int[] currentFace  =  new int[3];
				float x, y, z;

				currentFace[0]  =  faces[i].getV1();
				currentFace[1]  =  faces[i].getV2();
				currentFace[2]  =  faces[i].getV3();

				for ( int j = 0; j < 3; j++ )
				{
					x = normals[currentFace[j]].getX();
					y = normals[currentFace[j]].getY();
					z = normals[currentFace[j]].getZ();
					GL11.glNormal3f( x, y, z );

					x = positions[currentFace[j]].getX();
					y = positions[currentFace[j]].getY();
					z = positions[currentFace[j]].getZ();
					GL11.glVertex3f( x, y, z );
				}
			}
			GL11.glEnd();
			GL11.glEndList();
			GL11.glDisable( GL11.GL_TEXTURE_2D );
		}
		catch( Exception e ) 
		{ 
			e.printStackTrace(); 
			System.exit( 1 ); 
		}
		return retValue;
	}
}
