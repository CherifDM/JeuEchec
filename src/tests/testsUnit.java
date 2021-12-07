package tests;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import application.Coordonees;
import pieces.Couleur;
import pieces.Dame;
import pieces.Pion;
import pieces.Roi;
import plateau.IPieces;
import plateau.Plateau;

class testsUnit {

	@Test
	void testMouvementRoi() {
		Plateau plat = new Plateau();
		IPieces Roi = new Roi(Couleur.NOIR);
		Coordonees source = new Coordonees(3, 3);
		plat.placerPiece(Roi, source);
		ArrayList<Coordonees> crdRoi = plat.casesPossibles(Roi);
		for(Coordonees crd : crdRoi) {
			assert(plat.peutAtteindre(Roi, source, crd));
			assert(Roi.inPath(source, crd));
		}
		
		
	}
	
	@Test
	void placementPiece() {
		Plateau plat = new Plateau();
		IPieces Roi = new Roi(Couleur.NOIR);
		IPieces Dame = new Dame(Couleur.NOIR);
		IPieces Pion = new Pion(Couleur.NOIR);
		Coordonees srcRoi = new Coordonees(3, 3);
		Coordonees srcDame = new Coordonees(0, 0);
		Coordonees srcPion = new Coordonees(4, 7);
		plat.placerPiece(Roi, srcRoi);
		plat.placerPiece(Dame, srcDame);
		plat.placerPiece(Pion, srcPion);
		assert(plat.getPiece(srcRoi).equals(Roi));
		assert(plat.getPiece(srcDame).equals(Dame));
		assert(plat.getPiece(srcPion).equals(Pion));
		
	}
	
	@Test
	void p() {
		
	}
	
	@Test
	void deplacerTest() {
		Plateau plat = new Plateau();
		IPieces Dame = new Dame(Couleur.BLANC);
		Coordonees srcDame = new Coordonees(3, 3);
		Coordonees dstDame = new Coordonees(4, 4);
		plat.placerPiece(Dame, srcDame);
		plat.move(srcDame, dstDame);
		assert(plat.getPiece(dstDame).equals(Dame));
	}
	
	@Test
	void placerPieceTest() {
		Plateau plat = new Plateau();
		String [] ss = {"rd3", "tg2", "Rh1"};
		for(String s : ss) {
		plat.placer(s);
		}
		assert(plat.caseOccupee(3, 2) &&plat.caseOccupee(7, 0) &&plat.caseOccupee(6, 1));
		
	}
	
	@Test
	void pion() {
		Plateau plat = new Plateau();
		IPieces Pion = new Pion(Couleur.BLANC);
		Coordonees sEnPion = new Coordonees(1, 2);
		plat.placerPiece(Pion, sEnPion);
		plat.placer("pa1");
		Coordonees srcPion = new Coordonees(0, 0);
		Coordonees dstPion = new Coordonees(0, 2);
		IPieces pion = plat.getPiece(srcPion);
		assert(pion instanceof Pion);
		assert(plat.jouable(srcPion, dstPion, pion.getColor()));
		plat.jouer(srcPion, srcPion, pion.getColor());
		assert(!plat.jouable(dstPion, new Coordonees(0, 4), pion.getColor()));

	}
	
	@Test
	void echec() {
		Plateau plat = new Plateau();
		IPieces Roi = new Roi(Couleur.NOIR);
		Coordonees srcRoi = new Coordonees(1, 1);
		plat.placerPiece(Roi, srcRoi);
		String [] ss = { "Th3", "Ra8", "Tc7","Th2", "Th1"};
		for(String s : ss) {
			plat.placer(s);
		}
		assert(Roi.briser(plat, srcRoi, new Coordonees(2, 1), false, Roi));
	}
	

}
