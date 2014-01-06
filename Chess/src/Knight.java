import java.awt.Point;
import java.util.ArrayList;


public class Knight extends Piece{
	public Knight(char c1, char c2, Board b, int x, int y){
		super(c1,c2,b,x,y);
		baseMoves = GetBaseMoves();
	}
	
	public static ArrayList<Point> GetBaseMoves(){
		ArrayList<Point> m = new ArrayList<Point>();
		m.add(new Point(1,2));
		m.add(new Point(-1,2));
		m.add(new Point(-1,-2));
		m.add(new Point(1,-2));
		m.add(new Point(2,1));
		m.add(new Point(-2,1));
		m.add(new Point(-2,-1));
		m.add(new Point(2,-1));
		return m;
	}
}

