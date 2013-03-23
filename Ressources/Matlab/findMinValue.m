function [ a,b ] = findMinValue(M,N,x,y)
%FINDMINVALUE Permet de trouver l'indice median de la zone contenant la
%valeur la plus proche fournit en entrée par l'utilisatuer
%1 étape: Trouver la valeur la plus proche
[Y,idx] = min(((M(:)-x).^2+(N(:)-y).^2).^(1/2));
%2 étape: Trouver les zéros
A = abs(M-M(idx)*ones(size(M)));
%trouver les indices
[i,j] = find(A<=0);
%trouver le nombre 
n=ceil(size(i,1)/2);%voir pour le modulo cas pair
%récupérer le bon pixel
a = i(n);%ligne
b = j(n);%colonne

return

