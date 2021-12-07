package joueur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import application.Coordonees;
import application.Joueur;
import pieces.Couleur;
import pieces.Roi;
import plateau.IPieces;
import plateau.Plateau;

/**
 * @author alhas
 *
 */
public class Algo implements Joueur {
	Plateau plat;
	private Couleur Couleur;
	private Roi Roi;
	public Algo(Couleur c, Plateau plat) {
		this.plat = plat;
		Couleur = c;
	}

	@Override
	public int[] jouer() {
		if(Roi == null)
			Roi = getRoi();
		
		int[] coup =  null;
		
		if(Roi.isAttacked()) 
			coup = escape();
		
		
		if( coup == null)
			coup = attack();
		
		if( coup == null)
			coup = random();
		
		return coup;
	}
	
	/**
	 * Retourne le roi du joueur
	 * @return le roi du joueur
	 */
	private Roi getRoi() {
		for(Map.Entry<IPieces, Coordonees> set : plat.getPieces(Couleur).entrySet()) {
			if(set.getKey() instanceof Roi)
				return (pieces.Roi) set.getKey();
		}
		return null;
	}
	
	/**
	 * Retourne un mouvement tirer au hasard 
	 * @return  un mouvement tirer au hasardou nul
	 */
	private int[] random() {
		HashMap<IPieces , Coordonees> pieces = plat.getPieces(Couleur);
		int[] coup =  new int[4];
		ArrayList<Coordonees> moves = new ArrayList<>();
		Random rand = new Random();
		for(Map.Entry<IPieces, Coordonees> set : pieces.entrySet()) {
			for(Coordonees crd : plat.casesPossibles(set.getKey()) ) {
				if(plat.jouable(set.getValue(), crd, Couleur)) {
					moves.add(crd);
				}
			}
			if(moves.size()>0) {
				Coordonees cop = moves.get(rand.nextInt(moves.size()));
				coup[0] = set.getValue().getCoordx();
				coup[1] = set.getValue().getCoordy();
				coup[2] = cop.getCoordx();
				coup[3] = cop.getCoordy();
				return coup;
			}
		}
		return null;
		
	}
	
	/**
	 * Retourne un mouvement capable de sortir le roi d'echec 
	 * @return  un mouvement capable de sortir le roi d'echec ou null
	 */
	private int[] escape() {
		int[] coup =  new int[4];
		Coordonees src = plat.getCoordonees(Roi);
		for(Coordonees crd : plat.casesPossibles(Roi)){
			if(plat.jouable(src, crd, Couleur)) {
				
				coup[0] = src.getCoordx();
				coup[1] = src.getCoordy();
				coup[2] = crd.getCoordx();
				coup[3] = crd.getCoordy();
				return coup;
			}
		}
		return null;
	}
	
	/**
	 * Retourne un mouvement capable d'attaquer une piece adversaire 
	 * @return  un mouvement attaque ou null
	 */
	private int[] attack() {
		HashMap<IPieces , Coordonees> pieces = plat.getPieces(Couleur);
		int[] coup =  new int[4];
		ArrayList<Coordonees> moves = new ArrayList<>();
		Random rand = new Random();
		for(Map.Entry<IPieces, Coordonees> set : plat.getPieces(Couleur).entrySet()) {
			for(Map.Entry<IPieces, Coordonees> Inset : pieces.entrySet()) {
					if(plat.jouable(Inset.getValue(), set.getValue(), Couleur) /*&& plat.peutAtteindre(Inset.getKey(), Inset.getValue(), set.getValue())*/) {
						moves.add(new Coordonees(Inset.getValue().getCoordx(), Inset.getValue().getCoordy()));
					}
				}
			if(moves.size()>0) {
				Coordonees cop = moves.get(rand.nextInt(moves.size()));
				coup[0] = set.getValue().getCoordx();
				coup[1] = set.getValue().getCoordy();
				coup[2] = cop.getCoordx();
				coup[3] = cop.getCoordy();
				return coup;
			}
		}
		return null;
	
	}


	@Override
	public Couleur getCouleur() {
		return Couleur;
	}

	@Override
	public boolean abandonner() {
		return false;
	}

	@Override
	public boolean nul() {
		return false;
	}

	
	public boolean acceptNul(Boolean nul) {
		return nul;
	}



}
