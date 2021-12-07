package plateau;

import application.Coordonees;
import pieces.Couleur;

/**
 * @author alhas
 *
 */
public interface IPieces {
	
	/** Setter de couleur pour la piece 
	 * @param Couleur
	 */
	public void setCouleur(Couleur c);
	
	/**
	 * @return la couleur de la piece 
	 */
	public Couleur getColor();

	/**
	 * @return la lettre associé a la piece
	 */
	public  String getLettre();

	/** Determine si des coordonees de destinations sont dans le chemin de la piece a partir des coordonees de source
 	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @return
	 */
	public  boolean inPath(Coordonees src, Coordonees dest);

	/**Renvoie si le chemin d'une piece peut etre interrompue 
	 * @return
	 */
	public  boolean interruptible();
	
	/**Determine si les regles de jeu pour la piece sont briser par un mouvement donnees
	 * @param plat Plateau
 	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @param jouable Boolean de jouabilité
	 * @param p Piece
	 * @return
	 */
	public boolean briser(Plateau plat, Coordonees src, Coordonees dest, boolean jouable, IPieces p);
}
