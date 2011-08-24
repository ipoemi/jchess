package alcoholic.jchess.util;

import java.io.*;
import java.util.ArrayList;
import alcoholic.jchess.util.*;


public class Xml2Alc
{
	private enum Statement 
	{ 
		START,
		OPEN,
		F, FA, FAC, FACE, FACES,
		G, GE, GEO, GEOM, GEOME, GEOMET, GEOMETR, GEOMETRY,
		P, PO, POS, POSI, POSIT, POSITI, POSITIO, POSITION,
		N, NO, NOR, NORM, NORMA, NORMAL 
	}

	AlcHeader alcHeader = new AlcHeader();
	ArrayList vertices = new ArrayList();
	ArrayList faces = new ArrayList();

	BufferedReader inFile;
	RandomAccessFile outFile;

	Statement state = Statement.START;

	private void readPosition(char c) throws Exception
	{
		if (state == Statement.START)
		{
			if (c == '<') state = Statement.OPEN;
		}
		else if (state == Statement.OPEN)
		{
			if (c == '/') state = Statement.START;
			if (c == 'p') state = Statement.P;
		}
		else if (state == Statement.P)
		{
			if (c == 'o') state = Statement.PO;
			else state = Statement.START;
		}
		else if (state == Statement.PO)
		{
			if (c == 's') state = Statement.POS;
			else state = Statement.START;
		}
		else if (state == Statement.POS)
		{
			if (c == 'i') state = Statement.POSI;
			else state = Statement.START;
		}
		else if (state == Statement.POSI)
		{
			if (c == 't') state = Statement.POSIT;
			else state = Statement.START;
		}
		else if (state == Statement.POSIT)
		{
			if (c == 'i') state = Statement.POSITI;
			else state = Statement.START;
		}
		else if (state == Statement.POSITI)
		{
			if (c == 'o') state = Statement.POSITIO;
			else state = Statement.START;
		}
		else if (state == Statement.POSITIO)
		{
			if (c == 'n') state = Statement.POSITION;
			else state = Statement.START;
		}
		else if (state == Statement.POSITION)
		{
			String tmpLine;
			int i = 0;
			int startCharAt;
			int endCharAt;

			tmpLine = inFile.readLine();
			for (; tmpLine.charAt(i) != '"'; i++) ;
			startCharAt = i = i + 1;
			for (; tmpLine.charAt(i) != '"'; i++) ;
			endCharAt = i;
			vertices.add(
					Float.parseFloat(tmpLine.substring(startCharAt, endCharAt)));
			i += 1;

			for (; tmpLine.charAt(i) != '"'; i++) ;
			startCharAt = i = i + 1;
			for (; tmpLine.charAt(i) != '"'; i++) ;
			endCharAt = i;
			vertices.add(
					Float.parseFloat(tmpLine.substring(startCharAt, endCharAt)));
			i += 1;

			for (; tmpLine.charAt(i) != '"'; i++) ;
			startCharAt = i = i + 1;
			for (; tmpLine.charAt(i) != '"'; i++) ;
			endCharAt = i;
			vertices.add(
					Float.parseFloat(tmpLine.substring(startCharAt, endCharAt)));
			i += 1;

			state = Statement.START;
		}
	}

	public void readNormal(char c) throws Exception
	{
		if (state == Statement.START)
		{
			if (c == '<') state = Statement.OPEN;
		}
		else if (state == Statement.OPEN)
		{
			if (c == '/') state = Statement.START;
			if (c == 'n') state = Statement.N;
		}
		else if (state == Statement.N)
		{
			if (c == 'o') state = Statement.NO;
			else state = Statement.START;
		}
		else if (state == Statement.NO)
		{
			if (c == 'r') state = Statement.NOR;
			else state = Statement.START;
		}
		else if (state == Statement.NOR)
		{
			if (c == 'm') state = Statement.NORM;
			else state = Statement.START;
		}
		else if (state == Statement.NORM)
		{
			if (c == 'a') state = Statement.NORMA;
			else state = Statement.START;
		}
		else if (state == Statement.NORMA)
		{
			if (c == 'l') state = Statement.NORMAL;
			else state = Statement.START;
		}
		else if (state == Statement.NORMAL)
		{
			String tmpLine;
			int i = 0;
			int startCharAt;
			int endCharAt;

			tmpLine = inFile.readLine();
			for (; tmpLine.charAt(i) != '"'; i++) ;
			startCharAt = i = i + 1;
			for (; tmpLine.charAt(i) != '"'; i++) ;
			endCharAt = i;
			vertices.add(
					Float.parseFloat(tmpLine.substring(startCharAt, endCharAt)));
			i += 1;

			for (; tmpLine.charAt(i) != '"'; i++) ;
			startCharAt = i = i + 1;
			for (; tmpLine.charAt(i) != '"'; i++) ;
			endCharAt = i;
			vertices.add(
					Float.parseFloat(tmpLine.substring(startCharAt, endCharAt)));
			i += 1;

			for (; tmpLine.charAt(i) != '"'; i++) ;
			startCharAt = i = i + 1;
			for (; tmpLine.charAt(i) != '"'; i++) ;
			endCharAt = i;
			vertices.add(
					Float.parseFloat(tmpLine.substring(startCharAt, endCharAt)));
			i += 1;

			state = Statement.START;
		}
	}

	public void readGeometry(char c) throws Exception
	{
		if (state == Statement.START)
		{
			if (c == '<') state = Statement.OPEN;
		}
		else if (state == Statement.OPEN)
		{
			if (c == '/') state = Statement.START;
			if (c == 'g') state = Statement.G;
		}
		else if (state == Statement.G)
		{
			if (c == 'e') state = Statement.GE;
			else state = Statement.START;
		}
		else if (state == Statement.GE)
		{
			if (c == 'o') state = Statement.GEO;
			else state = Statement.START;
		}
		else if (state == Statement.GEO)
		{
			if (c == 'm') state = Statement.GEOM;
			else state = Statement.START;
		}
		else if (state == Statement.GEOM)
		{
			if (c == 'e') state = Statement.GEOME;
			else state = Statement.START;
		}
		else if (state == Statement.GEOME)
		{
			if (c == 't') state = Statement.GEOMET;
			else state = Statement.START;
		}
		else if (state == Statement.GEOMET)
		{
			if (c == 'r') state = Statement.GEOMETR;
			else state = Statement.START;
		}
		else if (state == Statement.GEOMETR)
		{
			if (c == 'y') state = Statement.GEOMETRY;
			else state = Statement.START;
		}
		else if (state == Statement.GEOMETRY)
		{
			String tmpLine;
			int i = 0;
			int startCharAt;
			int endCharAt;

			tmpLine = inFile.readLine();
			for (; tmpLine.charAt(i) != '"'; i++) ;
			startCharAt = i = i + 1;
			for (; tmpLine.charAt(i) != '"'; i++) ;
			endCharAt = i;
			alcHeader.numVertices =
					Integer.parseInt(tmpLine.substring(startCharAt, endCharAt));
			i += 1;

			state = Statement.START;
		}
	}

	private void readFaces(char c) throws Exception
	{
		if (state == Statement.START)
		{
			if (c == '<') state = Statement.OPEN;
		}
		else if (state == Statement.OPEN)
		{
			if (c == '/') state = Statement.START;
			if (c == 'f') state = Statement.F;
		}
		else if (state == Statement.F)
		{
			if (c == 'a') state = Statement.FA;
			else state = Statement.START;
		}
		else if (state == Statement.FA)
		{
			if (c == 'c') state = Statement.FAC;
			else state = Statement.START;
		}
		else if (state == Statement.FAC)
		{
			if (c == 'e') state = Statement.FACE;
			else state = Statement.START;
		}
		else if (state == Statement.FACE)
		{
			if (c == 's') state = Statement.FACES;
			else if (c == ' ')
			{
				String tmpLine;
				int i = 0;
				int startCharAt;
				int endCharAt;

				tmpLine = inFile.readLine();
				for (; tmpLine.charAt(i) != '"'; i++) ;
				startCharAt = i = i + 1;
				for (; tmpLine.charAt(i) != '"'; i++) ;
				endCharAt = i;
				faces.add(
						Integer.parseInt(tmpLine.substring(startCharAt, endCharAt)));
				i += 1;

				for (; tmpLine.charAt(i) != '"'; i++) ;
				startCharAt = i = i + 1;
				for (; tmpLine.charAt(i) != '"'; i++) ;
				endCharAt = i;
				faces.add(
						Integer.parseInt(tmpLine.substring(startCharAt, endCharAt)));
				i += 1;

				for (; tmpLine.charAt(i) != '"'; i++) ;
				startCharAt = i = i + 1;
				for (; tmpLine.charAt(i) != '"'; i++) ;
				endCharAt = i;
				faces.add(
						Integer.parseInt(tmpLine.substring(startCharAt, endCharAt)));
				i += 1;

				state = Statement.START;
			}
			else state = Statement.START;
		}
		else if (state == Statement.FACES)
		{
			String tmpLine;
			int i = 0;
			int startCharAt;
			int endCharAt;

			tmpLine = inFile.readLine();
			for (; tmpLine.charAt(i) != '"'; i++) ;
			startCharAt = i = i + 1;
			for (; tmpLine.charAt(i) != '"'; i++) ;
			endCharAt = i;
			alcHeader.numFaces =
					Integer.parseInt(tmpLine.substring(startCharAt, endCharAt));
			alcHeader.offsetVertices = 48 + alcHeader.numFaces;
			i += 1;

			state = Statement.START;
		}
	}

	private void writeFile() throws Exception
	{
		System.out.println("The number of faces is " + alcHeader.numFaces);
		System.out.println(
				"The number of vertices is " + alcHeader.numVertices);
		outFile.writeInt(alcHeader.descNum);
		outFile.writeByte((byte) 'A');
		outFile.writeByte((byte) 'L');
		outFile.writeByte((byte) 'C');
		for (int i = 3; i < alcHeader.desc.length; i++)
			outFile.writeByte(0);
		outFile.writeInt(alcHeader.version);
		outFile.writeInt(alcHeader.numFaces);
		outFile.writeInt(alcHeader.numVertices);
		outFile.writeInt(alcHeader.offsetFaces);
		outFile.writeInt(alcHeader.offsetVertices);
		for (int i = 0, numFaces = faces.size(); i < numFaces; i++)
			outFile.writeInt((Integer) faces.get(i));
		for (int i = 0, numVertices = vertices.size(); 
				i < numVertices; i++)
			outFile.writeFloat((Float) vertices.get(i));
	}

	public Xml2Alc() {}

	public void openFiles(String pFileName) 
	{
		try
		{
			String outFileName = 
				pFileName.substring(0, pFileName.length() - 4) + ".alc";
			File tmpFile= new File(outFileName);
			inFile = new BufferedReader(new FileReader(pFileName));
			if (tmpFile.exists()) tmpFile.delete();
			outFile = new RandomAccessFile(outFileName, "rw");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void closeFiles()
	{
		try
		{
			inFile.close();
			outFile.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void processFile()
	{
		try
		{
			char c;
			while (inFile.ready())
			{
				c = (char) inFile.read();
				readFaces(c);
				readGeometry(c);
				readNormal(c);
				readPosition(c);
			}
			writeFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args)
	{
		if (args.length < 1) System.exit(1);
		Xml2Alc xml2Alc = new Xml2Alc();
		System.out.println(args[0]);
		xml2Alc.openFiles(args[0]);
		xml2Alc.processFile();
		xml2Alc.closeFiles();
	}
}
