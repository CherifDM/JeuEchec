package pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.Coordonees;
import plateau.IPieces;
import plateau.Plateau;

public class Roi extends Pieces{
	private Boolean attacked;
	private ArrayList<IPieces> attaquant;
	
	/**
	 * @param c
	 */
	public Roi(Couleur c) {
		super(c);
		attacked = false;
		attaquant = new ArrayList<>();
	}
	

	public boolean interruptible () {
		return false;	
	}
	

	public boolean inPath(Coordonees src,Coordonees dest) {
		return (Math.abs(src.getCoordx()-dest.getCoordx())<2 && Math.abs(src.getCoordy()-dest.getCoordy())<2);		
	}

	public String getLettre() {
		if (super.getColor() == Couleur.BLANC)
				return "R";
		else return "r";
	}
	
	/**Determine si le roi est menacer
	 * @return true si le roi est menacer
	 */
	public boolean isAttacked() {
		return attacked;
	}

	/**Determine si le roi est menacer et met a jour la liste des pieces qui menacent le roi
	 * @param plat Plateau de jeu
	 * @param src Coordonnees de source
	 */
	public void checkAttacked(Plateau plat, Coordonees src) {
		attacked = false;
		attaquant.clear();
		for(Map.Entry<IPieces, Coordonees> set : plat.getPieces(this.getColor().opposite()).entrySet()) {
			if ( plat.peutAtteindre(set.getKey(), set.getValue(), src)) { 
				attacked = true;
				attaquant.add(set.getKey());
			}
		}
	}
	
	
	@Override
	public boolean briser(Plateau plat, Coordonees src, Coordonees dest, boolean jouable, IPieces p) {
		checkAttacked(plat, plat.getCoordonees(this));
		if(!(p == this)) {
			if(isAttacked() && !sorsEchec(plat, src, dest))
				return true;
		} else {
		if(misEnEchec(plat, src, dest))
			return true;
		}
		return false;
	}
	
	


	//King cant be put into check
	/**Determine le si un mouvement met le roi en echec
	 * @param plat Plateau de jeu
	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @return True si le mouvement met le roi en echec
	 */
	private boolean misEnEchec(Plateau plat, Coordonees src, Coordonees dest){
			HashMap<IPieces, Coordonees> pp = plat.getPieces(this.getColor().opposite());
			for(Map.Entry<IPieces, Coordonees> set : pp.entrySet() /*plat.getPieces(cl.opposite()).entrySet()*/) {
				IPieces en = set.getKey();
				Coordonees enC = set.getValue();
				if (plat.peutAtteindre(en, enC, dest)||(en.inPath(enC, dest)&&plat.peutAtteindre(en, enC, src))) {
					return true;	
			} else if(plat.caseOccupee(dest.getCoordx(), dest.getCoordy())) {
				if(plat.getPiece(dest).getColor().equals(color.opposite()))
					return false;
			}
		}
		return false;
	}

	/**Determine si le mouvement d'une piece sors le roi d'echec
	 * @param plat Plateau de jeu
	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @return True si le mouvement sors le roi d'echec
	 */
	private boolean sorsEchec(Plateau plat, Coordonees src, Coordonees dest){
		 int srcX = plat.getCoordonees(this).getCoordx();
		 int srcY = plat.getCoordonees(this).getCoordy();
		for(IPieces en : attaquant) {
			 int dstX = plat.getCoordonees(en).getCoordx();
			 int dstY = plat.getCoordonees(en).getCoordy();
			while (Math.abs(dstX-srcX)>0 || Math.abs(dstY-srcY)>0) {
				srcX += Math.signum(dstX-srcX);
				srcY +=	Math.signum(dstY-srcY);
				if(dest.equal(new Coordonees(srcX, srcY))) {
					return true;
				}			
			}
		}
			
		return false;
	}

	//EchacetMat//If King is in check and his next move doen't take him out of check/(All of his possible possition don't take him out of check) je loses
	/**Determine si le roi est en echec et mat
	 * @param plat Plateau de jeu
	 * @param src Coordonnees de source
	 * @return True si le roi est en echec et mat
	 */
	public boolean echecEtMat( Plateau plat, Coordonees src){
		checkAttacked(plat, src);

		if(isAttacked() ) {
			if(plat.canBeSaved(this, src) || !echec(plat, src))
				return false;
			return true;
		}else {
			return false;
		}
		
	}
	
	/**Determine si le roi est en pat
	 * @param plat Plateau de jeu
	 * @param src Coordonnees de source
	 * @return True si le roi est en pat
	 */
	public boolean pat( Plateau plat, Coordonees src){
		checkAttacked(plat, src);
		if(echec(plat, src)) {
		if(isAttacked() ) {
			if(plat.canBeSaved(this, src))
				return false;
			return true;
		} else {
			ArrayList<IPieces> def = defendant(plat, src, this);
			if(def.size() < plat.getPieces(this.getColor()).keySet().toArray().length) {
				return true;
			}
		}
		}
		return false;
	}
	
	//EchacetMat//If King is in check and his next move doen't take him out of check/(All of his possible possition don't take him out of check) je loses
	/**Determine si le roi est en echec
	 * @param plat Plateau de jeu
	 * @param src Coordonnees de source
	 * @return True si le roi est en echec
	 */
	public boolean echec( Plateau plat, Coordonees src){
		for(Coordonees crd : plat.casesPossibles(this)) {
			if (!misEnEchec(plat, src, crd)) {
				return false;
			}else  {
				if (plat.canBeSaved(this, crd))
					return false;
			}
		}
		return true;
	}
	
	
	/**Retourne une ArrayList de pieces qui defendent le roi
	 * @param plat Plateau de jeu
	 * @param src Coordonnees de source
	 * @param p
	 * @return ArrayList de defendant
	 */
	private ArrayList<IPieces> defendant(Plateau plat, Coordonees src, IPieces p) {
		int dstX ;
		int dstY ;
		ArrayList<IPieces> de = new ArrayList<IPieces>();
		for(Map.Entry<IPieces, Coordonees> set : plat.getPieces(this.getColor().opposite()).entrySet()) {
			if(set.getKey().inPath(set.getValue(), src)) {
				 dstX = set.getValue().getCoordx();
				 dstY = set.getValue().getCoordy();
				while (Math.abs(src.getCoordx()-dstX)>0 || Math.abs(src.getCoordy()-dstY)>0) {
					dstX += Math.signum((src.getCoordx())-dstX);
					dstY +=	Math.signum((src.getCoordy())-dstY);
					if(plat.caseOccupee(dstX, dstY)) {
						IPieces pt = plat.getPiece(new Coordonees(dstX, dstY));
						if((pt.getColor().equals(p.getColor())))
							de.add(plat.getPiece(new Coordonees(dstX, dstY)));
						break;
					}
				}
			}
		}
		return de;
	}

	




}
