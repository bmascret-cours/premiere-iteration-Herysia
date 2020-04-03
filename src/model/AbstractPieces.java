package model;

public abstract class AbstractPieces implements Pieces {
    private Coord coord;
    private Couleur color;

    public AbstractPieces(Couleur color, Coord coord) {
        this.coord = coord;
        this.color = color;
    }

    public boolean capture() {
        boolean captured = false;
        if(captured) {
            coord.x = coord.y = -1;
        }
        return captured;
    }
    public Couleur getCouleur() {
        return color;
    }
    public int getX() {
        return this.coord.x;
    }
    public int getY() {
        return this.coord.y;
    }

    public abstract boolean isMoveOk(int xFinal, int yFinal);
    public boolean move(int xFinal, int yFinal) {
        boolean moveOk = isMoveOk(xFinal, yFinal);
        if(moveOk) {
            this.coord.x = xFinal;
            this.coord.y = yFinal;
        }
        return moveOk;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +"{" +
                "coord=" + coord +
                '}';
    }
}