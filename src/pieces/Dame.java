package pieces;

import application.Coordonees;
import plateau.IPieces;
import plateau.Plateau;

public class Dame extends Pieces {

	public Dame(Couleur c) {
		super(c);
	}


	@Override
	public String getLettre() {
		if (super.getColor() == Couleur.BLANC)
			return "D";
		else return "d";
	}

	@Override
	public boolean inPath(Coordonees src, Coordonees dest) {
//		Fou f = new Fou(color);
//		Tour t = new Tour(color);
//		return(f.inPath(src, dest)||t.inPath(src, dest));
		int sX = src.getCoordx();
		int sY = src.getCoordy();
		int dX = dest.getCoordx();
		int dY = dest.getCoordy();
		return (  ((sX==dX) || (sY == dY))) || (Math.abs(sX-dX) == Math.abs(sY - dY));
	}

	@Override
	public boolean interruptible() {
		return true;
	}

	@Override
	public boolean briser(Plateau plat, Coordonees src, Coordonees dest, boolean jouable, IPieces p) {
		if(!(p == this)) {
			return false;
		}
		return false;
	}

}
