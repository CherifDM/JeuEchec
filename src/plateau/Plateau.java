package plateau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.Coordonees;
import pieces.Couleur;
import pieces.FabriquePiece;
import pieces.Pieces;
import pieces.Roi;

public class Plateau {
	private static final int platH = 8;
	private static final int platL = 8;
	private HashMap<IPieces, Coordonees> Blanc;
	private HashMap<IPieces, Coordonees> Noir;
	private Case [][] plat; 
	private Couleur perdant;
	private boolean matchNull;
	
	
	
	/**
	 * Constructeur d'un plateau
	 */
	public Plateau() {
		matchNull = false;
		perdant = null;
		Blanc = new HashMap<IPieces, Coordonees>();
		Noir = new HashMap<IPieces, Coordonees>();
		plat = new Case[platH][platL];
		for(int i = 0; i < platH; i ++) {
			for(int y = 0; y < platL; y ++) {
				plat[i][y] = new Case();
			}
		}
	}
	
	/**
	 * Met en jeu un coup a partir des coordonnees sources et destinations
	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @param couleur Couleur du joueur
	 */
	public void jouer(Coordonees src, Coordonees dst, Couleur couleur) {
		move(src, dst);
	}
	
	/**Determine si un coup est jouable
	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @param couleur Couleur du joueur
	 */
	public boolean jouable(Coordonees src, Coordonees dest, Couleur cl) {
		boolean jouable = true;
		IPieces p = getPiece(src);
		if(caseOccupee(src.getCoordx(), src.getCoordy())){
			if(!peutAtteindre(p, src, dest))
				jouable = false;
	
			if(!(cl == getPiece(src).getColor()))
				return false;	
		}
		else {
			return false;
		}
		if(src.equals(dest))
			return false;
		
		if(caseOccupee(dest.getCoordx(), dest.getCoordy())){
			if((cl == getPiece(dest).getColor()))
				return false;
		}
		for(Map.Entry<IPieces, Coordonees> set : getPieces(cl).entrySet()) {
			if(set.getKey().briser(this, src, dest, jouable, p))
					return false;
		}
		return jouable;
		//return !p.briser(this, src, dest, jouable, p);
		
	}
	
	/**Determine si une piece peut atteindre des coordonnees destinations a partis de coordonnees source
	 * @param p Piece 
	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @return True si la piece peut atteindre les coordonnees de destination
	 */
	public boolean peutAtteindre(IPieces p, Coordonees src, Coordonees dest) {
		if(!p.inPath(src, dest) || interrompue(p, src, dest))
			return false;
		return true;
	}
	
	/**
	 * Determine si le chemin d'une piece allant de coordonnees source a des coordonnees de destination est interrompue 
	 * @param p Piece 
	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @return True si le chemin est interompue
	 */
	private boolean interrompue(IPieces p, Coordonees src, Coordonees dest) {
		if(p.interruptible()) {
			int srcX = src.getCoordx();
			int srcY = src.getCoordy();
			
			while (Math.abs(dest.getCoordx()-srcX)>1 || Math.abs(dest.getCoordy()-srcY)>1) {
				srcX += Math.signum(dest.getCoordx()-srcX);
				srcY +=	Math.signum(dest.getCoordy()-srcY);
				if(caseOccupee(srcX, srcY))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Determine si une pieces au coordonnees source peut etre sauve des pieces qui lui menancent 
	 * @param p Piece 
	 * @param src Coordonnees de source
	 * @return
	 */
	public boolean canBeSaved(IPieces p, Coordonees src) {
		for(Map.Entry<IPieces, Coordonees> set : getPieces(p.getColor().opposite()).entrySet()) {
			if ( peutAtteindre(set.getKey(), set.getValue(), src)) {
				int srcX = src.getCoordx();
				int srcY = src.getCoordy();
				
				while (Math.abs(set.getValue().getCoordx()-srcX)>0 || Math.abs(set.getValue().getCoordy()-srcY)>0) {
					srcX += Math.signum(set.getValue().getCoordx()-srcX);
					srcY +=	Math.signum(set.getValue().getCoordy()-srcY);
					for(Map.Entry<IPieces, Coordonees> Inset : getPieces(p.getColor()).entrySet()) {
						if (peutAtteindre(Inset.getKey(), Inset.getValue(), new Coordonees(srcX, srcY)) && !Inset.getKey().equals(p))
							return true;
					}
						
				}
			}
		}
		return false;

	}
	/**
	 * Determine si le joueur associe a une couleur est en echec et mat ou en pat
	 * @param color
	 * @return
	 */
	public boolean terminer(Couleur color) {
		for(Map.Entry<IPieces, Coordonees> set : getPieces(color).entrySet()) {
			if(set.getKey() instanceof Roi) {
				Roi r = (Roi) set.getKey();
				if(r.echecEtMat(this, set.getValue())) {
					perdant = color;
					return true;
				} else if(r.pat(this, set.getValue())) {
					matchNull = true;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Retourne une hashmap contenant les pieces et leurs coordonnees du joueur associe avec la couleur
	 * @param cl
	 * @return un hashmap<Pieces, Coordonnees>
	 */
	public HashMap<IPieces, Coordonees> getPieces(Couleur cl){
		if(cl.equals(Couleur.BLANC))
			return Blanc;
		else if (cl.equals(Couleur.NOIR))
			return Noir;
		return null;
		
	}
	
	/**Retourne toutes les cases que peut atteindre une piece
	 * @param p Piece
	 * @return
	 */
	public ArrayList<Coordonees> casesPossibles(IPieces p) {
		ArrayList<Coordonees> crds = new ArrayList<>();
		Coordonees src = getCoordonees(p);
		Coordonees dst = new Coordonees(0,0);
		for(int i = 0; i < platH; i++) {
			for(int j = 0; j < platL; j++) {
				dst = new Coordonees(i, j);
				if( peutAtteindre(p, src, dst) && !src.equal(dst))
					crds.add(dst);
			}
		}
		return crds;
	}
	
	/**
	 * Determine si une case du plateau est occupee a partir de coordonnees X et Y
	 * @param srcX Coordonnees X
	 * @param srcY Coordonnees X
	 * @return
	 */
	public boolean caseOccupee(int srcX, int srcY) {
		if(plat[srcX][srcY].getPiece() != null)
			return true;
		return false;
	}
	/**
	 * Retourne la piece situees au coordonnees source
	 * @param src Coordonnees source
	 * @return
	 */
	public IPieces getPiece(Coordonees src) {
		return plat[src.getCoordx()][src.getCoordy()].getPiece();
	}
	
	/**
	 * Retourne les coordonnees d'une piece
	 * @param p Piece
	 * @return les coordonnees d'une piece 
	 */
	public Coordonees getCoordonees(IPieces p) {
		if(p.getColor().equals(Couleur.BLANC))
			return Blanc.get(p);
		else if (p.getColor().equals(Couleur.NOIR))
			return Noir.get(p);
		return null;
		
	}

	/**
	 * Deplace une piece des coordonnees source a des coordonnees de destinations
	 * @param src Coordonnees de source
	 * @param dest Coordonnees de destination
	 * @return
	 */
	public Pieces move(Coordonees src, Coordonees dest) {
		IPieces p = plat[src.getCoordx()][src.getCoordy()].getPiece();
		plat[dest.getCoordx()][dest.getCoordy()].setPiece(p, this);
		getPieces(p.getColor()).replace(p, dest);
		plat[src.getCoordx()][src.getCoordy()].vider(this);
		return null;
		
	}
	
	/**
	 * Place une piece dans les coordonnees de destinations
	 * @param p
	 * @param dest Coordonnees de destination
	 */
	public void placerPiece(IPieces p, Coordonees dest) {
		plat[dest.getCoordx()][dest.getCoordy()].setPiece(p, this);
		if(p.getColor().equals(Couleur.BLANC))
			Blanc.put(p, dest);
		else if (p.getColor().equals(Couleur.NOIR))
			Noir.put(p, dest);
	}

	
	/**
	 * Place une piece sur le plateau a partir d'un string
	 * @param answer String contenant la piece et ses coordonnees
	 */
	public  void placer(String answer) {
		boolean valide = true;
		///Change Maybe?
		FabriquePiece fb = new FabriquePiece();
		if(answer.length()!=3) 
			valide = false;			
		char [] c = answer.toCharArray();		
		//Making piece
		if(valide) {
			if(!Character.isLetter(c[0]) || !Character.isLetter(c[1]) || !Character.isDigit(c[2]))
				valide=false;
			IPieces p = fb.creerPiece(c[0]);
			if(p == null)
				valide=false;
			//Entering coordninates
			int crdx = c[1] - 'a' ;
	        if(crdx < 0 && crdx > 8 )
	        	valide = false;
	        int crdy = 0;
	        try {
	        	 crdy = Character.getNumericValue(c[2])-1;
			} catch (Exception e) {
				valide = false;
			}
			Coordonees crd = new Coordonees(crdx, crdy);
			if(valide) {
				placerPiece(p, crd);	
			}
			
		}
		if(!valide)
			System.out.println("#>");
	}
	
	
	/**
	 * @param answer
	 * @param roiB
	 * @param roiN
	 */
	public void placer(String answer, boolean roiB, boolean roiN) {
		if(answer.contains("R")) {
			if(!roiB) {
				roiB = true;
				placer(answer);
			}
			else 
				System.out.println("Il existe deja un roi blanc");
		} else if(answer.contains("r")) {
			if(!roiN) {
				roiN = true;
				placer(answer);
			}
			else 
				System.out.println("Il existe deja un roi noir");
		} else
			placer(answer);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
        sb.append( "    a   b   c   d   e   f   g   h\n");
        sb.append( "   --- --- --- --- --- --- --- ---\n");
        int y = platL ;
        while (y > 0){
        	sb.append( y + " |");
            for (int x = 1; x < platH+1; x++) {
            	sb.append( plat[x-1][y-1].toString());
            }
            sb.append( " " + y + "\n");
            sb.append( "   --- --- --- --- --- --- --- ---\n");
        y--;
        }
        sb.append( "    a   b   c   d   e   f   g   h\n");
        return sb.toString();
    }
	
	
	

	/**
	 *  
	 * @return True si match nul
	 */
	public boolean matchNull() {
		return matchNull;
	}
	/**
	 * Retourne la couleur du perdant du jeu
	 * @return
	 */
	public Couleur getPerdant() {
		return perdant;
	}
	/**
	 * Capture une case du plateau
	 * @param crd Coordonnees de case
	 */
	public void capture(Coordonees crd) {
		plat[crd.getCoordx()][crd.getCoordy()].capture(this);
		
	}
	/**
	 * Enleve une piece du plateau
	 * @param p Piece a enlenver
	 */
	public void kill(IPieces p) {
		if(p.getColor().equals(Couleur.BLANC))
			Blanc.remove(p);
		else if(p.getColor().equals(Couleur.NOIR))
			Noir.remove(p);
	}




}
