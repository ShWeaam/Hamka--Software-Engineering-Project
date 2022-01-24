package model;

import java.util.ArrayList;

import Utils.CellColor;
import Utils.CellType;
import Utils.PawnColor;

/**
 * 
 * this class represents the board of the game 
 * 2d array of cells 
 *
 */
public class Board {
	
	private static final int rows_count=8;
	private static final int columns_count=8;
	private static final int rows_of_pawns=3;
	private static final int num_of_pawns=24;

	private static Cell board[][] = new Cell[rows_count][columns_count];

	
	/** 
	 * constructor
	 * @param board
	 */
	public Board(Cell[][] board) {
		super();
		Board.board = board;
	}

	/**
	 * constructor
	 */
	public Board() {
		super();
		Cell temp[][] = new Cell[rows_count][columns_count];
		CellColor color = CellColor.BLACK;
		for(int i = 0; i < rows_count; i++)
		   {
		      for(int j = 0; j < columns_count; j++)
		      {
		    	  color=changeColor(color);
		    	  temp[i][j]= new Cell(i, j, color, CellType.REGULAR, null);
		      }
		      color=changeColor(color);
		   }
		System.out.println("hello from line 50 Board.java");
		Board.board = temp;
	}

	/**
	 * @return the board
	 */
	public Cell[][] getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(Cell[][] board) {
		Board.board = board;
	}
	
	/**
	 * 
	 * @param i - row coordination
	 * @param j	- column coordination
	 * @return
	 */
	public static Cell getCell(int i, int j) {
		return board[i][j];
	}
	
	public static void setCell(int i, int j, Cell cell) {
		board[i][j] = cell;
	}
	
	public static int getRowsCount() {
		return rows_count;
	}

	public static int getColumnsCount() {
		return columns_count;
	}

	public static int getRowsOfPawns() {
		return rows_of_pawns;
	}

	public static int getNumOfPawns() {
		return num_of_pawns;
	}

	/**
	 * this function changes the color black to white or white to black
	 * @param color - the current color
	 * @return the new color
	 */
	public CellColor changeColor(CellColor color) {
		if (color.equals(CellColor.BLACK))
	    	return CellColor.WHITE;
		return CellColor.BLACK;
	}
	
	public void printGrid()
	{
		System.out.println("                (0)                    (1)                       (2)                     (3)                       (4)                      (5)                        (6)                        (7)");
	   for(int i = 0; i < rows_count; i++)
	   {
		  System.out.print(i+") ");
	      for(int j = 0; j < columns_count; j++)
	      {
	    	 String str= board[i][j].toString();
	         //System.out.printf("%5d ", str); 
	         //System.out.println(board[i][j]);
	         System.out.print(board[i][j]+ " | ");
	         //System.out.println(String.format("%5d ", str));
	      }
	      System.out.println();
	   }
	}
	
	/**
	 * this function sorts the pawns on the board for a new game
	 * it goes through the loop adding both the white and black pawns at the same time
	 * @param-pawnID - the pawn id 
	 * @return
	 */
	public boolean start_new_game() {
		int pawnID=0;
		for( int iBlack=0, iWhite=rows_count-1 ; iBlack < rows_of_pawns; iBlack++, iWhite--)
		{
			for(int j= Math.abs((iBlack%2) - 1) ; j< columns_count; j=j+2)
			{
		    	 Board.board[iBlack][j].setPawn_on(new Pawn(iBlack, j, pawnID , PawnColor.BLACK));
		    	 if(j == 0)
		    		 Board.board[iWhite][j+1].setPawn_on(new Pawn(iWhite, j+1, num_of_pawns-pawnID , PawnColor.WHITE));
		    	 else {
		    		 if (j == columns_count - 2)
			    		 Board.board[iWhite][j+1].setPawn_on(new Pawn(iWhite, j+1, num_of_pawns-pawnID , PawnColor.WHITE));
		    		 Board.board[iWhite][j-1].setPawn_on(new Pawn(iWhite, j-1, num_of_pawns-pawnID , PawnColor.WHITE));
		    	 }
		    	 pawnID++;
			}
		}
		resetCells();
		return true;
	}
	
	private void resetCells()
	{
		for (int i = 0; i < getRowsCount() - 1; i++) {
			for (int j = 0; j <getColumnsCount() - 1; j++)
				getCell(i, j).setType(CellType.REGULAR);
		}
	}
	/***
	 * TODO
	 * @return 
	 */
	public boolean load_game(ArrayList<Cell> toLoad) {
//		ArrayList<Pawn> whites = new ArrayList<Pawn>();
//		ArrayList<Pawn> bleacks = new ArrayList<Pawn>();
//		for (int w=1; w<13 ; w++) {
//			whites.add(new Pawn(0, 0, w, PawnColor.WHITE));
//		}
//		for (int b=1; b<13 ; b++) {
//			whites.add(new Pawn(0, 0, b, PawnColor.BLACK));
//		}
//		Cell temp[][] = {
//				
//		};
		PawnColor color = PawnColor.BLACK;
		int id = 1;
		int x=0, y=0;
		for(int i=x ; i < 8; i++)
		{
			if ( i % 2 == 0)
				y=1;
			else
				y=0;
		    for(int j=y ; j < 8; j=j+2)
		    {	
		    	if (i>2) color= PawnColor.WHITE;
		    	if (i!=3 && i!=4) {
			    	 Board.board[i][j].setPawn_on(new Pawn(i, j, id , color));
			    	 id++;
		    	}
		    	if (i==1 && j==4) Board.board[i][j].setPawn_on(new Pawn(i, j, id , PawnColor.WHITE));
		    	if (i==0 && j==3) Board.board[i][j].setPawn_on(null);
		    	if (i==2 && j==5) Board.board[i][j].setPawn_on(null);
		    	if (i==3 && j==6) Board.board[i][j].setPawn_on(new Pawn(i, j, id , PawnColor.BLACK));
		      }
		   }
		Board.board[3][6].setPawn_on(new Pawn(3, 6, 1 , PawnColor.BLACK));
		return true;
		
	}
	
	/*
	 * check if string is null or empty
	 */
	public static boolean isNullOrEmpty(String str) {
		if(str == null || str.isEmpty())
			return true;
	    return false;
	}

}
