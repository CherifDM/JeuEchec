package pieces;

import application.Coordonees;
import plateau.IPieces;
import plateau.Plateau;

/**
 * @author alhas
 *
 */
public class Pion extends Pieces{

	private boolean enPassant;
	private boolean firstMove;
	private boolean capturing;
	private int nbMoves;
	private int direction;

	public Pion(Couleur c) {
		super(c);
		firstMove = true;
		nbMoves = 0;
		if(c.equals(Couleur.BLANC)) {
			direction = 1;
		} else if(c.equals(Couleur.NOIR))
			direction = -1;
	}

	@Override
	public String getLettre() {
		if (super.getColor() == Couleur.BLANC)
			return "P";
		else return "p";
	}

	@Override
	public boolean inPath(Coordonees src, Coordonees dest) {
		if(firstMove && !enPassant && !capturing)
			return  ((src.getCoordx()==dest.getCoordx()) && (direction*(dest.getCoordy()-src.getCoordy()))<3);
		
		else if(capturing)
			return  (Math.abs(dest.getCoordx()-src.getCoordx())==1 && (dest.getCoordy()-src.getCoordy())==1*direction);
		
		else if(enPassant)
			return  (Math.abs(src.getCoordx()-dest.getCoordx()) == 1 && (dest.getCoordy()-src.getCoordy())==1*direction);
		
		else
			return ((src.getCoordx() == dest.getCoordx()) && (dest.getCoordy() - src.getCoordy())==1*direction);		
	}



	@Override
	public boolean interruptible() {
		return true;
	}
	/**Retourne le nombre de mouvement effeteur par cette piece
	 * @return nombre de mouvement effectuer
	 */
	private int nbMoves() {return nbMoves;}

	@Override
	public boolean briser(Plateau plat, Coordonees src, Coordonees dest, boolean jouable, IPieces p) {
		if(!(p == this)) {
			return false;
		}
		if(!jouable) {
			if(enPassant(plat, src, dest) || capture(plat, src, dest)) {
				jouable = true;
				nbMoves++;
				firstMove = false;
				return false;
			}
		}
		if(jouable) {
			nbMoves++;
			firstMove = false;
		}
			
		return !jouable;
	}
	/**Determine si un coup est en passant et si elle est valide
	 * @param plat Plateau de jeu
	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @return true si c'eat un coup en passant valide
	 */
	private boolean enPassant(Plateau plat, Coordonees src, Coordonees dest) {
		enPassant = true;
		int dstY = dest.getCoordy();
		int dstX = dest.getCoordx();
		if(dstY==0 || dstY == 7){
			enPassant = false;
			return false;
		}
		if(plat.caseOccupee(dstX, dstY-direction) && inPath(src, dest)) {
			Coordonees crd = new Coordonees(dstX, dstY-direction);
			IPieces p = plat.getPiece(crd);
			if( p instanceof Pion) {
				if(((Pion) p).nbMoves()<2  && !p.getColor().equals(color)) {
					plat.capture(crd);
					enPassant = false;
					return true;
				} 
			} 
		
		}
		
		enPassant = false;
		return false;
	}


	/**Determine si un coup est en passant et si elle est valide
	 * @param plat Plateau de jeu
	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @return true si c'eat un coup en passant valide
	 */
	private boolean capture(Plateau plat, Coordonees src, Coordonees dest) {
		capturing = true;
		if(plat.caseOccupee(dest.getCoordx(), dest.getCoordy()) && inPath(src, dest) && !plat.getPiece(dest).getColor().equals(color)) {
			plat.capture(dest);
			capturing = false;
			nbMoves++;
			firstMove = false;
			return true;
		} 
		capturing = false;
		return false;
	}

}
