import java.awt.Point;
import java.util.ArrayList;


public class Player {
	char color;
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	Player(char c){
		color = c;
	}
	
	public void AddPiece(Piece p){
		pieces.add(p);
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
	
	public ArrayList<Point> getMoves(){ // TODO return set
		ArrayList<Point> m = new ArrayList<Point>();
		for(Piece p : pieces){
			m.addAll(p.getLegalMoves());
		}
		return m;
	}
	
	public ArrayList<Point> getCapturePoints(){ // TODO return set
		ArrayList<Point> m = new ArrayList<Point>();
		for(Piece p : pieces){
			m.addAll(p.getCapturePoints());
		}
		return m;
	}
}
