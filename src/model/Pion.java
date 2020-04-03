package model;

public class Pion extends AbstractPieces implements Pions{
    private boolean hasMoved = false;
    public Pion(Couleur color, Coord coord) {
        super(color, coord);
    }
    public Pion(Couleur color) {
        super(color, new Coord(0,0));
    }
    public Pion() {
        super(Couleur.BLANC, new Coord(0,0));
    }
    @Override
    public boolean isMoveOk(int xFinal, int yFinal) {
        boolean moveOk = true;
        if(xFinal == this.getX()) {
            int distance = hasMoved?1:2;
            int delta = yFinal - this.getY();
            if (this.getCouleur() == Couleur.BLANC) {
                if (delta >= 0 || Math.abs(delta)>distance)
                    moveOk = false;
            } else {
                if (delta <= 0 || Math.abs(delta)>distance)
                    moveOk = false;
            }
        }
        else {
            moveOk = isMoveDiagOk(xFinal, yFinal);
        }
        return moveOk;
    }
    @Override
    public boolean isMoveDiagOk(int xFinal, int yFinal) {
        //Todo: check if we capture
        boolean moveOk = true;
        if(Math.abs(xFinal - this.getX()) != 1) {
            moveOk = false;
        }
        else {
            int delta = yFinal - this.getY();
            if (this.getCouleur() == Couleur.BLANC) {
                if (delta != -1)
                    moveOk = false;
            } else {
                if (delta != 1)
                    moveOk = false;
            }
        }
        return moveOk;
    }
    @Override
    public boolean move(int xFinal, int yFinal) {
        boolean moveRet = super.move(xFinal, yFinal);
        if(moveRet)
            hasMoved = true;
        return moveRet;
    }
    public static void main(String[] args){

        //Depart double
        Pieces monPion = new Pion(Couleur.NOIR, new Coord(2, 1));
        System.out.println(monPion);
        System.out.println(monPion.getCouleur());
        monPion.move(2,1);//fail
        System.out.println(monPion);
        monPion.move(2,0);//fail
        System.out.println(monPion);
        monPion.move(2,3);
        System.out.println(monPion);

        //depart simple
        Pieces monPion1 = new Pion(Couleur.NOIR, new Coord(2, 1));
        System.out.println(monPion1);
        monPion1.move(2,2);
        System.out.println(monPion1);

        //movement simple
        monPion1.move(2,4);//fail
        System.out.println(monPion1);
        monPion1.move(2,3);
        System.out.println(monPion1);

        //movement diagonale
        monPion1.move(3,3);//fail
        System.out.println(monPion1);
        monPion1.move(3,4);
        System.out.println(monPion1);

    }
}
