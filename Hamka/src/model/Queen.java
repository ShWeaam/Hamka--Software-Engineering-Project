package model;

import Utils.PawnColor;
import Utils.QueenMove;

public class Queen extends Pawn{

	public Queen(int i_coordinat, int j_coordinat, int iD, PawnColor color) {
		super(i_coordinat, j_coordinat, iD, color);
		System.out.println("New Queen in town.");
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean move_is_legal(int target_i, int target_j) {
		int i=getI_coordinat(), j=getJ_coordinat();
		System.out.println("checking if move is legal");
		//TODO Check extreme cases
		if ( target_i> Board.getRowsCount()-1 || target_j>Board.getColumnsCount()-1 || target_i<0 || target_j<0) return false;
		System.out.println(getColor());
		System.out.println("to i="+target_i+", j="+target_j);
		System.out.println("current i="+getI_coordinat()+", j="+getJ_coordinat());
		QueenMove move = null;
		if (getI_coordinat()<target_i && getJ_coordinat()<target_j) move=QueenMove.RIGHT_DOWN;
		else if (getI_coordinat()<target_i && getJ_coordinat()>target_j) move=QueenMove.LEFT_DOWN;
		else if (getI_coordinat()>target_i && getJ_coordinat()<target_j) move=QueenMove.RIGHT_UP;
		else if (getI_coordinat()>target_i && getJ_coordinat()>target_j) move=QueenMove.LEFT_UP;
		else if (getI_coordinat() == target_i && getJ_coordinat()==target_j) {move=QueenMove.FULL_CIRCLE; return true;}
		else return false;
		
		//move without eating
		boolean interrupted = false;
		while (!interrupted || (i!=target_i && j!=target_j)) {
			if (move.equals(QueenMove.RIGHT_DOWN)) {
				i++;
				j++;
			}
			else if (move.equals(QueenMove.LEFT_DOWN)) {
				i++;
				j--;
			}
			else if (move.equals(QueenMove.RIGHT_UP)) {
				i--;
				j++;
			}
			else if (move.equals(QueenMove.LEFT_UP)) {
				i--;
				j--;
			}
			 if (i==target_i && j==target_j) break;
			System.out.println("moving queen to i="+i+", j="+j);
			Cell nextCell = Board.getCell(i,j);
			if (nextCell.getPawn_on()!=null) {
				interrupted=true;
				if(!nextCell.getPawn_on().getColor().equals(getColor())) {
					eat(i, j);
					return move(target_i, target_j);
				}
				else {
					return false;
				}
			}
		}
		if(interrupted) return false;
		else {
			return true;
		}
			
	}

	
	
	
	
}
