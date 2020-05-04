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
    private JPanel mouseDrawer;
    private Coord initialCoord;
    private Coord mouseDelta;
    private java.util.List<PieceIHM> piecesIHM;
    private String dragImgFile;
    @Override
    public void mouseClicked(MouseEvent e) {
        /*
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
        }*/
    }

    @Override
    public void mousePressed(MouseEvent e) {
        initialCoord = getCoordFromClick(e);
        if(!chessGameControlers.isPlayerOK(initialCoord)) {
            initialCoord = null;
            return;
        }
        System.out.println(initialCoord);

        dragImgFile = getImgFileByCoord(getCoordFromClick(e));
        panels[initialCoord.y][initialCoord.x].setBackground(Color.RED);
        panels[initialCoord.y][initialCoord.x].removeAll();
        mouseDelta = new Coord(panels[initialCoord.y][initialCoord.x].getX()-e.getX(), panels[initialCoord.y][initialCoord.x].getY()-e.getY());
        revealPossibleTargets(initialCoord);
        drawMousePanel(e);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if(initialCoord == null)
            return;
        System.out.println(getCoordFromClick(e));
        chessGameControlers.move(initialCoord, getCoordFromClick(e));
        panels[initialCoord.y][initialCoord.x].setBackground((initialCoord.y+initialCoord.x) % 2 == 0 ? Color.BLACK : Color.WHITE);
        initialCoord = null;


        updatePanels();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(initialCoord == null)
            return;
        drawMousePanel(e);
    }


    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update");
        piecesIHM = (List<PieceIHM>) arg;
        updatePanels();
    }

    private void clearPanels() {
        mouseDrawer.removeAll();
        mouseDrawer.updateUI();
        for(int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                panels[y][x].removeAll();
                panels[y][x].updateUI();
                panels[y][x].setBackground((y+x) % 2 == 0 ? Color.BLACK : Color.WHITE);
            }
        }
    }
    private void drawPanels() {
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
    private void drawMousePanel(MouseEvent e) {
        mouseDrawer.removeAll();
        mouseDrawer.updateUI();
        JLabel image = new JLabel( new ImageIcon(dragImgFile));
        mouseDrawer.setLocation(mouseDelta.x + e.getX(), mouseDelta.y + e.getY());
        mouseDrawer.add(image, BorderLayout.CENTER);
        mouseDrawer.updateUI();
    }

    private void updatePanels() {
        clearPanels();
        drawPanels();
    }
    private String getImgFileByCoord(Coord c) {
        for(PieceIHM pieceIHM : piecesIHM) {
            for(Coord coord : pieceIHM.getList()) {
                if(coord.x == c.x && coord.y == c.y) {
                    return ChessImageProvider.getImageFile(pieceIHM.getTypePiece(), pieceIHM.getCouleur());
                }
            }
        }
        return null;
    }

    private void revealPossibleTargets(Coord c) {
        for(int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if(chessGameControlers.isMoveOk(c, new Coord(x, y))) {
                    panels[y][x].setBackground(Color.blue);
                }
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
        mouseDrawer = new JPanel();
        mouseDrawer.setOpaque(false);
        mouseDrawer.setLayout(new BorderLayout());
        mouseDrawer.setSize( new Dimension(boardSize.width / 8, boardSize.height / 8));
        add(mouseDrawer);
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
        addMouseListener(this);
        addMouseMotionListener(this);



        //Hotfix
        add(new JPanel());
    }
}
