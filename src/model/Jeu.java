package model;
import tools.ChessPiecesFactory;

import java.awt.*;
import java.util.List;
import java.util.LinkedList;

public class Jeu {
    private Couleur color;
    private List<Pieces> pieces;
    private boolean isCastling = false;
    private boolean isCapturePossible = false;

    public Jeu(Couleur couleur) {
        pieces = ChessPiecesFactory.newPieces(couleur);
        color = couleur;
    }
    boolean	capture(int xCatch, int yCatch) {
        boolean capture = false;
        Pieces piece = findPiece(xCatch, yCatch);
        if(piece != null) {
            //TODO: prendre en compte les pièces intermédiaires
            capture = piece.capture();
        }
        return capture;
    }
    Couleur	getCouleur() {
        return color;
    }
    Coord getKingCoord() {
        for(Pieces piece : pieces) {
            if(piece instanceof Roi)
                return new Coord(piece.getX(), piece.getY());
        }
        return new Coord(0, 0);
    }
    Couleur	getPieceColor(int x, int y) {
        Couleur color = Couleur.NOIRBLANC;
        Pieces piece = findPiece(x, y);
        if(piece != null)
            color = piece.getCouleur();
        return color;
    }
    List<PieceIHM> getPiecesIHM() {
        PieceIHM newPieceIHM = null;
        List<PieceIHM> list = new LinkedList<PieceIHM>();

        for (Pieces piece : pieces){
            boolean existe = false;
            // si le type de piece existe déjà dans la liste de PieceIHM
            // ajout des coordonnées de la pièce dans la liste de Coord de ce type
            // si elle est toujours en jeu (x et y != -1)
            for ( PieceIHM pieceIHM : list){
                if ((pieceIHM.getTypePiece()).equals(piece.getClass().getSimpleName())){
                    existe = true;
                    if (piece.getX() != -1){
                        pieceIHM.add(new Coord(piece.getX(), piece.getY()));
                    }
                }
            }
            // sinon, création d'une nouvelle PieceIHM si la pièce est toujours en jeu
            if (! existe) {
                if (piece.getX() != -1){
                    newPieceIHM = new PieceIHM(piece.getClass().getSimpleName(),
                            piece.getCouleur());
                    newPieceIHM.add(new Coord(piece.getX(), piece.getY()));
                    list.add(newPieceIHM);
                }
            }
        }
        return list;
    }
    String getPieceType(int x, int y) {
        String type = "Vide";
        Pieces piece = findPiece(x, y);
        if(piece != null)
            type = piece.getClass().getSimpleName();
        return type;
    }
    boolean	isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
        boolean moveOk = false;
        Pieces piece = findPiece(xInit, yInit);
        if(piece != null)
            moveOk = piece.isMoveOk(xFinal, yFinal);
        return moveOk;
    }
    boolean	isPawnPromotion(int xFinal, int yfinal) {
        boolean ret = false;
        if(color == Couleur.NOIR) {
            if(yfinal == 7)
                ret = true;
        }
        else if (yfinal == 0)
            ret = true;
        return ret;
    }
    boolean	isPieceHere(int x, int y) {
        return findPiece(x, y) != null;
    }
    boolean	move(int xInit, int yInit, int xFinal, int yFinal) {
        boolean moveOk = false;
        Pieces piece = findPiece(xInit, yInit);
        if(piece != null)
            moveOk = piece.move(xFinal, yFinal);
        return moveOk;
    }
    boolean	pawnPromotion(int xFinal, int yfinal, String type) {
        //TODO
        return false;
    }
    void setCastling() {
        isCastling = true;
    }
    void setPossibleCapture() {
        isCapturePossible = true;
    }
    void undoCapture() {
    }
    void undoMove() {
    }
    private Pieces findPiece(int x, int y) {
        Pieces piece = null;
        for(Pieces p : pieces) {
            if(x == p.getX() && y == p.getY()) {
                piece = p;
            }
        }
        return piece;
    }

    @Override
    public String toString() {
        String out = "";
        for(Pieces piece : pieces) {
            out += (piece.toString() + "\n");
        }
        return out;
    }
    public static void main(String[] args) {
        Jeu noir = new Jeu(Couleur.NOIR);
        System.out.println(noir);
        System.out.println(noir.capture(0,0));
        System.out.println(noir);
        System.out.println(noir.getCouleur());
        System.out.println(noir.getKingCoord());
        System.out.println(noir.getPieceColor(0,1));
        System.out.println(noir.getPieceColor(0,2));
        System.out.println(noir.getPieceType(0,1));
        System.out.println(noir.getPieceType(0,2));
        System.out.println(noir.isMoveOk(1,1, 1, 3));
        System.out.println(noir.move(1,1, 1, 3));
        System.out.println(noir);
    }
}
