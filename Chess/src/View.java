import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class View extends JPanel implements MouseListener, MouseMotionListener{
	public static int TOP_OFFSET = 25;
	public static int LEFT_OFFSET = 25;
	public static int SQUARE_SIZE = 50;
	private static Dimension size = new Dimension(LEFT_OFFSET*2 + SQUARE_SIZE*8, TOP_OFFSET*2 + SQUARE_SIZE*8);

	private static Color COLOR_LIGHTSQUARE = new Color(225,215,150);
	private static Color COLOR_DARKSQUARE = new Color(150,100,50);
	private static Color COLOR_BLUELIGHT = new Color(64,255,255);
	private static Color COLOR_REDLIGHT = new Color(255,64,64);
	private static Color COLOR_BLACK = new Color(0,0,0);
	private static Color COLOR_WHITE = new Color(255,255,255);
	private static Color COLOR_BACKGROUND = new Color(255,0,0);

	private static View s_Instance;
	private static Game g_Instance;
	private Board b;
	private Controller ctrl;
	private Image[] images = new Image[12];
	private Point selected = null;
	private Point mouse = null;
	private int mode = 0;
	private ArrayList<Point> blueLight;
	private ArrayList<Point> redLight;

	public static View getInstance(){
		return s_Instance;
	}
	
	public View(Game g){
		s_Instance = this;
		g_Instance = g;
		ctrl = Controller.getInstance();
		Toolkit toolkit = getToolkit();
		String[] name = {"R","N","B","Q","K","P"};
		String color = "W";
		for(int i = 0; i < 12; i++){
			if(i >= 6)
				color = "B";
			images[i] = toolkit.createImage("img/" + color + name[i%6] + ".png");
			if(images[i] != null)
				images[i] = Transparency.makeColorTransparent(images[i], COLOR_BACKGROUND);
		}
	}
	
	public void SetMode(int n){
		mode = n;
	}

	public void RefreshView(Board b){
		this.b = b;
		redLight = ctrl.getEnemyPlayer().getCapturePoints();
		repaint();
	}

	public void update(Graphics g){
		paint(g);
	}

	public void paint(Graphics g)
	{
		g.setColor(COLOR_WHITE);
		g.fillRect(0, 0, size.width, size.height);
		g.translate(LEFT_OFFSET, TOP_OFFSET);
		DrawBoard(g);
		b = Controller.getInstance().b;
		if(b != null)
			DrawPieces(g);
	}

	private void DrawPieces(Graphics g){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
				Piece p = b.getPiece(i,j);
				if(p != null){
					if(selected != null)
						if(selected.x == i && selected.y == j)
							continue;
					if(mode == 0){
						g.drawImage(getImage(p), i*SQUARE_SIZE, j*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, this);
					}
					if(mode == 1){
						g.drawImage(getImage(p), (7-j)*SQUARE_SIZE, i*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, this);
					}
				}
			}
		if(selected != null){
			Piece p = b.getPiece(selected.x,selected.y);
			g.drawImage(getImage(p), mouse.x-SQUARE_SIZE/2, mouse.y-SQUARE_SIZE/2, SQUARE_SIZE, SQUARE_SIZE, this);
		}
	}
	
	private Image getImage(Piece p){
		if(p == null)
			return null;
		Image img = null;
		char n = p.getName();
		char c = p.getColor();
		char[] n2 = {'r','n','b','q','k','p'};
		for(int k = 0; k < 6; k++)
			if(n2[k] == n){
				if(c == 'w')
					img = images[k];
				else
					img = images[k+6];
			}
		return img;
	}
	
	private boolean IsBlueLight(Point p){
		return blueLight != null && blueLight.contains(p);
	}
	
	private boolean IsBlueLight(int x, int y){
		return IsBlueLight(new Point(x,y));
	}
	
	private boolean IsRedLight(Point p){
		return redLight != null && redLight.contains(p);
	}
	
	private boolean IsRedLight(int x, int y){
		return IsRedLight(new Point(x,y));
	}

	private void DrawBoard(Graphics g){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				DrawSquare(g,i,j);
	}

	private void DrawSquare(Graphics g, int i, int j){
		boolean light = (i+j)%2 == 1;
		boolean redLight = false;
		boolean blueLight = false;
		if(mode == 0 && IsBlueLight(i,j) || mode == 1 && IsBlueLight(j,7-i))
			blueLight = true;
		if(mode == 0 && IsRedLight(i,j) || mode == 1 && IsRedLight(j,7-i))
			redLight = true;
		if(redLight){
			g.setColor(COLOR_REDLIGHT);
			g.fillRect(SQUARE_SIZE*i, SQUARE_SIZE*j, SQUARE_SIZE, SQUARE_SIZE);
		}
		if(blueLight){
			g.setColor(COLOR_BLUELIGHT);
			g.fillRect(SQUARE_SIZE*i, SQUARE_SIZE*j, SQUARE_SIZE, SQUARE_SIZE);
		}
		if(light)
			g.setColor(COLOR_LIGHTSQUARE);
		else
			g.setColor(COLOR_DARKSQUARE);
		if(redLight || blueLight)
			g.fillRect((int)(SQUARE_SIZE*i+SQUARE_SIZE*0.1), (int)(SQUARE_SIZE*j+SQUARE_SIZE*0.1), (int)(SQUARE_SIZE*0.8)+1, (int)(SQUARE_SIZE*0.8)+1);
		else
			g.fillRect(SQUARE_SIZE*i, SQUARE_SIZE*j, SQUARE_SIZE, SQUARE_SIZE);
		g.setColor(COLOR_BLACK);
		g.drawRect(SQUARE_SIZE*i, SQUARE_SIZE*j, SQUARE_SIZE, SQUARE_SIZE);
		
	}

	public void SetSize(){
		setPreferredSize(size);
	}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		mouse = new Point(x-LEFT_OFFSET, y-TOP_OFFSET);
		Point l = getIndexFromLocation(x,y);
		if(l == null)
			return;
		int i = l.x;
		int j = l.y;
		Piece p = b.getPiece(i,j);
		if(p == null)
			return;
		if(p.getColor() == ctrl.getTurn())
			Select(i,j);
		p.pos = new Point(i,j);
		blueLight = p.getLegalMoves();
		repaint();
	}
	
	public void mouseReleased(MouseEvent arg0) {
		if(selected == null){
			blueLight = null;
			repaint();
			return;
		}
		Point l = getIndexFromLocation(arg0.getX(),arg0.getY());
		if(l != null && selected.x != l.x || selected.y != l.y){
			if(IsBlueLight(l.x, l.y)){
				b.Move(selected.x, selected.y, l.x, l.y);
				ctrl.endTurn();
				redLight = ctrl.getEnemyPlayer().getCapturePoints();
			}
			else{
				g_Instance.print("Invalid Move");
			}
		}
				/*try {
					ctrl.Move(selected.x, selected.y, l.x, l.y);
				} catch (Exception e) {
					g_Instance.print(e.getMessage());
				}*/
		selected = null;
		blueLight = null;
		repaint();
	}

	public void mouseDragged(MouseEvent arg0) {
		mouse = new Point(arg0.getX()-LEFT_OFFSET, arg0.getY()-TOP_OFFSET);
		if(selected != null){
			repaint();
		}
	}
	
	public Point getIndexFromLocation(int x, int y){
		if(x < LEFT_OFFSET || y < TOP_OFFSET)
			return null;
		int i = (x - LEFT_OFFSET)/SQUARE_SIZE;
		int j = (y - TOP_OFFSET)/SQUARE_SIZE;
		if(i >= 8 || j >= 8)
			return null;
		if(mode == 1){
			int t = j;
			j = 7-i;
			i = t;
		}
		return new Point(i,j);
	}
	
	public char getPromotionValue(){
		String s = null;
		String[] pieces = {"Queen", "Rook", "Bishop", "Knight"};
		char[] pieces2 = {'q','r','b','n'};
		while(s == null)
			s = (String) JOptionPane.showInputDialog(this, "Which do you want?", "Choose your prize", JOptionPane.QUESTION_MESSAGE, null ,pieces, "Queen");
		for(int i = 0; i < 4; i++)
			if(s.equals(pieces[i]))
				return pieces2[i];
		return 'q';
	}
	
	public void Select(int i, int j){
		selected = new Point(i,j);
		repaint();
	}
}

class Transparency {
	public static Image makeColorTransparent (Image im, final Color color) {
		ImageFilter filter = new RGBImageFilter() {
			// the color we are looking for... Alpha bits are set to opaque
			public int markerRGB = color.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ( ( rgb | 0xFF000000 ) == markerRGB ) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				}
				else {
					// nothing to do
					return rgb;
				}
			}
		}; 

		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}
}
