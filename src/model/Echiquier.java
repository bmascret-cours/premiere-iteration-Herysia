package model;
import java.awt.*;
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
        jeuCourant = jeuNoir;
        jeuNonCourant = jeuBlanc;
    }

    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
        return false;
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
        return null;
    }

    @Override
    public Couleur getPieceColor(int x, int y) {
        Couleur color = jeuNoir.getPieceColor(x, y);
        if(color == Couleur.NOIRBLANC)
            color = jeuBlanc.getPieceColor(x, y);
        return color;
    }

    public List<PieceIHM> getPiecesIHM() {
        return null;
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
        /*
        * TODO:
        *  s'il existe une piéce positionnées aux coordonnées finales :
        *  si elle est de la méme couleur --> false ou tentative roque du roi,
        *  sinon : prendre la piéce intermédiaire (vigilance pour le cas du pion) et déplacer la piéce -->true,
        *  sinon déplacer la piéce -->true
        * */
        return ret;
    }
    private boolean hasCollision(int xInit, int yInit, int xFinal, int yFinal) {
        boolean ret = false;
        if(jeuCourant.getPieceType(xInit, yInit).equals("Cavalier"))
            ret = false;
        else if (xInit == xFinal) {//horizontal
            int sign = Integer.signum(xFinal - xInit);
            for (int x = xInit + sign; x != xFinal; x+=sign) {
                if(getPieceColor( x, yInit) != Couleur.NOIRBLANC) {
                    ret = true;
                    break;
                }
            }
        }
        else if (yInit == yFinal) {//vertical
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
            int x = xInit + signX;
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
