/**
*@author I.Dussine , Y.Monnot
*partie du jeu de marienbad en joueur contre joueur
*/
class MarienbadJvsJ_Dussine_Monnot2{
	void principal(){
		testCheckFin();
		char debut=SimpleInput.getChar("voulez vous lancer une partie (y si oui n si non) : ");
		while(debut!='y' && debut!='n'){
			debut=SimpleInput.getChar("voulez vous lancer une partie (y si oui n si non) : ");
		}
		if (debut=='y'){
			System.out.println("début de la partie  : ");
			partie();
		}
	}
	
	/**
	 * test la méthode saisieLigne
	 */
	void testCheckFin(){
		System.out.println ();
		System.out.println ("*** testCheckFin()");
		testCasCheckFin (new int[]{1,3,5,7,9}, true);
		testCasCheckFin (new int[]{0,3,2,7,0}, true);
		testCasCheckFin (new int[]{0,0,0,0,0}, false);
	}
	
	/**
	 * @param t tableau representant les lignes du jeu.
	 * @param result le résultat désiré
	 */
	void testCasCheckFin(int[] t, boolean result){
		// Affichage
		System.out.print ("la partie est fini : "+result);
		
		// Appel
		boolean resExec = checkFin(t);
		
		// Verification
		if (resExec == result){
			System.out.println (" OK");
		} else{
			System.err.println (" ERREUR");
		}
	}
	/**
	 * creer le tableau representant le nombre d'allumette par ligne
	 * @param tab le tableau à remplir
	 */
	void creationTab(int[] tab){
		tab[0]=1;
		for (int i=1; i<tab.length;i+=1){
			tab[i]=tab[i-1]+2;
		}
	}
	
	/**
	 * affiche les allumette dans chaque ligne
	 * @param t le tableau contenant le nombre d'allumettes
	 */
	void afficheAlumette(int[] t){
		for (int i=0;i<t.length;i+=1){
			System.out.print("\u001B[32m"+(i+1)+" "+"\u001B[0m"); // affichage en couleur avec "\u001B[32m" et "\u001B[0m"
			for(int j=0;j<t[i];j+=1){
				System.out.print("|");
			}
			System.out.println("\u001B[32m"+" " +t[i]+"\u001B[0m");
		}
	}
	
	/**
	 * met à jour le tableau
	 * @param t tableau representant les lignes du jeu.
	 * @param ligne ligne sur laquelle le joueur à joué
	 * @param nb nombre d'allumettes retiré sur la ligne
	 */
	void jeu(int[] t,int ligne,int nb){
		t[ligne]=t[ligne]-nb;
	}
	
	/**
	 * demande au joueur de saisir le numoré de la ligne
	 * vérifie si elle est valide ou pas
	 * @param i le nombre max de ligne
	 * @param t tableau representant les lignes du jeu.
	 * @return l'indice de la ligne saisi par le joueur
	 */
	int saisieLigne(int i ,int[] t){
		int ligne = SimpleInput.getInt("En quelle ligne voulez-vous jouer : ");
		while(ligne < 1 || ligne > t.length || t[ligne - 1] == 0) {
			if(ligne < 1) {
				ligne = SimpleInput.getInt("Il faut jouer sur une ligne positive : ");
			} else if(ligne > t.length) {
				System.out.println("On ne peut pas jouer sur cette ligne.");
				ligne = SimpleInput.getInt("En quelle ligne voulez-vous jouer : ");
			} else {
				System.out.println("Cette ligne est vide.");
				ligne = SimpleInput.getInt("En quelle ligne voulez-vous jouer : ");
			}
		}
		return ligne;
	}
	
	/**
	 * demande au joueur le nombre d'allumettes qu'il veut retiré
	 * @param i le nombre max de ligne
	 * @param t tableau representant les lignes du jeu.
	 * @return le nombe d'allumettes à retirer
	 */
	int nbAlumette(int i,int[] t){
		int nb=SimpleInput.getInt("combien d'allumette voulez vous prendre :");
		while(nb<1 || nb>t[i]){
			if(nb<1){
				System.out.println("vous devez jouer au moins 1 allumette");
			}else{
				System.out.println("il n'y a pas autant d'allumette sur cette ligne");
			}
			nb=SimpleInput.getInt("combien d'allumette voulez vous prendre :");
		}
		return nb;
	}
	
	/**
	 * vérifie si la partie est terminée
	 * @param t tableau representant les lignes du jeu.
	 * @return false si la partie est fini,true sinon
	 */
	boolean checkFin(int[] t){
		boolean fin=false;
		for(int i=0;i<t.length;i+=1){
			if(t[i]!=0){
				fin=true;
			}
		}
		return fin;
	}
	
	/**
	 * partie gère le déroulement du jeu
	 * damnde les noms des joueurs, le nombre de ligne à jouer
	 * alterne les tour entre les joueurs
	 */
	void partie(){
		boolean fin=true;
		int tour=0;
		int ligne,nb_allu;
		String j1=SimpleInput.getString("Nom du joueur 1 : ");
		String j2=SimpleInput.getString("Nom du joueur 2 : ");
		int i=SimpleInput.getInt("avec combien de ligne voulez vous jouez (de 2 a 15 lignes): ");
		while (i<2 || i>15){
			i=SimpleInput.getInt("avec combien de ligne voulez vous jouez (de 2 a 15 lignes): ");
		}
		int[] t=new int[i];
		creationTab(t);
		while(fin){
			afficheAlumette(t);
			if (tour%2==0){
				System.out.println("c'est au tour de "+j1);
				ligne=saisieLigne(i,t)-1;
				nb_allu=nbAlumette(ligne,t);
			}else{
				System.out.println("c'est au tour de "+j2);
				ligne=saisieLigne(i,t)-1;
				nb_allu=nbAlumette(ligne,t);
			}
			jeu(t,ligne,nb_allu);
			tour+=1;
			fin=checkFin(t);
		}
		if ((tour-1)%2==0){
			System.out.println("victoire de "+j1);
		}else{
			System.out.println("victoire de "+j2);
		}
	}
}
