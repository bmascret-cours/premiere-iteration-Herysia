package model;
import java.util.List;
public class Echiquier implements BoardGames {

    public Echiquier() {
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
        return null;
    }

    @Override
    public Couleur getColorCurrentPlayer() {
        return null;
    }

    @Override
    public Couleur getPieceColor(int x, int y) {
        return null;
    }

    public List<PieceIHM> getPiecesIHM() {
        return null;
    }

    public boolean 	isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
        return false;
    }

    public void switchJoueur() {

        return;
    }

    @Override
    public String toString() {
        return "Echiquier{}";
    }
}
