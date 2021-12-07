package pieces;

import application.Coordonees;
import plateau.IPieces;
import plateau.Plateau;

public class Tour extends Pieces{
	
	public Tour(Couleur c) {
		super(c);
	}
	
	public boolean interruptible () {
		return true;	
	}
	
	public boolean inPath(Coordonees src,Coordonees dest) {
		return (src.getCoordx()==dest.getCoordx()||src.getCoordy()==dest.getCoordy());		
	}
	
	public String getLettre() {
		if (super.getColor() == Couleur.BLANC)
				return "T";
		else return "t";
	}

	public boolean briser(Plateau plat, Coordonees src, Coordonees dest, boolean jouable, IPieces p) {
		if(!(p == this)) {
			return false;
		}
		return false;
	}

}
