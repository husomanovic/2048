package console;

import java.util.*;
import java.lang.Math;
import logic.*;

public class Main {

	static void ispisiPlocu(Ploca p) {
		int [][] matrica=p.getPloca();
		for(int i=0 ; i<p.getDimenzija() ; i++) {
			for(int j=0 ; j<p.getDimenzija() ; j++) {
				int element=matrica[i][j];
				System.out.print(element + " ".repeat(10-((int)Math.log10(element+1))));
			}
			System.out.println();
		}		
	}
	
	public static void main(String[] args) {
		
		Ploca ploca=new Ploca(4);
	    Scanner scaner = new Scanner(System.in);  
	    
		while (true) {
			if(ploca.getKraj()) {
				ispisiPlocu(ploca);
				System.out.println("Igra je gotova!");
				System.out.println("Trenutni rekord : " + ploca.getTrenutniRekord());
				System.out.println("najbolji rekord : " + ploca.getRekord());
				System.out.println("Unesi 'r' za ponovno pokretannje,sve ostalo je kraj igre.");
				String input=scaner.nextLine();
				if (input.equals("r")) {
					ploca.restartujPlocu();
				}else {
					break;

				}	
			}
			ispisiPlocu(ploca);
			System.out.println("Unesi 'w' za gore, 's' za dole, 'd' za desno, 'a' za lijevo ");
			System.out.println("Trenutni rekord : " + ploca.getTrenutniRekord());

			String input=scaner.nextLine();
			if (input.equals("w")) {
				ploca.pomjeriGore();
			}
			else if (input.equals("s")) {
				ploca.pomjeriDole();
			}
			else if (input.equals("a")) {
				ploca.pomjeriLijevo();
			}
			else if (input.equals("d")) {
				ploca.pomjeriDesno();
			}
			else {
				System.out.println("Pogresan input. ");
			}
			System.out.println();
			System.out.println();
			System.out.println();


		}

	}
}
