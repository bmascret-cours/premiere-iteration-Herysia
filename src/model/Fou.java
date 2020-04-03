package model;

public class Fou extends AbstractPieces {
    public Fou(Couleur color, Coord coord) {
        super(color, coord);
    }
    public Fou(Couleur color) {
        super(color, new Coord(0,0));
    }
    public Fou() {
        super(Couleur.BLANC, new Coord(0,0));
    }
    @Override
    public boolean isMoveOk(int xFinal, int yFinal) {
        return Math.abs(xFinal - this.getX()) ==  Math.abs(yFinal - this.getY());
    }
    public static void main(String[] args){
        Pieces monFou = new Fou(Couleur.NOIR, new Coord(2, 0));


        System.out.println(monFou);
        monFou.move(2,1);
        System.out.println(monFou);
        monFou.move(3,1);
        System.out.println(monFou);
        monFou.move(1,3);
        System.out.println(monFou);
    }
}
