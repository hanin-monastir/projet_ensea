function C = rebuild(X)
%REBUILD Summary of this function goes here
%   Detailed explanation goes here
A=[];
%trouve les indices des points non nuls
%en pratique 1/200
[i,j] = find(X);

%limite de la zone 
minx = min(j); maxx = max(j); miny = min(i); maxy = max(i);
%sera utile pour l'extension de la matrice ie restaurer les lignes
%totalement nulle
box = [minx maxx miny maxy];

%Extraction de la sous matrice ne contenant aucune ligne/colonne nulle
C(1:1+maxy-miny,1:1+maxx-minx)=X(miny:maxy,minx:maxx);
[k,l] = find(C);

%Création du vecteur de valeur pour le fitting
for m = 1:size(k,1)
   A = [A;C(k(m),l(m))];
end

%trouve le modèle par fitting
P=fit([k,l],A,'lowess')

%on reconstruit la matrice, en remplaçant les 0 par la valeur du modèle
[e,f] = find(~C);
for t = 1:size(e,1)
   C(e(t),f(t)) = P(e(t),f(t)); 
end
for t = 1:size(k,1)
  C(k(t),l(t)) = P(k(t),l(t)); 
end



% Durant, la reconstruction certains points NaN peuvent apparaitre pour corriger
% ce problème, on convolue pour avoir la moyenne des points autours
% Création des matrices qui vont subir la convolution
idx = isnan(C);
C(idx) = 0;

D = zeros(size(C));
D(idx) = 1;
dist = bwdist(D);
sub = sqrt(2)*ones(size(C));
dist(~idx) = sub(~idx) - dist(~idx);

D=zeros(size(C));
D(~idx) = C(~idx) .* dist(~idx);

I = abs(C) > 0;
%on est obligé de convertir
I = double(I);
I(~idx) = I(~idx) .* dist(~idx);

%création du masque
B = ones(3);
B(2,2)=0;

%convolution
Q = conv2(I,B);
R = conv2(D,B);

%Extraction des bonnes lignes et colonnes
Q = Q(2:end-1,2:end-1);
R = R(2:end-1,2:end-1);

%Matrice moyenne
Z = R ./ Q;

%Correction des NaN par les valeurs moyennes
C(idx) = Z(idx);

%extension de la matrice
C=adjustA(X,C,box);

end

