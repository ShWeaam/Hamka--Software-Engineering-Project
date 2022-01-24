package model;

import Utils.CellColor;
import Utils.CellType;

/**
 * 
 * this class represents the cells
 * each cell has coordinates of it`s position on the board,
 * type which represents the special cells
 * holds pawn if one is on it 
 *
 */
public class Cell {
	
	private int i_coordinat;
	private int j_coordinat;
	private CellColor color;
	private CellType type;
	private Pawn pawn_on;
	
	/**
	 * @param i_coordinat
	 * @param j_coordinat
	 * @param color
	 * @param type
	 * @param pawn_on
	 */
	public Cell(int i_coordinat, int j_coordinat, CellColor color, CellType type, Pawn pawn_on) {
		super();
		this.i_coordinat = i_coordinat;
		this.j_coordinat = j_coordinat;
		this.color = color;
		this.type = type;
		this.pawn_on = pawn_on;
	}
	
	public int getI_coordinat() {
		return i_coordinat;
	}

	public void setI_coordinat(int i_coordinat) {
		this.i_coordinat = i_coordinat;
	}

	public int getJ_coordinat() {
		return j_coordinat;
	}

	public void setJ_coordinat(int j_coordinat) {
		this.j_coordinat = j_coordinat;
	}

	public CellColor getColor() {
		return color;
	}

	public void setColor(CellColor color) {
		this.color = color;
	}

	public CellType getType() {
		return type;
	}

	public void setType(CellType type) {
		this.type = type;
	}

	public Pawn getPawn_on() {
		return pawn_on;
	}

	public void setPawn_on(Pawn pawn_on) {
		this.pawn_on = pawn_on;
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean move_pawn_on_cell(int i,int j) {
		//TODO check for extreme cases
		if(getPawn_on() == null)
			return false;
		boolean toreturn = this.pawn_on.move(i, j);
		if (toreturn)
				this.pawn_on=null;
		return toreturn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
//		System.out.println(color);
//		System.out.println(type);
//		System.out.println(pawn_on);
		return "[" + color + ", " + type + ", " + pawn_on + "]";
	}

	

}
