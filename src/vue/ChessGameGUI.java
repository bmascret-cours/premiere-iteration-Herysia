package vue;

import controler.ChessGameControlers;
import controler.controlerLocal.ChessGameControler;
import model.Coord;
import model.Couleur;
import model.PieceIHM;
import tools.ChessImageProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ChessGameGUI extends JFrame implements MouseListener, MouseMotionListener, Observer {

    ChessGameControlers chessGameControlers;
    private JPanel[][] panels;
    Coord initialCoord;

    @Override
    public void mouseClicked(MouseEvent e) {
        if(initialCoord == null) {
            initialCoord = getCoordFromClick(e);
            System.out.println(initialCoord);
            panels[initialCoord.y][initialCoord.x].setBackground(Color.RED);
        }
        else {
            System.out.println(getCoordFromClick(e));
            chessGameControlers.move(initialCoord, getCoordFromClick(e));
            panels[initialCoord.y][initialCoord.x].setBackground((initialCoord.y+initialCoord.x) % 2 == 0 ? Color.BLACK : Color.WHITE);
            initialCoord = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update");
        java.util.List<PieceIHM> piecesIHM = (List<PieceIHM>) arg;
        for(int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                panels[y][x].removeAll();
            }
        }
        for(PieceIHM pieceIHM : piecesIHM) {
            Couleur color = pieceIHM.getCouleur();
            String imgFile = ChessImageProvider.getImageFile(pieceIHM.getTypePiece(), color);
            for(Coord coord : pieceIHM.getList()) {
                JLabel image = new JLabel( new ImageIcon(imgFile));
                panels[coord.y][coord.x].add(image, BorderLayout.CENTER);
                panels[coord.y][coord.x].updateUI();
            }
        }
    }
    public Coord getCoordFromClick(MouseEvent e) {
        return new Coord(e.getX()/(this.getWidth()/8), e.getY()/(this.getHeight()/8));
    }
    public ChessGameGUI(java.lang.String name,
                        ChessGameControlers chessGameControler,
                        java.awt.Dimension boardSize) {
        this.chessGameControlers = chessGameControler;
        setTitle(name);
        setPreferredSize(new Dimension(boardSize));
        panels = new JPanel[8][8];
        for(int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JPanel gameCase = new JPanel();
                gameCase.setLayout(new BorderLayout());
                gameCase.setSize( new Dimension(boardSize.width / 8, boardSize.height / 8));
                gameCase.setLocation(x * boardSize.width / 8, y * boardSize.height / 8);
                gameCase.setBackground((y+x) % 2 == 0 ? Color.BLACK : Color.WHITE);
                panels[y][x] = gameCase;
                add(gameCase);
            }
        }
        //Hotfix
        addMouseListener(this);
        addMouseMotionListener(this);
        add(new JPanel());
    }
}
