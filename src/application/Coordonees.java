package application;

/**
 * @author alhas
 *
 */
/**
 * @author alhas
 *
 */
public class Coordonees {
	private int coordx;
	private int coordy;
	
	
	/**Constructor de la classe Coordonees
	 * @param coordx
	 * @param coordy
	 */
	public Coordonees(int coordx, int coordy) {
		this.coordx = coordx;
		this.coordy = coordy;
	}

	/** 
	 * @return la coordonnee x
	 */
	public int getCoordx() {
		return coordx;
	}
	/**
	 * @return la coordonnee y
	 */
	public int getCoordy() {
		return coordy;
	}
	

	@Override
	public String toString() {
		return coordx + " " + coordy;
	}
	
	/**Determine si deux coordonnees sont egaux
	 * @param obj
	 * @return
	 */
	public boolean equal(Coordonees obj) {
		return (coordx == obj.coordx && coordy == obj.coordy);
	}

	

}
