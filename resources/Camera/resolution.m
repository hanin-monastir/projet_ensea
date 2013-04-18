%TOUTES LES DISTANCES SONT EN METRE
%Résolution désirée
res = 1e-1;
%Altitude désirée
alt = 20;
%focale
f = 4e-3;
%Taille de la matrice de pixel
largeur = 3673.6e-6;
hauteur = 2738.4e-6;
%Taille d'un pixel
p = 1.4e-6;
%repartition de l'information 
k = 4/3;

disp('Données pour le calcul de la résolution');
disp(['Résolution en metre recherchée: ',num2str(res),' m'])
disp(['Altitude moyenne de vol: ',num2str(alt),' m'])
disp(['Focale de la caméra: ',num2str(f),' m'])
disp(['Largeur de la matrice de pixel: ',num2str(largeur),' m'])
disp(['Hauteur de la matrice de pixel: ',num2str(hauteur),' m'])
disp(['dimension d"un pixel: ',num2str(p),' m'])
disp(' ')

%On applique Thales
n1 = (largeur*alt)/(res*f);
n2 = (hauteur*alt)/(res*f);
disp('Calcul des résolutions en largeur et hauteur')
disp('En largeur: n = (largeur*altitude)/(resolution_cherchée*focale)')
disp('En hauteur: n = (hauteur*altitude)/(resolution_cherchée*focale)')
disp(' ')
disp(['resolution sur la largeur: ',num2str(n1),' pixels'])
disp(['resolution sur la hauteur: ',num2str(n2),' pixels'])
disp(['resolution totale de l"image: ', num2str(n1*n2),' pixels'])

%Vérification de la formule en trouvant la résolution en mètre
%correspondant à une image 5M ie 2592*1800, on inverse la formule
%précédente pour obtenir la resolution
%TEST sur la largeur
nl5M = 2592;
resl5M = largeur*alt/(f*nl5M);
nh5M = 1944;
resh5M = hauteur*alt/(f*nh5M);
disp(' ')
disp('Vérification des données')
disp('1: Inversion de la formule pour trouver la resolution en metre d"une image 5M')
disp(['La résolution sur la largeur d"une photo 5M est: ',num2str(resl5M),' m'])
disp(['La resolution sur la hauteur d"une photo 5M est ',num2str(resh5M),' m'])

%Deuxième test demandé par Mr Tang
%Un pixel en 5MP correspond à 1.4e-6, on applique thales pour obtenir la
%resolution, on calcul la resolution sur la largeur 
res5M = alt/f*p;
disp('Vérification avec la valeur attendue avec les données de la caméra, pour une photo en 5M utilisant tous les pixels')
disp(['La résolution en 5M sur la largeur vaut: ', num2str(res5M),' m']);
