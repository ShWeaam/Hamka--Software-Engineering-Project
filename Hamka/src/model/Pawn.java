package model;

import java.util.ArrayList;
import java.util.WeakHashMap;

import Utils.PawnColor;

/**
 * 
 * this class manages the game pawns
 * i_coordinat is for the row where the pawn is located 
 * j_coordinat is for the column where the pawn is located 
 *
 */
public class Pawn {
	
	private int i_coordinat;
	private int j_coordinat;
	private int ID;
	private PawnColor color;
	
	/**
	 * @param i_coordinat - current coordination of the pawn
	 * @param j_coordinat - current coordination of the pawn
	 * @param iD- 1 to 12 - for internal use only, @TODO remove
	 * @param color- black or white
	 */
	public Pawn(int i_coordinat, int j_coordinat, int iD, PawnColor color) {
		super();
		this.i_coordinat = i_coordinat;
		this.j_coordinat = j_coordinat;
		ID = iD;
		this.color = color;
	}
	
	/**
	 * @return the i_coordinat
	 */
	public int getI_coordinat() {
		return i_coordinat;
	}
	
	/**
	 * @param i_coordinat the i_coordinat to set
	 */
	public void setI_coordinat(int i_coordinat) {
		this.i_coordinat = i_coordinat;
	}
	
	/**
	 * @return the j_coordinat
	 */
	public int getJ_coordinat() {
		return j_coordinat;
	}
	
	/**
	 * @param j_coordinat the j_coordinat to set
	 */
	public void setJ_coordinat(int j_coordinat) {
		this.j_coordinat = j_coordinat;
	}
	
	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the color
	 */
	public PawnColor getColor() {
		return color;
	}
	
	/**
	 * @param color the color to set
	 */
	public void setColor(PawnColor color) {
		this.color = color;
	}
	
	//------------------------------------------------------------------------------
	/**
	 * check if there is a pawn on specific cell
	 * for eating possibility, a pawn can jump over the cell only if there is a opposite color pawn on it 
	 * @param i => row to check
	 * @param j => column to check
	 * @param pawn_color to check
	 * @return
	 */
	public PawnColor check_color_pawn_on_cell(int i, int j){
		return Board.getCell(i,j).getPawn_on().color;
	}
	
	/**
	 * 
	 * @param i => row of the cell
	 * @param j => column of the cell
	 * @return pawn on specific cell
	 */
	public Pawn pawn_on_cell(int i, int j){
		return  Board.getCell(i,j).getPawn_on();
	}
	
	/**
	 * This function is checking for possible move for each paw:
	 * White pawns can move up and slant
	 * Black pawns can move up and slant
	 * check for eating possible (skip row and column)
	 * @param i = row to move to 
	 * @param j = column to move to 
	 * @return
	 */
	public boolean move_is_legal(int i, int j) {
		System.out.println("checking if move is legal");
		//TODO Check extreme cases
		if ( i>(Board.getRowsCount()-1) || j>(Board.getColumnsCount()-1) || i<0 || j<0) return false;
		System.out.println("From Pawn-moveIsLegal func. this is the pawn color  " + color);
		System.out.println("to i="+i+", j="+j);
		System.out.println("current i="+i_coordinat+", j="+j_coordinat);
		if (this.color.equals(PawnColor.BLACK)) { //Pawn is BLACK
			//BLACK pawns can move down and slant
			if (i == this.i_coordinat+1 && (j==this.j_coordinat-1 || j==this.j_coordinat+1))
				return true;
			//eating possibility
			else if (i == this.i_coordinat+2 && j==this.j_coordinat-2 && ( pawn_on_cell(i_coordinat+1,j_coordinat-1)!=null && check_color_pawn_on_cell(i_coordinat+1,j_coordinat-1).equals(PawnColor.WHITE))) {
				eat(i_coordinat+1,j_coordinat-1);
				return true;
			}
			else if (i == this.i_coordinat+2 && j==this.j_coordinat+2 && ( pawn_on_cell(i_coordinat+1,j_coordinat+1)!=null && check_color_pawn_on_cell(i_coordinat+1,j_coordinat+1).equals(PawnColor.WHITE))) {
				eat(i_coordinat+1,j_coordinat+1);
				return true;
			}
			//Move is not eating nor legal move	
			else return false;
		}
		else{ //Pawn is WHITE
			//WHITE pawns can move up and slant
			if (i == this.i_coordinat-1 && (j==this.j_coordinat-1 || j==this.j_coordinat+1))
				return true;
			//eating possibility
			else if (i == this.i_coordinat-2 && j==this.j_coordinat-2 && ( pawn_on_cell(i_coordinat-1,j_coordinat-1)!=null &&  check_color_pawn_on_cell(i_coordinat-1,j_coordinat-1).equals(PawnColor.BLACK))) {
				eat(i_coordinat-1,j_coordinat-1);
				return true;
			}
			else if (i == this.i_coordinat-2 && j==this.j_coordinat+2 && ( pawn_on_cell(i_coordinat-1,j_coordinat+1) !=null && check_color_pawn_on_cell(i_coordinat-1,j_coordinat+1).equals(PawnColor.BLACK))) {
				eat(i_coordinat-1,j_coordinat+1);
				return true;
			}
				
			//Move is not eating nor legal move	
			else return false;
		}
	}
	
	/**
	 * move the pawn\queen to the given coordinates
	 * 
	 * @param i - row to move to 
	 * @param j - column to move to
	 * @return
	 */
	public boolean move(int i, int j) {
		System.out.println("tying to move "+ID+" from: "+i_coordinat+","+j_coordinat+" to: "+i+","+j);
		Cell temp = Board.getCell(i,j); //find the cell the player want to move to
		if (temp.getPawn_on() == null && move_is_legal(i,j)) { //check if the cell is empty
			//make the move:
			//set new coordinate for the pawn
			this.setI_coordinat(i);
			this.setJ_coordinat(j);
			//update cell with this new pawn on
			temp.setPawn_on (this);
			//update the cell on the board
			Board.setCell(i,j,temp);
			//TODO Check Cell Type and activate unique move if needed
			//TODO Check if queen needed
			if (i==0 || i==(Board.getRowsCount()-1) ) make_queen(temp);
			return true;
		}
		else {
			System.out.println("Unlegal Move");
			return false;
		}
	}
	

	public Boolean can_eat() {
		PawnColor enemy_color = null;
		int addTo_i=1;
		switch(this.color) {
		  case BLACK:
			  enemy_color=PawnColor.WHITE;
			  addTo_i=1;
		    break;
		  case WHITE:
			  enemy_color=PawnColor.BLACK;
			  addTo_i=-1;
		    break;
		  default:
			  enemy_color=null;
		}
		
		if (i_coordinat+addTo_i>=0 && i_coordinat+addTo_i<=Board.getRowsCount()) {
			if (this.j_coordinat-1>=0 && pawn_on_cell(this.i_coordinat+addTo_i,this.j_coordinat-1)!=null && pawn_on_cell(this.i_coordinat+addTo_i,this.j_coordinat-1).getColor().equals(enemy_color)) {
				return true;
			}
			else if (this.j_coordinat+1<=Board.getColumnsCount() && pawn_on_cell(this.i_coordinat+addTo_i,this.j_coordinat+1)!=null && pawn_on_cell(this.i_coordinat+addTo_i,this.j_coordinat+1).getColor().equals(enemy_color)) {
				return true;
			}
			else return false;
		}
		return false;
	}
	
	/**
	 * transform the pawn to queen on the given cell
	 * @param onCell
	 */
	private void make_queen(Cell onCell) {
		System.out.println("I am gonna be a fu#ing Queen!");
		Queen newQueen = new Queen(this.i_coordinat, this.j_coordinat, this.ID, this.color);
		onCell.setPawn_on(newQueen);
	}
	
	/**
	 * eat a given pawn by the coordinates given
	 * @param i - row to move to 
	 * @param j - column to move to
	 * @return
	 */
	public Boolean eat(int i, int j) {
		System.out.println("eating! yammmm");
			Cell temp = Board.getCell(i,j);
			temp.setPawn_on(null);
			Board.setCell(i,j,temp);
			return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return color + "";
	}

	public ArrayList<Cell> get_moving_possibilities() {
		//if ( i>(Board.getRowsCount()-1) || j>(Board.getColumnsCount()-1) || i<0 || j<0) return false;
		ArrayList<Cell> moving_possibilities = new ArrayList<Cell>();
		if (this.color.equals(PawnColor.BLACK)) { //Pawn is BLACK
			//BLACK pawns can move down and slant
//			System.out.println("j_coordinat-1: " );
//			System.out.println(j_coordinat-1);
//			System.out.println("i_coordinat+1: " );
//			System.out.println(i_coordinat+1);
//			System.out.println("j_coordinat+1: " );
//			System.out.println(j_coordinat+1);
			if (j_coordinat-1>=0 && i_coordinat+1<=Board.getRowsCount() && Board.getCell(i_coordinat+1, j_coordinat-1).getPawn_on()==null)
				moving_possibilities.add(Board.getCell(i_coordinat+1, j_coordinat-1));
			if (j_coordinat+1<=Board.getColumnsCount() && i_coordinat+1<=Board.getRowsCount() && Board.getCell(i_coordinat+1, j_coordinat+1).getPawn_on()==null)
				moving_possibilities.add(Board.getCell(i_coordinat+1, j_coordinat+1));
		}
		else{ //Pawn is WHITE
			//WHITE pawns can move up and slant
//			System.out.println("i_coordinat-1: " );
//			System.out.println(i_coordinat-1);
//			System.out.println("j_coordinat-1: " );
//			System.out.println(j_coordinat-1);
			if (j_coordinat-1>=0 && i_coordinat-1>=0 && Board.getCell(i_coordinat-1, j_coordinat-1).getPawn_on()==null) {
				moving_possibilities.add(Board.getCell(i_coordinat-1, j_coordinat-1));
			}
				
			if (j_coordinat+1<=Board.getColumnsCount() && i_coordinat-1>=0 && Board.getCell(i_coordinat-1, j_coordinat+1).getPawn_on()==null)
				moving_possibilities.add(Board.getCell(i_coordinat-1, j_coordinat+1));
		}
		return moving_possibilities;
	}
	
	
	
	
}
