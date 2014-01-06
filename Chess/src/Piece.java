import java.awt.Point;
import java.util.ArrayList;

public class Piece {
	protected char name;
	protected char color;
	protected boolean moved;
	protected boolean last;
	Board b;
	Point pos;
	ArrayList<Point> baseMoves;
	ArrayList<Point> direction;
	
	public Piece(char c1, char c2, Board b, int x, int y){
		this.name = c1;
		this.color = c2;
		this.moved = false;
		this.b = b;
		pos = new Point(x,y);
		//baseMoves = GetBaseMoves(); // TODO: remove this line from other piece classes and see if basemoves still works
	}
	
	public void setName(char c) {
		this.name = c;
	}

	public void setColor(char c) {
		this.color = c;
	}

	public void Move(int x, int y) {
		this.moved = true;
		pos = new Point(x,y);
	}

	public void setLastMove(boolean l) {
		this.last = l;
	}

	public char getName() {
		return name;
	}

	public char getColor() {
		return color;
	}

	public boolean hasMoved() {
		return moved;
	}

	public boolean wasLastMove() {
		return last;
	}
	
	/*public Piece copy(){ //TODO add other variables to copy
		Piece p = new Piece(name, color, b);
		p.last = last;
		p.moved = moved;
		return p;
	}*/
	
	public static ArrayList<Point> GetBaseMoves(){
		return new ArrayList<Point>();
	}
	
	public static ArrayList<Point> GetDirection(){
		return new ArrayList<Point>();
	}
	
	public ArrayList<Point> getCapturePoints(){
		ArrayList<Point> capturePoints = new ArrayList<Point>();
		if(baseMoves != null){
			for(Point p : baseMoves){
				Point p2 = new Point(p.x+pos.x, p.y+pos.y);
			  if(p2.x < 0 || p2.x > 7 || p2.y < 0 || p2.y > 7) continue;
				capturePoints.add(p2);
			}
		}
		if(direction != null){
			for(Point p : direction){
				Point p2 = new Point(pos.x, pos.y);
				while(true){
					p2.x += p.x;
					p2.y += p.y;
				  if(p2.x < 0 || p2.x > 7 || p2.y < 0 || p2.y > 7) break;
					capturePoints.add(new Point(p2));
				  if(b.getPiece(p2.x, p2.y) != null) break;
				}
			}
		}
		return capturePoints;
	}
	
	public ArrayList<Point> getLegalMoves(){
		ArrayList<Point> legalMoves = new ArrayList<Point>();
		if(baseMoves != null){
			for(Point p : baseMoves){
				Point p2 = new Point(p.x+pos.x, p.y+pos.y);
			  if(p2.x < 0 || p2.x > 7 || p2.y < 0 || p2.y > 7) continue;
			  if(b.getPiece(p2.x, p2.y) != null && b.getPiece(p2.x, p2.y).color == this.color) continue;
				legalMoves.add(p2);
			}
		}
		if(direction != null){
			for(Point p : direction){
				Point p2 = new Point(pos.x, pos.y);
				while(true){
					p2.x += p.x;
					p2.y += p.y;
				  if(p2.x < 0 || p2.x > 7 || p2.y < 0 || p2.y > 7) break;
				  if(b.getPiece(p2.x, p2.y) != null && b.getPiece(p2.x, p2.y).color == this.color) break;
					legalMoves.add(new Point(p2));
				  if(b.getPiece(p2.x, p2.y) != null && b.getPiece(p2.x, p2.y).color != this.color) break;
				}
			}
		}
		return legalMoves;
	}
	
	public String toString(){
		return ""+name+" "+color;
	}
}