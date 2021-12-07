package tests;

import org.junit.jupiter.api.Test;

import application.Coordonees;
import pieces.Couleur;
import pieces.Pieces;
import pieces.Roi;
import plateau.Plateau;

public class testsUni {
	@Test
	void test() {
		Plateau p = new Plateau();
		Pieces roi = new Roi(Couleur.BLANC);
		Coordonees src = new Coordonees(3, 3);
		p.placerPiece(roi, src);
		assert(p.getPiece(src) == roi);
		
		Coordonees dest = new Coordonees(5, 5);
		p.move(src, dest);
		assert(p.getPiece(dest) == roi);
		
		
	}
}
