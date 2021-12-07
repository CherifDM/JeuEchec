package plateau;

/**
 * @author alhas
 *
 */
public interface IFabriquePiece {
	/** Retourne une piece determiner a partir d'une caractere
	 * @param c Caract�re d'entre
	 * @return
	 */
	IPieces creerPiece(char c);
	
//	IPieces creerPiece(char c, Plateau p);
}
