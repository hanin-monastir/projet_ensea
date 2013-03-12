function imd = Distance(MI1,MI2,MIII,im1,im2,imd)
%DISTANCE permet de pondérer les pixels par la distance au coté dss images
%on cherche les deux droite les plus proches
%ici MI1 estla matrice qui contient des 1 ou se trouve l'image 1
%idem pour MI2
%MIII est la matrice qui contient des 1 dans la zone de recouvrement
%on créer des matrices qui contiennent des 1 
U1 = ones(size(MI1));
U2 = ones(size(MI2));
%on calcul la  distance de chaque pixel de U1 au bord

%On trouve les positions respectives des images
I1 = MI1 > 0;
I2 = MI2 > 0;

%on mets des 0 dans les matrice U aux bons endroits ie ou il y a les images
U1(I1) = 0;
U2(I2) = 0;

%Pour ne pas avoir d'erreur on remet des 1 en 1ligne/colonne dr
%ligne/colonne
U1(1,:) = 1;
U1(:,1) = 1;
U1(end,:) = 1;
U1(:,end) = 1;

U2(1,:) = 1;
U2(:,1) = 1;
U2(end,:) = 1;
U2(:,end) = 1;


% MI1     MI2    U1(I1)  U2(I2)  MIII
% 0011    0000   1111    1111    0000
% 0011    0011   1101    1101    0011
% 0000    0011   1111    1101    0000
% 0000    0000   1111    1111    0000



%on obtient les matrices de distance euclidienne par rapport au bord le
%plus proche des pixels le bord étant le premier 1 trouvé
[D1,IDX1] = bwdist(U1);
[D2,IDX2] = bwdist(U2);

%on obtient les matrices de distances en ne gardant des 1 que dans la bonne
%zone
d1 = D1 .* MIII;
d2 = D2 .* MIII;

%on remplace les zeros par des 1 dans les deux matrices pour ne pas diviser
%par 0 pendant la pondération
K1 = d1 < 1;    
K2 = d2 < 1;
%obliger de mettredes 1 sinon a/0
d1(K1)=1;
d2(K2)=1;

%on obtient la somme 
Sd = d1+d2;

%on pondère les pixels par leur distance au bord
%ici on travail sur toute la matrice pour gagner du tempas par la suite on
%pourra travailler que sur la zone de recouvrement en remplaçant les :,:
%par l'emplacement des 1 dans la matrice MIII
%cette méthdoe permet de ne pas utiliser de boucle for balayant chaque
%pixel des matrices
for i = 1:3
    imc(:,:,i) = MIII .* (d1 .* im1(:,:,i) + d2 .* im2(:,:,i)) ./ Sd;
end
%on rajoute un test pour mettre à 0 les nan et inf
imc(isnan(imc)) = 0;
imc(isinf(imc)) = 0;

%on reconstitue l'image finale en supprimant l'ancienne zone de
%recouvrement et en y ajoutant la nouvelle
for i = 1:3
   imd(:,:,i) = imd(:,:,i) - MIII .* imd(:,:,i) + imc(:,:,i); 
end

return
