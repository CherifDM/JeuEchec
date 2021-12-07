package pieces;

import application.Coordonees;
import plateau.IPieces;
import plateau.Plateau;

public abstract class Pieces implements IPieces{
	String lettre;
	Couleur color;
	
	public Pieces(Couleur c) {
		color = c;
	}
	
	public void setCouleur(Couleur c) {
		 color = c;	
	}
	
	public Couleur getColor() {
		return color;		
	}

	public abstract String getLettre();


	public abstract boolean inPath(Coordonees src, Coordonees dest);


	public abstract boolean interruptible();
	

	public abstract boolean briser(Plateau plat, Coordonees src, Coordonees dest, boolean jouable, IPieces p);
	
}
