package joueur;

import java.util.Scanner;

import application.Joueur;
import pieces.Couleur;

public class Humain implements Joueur  {
	private Couleur Couleur;
	private boolean valide;
	private boolean abandon;
	private boolean nul;
	
	public Humain(Couleur c) {
		Couleur = c;
		valide = true;
		abandon = false;
	}
	@Override
	public Couleur getCouleur() {	
		return Couleur;
	}



	public int[] jouer() {
		
		 @SuppressWarnings("resource")
	        Scanner c = new Scanner(System.in);
		 int[] i = null;
		 	do {
		 		valide = true;
		        String coup = c.nextLine(); 
		        
		        if(coup.toUpperCase().equals("ABANDON")) {
		        	abandon = true;
		        	break;
		        	
		        } else if(coup.toUpperCase().equals("NUL")) {
		        	nul = true;
		        	break;
		        }
		        
		        if(coup.length()!=4)
		        	valide = false;
		        	
		        char [] s = coup.toCharArray();
		        
		        
		        try {
			         i = new int[] {getIntFromChar(s[0])-1,Integer.parseInt(String.valueOf(s[1]))-1,getIntFromChar(s[2])-1,Integer.parseInt(String.valueOf(s[3]))-1}; 
				} catch (Exception e) {
					valide = false;
				}
		        if(!valide)
		        	System.out.println("#>");
		 	} while(!valide);
			 	        	
	        return i;
		
		
	}
    /** Retourne la valeur associe d'une caractere 
     * @param c caractere d'entre
     * @return
     */
    private  int getIntFromChar(char c) {
    	int val = c - 'a' + 1;
        if(val < 0 || val > 8 )
        	valide = false;
        return val;

    }
	@Override
	public boolean abandonner() {
		return abandon;
	}
	@Override
	public boolean nul() {
		return nul;
	}
	@Override
	public boolean acceptNul(Boolean nul) {
		if(nul) {
		Scanner c = new Scanner(System.in);
		String answer;
		System.out.println("L'opposant vous propose un Match Nul. Voulez-vous accepter ? Y/N");
		answer = c.nextLine();
		while(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N")) {
			System.out.println("#>");
			answer = c.nextLine();
		}
		c.close();
		if(answer.toUpperCase().equals("Y"))
			return true;
		else
			return false;
		}
		return false;
		

	}
    
	



}
