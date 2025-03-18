/**
*@author I.Dussine , Y.Monnot
*partie du jeu de marienbad en joueur contre joueur
*/
class MarienbadJvsO_Dussine_Monnot2{
	void principal(){
		testCheckFin();
		testCalculSomme();
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
	 * test la méthode calculSomme
	 */
	void testCalculSomme(){
		System.out.println ();
		System.out.println ("*** testCalculSomme()");
		testCasCalculSomme(new int[]{1, 2, 3}, 0); 	// 1 XOR 2 XOR 3 = 0
		testCasCalculSomme(new int[]{5}, 5);	// 5 XOR 0 = 5
		testCasCalculSomme(new int[]{4, 4, 4}, 4); 	// 4 XOR 4 XOR 4 = 4
		testCasCalculSomme(new int[]{}, 0);	// XOR d'aucun élément = 0
	}
	
	/**
	 */
	void testCasCalculSomme(int[] t, int result){
		// Affichage
		System.out.print ("Somme XOR attendu : "+result);
		
		// Appel
		int resExec = calculSomme(t);
		
		// Verification
		if (resExec == result){
			System.out.println (" OK");
		} else{
			System.err.println (" ERREUR");
		}
	}

	/**
     * cette methode Ccree un tableau representant le nombre d'allumettes dans chaque ligne du jeu.
     * Les lignes contiennent des allumettes en nombre croissant.
     * @param tab Le tableau representant les lignes du jeu.
     */
	void creationTab(int[] tab){
		tab[0]=1;
		for (int i=1; i<tab.length;i+=1){
			tab[i]=tab[i-1]+2;
		}
	}

	/**
	 * affiche le nombre d'allumette dans chaque ligne sous forme de "|"
	 * @param t tableau representant les lignes du jeu.
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
	 * réduit le nombre d'alumette d'une ligne donnee
	 * @param t tableau representant les lignes du jeu.
	 * @param ligne ligne sur laquelle on enleve des allumettes
	 * @param nb nombre d'allumette a enlever sur la ligne
	 */
	void jeu(int[] t,int ligne,int nb){
		t[ligne]=t[ligne]-nb;
	}

	/**
	 * cette methode demande au joueur la ligne sur laquelle il veut jouer
	 * @param i longueur max du tableau
	 * @param t tableau representant les lignes du jeu.  
	 * @return l'indice de la ligne sur la quelle ont veux jouer
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
	 * cette methode demande le nombre d'allumette que l'utilisateur veut prendre
	 * @param i longueur max du tableau
	 * @param t tableau representant les lignes du jeu.
	 * @return le nombre d'alumette que l'utilisateur veut prendre
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
	 * verifie si la partie est terminee
	 * @param t tableau representant les lignes du jeu.
	 * @return false si la partie est fini, true sinon
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
	 * l'ordinateur joue un coup en prenent un nombre d'allummettes sur une ligne au hasard
	 * @param nb_ligne nombre de ligne disponible
	 * @param t tableau representant les lignes du jeu.
	 */
	void jeuIaRandom(int nb_ligne,int[] t){
		int ligne=(int)(Math.random()*nb_ligne);
		while((ligne<1 || ligne>=t.length) || t[ligne]==0){
			ligne=(int)(Math.random()*nb_ligne);
		}
		int nb_allu=(int)(Math.random()*t[ligne]);
		nb_allu+=1;
		System.out.println("l'IA joue dans la ligne "+(ligne+1) + " et prend "+nb_allu+ " allumettes");
		jeu(t,ligne,nb_allu);
	}
	
	/**
	 * l'ordinateur effectue un coup en utilisant la stratégie gagnante. 
 	 * Si aucun coup gagnant n'est possible, l'ordianateur effectue un coup aléatoire.
	 * @param t tableau representant les lignes du jeu.
	 */
	void jouerCoup(int[] t) {
		int somme = calculSomme(t); // Calcul de la somme de nim
		//trouver un coup gagnant
		for (int i = 0; i < t.length; i++) { // parcoure chaque ligne du tableau
			int ligne = t[i]; // sauvegarde la longeur d'une ligne
			if (ligne > 0) { // fait toute les instructions en dessuous si la ligne est non vide
				// Calculer la nouvelle valeur de la ligne après avoir appliqué le XOR avec la somme de nim
				int nouveauLigne = ligne ^ somme; // magie du XOR
				if (nouveauLigne < ligne) { // Assurer que le nouveau nombre d'allumettes est plus petit (et valide)
					int nbAllu = ligne - nouveauLigne; // Calculer combien d'allumettes à enlever.
					System.out.println("L'IA joue dans la ligne " + (i + 1) + " et prend " + nbAllu + " allumettes.");
					jeu(t, i, nbAllu); // L'IA enlève les allumettes calculées de la ligne choisie.
					return ; // on return rien car l'IA a trouvé un coup et pour evitér qu'elle joue deux fois
				}
			}
		}
		jeuIaRandom(t.length,t); // si l'IA n'as pas trouvé de coup elle joue aléatoirement
	}

	/**
	 * cette méthode cacul la somme XOR de toute les lignes du jeu
	 * utilisé dans la stratégie gagnante
	 * @param t tableau representant les lignes du jeu.
	 * @return le résultat de la somme XOR de tout les élément du tableau
	 */
	int calculSomme(int[] t) {
		int somme = 0;
		for (int i=0;i<t.length;i++) {
			somme ^= t[i]; // XOR de toutes les lignes.
		}
		return somme;
	}

	/**
	 * affiche les valeurs du tableau
	 * @param t tableau representant les lignes du jeu.
	 */	
	void displayTab(int[] t){
		int i = 0;
		System.out.print("{");
		while(i<t.length-1){
			System.out.print(t[i] + ",");
			i=i+1;
		}
		if (t.length==0){
			System.out.println("}");
		}else{
			System.out.println(t[i]+"}");
		}
	}

    /**
     * cette méthode gère le déroulement de la partie
     */
    void partie(){
        boolean fin=true;
		int ligne,nb_allu,tour,somme;
		String j=SimpleInput.getString("Nom du joueur : ");
		char first=SimpleInput.getChar("Voulez vous jouez en premier(y) ou non(n) : ");
		while(first!='y' && first!='n'){
			first=SimpleInput.getChar("Voulez vous jouez en premier(y) ou non(n)");
		}
		if(first=='y'){
			tour=1;
		}else{
			tour=0;
		}
		int i=SimpleInput.getInt("avec combien de ligne voulez vous jouez (de 2 a 15 lignes): ");
		while (i<2 || i>15){
			i=SimpleInput.getInt("avec combien de ligne voulez vous jouez (de 2 a 15 lignes): ");
		}
		int[] t=new int[i];
		char easy_mode=SimpleInput.getChar("Voulez vous jouez en mode facile (y) ou non(n) : "); //le mode facile correspond a l'ia en aléatoire autrement elle joue la stratégie gagnante
		while(easy_mode!='y' && easy_mode!='n'){
			easy_mode=SimpleInput.getChar("Voulez vous jouez en mode facile(y) ou non(n)");
		}
		creationTab(t);
		displayTab(t);
		while(fin){
			afficheAlumette(t);
			if (tour%2==0){
				System.out.println("c'est au tour de l'ia");
				if(easy_mode=='y'){
					jeuIaRandom(i,t);
				}else{	
					jouerCoup(t);
				}
			}else{
				System.out.println("c'est au tour de "+j);
				ligne=saisieLigne(i,t)-1;
				nb_allu=nbAlumette(ligne,t);
				jeu(t,ligne,nb_allu);
			}
			tour+=1;
			fin=checkFin(t);
			
		}
		if ((tour-1)%2==0){
			System.out.println("victoire de l'ia");
		}else{
			System.out.println("victoire de "+j);
		}
    }
}
