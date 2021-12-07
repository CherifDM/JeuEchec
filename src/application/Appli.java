package application;


import java.util.Scanner;


import joueur.Algo;
import joueur.Humain;
import pieces.Couleur;
import plateau.Plateau;

public class Appli {
	final static int hvh = 1;
	final static int hva = 2;
	final static int ava = 3;

	
	public static void main(String[] args) {
		Plateau plat = new Plateau();
		System.out.println("Entre mode de jeux. 1 = Humain vs Humain / 2 = Humain vs Algo / 3 = Algo vs Algo");
		Scanner sc = new Scanner(System.in);
		String answer;
		Joueur BLANC;
		Joueur NOIR;
		int res = 0;
		//***Initialisation de jeu***//
		boolean valAnswer = false;
		do {
			valAnswer = true;
			answer = sc.next();
			try {
				res = Integer.parseInt(answer);
			} catch (Exception e) {
				valAnswer = false;
			}
			if(res != hvh && res != hva && res!= ava) {
				valAnswer = false;
				System.out.println("#>" );
			}
		} while(!valAnswer);
		
		if(res == hvh) {
			 BLANC = new Humain(Couleur.BLANC);
			 NOIR = new Humain(Couleur.NOIR);
			 System.out.println("Humain vs Humain");
		}
		else if(res == hva) {
			 BLANC = new Humain(Couleur.BLANC);
			 NOIR = new Algo(Couleur.NOIR, plat);
			 System.out.println("Algo vs Humain");
		} else {
			BLANC = new Algo(Couleur.BLANC, plat);
			NOIR = new Algo(Couleur.NOIR, plat);
			System.out.println("Algo vs Algo");
		}
		System.out.println("Place vos pieces");
		boolean roiB = false;
		boolean roiN = false;
		while (!answer.equals("fin") || !roiB || !roiN) {

			answer = sc.next();
			if(answer.equals("fin"))
				break;
			plat.placer(answer);
			System.out.println("-");
		}

		//***Debut de jeu***//
		System.out.println(plat.toString());
		int nbGames = 0;
		Joueur courant = BLANC;
		Joueur opposant = NOIR;
		boolean valid = false;
		boolean abandon = false;
		boolean nul = false; 	
		while(!plat.terminer(courant.getCouleur())) {
			Coordonees src = null;
			Coordonees dst = null;
			int[] coup;
			do {
				valid = true;
				coup = courant.jouer();
				abandon = courant.abandonner();//Demande au joueur courant si il souhaite abandonner
				nul = opposant.acceptNul(courant.nul());//Demande a l'adversaire si il accepte le match null
				if(abandon || nul )
					break;
				src = new Coordonees(coup[0], coup[1]);
				dst = new Coordonees(coup[2], coup[3]);
				valid = plat.jouable(src, dst, courant.getCouleur());//Verification de coup valable
				if (!valid) {
					System.out.println("#>");
				}
			} while (!valid) ;
			if(abandon || nul )
				break;
			plat.jouer(src, dst, courant.getCouleur()); 
			System.out.println(plat.toString());
			courant = switchJoueur(courant, BLANC, NOIR);
			opposant = switchJoueur(courant, BLANC, NOIR);
			System.out.println(courant.getCouleur() + " Is Next \n" );
			nbGames ++;
		}
		
		if(plat.matchNull() || nul) {
		System.out.println("Match Null");
		} else if(plat.getPerdant() != null) {
			System.out.println( plat.getPerdant() + " a perdu");
		}else if(abandon)
			System.out.println(courant.getCouleur() + " a perdu");
		System.out.println(nbGames);
		sc.close();
	}
		

	/**Renvoie le joueur opposé du joueur courant mis en paramettre 
	 * @param courant Joueur a etre inverse
	 * @param BLANC Joueur Blanc
	 * @param NOIR Joueur Blanc
	 * @return Joueur
	 */
	private static Joueur switchJoueur(Joueur courant, Joueur BLANC, Joueur NOIR) {
		if ( courant == BLANC )
			return NOIR;
		return BLANC;
		
	}
}
