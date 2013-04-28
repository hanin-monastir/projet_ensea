% RECOLLEMENT permet de recoller deux images
%
% Utilistion: [imd,MII,MIII] = Recollement(im1,im2)
%
% Arguments: 
%	im1	- Premiere image
%	im2	- Deuxieme images
%	
% Returns:
%	imd l'image finale
%	MII la zone de non superposition des images
%	MIII la zone de superposition des images
function [imd,MII,MIII] = Recollement(im1,im2)
%fonction qui permet de recoller les images proprement, on ouvre d'abord
%les images en gray afin de créer les matrices de zone. Une fois trouvées
%on peut éffecteur le lissage, l'amélioration de la teinte et enfin tout
%recoller
t = size(im1);

ima = im1;
imb = im2;
%pour la zone de recollement on prend la moyenne pondéré, on pondera par la
%suite par la distance au bord
imc = (im1+im2)/2;

%on ouvre les images en nuance de gris
I1 = rgb2gray(im1);
I2 = rgb2gray(im2);

%on créer une matrice contenant des 1
Aa = ones(t(1),t(2));

%on calcul les matrices
MI1=floor((256*Aa-I1)/256);%contient des 1 là où il y a l'image 1
MI2=floor((256*Aa-I2)/256);%contient des 1 là où il y a l'image 2
Ms=(MI1 + MI2);%contient des 0 dans la zone I, des 1 dans la zone II, et des 2 dans la zone III

MII=mod(Ms,2); %contient donc des 1 uniquement dans la zone II
MIII=floor((2*Aa-Ms)/2);%contient des 1 dans la zone III de recouvrement

%on applique un traitement sur la teinte voir si il faut le faire pour
%chacun des canaux
%on applique un traitement pour avoir une teinte uniformisée sur les troix
%canaux
idx = MIII > 0;
im2 = Teinte(im1,im2,idx);


%zone II image 1; 
ima(:,:,1) = MII .* im1(:,:,1);
ima(:,:,2) = MII .* im1(:,:,2);
ima(:,:,3) = MII .* im1(:,:,3);

%zone II image 2;
imb(:,:,1) = MII .* im2(:,:,1);
imb(:,:,2) = MII .* im2(:,:,2);
imb(:,:,3) = MII .* im2(:,:,3);

%zone III
imc(:,:,1) = MIII .* imc(:,:,1);
imc(:,:,2) = MIII .* imc(:,:,2);
imc(:,:,3) = MIII .* imc(:,:,3);

%on remplace les nan qui empêchent la somme
ima(isnan(ima))=0;
imb(isnan(imb))=0;
imc(isnan(imc))=0;

%on somme
imd=ima+imb+imc;

%on lisse l'image par la méthode des plus proches voisins
imd = Distance(MI1,MI2,MIII,im1,im2,imd);

%on attenue les bandes noires
%moyennage des bords gauche et droite sur 10 colonnes de pixel
%le résulta est bien meilleurs sans cete fonction
imd = Attenuation(imd,3,MIII);

%on applique un premier lissage
%imd = Lissage(imd,idx);

%on affiche les zoness
%figure(1);
%imshow(imd);
%figure(2);
%subplot(2,2,1);
%imshow(ime);
%subplot(2,1,2);
%imshow(imd);
%subplot(2,2,2);
%imshow(imc);

return
