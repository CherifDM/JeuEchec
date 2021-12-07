package pieces;

public enum Couleur {
	BLANC,NOIR;
	
	public Couleur opposite() {
		if (this == BLANC)
			return NOIR;
		return BLANC;
	}
}
