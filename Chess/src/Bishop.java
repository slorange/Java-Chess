import java.awt.Point;
import java.util.ArrayList;


public class Bishop extends Piece{
	public Bishop(char c1, char c2, Board b, int x, int y){
		super(c1,c2,b,x,y);
		direction = GetDirection();
	}
	
	public static ArrayList<Point> GetDirection(){
		ArrayList<Point> m = new ArrayList<Point>();
		m.add(new Point(1,1));
		m.add(new Point(-1,1));
		m.add(new Point(-1,-1));
		m.add(new Point(1,-1));
		return m;
	}
}

