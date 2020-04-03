package model;

public class Reine extends AbstractPieces {
    public Reine(Couleur color, Coord coord) {
        super(color, coord);
    }
    public Reine(Couleur color) {
        super(color, new Coord(0,0));
    }
    public Reine() {
        super(Couleur.BLANC, new Coord(0,0));
    }
    @Override
    public boolean isMoveOk(int xFinal, int yFinal) {
        int deltaX = Math.abs(xFinal - this.getX());
        int deltaY = Math.abs(yFinal - this.getY());
        boolean ret = false;

        if(deltaX == 0 && deltaY != 0 || deltaX != 0 && deltaY == 0)//Ligne droite
            ret = true;
        else if(deltaX == deltaY)//Diagonale
            ret = true;
        return ret;
    }
    public static void main(String[] args){
        Pieces maReine = new Reine(Couleur.NOIR, new Coord(5, 0));


        System.out.println(maReine);
        maReine.move(5, 5);
        System.out.println(maReine);
        maReine.move(4,5);
        System.out.println(maReine);
        maReine.move(6,7);
        System.out.println(maReine);
        maReine.move(5,3);
        System.out.println(maReine);
    }
}
