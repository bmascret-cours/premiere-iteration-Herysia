package model;

public class Roi extends AbstractPieces {
    public Roi(Couleur color, Coord coord) {
        super(color, coord);
    }
    public Roi(Couleur color) {
        super(color, new Coord(0,0));
    }
    public Roi() {
        super(Couleur.BLANC, new Coord(0,0));
    }
    @Override
    public boolean isMoveOk(int xFinal, int yFinal) {
        int deltaX = Math.abs(xFinal - this.getX());
        int deltaY = Math.abs(yFinal - this.getY());
        return deltaX <= 1 && deltaY <= 1;
    }
    public static void main(String[] args){
        Pieces monRoi = new Roi(Couleur.NOIR, new Coord(1, 0));


        System.out.println(monRoi);
        monRoi.move(3, 0);
        System.out.println(monRoi);
        monRoi.move(2,0);
        System.out.println(monRoi);
        monRoi.move(2,2);
        System.out.println(monRoi);
        monRoi.move(2,1);
        System.out.println(monRoi);
        monRoi.move(3,2);
        System.out.println(monRoi);
        monRoi.move(5,4);
        System.out.println(monRoi);
    }
}
