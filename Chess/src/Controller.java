import java.lang.Math;
import java.awt.Point;

public class Controller{
	private char turn;
	private static Controller s_Instance = null;
	public Player p1 = new Player('w');
	public Player p2 = new Player('b');
	public Board b = new Board(p1, p2);
	//public Board prevB = new Board();
	//private static ArrayList<Board> prevBoards = new ArrayList<Board>();
	public boolean gameHasStarted = false;
	
	private Controller() {}

	public char getTurn() {
		return this.turn;
	}
	
	public static Controller getInstance() {
		if (s_Instance == null)
			s_Instance = new Controller();
		return s_Instance;
	}

	// Only for rook, bishop, queen and pawn (on its first move):
	// Detect if there exists a blocking piece in between the current location and destination.
	/*private boolean hasBlockingPiece(int x1, int y1, int x2, int y2) {
		// Moving diagonally.
		if (x1 != x2 && y1 != y2 && (Math.abs(x1 - x2) == Math.abs(y1 - y2))) {
			if(x1-x2 == y1-y2){// Moving like \
				for(int i = 1; i < Math.abs(x2-x1); i++)
					if (b.getPiece(Math.min(x1, x2)+i, Math.min(y1, y2)+i) != null)
						return true;
			}
			else// Moving like /
				for(int i = 1; i < Math.abs(x2-x1); i++)
					if (b.getPiece(Math.min(x1, x2)+i, Math.max(y1, y2)-i) != null)
						return true;
			return false;
		}
		// Moving vertically or horizontally.
		else if (x1 != x2 ^ y1 != y2) {
			if (x1 == x2) {
				if (Math.abs(y1 - y2) > 1) {
					for (int i = Math.min(y1, y2) + 1; i < Math.max(y1, y2); i++)
						if (b.getPiece(x1, i) != null)
							return true;
				} else
					return false;
			}
			else {
				if (Math.abs(x1 - x2) > 1) {
					for (int i = Math.min(x1, x2) + 1; i < Math.max(x1, x2); i++)
						if (b.getPiece(i, y1) != null)
							return true;
				} else
					return false;
			}
			return false;
		}
		// Invalid input. Should never get here.
		else
			return false;

	}

	public String isMoveValid(int x1, int y1, int x2, int y2, boolean check) {
		Piece p1 = b.getPiece(x1, y1);
		Piece p2 = b.getPiece(x2, y2);

		// First check if all coordinates are within bound.
		if (!(x1 > -1 && y1 > -1 && x2 > -1 && y2 > -1 && x1 < 8 && y1 < 8 && x2 < 8 && y2 < 8))
			return "ERROR: Chess piece out of board.";
		if(x1 == x2 && y1 == y2)
			return "Invalid Move: Piece didn't move anywhere";
		if (p1 == null)
			return "ERROR: No piece at that location";

		if (p2 != null)
			if (p2.getColor() == p1.getColor())
				return "Invalid Move: Can't take your own piece";

		if (p1.getName() == 'r') {
			if (x1 != x2 ^ y1 != y2) {
				if (hasBlockingPiece(x1, y1, x2, y2))
					return "Invalid move: rook cannot leap over pieces.";
			} else
				return "Invalid move: rook can only move in straight lines.";
		} else if (p1.getName() == 'n') {
			if (!(Math.abs(x1 - x2) + Math.abs(y1 - y2) == 3 && x1 != x2 && y1 != y2))
				return "Invalid move: knight can only move in an L shape.";
		} else if (p1.getName() == 'b') {
			if (x1 != x2 && y1 != y2 && (Math.abs(x1 - x2) == Math.abs(y1 - y2))) {
				if (hasBlockingPiece(x1, y1, x2, y2))
					return "Invalid move: bishop cannot leap over pieces.";
			} else
				return "Invalid move: bishop can only move in diagonal.";
		} else if (p1.getName() == 'q') {
			if ((x1 != x2 ^ y1 != y2) || (x1 != x2 && y1 != y2 && (Math.abs(x1 - x2) == Math.abs(y1 - y2)))) {
				if (hasBlockingPiece(x1, y1, x2, y2))
					return "Invalid move: qeen cannot leap over pieces.";
			} else
				return "Invalid move: queen can only move in straight lines or diagonally.";
		} else if (p1.getName() == 'p') {
			if(p2 == null){
				if(p1.getColor() == 'w'){
					if (x1 == 3 && x2 == x1-1 && (y2 == y1-1 || y2 == y1+1)){
						Piece n = b.getPiece(x1, y2);
						if (n != null && n.getName() == 'p' && n.getColor() == 'b' && n.wasLastMove())
							return "En Passant";
					}
					if (!p1.hasMoved() && x2 == x1 - 2 && y1 == y2) {
						if (hasBlockingPiece(x1, y1, x2, y2))
							return "Invalid move: pawn cannot advance two squares if intermediate square is occupied.";
					}
					else if(x2 != x1-1 || y2 != y1)
						return "Invalid move: pawn can move in straight lines.";
				}
				else{
					if (x1 == 4 && x2 == x1+1 && (y2 == y1-1 || y2 == y1+1)){
						Piece n = b.getPiece(x1, y2);
						if (n != null && n.getName() == 'p' && n.getColor() == 'w' && n.wasLastMove())
							return "En Passant";
					}

					if (!p1.hasMoved() && x2 == x1 + 2 && y1 == y2) {
						if (hasBlockingPiece(x1, y1, x2, y2))
							return "Invalid move: pawn cannot advance two squares if intermediate square is occupied.";
					}
					else if(x2 != x1+1 || y2 != y1)
						return "Invalid move: pawn can move in straight lines.";
				}
			}
			else{
				if(p1.getColor() == 'w'){
					if(x2 != x1-1 || (y2 != y1-1 && y2 != y1+1))
						return "Invalid move: pawn can only capture in diagonals.";
				}
				else{
					if(x2 != x1+1 || (y2 != y1-1 && y2 != y1+1))
						return "Invalid move: pawn can only capture in diagonals.";
				}
			}
		} else { // p.getName() == 'k'
			if(!p1.hasMoved() && x1 == x2 && !inCheck(new Point(x1,3),p1.getColor())){//if on the same row and the king is not in check and hasn't moved
				if(y2 == 5){//queen side castle
					Piece rook = b.getPiece(x2,7);
					if(rook.getName() == 'r' && rook.getColor() == b.getPiece(x1,y1).getColor())//if the rook is there
						if(!hasBlockingPiece(x1,y1,x2,y2))//if the spaces in between are free
							if(!inCheck(new Point(x1,4),p1.getColor()) && !inCheck(new Point(x1,5),p1.getColor()))//and the king would not be moving through check
								return "Queen Side Castle";
						
				}
				if(y2 == 1){//king side castle
					Piece rook = b.getPiece(x2,0);
					if(rook.getName() == 'r' && rook.getColor() == b.getPiece(x1,y1).getColor())//if the rook is there
						if(!hasBlockingPiece(x1,y1,x2,y2))//if the spaces in between are free
							if(!inCheck(new Point(x1,2),p1.getColor()) && !inCheck(new Point(x1,1),p1.getColor()))//and the king would not be moving through check
								return "King Side Castle";
				}
			}
			if (!(Math.abs(x1 - x2) < 2 && Math.abs(y1 - y2) < 2))
				return "Invalid move: king can only move one square in any direction.";
		}
		
		if(check)
			if(isKingInCheck(x1,y1,x2,y2, p1.getColor()))
				return "Cannot put king into check";
		
		return "";
	}*/
	
	private boolean isKingInCheck(int x1, int y1, int x2, int y2, char turn){ //TODO
		/*prevB.copyBoard(b);
		b.Move(x1,y1,x2,y2);
		if(inCheck(kingLocation(turn), turn)){
			b.copyBoard(prevB);
			return true;
		}
		b.copyBoard(prevB);*/
		return false;
	}

	/*public void Move(int x1, int y1, int x2, int y2) throws Exception{
		String error = isMoveValid(x1,y1,x2,y2, true);
		if(error.equals("Queen Side Castle"))
			b.Move(x1,7,x2,4);
		else if(error.equals("King Side Castle"))
			b.Move(x1,0,x2,2);
		else if(error.equals("En Passant"))
			b.SetPiece(x1,y2,null);
		else if(!error.equals(""))
			throw new Exception(error);
		
		b.Move(x1,y1,x2,y2);
		
		Piece p = b.getPiece(x2, y2);
		if(p.getName() == 'p'){
			if(p.getColor() == 'w' && x2 == 0){
				Piece p2 = new Piece(View.getInstance().getPromotionValue(), turn, b);
				b.SetPiece(x2,y2,p2);
			}
			if(p.getColor() == 'b' && x2 == 7){
				Piece p2 = new Piece(View.getInstance().getPromotionValue(), turn, b);
				b.SetPiece(x2,y2,p2);
			}
		}
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
				Piece p2 = b.getPiece(i,j);
				if(p2 != null && p2.getColor() == turn)
					p2.setLastMove(false);
			}
		p.setLastMove(true);
					
		turn = oppositePlayer(turn);
		if (!possibleMove(turn)) {
			boolean b = inCheck(kingLocation(turn), turn);
			turn = 'd';
			if(b)
				throw new Exception("CheckMate!");
			else
				throw new Exception("StaleMate!");
		}
		else
			if(inCheck(kingLocation(turn), turn))
				throw new Exception("Check");
	}
	
	private Point kingLocation(char c){
		int kingX = -1, kingY = -1;
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
				Piece p = b.getPiece(i,j);
				if(p != null && p.getColor() == c && p.getName() == 'k'){
					kingX = i;
					kingY = j;
				}
			}
		return new Point(kingX, kingY);
	}
	
	private boolean inCheck(Point pnt, char c){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
				Piece p = b.getPiece(i,j);
				if(p != null && p.getColor() == oppositePlayer(c)){
					if(isMoveValid(i,j,pnt.x,pnt.y, false).equals(""))
						return true;
				}
			}
		return false;
	}
	
	public boolean possibleMove(char c){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
				Piece p = b.getPiece(i,j);
				if(p != null && p.getColor() == c){
					for(int k = 0; k < 8; k++)
						for(int l = 0; l < 8; l++)
							if(isMoveValid(i,j,k,l, true).equals("") || isMoveValid(i,j,k,l, true).equals("En Passant"))
								return true;
				}
			}
		return false;
	}*/
	
	public void endTurn(){
		turn = oppositePlayer(turn);
	}
	
	public char oppositePlayer(char c){
		if(c == 'w')
			return 'b';
		return 'w';
	}
	
	public Player getCurrentPlayer(){
		if(turn == 'w')
			return p1;
		return p2;
	}
	
	public Player getEnemyPlayer(){
		if(turn == 'w')
			return p2;
		return p1;
	}
	
	public void startNewGame() {
		gameHasStarted = true;
		b.initBoard();
		turn = 'w';
		View.getInstance().RefreshView(b);
	}
}
