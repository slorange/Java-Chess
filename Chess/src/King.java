import java.awt.Point;
import java.util.ArrayList;


public class King extends Piece{
	public King(char c1, char c2, Board b, int x, int y){
		super(c1,c2,b,x,y);
		baseMoves = GetBaseMoves();
	}
	
	public static ArrayList<Point> GetBaseMoves(){//TODO show castling, dont show moving into check
		ArrayList<Point> m = new ArrayList<Point>();
		m.add(new Point(1,1));
		m.add(new Point(-1,1));
		m.add(new Point(-1,-1));
		m.add(new Point(1,-1));
		m.add(new Point(0,1));
		m.add(new Point(0,-1));
		m.add(new Point(-1,0));
		m.add(new Point(1,0));
		return m;
	}
	
	public ArrayList<Point> getLegalMoves(){
		ArrayList<Point> legalMoves = super.getLegalMoves();
		if(!moved){//TODO not in check, not moving through check
			Piece p = b.getPiece(pos.x,pos.y+4);
			if(p.getName() == 'r' && p.getColor() == color){
				legalMoves.add(new Point(pos.x,pos.y+2));
			}
			p = b.getPiece(pos.x,pos.y-3);
			if(p.getName() == 'r' && p.getColor() == color){
				legalMoves.add(new Point(pos.x,pos.y-2));
			}
		}
		return legalMoves;
	}
}

