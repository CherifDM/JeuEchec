package plateau;

public class Case {
	private IPieces piece;
	
	public Case() {
		piece = null;
	}
	@Override 
	public String toString() {
		if (!(piece == null))
			return " "+piece.getLettre()+" "+"|";
		else return "   |";
		
	}
	
	/**
	 * @param p
	 * @param plat
	 */
	public void setPiece(IPieces p, Plateau plat) {
		if(!(piece == null))
			plat.kill(piece);
		piece = p;
		
	}
	/**
	 * @return
	 */
	public IPieces getPiece() {
		return piece;
	}
	/**
	 * @param plat
	 */
	public void capture(Plateau plat) {
		if(!(piece == null))
			plat.kill(piece);
		piece = null;
		
	}
	/**
	 * @param plat
	 */
	public void vider(Plateau plat) {
		piece = null;
		
	}
	
}
