import java.awt.Point;
import java.util.ArrayList;


public class Rook extends Piece{
	public Rook(char c1, char c2, Board b, int x, int y){
		super(c1,c2,b,x,y);
		direction = GetDirection();
	}
	
	public static ArrayList<Point> GetDirection(){
		ArrayList<Point> m = new ArrayList<Point>();
		m.add(new Point(0,1));
		m.add(new Point(-1,0));
		m.add(new Point(1,0));
		m.add(new Point(0,-1));
		return m;
	}
}

