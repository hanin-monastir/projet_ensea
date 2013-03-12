function [ima,H,bbox,MII,MIII] = Surf(image1, image2)
%cette fonction permet de recoller deux images
%certains warning de prévention apparaissent
%detection
[F1, F2, pointsF1, pointsF2] = Detection(image1, image2);
%extraction
[features1, validPoints1, features2, validPoints2] = Extraction(F1,F2,pointsF1,pointsF2);

%appariement
[match1, match2] = Matching(features1, validPoints1, features2, validPoints2);

%on affiche les points appariés
%Showkeys(F1,F2,match1,match2);

%Recherche de l'homographie grace à RANSAC
%retourne l'homographie et les indices des points les mieux appariés gardé après l'algo ransac
[H, ppositif ] = Homographie(match1, match2);


%on affiche utile mais prend du temps pas besoin si automatisation
%Showhomographie(F1, F2, match1, match2, ppositif);

%on va rassembler les images
%on créer une boite pour contenir les deux images
%on calcul la taille optimale de l'image finale et on calcul aussi la zone de recouvrement
bbox= Box(imread(image1),imread(image2),H);
Im1 = imread(image1);
Im2 = imread(image2);
%on prend comme origine la premiere image en utilisant matrice unité comme homographie
im1 = Warping(Im1, eye(3), 'linear', bbox);
%on applique l'homographie à l'image 2
im2 = Warping(Im2, H, 'linear', bbox);  

%quand ça marchera on pourra l'utiliser
[ima,MII,MIII] = Recollement(im1,im2);
return
