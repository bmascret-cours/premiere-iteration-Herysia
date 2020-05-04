package model;
import sun.awt.image.ImageWatched;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
public class Echiquier implements BoardGames {

    private Jeu jeuNoir;
    private Jeu jeuBlanc;
    private Jeu jeuCourant;
    private Jeu jeuNonCourant;
    private String message = "";

    public Echiquier() {
        jeuNoir = new Jeu(Couleur.NOIR);
        jeuBlanc = new Jeu(Couleur.BLANC);
        jeuCourant = jeuBlanc;
        jeuNonCourant = jeuNoir;
    }

    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
        boolean ret = true;
        boolean shouldCapture = getPieceColor(xFinal, yFinal) == jeuNonCourant.getCouleur();

        if(isMoveOk(xInit, yInit, xFinal, yFinal)) {
            ret = jeuCourant.move(xInit, yInit, xFinal, yFinal);
        }

        if(shouldCapture && ret) {
            jeuNonCourant.capture(xFinal, yFinal);
        }
        //check si on est en échec
        if(ret && enEchec()) {
            //TODO: undoMove implementation, //For now, it's just a move function without checks /!\store last move, and cancel it properly in lower classes
            //TODO: check if player has lost->check all possible moves, and if no one is possible, player has lost
            //TODO: check after catpure + undoCapture
            System.out.println("Joueur " + jeuCourant.getCouleur() + " en echec, peut être une défaite ?");
            jeuCourant.undoMove(xFinal, yFinal, xInit, yInit);
            if(shouldCapture)
                jeuNonCourant.undoCapture();
            ret = false;
        }

        return ret;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Couleur getColorCurrentPlayer() {
        return jeuCourant.getCouleur();
    }

    @Override
    public Couleur getPieceColor(int x, int y) {
        Couleur color = jeuNoir.getPieceColor(x, y);
        if(color == Couleur.NOIRBLANC)
            color = jeuBlanc.getPieceColor(x, y);
        return color;
    }

    public List<PieceIHM> getPiecesIHM() {
        List<PieceIHM> total = new LinkedList<>();
        total.addAll(jeuCourant.getPiecesIHM());
        total.addAll(jeuNonCourant.getPiecesIHM());
        return total;
    }

    public boolean enEchec() {
        boolean ret = false;
        for(Pieces p : jeuNonCourant.getPieces()) {
            if(p.getX()==-1) continue;
            Coord kingCoord = jeuCourant.getKingCoord();

            //Changement du joueur pour tester l'echec
            switchJoueur();
            if(isMoveOk(p.getX(), p.getY(), kingCoord.x, kingCoord.y)) {
                ret = true;
            }
            //Restauration du joueur courant
            switchJoueur();
            if(ret)
                break;
        }
        return ret;
    }

    public boolean 	isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
        boolean ret = true;
        if(!jeuCourant.isPieceHere(xInit, yInit))
            ret = false;
        else if (xFinal == xInit && yFinal == yInit)
            ret = false;
        else if (xFinal > 7 || xFinal < 0 || yFinal > 7 || yFinal < 0)
            ret = false;
        else if (!jeuCourant.isMoveOk(xInit, yInit, xFinal, yFinal))
            ret = false;
        else if (hasCollision(xInit, yInit, xFinal, yFinal))
            ret = false;

        else if( getPieceColor(xFinal, yFinal) == jeuCourant.getCouleur()) {
            // TODO: handle castling
            ret = false;
        }
        else if (getPieceColor(xFinal, yFinal) == jeuNonCourant.getCouleur()) {
            //prise de pièce
            if(jeuCourant.getPieceType(xInit, yInit).equals("Pion") && xFinal==xInit)
                ret = false;
            else
                ret = true;
        }
        else {
            //Déplacement dans le vide
            if(jeuCourant.getPieceType(xInit, yInit).equals("Pion") && xFinal!=xInit)
                ret = false;
            else
                ret = true;
        }
        return ret;
    }
    private boolean hasCollision(int xInit, int yInit, int xFinal, int yFinal) {
        boolean ret = false;
        if(jeuCourant.getPieceType(xInit, yInit).equals("Cavalier"))//Pas de collisions pour le cavalier
            ret = false;
        else if (yInit == yFinal) {//horizontal
            int sign = Integer.signum(xFinal - xInit);
            for (int x = xInit + sign; x != xFinal; x+=sign) {
                if(getPieceColor( x, yInit) != Couleur.NOIRBLANC) {
                    ret = true;
                    break;
                }
            }
        }
        else if (xInit == xFinal) {//vertical
            int sign = Integer.signum(yFinal - yInit);
            for (int y = yInit + sign; y != yFinal; y+=sign) {
                if(getPieceColor( xInit, y) != Couleur.NOIRBLANC) {
                    ret = true;
                    break;
                }
            }
        }
        else {//diag
            int signX = Integer.signum(xFinal - xInit);
            int signY = Integer.signum(yFinal - yInit);
            int x = xInit;
            for (int y = yInit + signY; y != yFinal; y+=signY) {
                x += signX;
                if(getPieceColor( x, y) != Couleur.NOIRBLANC) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }
    public void switchJoueur() {
        if(jeuCourant.getCouleur() == Couleur.NOIR) {
            jeuCourant = jeuBlanc;
            jeuNonCourant = jeuNoir;
        }
        else {
            jeuCourant = jeuNoir;
            jeuNonCourant = jeuBlanc;
        }
    }


    @Override
    public String toString() {
        return jeuNoir.toString() + "\n" + jeuBlanc.toString();
    }
    public static void main(String[] args) {
        Echiquier eq = new Echiquier();
        System.out.println(eq);
    }

}
