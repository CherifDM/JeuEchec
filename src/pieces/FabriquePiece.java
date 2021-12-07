package pieces;

import plateau.IFabriquePiece;
import plateau.IPieces;

public class FabriquePiece implements IFabriquePiece{
	public IPieces creerPiece(char c) {
		///Interface Fabrique Pieces
		if (c == 'r')
          return new Roi(Couleur.NOIR);

      else if (c == 'R')
          return new Roi(Couleur.BLANC);

      else if (c == 't')
          return new Tour(Couleur.NOIR);

      else if (c == 'T')
          return new Tour(Couleur.BLANC);
		
      else if (c == 'f')
          return new Fou(Couleur.NOIR);

      else if (c == 'F')
          return new Fou(Couleur.BLANC);
		
      else if (c == 'd')
          return new Dame(Couleur.NOIR);

      else if (c == 'D')
          return new Dame(Couleur.BLANC);
		
      else if (c == 'p')
          return new Pion(Couleur.NOIR);

      else if (c == 'P')
          return new Pion(Couleur.BLANC);
		
		return null;
	}



}
