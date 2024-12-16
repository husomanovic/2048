package logic;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Ploca {
	
	static private int najboljiRekord;
	public int dimenzija;
	private int[][] ploca;
	private int trenutniRekord;
	private boolean kraj;
	private boolean validan_potez;

	public Ploca(int i) {
		trenutniRekord=0;
		kraj=false;
		validan_potez=false;
		if(i==4) {
			ploca= new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
			dimenzija=4;
		}else {
			ploca= new int[][] {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
			dimenzija=5;
		}
		dodaj();
		najboljiRekord=getRekord();

	}
	
	public int[][] getPloca(){return ploca;}
	public int getDimenzija(){return dimenzija;}
	public boolean getKraj(){return kraj;}
	public int getTrenutniRekord(){return trenutniRekord;}
	public int getRekord(){
		try {
		File  file= new File("src/logic/najbolji_rezultat.txt");
        BufferedReader br;
		br = new BufferedReader(new FileReader(file));
		int rez = Integer.parseInt(br.readLine());
		br.close();
	    return rez;
		}catch (Exception e) {
			return 0;
		}
	}
	


	public void dodaj(){
        while (true) {
			int i=new Random().nextInt(dimenzija);
			int j=new Random().nextInt(dimenzija);

			if(ploca[i][j]==0) {
				if(Math.random()<0.9) {
					ploca[i][j]=2;
				}
				else {
					ploca[i][j]=4;
				}
			break;
			}
		}
	
	}
	
	private void provjeriKraj() {
		for (int i = 0 ; i<dimenzija ; i++) {
			for (int j = 0 ; j<dimenzija ; j++) {
				if(ploca[i][j]==0) {
					{return;}
				}
				if (i==0 && j==0) {
					if (ploca[i][j]==ploca[i+1][j] || ploca[i][j]==ploca[i][j+1]){return;}
				}
				else if (i==dimenzija-1 && j==0) {
					if (ploca[i][j]==ploca[i-1][j] || ploca[i][j]==ploca[i][j+1]) {return;}
				}
				else if (i==dimenzija-1 && j==dimenzija-1) {
					if (ploca[i][j]==ploca[i-1][j] || ploca[i][j]==ploca[i][j-1]) {return;}
				}
				else if (i==0 && j==dimenzija-1) {
					if (ploca[i][j]==ploca[i+1][j] || ploca[i][j]==ploca[i][j-1]) {return;}
				}
				
				else if (i==0) {
					if (ploca[i][j]==ploca[i+1][j] ) {return;}
				}
				else if (i==dimenzija-1) {
					if (ploca[i][j]==ploca[i-1][j] ) {return;}
				}
				else if (j==0) {
					if (ploca[i][j]==ploca[i][j+1] ) {return;}
				}
				else if (j==dimenzija-1) {
					if (ploca[i][j]==ploca[i][j-1] ) {return;}
				}
				else {
					if (ploca[i][j]==ploca[i][j+1] || ploca[i][j]==ploca[i][j-1] ||
							ploca[i][j]==ploca[i+1][j] || ploca[i][j]==ploca[i-1][j]) {return;}
				}
			}			
		}
		
		kraj = true;
	}
	
	
	//za primljenu vrijednost jednaku 1 pomjera prema dole,a za -1 prema gore
	private void pomjeriVertikalno(int smjer) {
	    for (int i = 0; i < dimenzija; i++) {
	        int pozicija = (smjer == 1) ? dimenzija - 1 : 0; // Smjer: 1 za dolje, -1 za gore

	        while ((smjer == 1 && pozicija > 0) || (smjer == -1 && pozicija < dimenzija - 1)) {
	            if (ploca[pozicija][i] == 0 && ploca[pozicija - smjer][i] != 0) {
	                ploca[pozicija][i] = ploca[pozicija - smjer][i];
	                ploca[pozicija - smjer][i] = 0;
					validan_potez=true;
	                if ((smjer == 1 && pozicija == dimenzija - 1) || (smjer == -1 && pozicija == 0)) {
	                    pozicija -= smjer;
	                } else {
	                    pozicija += smjer;
	                }
	            } else {
	                pozicija -= smjer;
	            }
	        }
	    }
	}
	
	
	public void pomjeriDole(){
		validan_potez=false;

		pomjeriVertikalno(1);
		// spajanje
		for (int i = dimenzija-1 ; i>0 ; i--) {
			for (int j = dimenzija-1 ; j>=0 ; j--) {
				if (ploca[i][j]==ploca[i-1][j] && ploca[i][j]!=0 ) {
					ploca[i][j]*=2;
					ploca[i-1][j]=0;
					validan_potez=true;
					trenutniRekord+=ploca[i][j];
				}
			}
		}
		pomjeriVertikalno(1);
		if(validan_potez) {
			dodaj();
			provjeriKraj();
		}
	}
	
	public void pomjeriGore(){
		validan_potez=false;

		pomjeriVertikalno(-1);
		// spajanje
		for (int i = 0 ; i<dimenzija-1 ; i++) {
			for (int j = dimenzija-1 ; j>=0 ; j--) {
				if ( ploca[i][j]==ploca[i+1][j] && ploca[i][j]!=0 ) {
					ploca[i][j]*=2;
					ploca[i+1][j]=0;
					validan_potez=true;
					trenutniRekord+=ploca[i][j];
				}
			}
		}
		pomjeriVertikalno(-1);
		if(validan_potez) {
			dodaj();
			provjeriKraj();
		}
	}
	
	
	//za primljenu vrijednost jednaku 1 pomjera prema lijevo, za -1 pomjera prema desno
	private void pomjeriHorizontalno(int smjer) {
	    for (int i = 0; i < dimenzija; i++) {
	        int pozicija = (smjer == 1) ? dimenzija - 1 : 0; // Smjer: 1 za desno, -1 za lijevo

	        while ((smjer == 1 && pozicija > 0) || (smjer == -1 && pozicija < dimenzija - 1)) {
	            if (ploca[i][pozicija] == 0 && ploca[i][pozicija - smjer] != 0) {
	                ploca[i][pozicija] = ploca[i][pozicija - smjer];
	                ploca[i][pozicija - smjer] = 0;
					validan_potez=true;
	                if ((smjer == 1 && pozicija == dimenzija - 1) || (smjer == -1 && pozicija == 0)) {
	                    pozicija -= smjer;
	                } else {
	                    pozicija += smjer;
	                }
	            } else {
	                pozicija -= smjer;
	            }
	        }
	    }
	}

	public void pomjeriDesno(){
		validan_potez=false;
		pomjeriHorizontalno(1);

		// spajanje
		for (int i = dimenzija-1 ; i>=0 ; i--) {
			for (int j = dimenzija-1 ; j>0 ; j--) {
				if (ploca[i][j]==ploca[i][j-1] && ploca[i][j]!=0) {
					ploca[i][j]*=2;
					ploca[i][j-1]=0;
					validan_potez=true;
					trenutniRekord+=ploca[i][j];
				}
			}
		}
		pomjeriHorizontalno(1);
		if(validan_potez) {
			dodaj();
			provjeriKraj();
		}
	}
	
	public void pomjeriLijevo(){
		validan_potez=false;

		pomjeriHorizontalno(-1);
		// spajanje
		for (int i = 0 ; i<dimenzija; i++) {
			for (int j = 0 ; j<dimenzija-1 ; j++) {
				if (ploca[i][j]==ploca[i][j+1] && ploca[i][j]!=0 ) {
					ploca[i][j]*=2;
					ploca[i][j+1]=0;
					validan_potez=true;
					trenutniRekord+=ploca[i][j];
				}
			}
		}
		pomjeriHorizontalno(-1);
		if(validan_potez) {
			dodaj();
			provjeriKraj();
		}
	}
	
	
	public void restartujPlocu() {
		
		for (int i=0;i<dimenzija ; i++) {
			for( int j=0 ; j<dimenzija ; j++) {
				ploca[i][j]=0;
			}	
		}
		int i=new Random().nextInt(dimenzija);
		int j=new Random().nextInt(dimenzija);

		if(Math.random()<0.9) {
			ploca[i][j]=2;
		}
		else {
			ploca[i][j]=4;
		}
		trenutniRekord=0;
		kraj=false;
		validan_potez=false;		
	}
	
	
	public void postaviRekord() {
		if(trenutniRekord> najboljiRekord) {
			try {
				FileWriter myWriter = new FileWriter("src/logic/najbolji_rezultat.txt");
    	      	myWriter.write(String.valueOf(trenutniRekord));
    	      	myWriter.close();
				najboljiRekord=trenutniRekord;

    
			}catch (Exception e) {}
		}
	}
}
