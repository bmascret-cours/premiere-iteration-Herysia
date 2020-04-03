package model;

public class Cavalier extends AbstractPieces {
    public Cavalier(Couleur color, Coord coord) {
        super(color, coord);
    }
    public Cavalier(Couleur color) {
        super(color, new Coord(0,0));
    }
    public Cavalier() {
        super(Couleur.BLANC, new Coord(0,0));
    }
    @Override
    public boolean isMoveOk(int xFinal, int yFinal) {
        int deltaX = Math.abs(xFinal - this.getX());
        int deltaY = Math.abs(yFinal - this.getY());
        return deltaX + deltaY == 3 && (deltaX == 1 || deltaY == 1 );
    }
    public static void main(String[] args){
        Pieces monCheval = new Cavalier(Couleur.NOIR, new Coord(1, 0));


        System.out.println(monCheval);
        monCheval.move(1, 3);
        System.out.println(monCheval);
        monCheval.move(4,0);
        System.out.println(monCheval);
        monCheval.move(2,2);
        System.out.println(monCheval);
        monCheval.move(0,3);
        System.out.println(monCheval);
    }
}
