package model;

public class Tour extends AbstractPieces {
    public Tour(Couleur color, Coord coord) {
        super(color, coord);
    }
    public Tour(Couleur color) {
        super(color, new Coord(0,0));
    }
    public Tour() {
        super(Couleur.BLANC, new Coord(0,0));
    }
    @Override
    public boolean isMoveOk(int xFinal, int yFinal) {
        return xFinal == this.getX() ^ yFinal == this.getY();
    }
    public static void main(String[] args){
        Pieces maTour = new Tour(Couleur.NOIR, new Coord(5, 0));
        Pieces maTour1 = new Tour();


        System.out.println(maTour);
        System.out.println(maTour1);

        System.out.println(maTour.getCouleur());
        System.out.println(maTour1.getCouleur());
        maTour.move(10,1);
        System.out.println(maTour);
        maTour.move(10,0);
        System.out.println(maTour);
        maTour.move(10,1);
        System.out.println(maTour);
    }
}
