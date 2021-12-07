package application;

import pieces.Couleur;

public interface Joueur {

	/**
	 * @return la couleur associé au joueur
	 */
	public  Couleur getCouleur();
	
	
	/**Exige au joueur de jouer un coup de jeu
	 * @return array contenant le coordonnees du coup
	 */
	public abstract int[] jouer();
	
	/**Renvoie un boolean qui determine si oui ou non le joueur souhaite abondonner
	 * @return
	 */
	public boolean abandonner();
	
	/**Renvoie un boolean qui determine si oui ou non le joueur souhaite faire match nul
	 * @return
	 */
	public boolean nul();
	
	/**Demande au joueur d'accepter ou non une proposition de match nul
	 * @param nul
	 * @return Reponse du joueur
	 */
	public boolean acceptNul(Boolean nul);
	

    


	

}
