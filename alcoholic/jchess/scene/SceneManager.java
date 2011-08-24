package alcoholic.jchess.scene;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.glu.GLU;
import org.lwjgl.LWJGLException;

import alcoholic.jchess.core.*;
import alcoholic.jchess.model.*;

public class SceneManager 
{ 
	private FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
	private FloatBuffer spotDirection = BufferUtils.createFloatBuffer(4);

	public SceneManager()
	{
		FloatBuffer ambientLight = BufferUtils.createFloatBuffer(4);
		FloatBuffer diffuseLight = BufferUtils.createFloatBuffer(4);
		FloatBuffer specularLight = BufferUtils.createFloatBuffer(4);
		FloatBuffer matSpecular = BufferUtils.createFloatBuffer(4);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading
		GL11.glClearColor(0.2f,0.2f,0.2f,1.0f); // Background
		GL11.glClearDepth(1.0); // Depth Buffer Setup
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glDepthFunc(GL11.GL_LESS); // The Type Of Depth Testing To Do

		// Setting of Light
		ambientLight.put(0, 0.3f);
		ambientLight.put(1, 0.3f);
		ambientLight.put(2, 0.3f);
		ambientLight.put(3, 1.0f);

		diffuseLight.put(0, 1.0f);
		diffuseLight.put(1, 1.0f);
		diffuseLight.put(2, 1.0f);
		diffuseLight.put(3, 1.0f);

		specularLight.put(0, 1.0f);
		specularLight.put(1, 1.0f);
		specularLight.put(2, 1.0f);
		specularLight.put(3, 1.0f);

		matSpecular.put(0, 1.0f);
		matSpecular.put(1, 1.0f);
		matSpecular.put(2, 1.0f);
		matSpecular.put(3, 1.0f);

		spotDirection.put(0, 0.0f);
		spotDirection.put(1, -0.8f);
		spotDirection.put(2, 0.0f);
		spotDirection.put(3, 0.0f);

		lightPos.put(0, 0.0f);
		lightPos.put(1, 6.0f);
		lightPos.put(2, 0.0f);
		lightPos.put(3, 1.0f);

		spotDirection.put(0, 0.0f);
		spotDirection.put(1, -1.0f);
		spotDirection.put(2, 0.0f);
		spotDirection.put(3, 0.0f);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glLight(GL11.GL_LIGHT0,GL11.GL_AMBIENT,ambientLight);
		GL11.glLight(GL11.GL_LIGHT0,GL11.GL_DIFFUSE,diffuseLight);
		GL11.glLight(GL11.GL_LIGHT0,GL11.GL_SPECULAR,specularLight);
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glLightModeli(GL12.GL_LIGHT_MODEL_COLOR_CONTROL, 
							GL12.GL_SEPARATE_SPECULAR_COLOR);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, matSpecular);
		GL11.glMateriali(GL11.GL_FRONT, GL11.GL_SHININESS, 128);
		GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_SPOT_CUTOFF, 90.0f);
		GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_SPOT_EXPONENT, 0.5f);

		// Select The Projection Matrix
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(
				45.0f,
				((float) Display.getDisplayMode().getWidth())
					/ ((float) Display.getDisplayMode().getHeight()),
				0.1f,
				100.0f);
		// Really Nice Perspective Calculations
		// GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_FASTEST);
		// Select The Modelview Matrix
	}

	public void begin()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void end()
	{
		try
		{
			GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION,lightPos);
			GL11.glLight(
				GL11.GL_LIGHT0,GL11.GL_SPOT_DIRECTION,spotDirection);
			GL11.glLoadIdentity();
			Display.update();
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}
}
