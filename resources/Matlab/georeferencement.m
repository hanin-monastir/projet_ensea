%GEOREFERENCEMENT creer les matrices de coordonnees
%
% Utilisation: [H,lat,lon] = georeferencement(tab,siz,taille,nombre,z)
%
% Arguments:
%	tab	- Un tableau contenant les centres des images apres recollements
%	siz	- La taille de l'image finale
%	taille	- Le nombre de bande dans l'images
%	nombre	- Le nombre de photo par bande
%	z	- La zone UTM correspondant à la zone de survol
%
% Returns:
%	H L'homographie liant les pixels aux coordonnées
%	lat la matrice de latitude
%	lon la matrice de longitude
% 
function [H,lat,lon] = georeferencement(tab,Mosaique,taille,nombre,z)
%GEOREFERENCEMENT fonction de georeferencement
%Elle permet de trouver les matrices de coordonnées
disp('debut du géoréferencement');
%premier point 
pt1 = double(tab(1,:));
%deuxième point
pt2 = double(tab(nombre,:));
%troisième point
pt3 = double(tab(taille*nombre,:));

%colonne
tx = size(Mosaique,2);
%ligne
ty = size(Mosaique,1);
zone = z;

%conversion
disp('Projection des coordonnées vers UTM')
% on peut utiliser ransac, mais ça ne me donne pas de bons résultats 
% [E1,N1] = wgs2utm(tab(:,3),tab(:,4));
% 
% X1(1:size(tab,1),1:2) = [E1,N1];
% X2(1:size(tab,1),1:2) = tab(:,1:2);
% [H,ppositif] = ransacfithomography(X2', X1', 0.99);
%X = H(1:2,:);
[x1,y1] = wgs2utm(pt1(3),pt1(4));
[x2,y2] = wgs2utm(pt2(3),pt2(4));
[x3,y3] = wgs2utm(pt3(3),pt3(4));

disp('Calcul de l"homographie')
Y = [x1;y1;x2;y2;x3;y3];
H = [[pt1(2),pt1(1),1,0,0,0];[0,0,0,pt1(2),pt1(1),1];[pt2(2),pt2(1),1,0,0,0];[0,0,0,pt2(2),pt2(1),1];[pt3(2),pt3(1),1,0,0,0];[0,0,0,pt3(2),pt3(1),1]];
X = H^-1 * Y ;
X = reshape(X,3,2)';

disp('Calcul des coordonnees UTM de chaque pixel et retour aux coordonnees WGS84 ')
%creation des matrices d'indice
[Col, Lin] = meshgrid(1:tx,1:ty);
x = reshape(Col,1,tx*ty);
y = reshape(Lin,1,tx*ty);
z = ones(1,tx*ty);

%Calcul des coordonnées utm de chaque pixel
V = X*[x;y;z];
E(1:size(V,2)) = V(1,:)';
N(1:size(V,2)) = V(2,:)';

%retour aux vrais coordonnées
[lat,lon] = utm2wgs(E,N,zone);

%remise en forme
lat = reshape(lat,ty,tx);
lon = reshape(lon,ty,tx);
lat = double(lat);
lon = double(lon);

%on mets à 0 les zones sans pixel
Iz = find(rgb2gray(Mosaique));
M = zeros(ty,tx);
M(Iz) = 1;

lat = M .* lat;
lon = M .* lon;

disp('fin du géoréférencement');
end
