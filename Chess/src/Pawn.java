import java.awt.Point;
import java.util.ArrayList;


public class Pawn extends Piece{
	public Pawn(char c1, char c2, Board b, int x, int y){
		super(c1,c2,b,x,y);
	}

	public ArrayList<Point> getLegalMoves(){//TODO show en passant
		ArrayList<Point> legalMoves = new ArrayList<Point>();
		int dir = 0;//direction
		if(color == 'w') dir = -1;
		if(color == 'b') dir = 1;
		Point l1 = new Point(pos.x+dir, pos.y);
		Piece p1 = b.getPiece(l1);
		if(p1 == null){
			legalMoves.add(l1);
			Point l2 = new Point(pos.x+dir*2, pos.y);
			Piece p2 = b.getPiece(l2);
			if(!moved && p2 == null){
				legalMoves.add(l2);
			}
		}
		Point l3 = new Point(pos.x+dir, pos.y+1);
		if(b.onBoard(l3)){
			Piece p3 = b.getPiece(l3);
			if(p3 != null && p3.color != this.color){
				legalMoves.add(l3);
			}
		}
		Point l4 = new Point(pos.x+dir, pos.y-1);
		if(b.onBoard(l4)){
			Piece p4 = b.getPiece(l4);
			if(p4 != null && p4.color != this.color){
				legalMoves.add(l4);
			}
		}
		return legalMoves;
	}
	
	public ArrayList<Point> getCapturePoints(){
		ArrayList<Point> capturePoints = new ArrayList<Point>();
		int dir = 0;//direction
		if(color == 'w') dir = -1;
		if(color == 'b') dir = 1;
		Point l3 = new Point(pos.x+dir, pos.y+1);
		if(b.onBoard(l3)){
			capturePoints.add(l3);
		}
		Point l4 = new Point(pos.x+dir, pos.y-1);
		if(b.onBoard(l4)){
			capturePoints.add(l4);
		}
		return capturePoints;
	}
}

