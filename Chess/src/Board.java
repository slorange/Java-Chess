import java.awt.Point;
import java.util.ArrayList;

public class Board {
	private Piece[][] board = new Piece[8][8];
	Player p1,p2;
	
	public Board(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		initBoard();
	}
	
	public void initBoard() {
		//char[] s = {'r','n','b','k','q','b','n','r'};
		board[0][0] = new Rook('r','b', this, 0, 0);
		board[0][7] = new Rook('r','b', this, 0, 7);
		board[0][1] = new Knight('n','b', this, 0, 1);
		board[0][6] = new Knight('n','b', this, 0, 6);
		board[0][2] = new Bishop('b','b', this, 0, 2);
		board[0][5] = new Bishop('b','b', this, 0, 5);
		board[0][4] = new Queen('q','b', this, 0, 4);
		board[0][3] = new King('k','b', this, 0, 3);
		board[7][0] = new Rook('r','w', this, 7, 0);
		board[7][7] = new Rook('r','w', this, 7, 7);
		board[7][6] = new Knight('n','w', this, 7, 6);
		board[7][1] = new Knight('n','w', this, 7, 1);
		board[7][2] = new Bishop('b','w', this, 7, 2);
		board[7][5] = new Bishop('b','w', this, 7, 5);
		board[7][4] = new Queen('q','w', this, 7, 4);
		board[7][3] = new King('k','w', this, 7, 3);
		for(int i = 0; i < 8; i++){
			board[1][i] = new Pawn('p','b', this, 1, i);
			p2.AddPiece(board[1][i]);
			p2.AddPiece(board[0][i]);
			board[6][i] = new Pawn('p','w', this, 6, i);
			p1.AddPiece(board[6][i]);
			p1.AddPiece(board[7][i]);
		}
	}
	
	public void Move(int x1, int y1, int x2, int y2){
		Piece p = board[x1][y1];
		Move2(x1, y1, x2, y2);
		if(p.name == 'k' && Math.abs(y1 - y2) > 1){
			if(y2 == 7){
				Move2(x1, 7, x2, 4);
			}
			if(y2 == 0){
				Move2(x1, 0, x2, 2);
			}
		}
	}
	
	private void Move2(int x1, int y1, int x2, int y2){
		board[x2][y2] = board[x1][y1];
		board[x2][y2].Move(x2,y2);
		board[x1][y1] = null;
	}
	
	public Piece getPiece(Point p) {
		if(!onBoard(p)) return null;
		return board[p.x][p.y];
	}
	
	public boolean onBoard(Point p){
		if(p.x > 7 || p.x < 0)
			return false;
		if(p.y > 7 || p.y < 0)
			return false;
		return true;
	}
	
	public Piece getPiece(int x, int y) {
		return board[x][y];
	}
	
	public void SetPiece(int x, int y, Piece p){
		board[x][y] = p;
	}
	
	/*public void copyBoard(Board b2){
		board = new Piece[8][8];
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
				Piece p = b2.getPiece(i, j);
				if(p != null)
					board[i][j] = p.copy();
				else
					board[i][j] = null;
			}
	}*/
	
	public void testPrint(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(getPiece(i,j) == null)
					System.out.print("-");
				else
					System.out.print(getPiece(i,j).getName());
			}
			System.out.println();
		}
		System.out.println();
	}
}
