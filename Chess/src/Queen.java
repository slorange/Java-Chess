import java.awt.Point;
import java.util.ArrayList;


public class Queen extends Piece{
	public Queen(char c1, char c2, Board b, int x, int y){
		super(c1,c2,b,x,y);
		direction = GetDirection();
	}
	
	public static ArrayList<Point> GetDirection(){
		ArrayList<Point> m = new ArrayList<Point>();
		m.addAll(Bishop.GetDirection());
		m.addAll(Rook.GetDirection());
		return m;
	}
}

