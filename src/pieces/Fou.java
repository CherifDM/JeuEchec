package pieces;

import application.Coordonees;
import plateau.IPieces;
import plateau.Plateau;

public class Fou extends Pieces{

	public Fou(Couleur c) {
		super(c);
	}

	@Override
	public String getLettre() {
		if (super.getColor() == Couleur.BLANC)
			return "F";
		else return "f";
		
	}

	@Override
	public boolean inPath(Coordonees src, Coordonees dest) {
		return(Math.abs(src.getCoordx()-dest.getCoordx())== Math.abs(src.getCoordy()-dest.getCoordy()));
	}

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
