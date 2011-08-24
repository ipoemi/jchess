package alcoholic.jchess.core;

import java.io.Serializable;

public final class Field implements Serializable, Cloneable
{
	public static final int NONE = 0;

	public static final int PAWN = 1;
	public static final int ROOK = 2;
	public static final int KNIGHT = 3;
	public static final int BISHOP = 4;
	public static final int QUEEN = 5;
	public static final int KING = 6;

	public static final int WHITE = 7;
	public static final int BLACK = 8;

	public int kind = NONE;
	public int color = NONE;
	public boolean highlight = false;

	public Object clone() {
		try {
			super.clone();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit( 1 );
		}
		Field ret = new Field();
		ret.kind = kind;
		ret.color = color;
		ret.highlight = highlight;
		return ret;
	}

	public boolean equals(Field pField)
	{
		if( pField.kind != kind) return false;
		if( pField.color != color) return false;
		if( pField.highlight!= highlight) return false;
		return true;
	}
}
