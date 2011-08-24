package alcoholic.jchess.core;

import java.io.Serializable;
import alcoholic.jchess.core.*;

public class State implements Serializable, Cloneable
{
	/* private area */
	private Field[][] field = new Field[8][8];
	private int[] whiteDead = new int[16];
	private int[] blackDead = new int[16];
	private int whiteDeadCnt;
	private int blackDeadCnt;

	private int currentColor;

	private int whiteKingRow;
	private int whiteKingCol;
	private int blackKingRow;
	private int blackKingCol;

	private boolean whiteSpecialMove;
	private boolean blackSpecialMove;

	private int selectedRow;
	private int selectedCol;

	private Field[][] copyField(Field[][] pField)
	{
		Field[][] cloneField = new Field[8][8];
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				cloneField[i][j] = (Field)pField[i][j].clone();
			}
		}
		return cloneField;
	}

	private boolean isAvailableField(int pRow, int pCol)
	{
		if(pRow < 0 || pRow > 7) return false;
		if(pCol < 0 || pCol > 7) return false;
		return true;
	}

	private boolean isNoneField(int pRow, int pCol)
	{
		if(field[pCol][pRow].color == Field.NONE) return true;
		else return false;
	}

	private boolean isEnemyField(int pRow, int pCol)
	{
		int color = field[pCol][pRow].color;
		if(color != currentColor && color != Field.NONE) return true;
		else return false;
	}

	private boolean isOurField(int pRow, int pCol)
	{
		if(field[pCol][pRow].color == currentColor) return true;
		else return false;
	}

	private boolean isAvailableSelection(int pRow, int pCol)
	{
		if(!isAvailableField(pRow, pCol)) return false;
		if(!isOurField(pRow, pCol)) return false;
		return true;
	}

	private boolean isHighlightOn(int pRow, int pCol)
	{
		return field[pCol][pRow].highlight;
	}

	/*
	private void verifyHighlight()
	{
		Field[][] backupField = copyField(field);
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(field[i][j].highlight)
				{
					if(getSelectedKind() == Field.KING)
					{
						if(currentColor == Field.WHITE)
						{
							whiteKingRow = j;
							whiteKingCol = i;
						}
						else
						{
							blackKingRow = j;
							blackKingCol = i;
						}
					}
					field[i][j].kind =  
						field[selectedCol][selectedRow].kind;
					field[i][j].color =  
						field[selectedCol][selectedRow].color;
					field[selectedCol][selectedRow].kind = Field.NONE;
					field[selectedCol][selectedRow].color = Field.NONE;
					if(isCheckMate())
					{
						backupField[i][j].highlight = false;
					}
					field = copyField(backupField);
					if(getSelectedKind() == Field.KING)
					{
						if(currentColor == Field.WHITE)
						{
							whiteKingRow = selectedRow;
							whiteKingCol = selectedCol;
						}
						else
						{
							blackKingRow = selectedRow;
							blackKingCol = selectedCol;
						}
					}
					field = copyField(backupField);
				}
			}
		}
	}
	*/

	private void clearHighlight()
	{
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				field[i][j].highlight = false;
			}
		}
	}

	private void setPawnHighlightOn()
	{
		if(currentColor == Field.WHITE)
		{
			if(isAvailableField(selectedRow, selectedCol + 1) && 
					isNoneField(selectedRow, selectedCol + 1))
			{
				field[selectedCol + 1][selectedRow].highlight = true;
				if(selectedCol == 1)
					if(isAvailableField(selectedRow, selectedCol + 2) &&
							isNoneField(selectedRow, selectedCol + 2))
						field[selectedCol + 2][selectedRow].highlight 
							= true;
			}
			if(isAvailableField(selectedRow + 1, selectedCol + 1) && 
					isEnemyField(selectedRow + 1, selectedCol + 1))
				field[selectedCol + 1][selectedRow + 1].highlight = true;
			if(isAvailableField(selectedRow - 1, selectedCol + 1) && 
					isEnemyField(selectedRow - 1, selectedCol + 1))
				field[selectedCol + 1][selectedRow - 1].highlight = true;
		}
		else
		{
			if(isAvailableField(selectedRow, selectedCol - 1) && 
					isNoneField(selectedRow, selectedCol - 1))
			{
				field[selectedCol - 1][selectedRow].highlight = true;
				if(selectedCol == 6)
					if(isAvailableField(selectedRow, selectedCol - 2) &&
							isNoneField(selectedRow, selectedCol - 2))
						field[selectedCol - 2][selectedRow].highlight 
							= true;
			}
			if(isAvailableField(selectedRow + 1, selectedCol - 1) && 
					isEnemyField(selectedRow + 1, selectedCol - 1))
				field[selectedCol - 1][selectedRow + 1].highlight = true;
			if(isAvailableField(selectedRow - 1, selectedCol - 1) && 
					isEnemyField(selectedRow - 1, selectedCol - 1))
				field[selectedCol - 1][selectedRow - 1].highlight = true;
		}
	}

	private void setRookHighlightOn()
	{
		for(int i = 1; 
				isAvailableField(selectedRow + i, selectedCol); i++)
		{
			if(isNoneField(selectedRow + i, selectedCol))
				field[selectedCol][selectedRow + i].highlight = true;
			else if(isEnemyField(selectedRow + i, selectedCol))
			{
				field[selectedCol][selectedRow + i].highlight = true;
				break;
			}
			else break;
		}
		for(int i = 1; 
				isAvailableField(selectedRow - i, selectedCol); i++)
		{
			if(isNoneField(selectedRow - i, selectedCol))
				field[selectedCol][selectedRow - i].highlight = true;
			else if(isEnemyField(selectedRow - i, selectedCol))
			{
				field[selectedCol][selectedRow - i].highlight = true;
				break;
			}
			else break;
		}
		for(int i = 1; 
				isAvailableField(selectedRow, selectedCol + i); i++)
		{
			if(isNoneField(selectedRow, selectedCol + i))
				field[selectedCol + i][selectedRow].highlight = true;
			else if(isEnemyField(selectedRow, selectedCol + i))
			{
				field[selectedCol + i][selectedRow].highlight = true;
				break;
			}
			else break;
		}
		for(int i = 1; 
				isAvailableField(selectedRow, selectedCol - i); i++)
		{
			if(isNoneField(selectedRow, selectedCol - i))
				field[selectedCol - i][selectedRow].highlight = true;
			else if(isEnemyField(selectedRow, selectedCol - i))
			{
				field[selectedCol - i][selectedRow].highlight = true;
				break;
			}
			else break;
		}
	}

	private void setKnightHighlightOn()
	{
		if(isAvailableField(selectedRow + 2, selectedCol + 1) && 
				!isOurField(selectedRow + 2, selectedCol + 1))
			field[selectedCol + 1][selectedRow + 2].highlight = true;
		if(isAvailableField(selectedRow - 2, selectedCol + 1) && 
				!isOurField(selectedRow - 2, selectedCol + 1))
			field[selectedCol + 1][selectedRow - 2].highlight = true;
		if(isAvailableField(selectedRow + 2, selectedCol - 1) && 
				!isOurField(selectedRow + 2, selectedCol - 1))
			field[selectedCol - 1][selectedRow + 2].highlight = true;
		if(isAvailableField(selectedRow - 2, selectedCol - 1) && 
				!isOurField(selectedRow - 2, selectedCol - 1))
			field[selectedCol - 1][selectedRow - 2].highlight = true;
		if(isAvailableField(selectedRow + 1, selectedCol + 2) && 
				!isOurField(selectedRow + 1, selectedCol + 2))
			field[selectedCol + 2][selectedRow + 1].highlight = true;
		if(isAvailableField(selectedRow - 1, selectedCol + 2) && 
				!isOurField(selectedRow - 1, selectedCol + 2))
			field[selectedCol + 2][selectedRow - 1].highlight = true;
		if(isAvailableField(selectedRow + 1, selectedCol - 2) && 
				!isOurField(selectedRow + 1, selectedCol - 2))
			field[selectedCol - 2][selectedRow + 1].highlight = true;
		if(isAvailableField(selectedRow - 1, selectedCol - 2) && 
				!isOurField(selectedRow - 1, selectedCol - 2))
			field[selectedCol - 2][selectedRow - 1].highlight = true;
	}

	private void setBishopHighlightOn()
	{
		for(int i = 1; 
				isAvailableField(selectedRow + i, selectedCol + i); i++)
		{
			if(isNoneField(selectedRow + i, selectedCol + i))
				field[selectedCol + i][selectedRow + i].highlight = true;
			else if(isEnemyField(selectedRow + i, selectedCol + i))
			{
				field[selectedCol + i][selectedRow + i].highlight = true;
				break;
			}
			else break;

		}
		for(int i = 1; 
				isAvailableField(selectedRow + i, selectedCol - i); i++)
		{
			if(isNoneField(selectedRow + i, selectedCol - i))
				field[selectedCol - i][selectedRow + i].highlight = true;
			else if(isEnemyField(selectedRow + i, selectedCol - i))
			{
				field[selectedCol - i][selectedRow + i].highlight = true;
				break;
			}
			else break;
		}
		for(int i = 1; 
				isAvailableField(selectedRow - i, selectedCol + i); i++)
		{
			if(isNoneField(selectedRow - i, selectedCol + i))
				field[selectedCol + i][selectedRow - i].highlight = true;
			else if(isEnemyField(selectedRow - i, selectedCol + i))
			{
				field[selectedCol + i][selectedRow - i].highlight = true;
				break;
			}
			else break;
		}
		for(int i = 1; 
				isAvailableField(selectedRow - i, selectedCol - i); i++)
		{
			if(isNoneField(selectedRow - i, selectedCol - i))
				field[selectedCol - i][selectedRow - i].highlight = true;
			else if(isEnemyField(selectedRow - i, selectedCol - i))
			{
				field[selectedCol - i][selectedRow - i].highlight = true;
				break;
			}
			else break;
		}
	}

	private void setQueenHighlightOn()
	{
		setRookHighlightOn();
		setBishopHighlightOn();
	}

	private void setKingHighlightOn()
	{
		if(isAvailableField(selectedRow, selectedCol + 1) && 
				!isOurField(selectedRow, selectedCol + 1))
			field[selectedCol + 1][selectedRow].highlight = true;
		if(isAvailableField(selectedRow + 1, selectedCol) && 
				!isOurField(selectedRow + 1, selectedCol))
			field[selectedCol][selectedRow + 1].highlight = true;
		if(isAvailableField(selectedRow, selectedCol - 1) && 
				!isOurField(selectedRow, selectedCol - 1))
			field[selectedCol - 1][selectedRow].highlight = true;
		if(isAvailableField(selectedRow - 1, selectedCol) && 
				!isOurField(selectedRow - 1, selectedCol))
			field[selectedCol][selectedRow - 1].highlight = true;
		if(isAvailableField(selectedRow + 1, selectedCol + 1) && 
				!isOurField(selectedRow + 1, selectedCol + 1))
			field[selectedCol + 1][selectedRow + 1].highlight = true;
		if(isAvailableField(selectedRow + 1, selectedCol - 1) && 
				!isOurField(selectedRow + 1, selectedCol - 1))
			field[selectedCol - 1][selectedRow + 1].highlight = true;
		if(isAvailableField(selectedRow - 1, selectedCol + 1) && 
				!isOurField(selectedRow - 1, selectedCol + 1))
			field[selectedCol + 1][selectedRow - 1].highlight = true;
		if(isAvailableField(selectedRow - 1, selectedCol - 1) && 
				!isOurField(selectedRow - 1, selectedCol - 1))
			field[selectedCol - 1][selectedRow - 1].highlight = true;
		if(currentColor == Field.WHITE &&
				field[0][5].color == Field.NONE &&
				field[0][6].color == Field.NONE &&
				whiteSpecialMove &&
				field[0][7].kind == Field.ROOK &&
				field[0][7].color == Field.WHITE)
			field[0][6].highlight = true;
		else if(currentColor == Field.BLACK &&
				field[7][1].color == Field.NONE &&
				field[7][2].color == Field.NONE &&
				whiteSpecialMove &&
				field[7][0].kind == Field.ROOK &&
				field[7][0].color == Field.BLACK)
			field[7][1].highlight = true;
	}

	private void setHighlightOn()
	{
		if(!isAvailableField(selectedRow, selectedCol)) return;
		switch (field[selectedCol][selectedRow].kind)
		{
			case Field.PAWN :
				setPawnHighlightOn();
				break;
			case Field.ROOK :
				setRookHighlightOn();
				break;
			case Field.KNIGHT :
				setKnightHighlightOn();
				break;
			case Field.BISHOP :
				setBishopHighlightOn();
				break;
			case Field.QUEEN :
				setQueenHighlightOn();
				break;
			case Field.KING :
				setKingHighlightOn();
				break;
		}
	}

	/* public area */
	public State()
	{
		reset();
	}

	public void reset()
	{
		currentColor = Field.WHITE;

		whiteDeadCnt = 0;
		blackDeadCnt = 0;

		whiteSpecialMove = true;
		blackSpecialMove = true;

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				field[i][j] = new Field();
			}
		}

		deselect();

		for(int i = 0; i < 16; i++)
		{
			whiteDead[i] = Field.NONE;
			blackDead[i] = Field.NONE;
		}

		for(int i = 0; i < 8; i++)
		{
			field[1][i].color = Field.WHITE;
			field[1][i].kind = Field.PAWN;
			field[6][i].color = Field.BLACK;
			field[6][i].kind = Field.PAWN;
		}

		field[0][0].kind = Field.ROOK;
		field[0][0].color = Field.WHITE;
		field[0][1].kind = Field.KNIGHT;
		field[0][1].color = Field.WHITE;
		field[0][2].kind = Field.BISHOP;
		field[0][2].color = Field.WHITE;
		field[0][3].kind = Field.QUEEN;
		field[0][3].color = Field.WHITE;
		field[0][4].kind = Field.KING;
		field[0][4].color = Field.WHITE;
		whiteKingRow = 4;
		whiteKingCol = 0;
		field[0][5].kind = Field.BISHOP;
		field[0][5].color = Field.WHITE;
		field[0][6].kind = Field.KNIGHT;
		field[0][6].color = Field.WHITE;
		field[0][7].kind = Field.ROOK;
		field[0][7].color = Field.WHITE;

		field[7][0].kind = Field.ROOK;
		field[7][0].color = Field.BLACK;
		field[7][1].kind = Field.KNIGHT;
		field[7][1].color = Field.BLACK;
		field[7][2].kind = Field.BISHOP;
		field[7][2].color = Field.BLACK;
		field[7][3].kind = Field.KING;
		field[7][3].color = Field.BLACK;
		blackKingRow = 3;
		blackKingCol = 7;
		field[7][4].kind = Field.QUEEN;
		field[7][4].color = Field.BLACK;
		field[7][5].kind = Field.BISHOP;
		field[7][5].color = Field.BLACK;
		field[7][6].kind = Field.KNIGHT;
		field[7][6].color = Field.BLACK;
		field[7][7].kind = Field.ROOK;
		field[7][7].color = Field.BLACK;
	}

	public int getTurn()
	{
		return currentColor;
	}

	public int getSelectedKind()
	{
		return field[selectedCol][selectedRow].kind;
	}

	public int getSelectedRow()
	{
		return selectedRow;
	}

	public int getSelectedCol()
	{
		return selectedCol;
	}

	public Field[][] getField()
	{
		return copyField(field);
	}

	public int[] getWhiteDead()
	{
		return whiteDead;
	}

	public int getWhiteDeadCnt()
	{
		return whiteDeadCnt;
	}

	public int[] getBlackDead()
	{
		return blackDead;
	}

	public int getBlackDeadCnt()
	{
		return blackDeadCnt;
	}

	public int getLoser()
	{
		boolean blackFlag = false;
		boolean whiteFlag = false;
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(field[i][j].color == Field.WHITE)
				{
					if(field[i][j].kind == Field.KING)
						whiteFlag = true;
				}
				else if(field[i][j].color == Field.BLACK)
				{
					if(field[i][j].kind == Field.KING)
						blackFlag = true;
				}
			}
		}
		if(blackFlag == false) return Field.BLACK;
		else if(whiteFlag == false) return Field.WHITE;
		else return Field.NONE;
	}

	public boolean isSelected()
	{
		if(!isAvailableSelection(selectedRow, selectedCol)) 
			return false;
		return true;
	}

	public boolean isCheckMate()
	{
		int selectedRowBackup = selectedRow;
		int selectedColBackup = selectedCol;
		int currentColorBackup = currentColor;
		int currentKingRow;
		int currentKingCol;

		if(currentColor == Field.WHITE)
		{
			currentKingRow = whiteKingRow;
			currentKingCol = whiteKingCol;
			currentColor = Field.BLACK;
		}
		else
		{
			currentKingRow = blackKingRow;
			currentKingCol = blackKingCol;
			currentColor = Field.WHITE;
		}

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(isOurField(j, i))
				{
					clearHighlight();
					selectedRow = j;
					selectedCol = i;
					setHighlightOn();
					if(field[currentKingCol][currentKingRow].highlight)
					{
						clearHighlight();
						selectedRow = selectedRowBackup;
						selectedCol = selectedColBackup;
						currentColor = currentColorBackup;
						setHighlightOn();
						return true;
					}
				}
			}
		}
		clearHighlight();
		selectedRow = selectedRowBackup;
		selectedCol = selectedColBackup;
		currentColor = currentColorBackup;
		setHighlightOn();
		return false;
	}

	public boolean select(int pRow, int pCol)
	{
		if(!isAvailableSelection(pRow, pCol)) return false;
		selectedRow = pRow;
		selectedCol = pCol;
		setHighlightOn();
		//verifyHighlight(); // next version
		return true;
	}

	public void deselect()
	{
		selectedRow =  - 1;
		selectedCol =  - 1;
		clearHighlight();
	}

	public void pass()
	{
		deselect();
		if (currentColor == Field.WHITE) currentColor = Field.BLACK;
		else currentColor = Field.WHITE;
	}

	public boolean moveTo(int pRow, int pCol)
	{
		if(!isSelected()) return false;
		if(!isAvailableField(pRow, pCol)) return false;
		if(!isHighlightOn(pRow, pCol)) return false;
		if(field[pCol][pRow].kind != Field.NONE)
		{
			if(currentColor == Field.WHITE)
			{
				blackDead[blackDeadCnt++] = field[pCol][pRow].kind;
			}
			else
			{
				whiteDead[whiteDeadCnt++] = field[pCol][pRow].kind;
			}
		}
		int selectedKind = getSelectedKind();
		if(currentColor == Field.WHITE)
		{
			if(selectedKind == Field.KING)
			{
				whiteKingRow = pRow;
				whiteKingCol = pCol;
				if (whiteSpecialMove && pRow == 6 && pCol == 0)
				{
					field[0][7].kind = Field.NONE;
					field[0][7].color = Field.NONE;
					field[0][5].kind = Field.ROOK;
					field[0][5].color = Field.WHITE;
				}
				whiteSpecialMove = false;
			}
			else if(selectedCol == 0 && 
					selectedRow == 7 && selectedKind == Field.ROOK) 
				whiteSpecialMove = false;
			else if(selectedKind == Field.PAWN && pCol == 7)
				field[selectedCol][selectedRow].kind = Field.QUEEN;
		}
		else
		{
			if(selectedKind == Field.KING)
			{
				blackKingRow = pRow;
				blackKingCol = pCol;
				if (blackSpecialMove && pRow == 1 && pCol == 7)
				{
					field[7][0].kind = Field.NONE;
					field[7][0].color = Field.NONE;
					field[7][2].kind = Field.ROOK;
					field[7][2].color = Field.BLACK;
				}
				blackSpecialMove = false;
			}
			else if(selectedCol == 7 && 
					selectedRow == 0 && selectedKind == Field.ROOK) 
				blackSpecialMove = false;
			else if(selectedKind == Field.PAWN && pCol == 0)
				field[selectedCol][selectedRow].kind = Field.QUEEN;
		}

		field[pCol][pRow].kind 
			=  field[selectedCol][selectedRow].kind;
		field[pCol][pRow].color 
			=  field[selectedCol][selectedRow].color;
		field[selectedCol][selectedRow].kind = Field.NONE;
		field[selectedCol][selectedRow].color = Field.NONE;
		if(currentColor == Field.WHITE) currentColor = Field.BLACK;
		else currentColor = Field.WHITE;
		deselect();
		return true;
	}

	public Object clone()
	{
		State cloneState = new State();

		cloneState.field = copyField(field);
		cloneState.whiteDead = new int[16];
		cloneState.blackDead = new int[16];
		for(int i = 0; i < whiteDead.length; i++)
		{
			cloneState.whiteDead[i] = whiteDead[i];
			cloneState.blackDead[i] = blackDead[i];
		}
		cloneState.whiteDeadCnt = whiteDeadCnt;
		cloneState.blackDeadCnt = blackDeadCnt;

		cloneState.currentColor = currentColor;

		cloneState.whiteKingRow = whiteKingRow;
		cloneState.whiteKingCol = whiteKingCol;
		cloneState.blackKingRow = blackKingRow;
		cloneState.blackKingCol = blackKingCol;

		cloneState.whiteSpecialMove = whiteSpecialMove;
		cloneState.blackSpecialMove = blackSpecialMove;

		cloneState.selectedRow = selectedRow;
		cloneState.selectedCol = selectedCol;

		return cloneState;
	}

	public boolean equals(State pState)
	{
		for(int i = 0; i < field.length; i++)
		{
			for(int j = 0; j < field[0].length; j++)
			{
				if(!pState.field[i][j].equals(field[i][j])) return false;
			}
		}
		for(int i = 0; i < whiteDead.length; i++)
		{
			if(pState.whiteDead[i] != whiteDead[i]) return false;
			if(pState.blackDead[i] != blackDead[i]) return false;
		}
		if(pState.whiteDeadCnt == whiteDeadCnt) return false;
		if(pState.blackDeadCnt == blackDeadCnt) return false;

		if(pState.currentColor == currentColor) return false;

		if(pState.whiteKingRow == whiteKingRow) return false;
		if(pState.whiteKingCol == whiteKingCol) return false;
		if(pState.blackKingRow == blackKingRow) return false;
		if(pState.blackKingCol == blackKingCol) return false;

		if(pState.whiteSpecialMove == whiteSpecialMove) return false;
		if(pState.blackSpecialMove == blackSpecialMove) return false;

		if(pState.selectedRow == selectedRow) return false;
		if(pState.selectedCol == selectedCol) return false;

		return true;
	}

	/* debug function */
	public void printAll()
	{
		System.out.print("Current Color : ");
		if(currentColor == Field.WHITE) System.out.println("White");
		else System.out.println("Black");
		if(isSelected())
		{
			System.out.println("Selected [" + selectedRow +
					", " + selectedCol + "]");
		}
		System.out.println("White King : [" + whiteKingRow +
				"," + whiteKingCol + "]");
		System.out.println("Black King : [" + blackKingRow +
				"," + blackKingCol + "]");
		System.out.print("Dead : ");
		for(int i = 0; i < whiteDeadCnt; i++)
		{
			System.out.print(whiteDead[i] + "W  ");
		}
		System.out.println();

		for(int i = 7; i >= 0; i--)
		{
			System.out.print("00" + i + "  ");
			for(int j = 0; j < 8; j++)
			{
				System.out.print(field[i][j].kind);
				if(field[i][j].color == Field.WHITE)
					System.out.print("W");
				else if(field[i][j].color == Field.BLACK)
					System.out.print("B");
				else
					System.out.print("0");
				if(field[i][j].highlight == true)
					System.out.print("1  ");
				else
					System.out.print("0  ");
			}
			System.out.println();
		}

		System.out.print("     ");
		for(int i = 0; i < 8; i++)
		{
			System.out.print("00" + i + "  ");
		}

		System.out.println();

		System.out.print("Dead : ");
		for(int i = 0; i < blackDeadCnt; i++)
		{
			System.out.print(blackDead[i] + "B  ");
		}
		System.out.println();
		System.out.println("White Special Move : " + whiteSpecialMove);
		System.out.println("Black Special Move : " + blackSpecialMove);
	}
}
