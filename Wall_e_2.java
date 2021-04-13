import java.util.ArrayList;

public class Jusy_David {
	
	private static int batterie = 81;
	private static int cote = 6;
	private static int minVal ;
	private static int actuelx ;
	private static int actuely ;
	static Boolean needBat= false;
	final static String SEPARATEUR = ",";
	static int Plist;
	static ArrayList<Integer> home = new ArrayList<>();
	static ArrayList<Integer> position_actuel = new ArrayList<>();
	static ArrayList<String> position_dechets = new ArrayList<>();
	static ArrayList<String> position_chargeur = new ArrayList<>();
	static ArrayList<Integer> bufferposition = new ArrayList<>();
	
	public static void Start() {
		
		// donnees de démarrage
		batterie = 81;
		cote = 6;
		
		home.add(1);
		home.add(1);
		
		//positon en x
		position_actuel.add(1);
		actuelx = position_actuel.get(0);
		//position en y = y
		position_actuel.add(1);
		actuely = position_actuel.get(1);
		
		//chargeent des donnees dechets
		position_dechets.add("3,2");
		position_dechets.add("5,1");
		position_dechets.add("2,1");
		position_dechets.add("3,5");
		position_dechets.add("6,4");
		position_dechets.add("6,3");
		
		//chargelent position chargeur
		position_chargeur.add("2,6");
		position_chargeur.add("6,1");
		
		
		}
	
	//method de recherche de la postion la plus proche
	public static void recherche_distance(ArrayList<String> list) {
		
		int bufferreverse;
		int bufferx =0 ;
		int buffery =0 ;
		int bufferminmax = 0;
		minVal= cote*10;
		
		//vide le buffer a chaque appel
		bufferposition.clear();
	
	//recup les diff position 
	for (int j=0 ; j< list.size(); j++) {
        //retire la virgule et injecte la position x et y 
        String xySplit[] = list.get(j).split(SEPARATEUR);
 
        //buff la positiopn en x et en y avant de les additionners
	        for (int i = 0; i < xySplit.length +1; i++) {
	            if(i == 0) {
	            	bufferx = Integer.parseInt(xySplit[i]);
	            }else if(i == 1) {
	            	buffery = Integer.parseInt(xySplit[i]);
	            }else if( i ==2) {
	            	bufferminmax = bufferx-actuelx + buffery-actuely;
	            	bufferposition.add(bufferminmax);
	            }
	        }
		}
	
		
			
	//si nombre negatif , le transforme en positif		
    for (int k = 0 ; k < bufferposition.size() ; k++)
			
    	if (bufferposition.get(k) < 0) {
    		bufferreverse = bufferposition.get(k);
    		bufferreverse = bufferreverse - bufferreverse*2;
    		bufferposition.remove(k);
    		bufferposition.add(k, bufferreverse);
    	}
    
		//trouve l'ement le plus petit de la list (plus proche) et donne sa position dans la list
	  for(int o = 0; o < bufferposition.size(); o++){   
		  
	         if(bufferposition.get(o) < minVal) {
	           minVal = bufferposition.get(o);
	           Plist = o;
	           
	          }
	     }
	}
	
	public static void verifBat() {
			
			//represente la valeur max de 6x+6y
			int secu = cote*2;
			
			System.out.println("Wall E veux prendre l'element en :" +position_dechets.get(Plist) );
			
			
			int valBat = batterie - minVal*3;
			
			//verif si il y a assé de batterie (valeur de secu = 12)
			if (  secu >= valBat ) {
				System.out.println("oh non il pas pas la batterie pour ca !");
				rechargement();
			}
			
			else {
				System.out.println("la batterie est bonne ! il va prendre l'element en : "+ Plist);
				batterie -= minVal;
				System.out.println("il reste " + batterie + " % batterie");
				retour_maison();
			}
		}
	
	//si la batterie est pas ok recharge + set des nouvelles position de wall e
	public static void rechargement() {
				
		recherche_distance(position_chargeur);
			
		System.out.println("la station de recherchement la plus proche se trouve a "+ position_chargeur.get(Plist));
				
		String xySplitCharge[] = position_chargeur.get(Plist).split(SEPARATEUR);
		actuelx = Integer.parseInt(xySplitCharge[0]);
		actuely = Integer.parseInt(xySplitCharge[1]);
					
		batterie = 100;
		System.out.println("Wall E se recharge a la station " + actuelx + ","+actuely +" batterie : "+batterie+" %");
				
				
		}
	
	//depause le dechet et set la valeur ou se trouve wall e + supprimer valeur dans la liste
	public static void retour_maison() {
		
		
		if (actuelx == 1 & actuely == 1) {
		batterie -= minVal*2;
		}
		else {
		batterie -= (actuelx - home.get(0)) + (actuely - home.get(1))  ;	
		}
		System.out.println("wall a depause son dechet, il reste " + batterie + " % batterie");
		actuelx= home.get(0);
		actuely = home.get(1);
		position_dechets.remove(Plist);
		
	}

	public static void main(String[] args) {
		
		
		Start();
		
		while ( position_dechets.size() != 0 ) {
		
		System.out.println("");
		//recherche dechet le plus proche 
		recherche_distance(position_dechets);
		
		verifBat();
		
		
		
				}
			}
		}
