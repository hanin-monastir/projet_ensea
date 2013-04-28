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
function [H,lat,lon] = georeferencement(tab,siz,taille,nombre,z)
%GEOREFERENCEMENT fonction de georeferencement
%Elle permet de trouver les matrices de coordonnées
disp('debut du géoréferencement');
%premier point 
pt1 = tab(1,:);
%deuxième point
pt2 = tab(nombre,:);
%troisième point
pt3 = tab((taille-2)*(nombre-1),:);

%colonne
tx = siz(2);
%ligne
ty = siz(1);
zone = z;

%conversion
disp('Projection des coordonnées vers UTM')
[x1,y1] = wgs2utm(pt1(1),pt1(2));
[x2,y2] = wgs2utm(pt2(1),pt2(2));
[x3,y3] = wgs2utm(pt3(1),pt3(2));

disp('Calcul de l"homographie')
Y = [x1;y1;x2;y2;x3;y3];
H = [[1,1,1,0,0,0];[0,0,0,1,1,1];[tx,1,1,0,0,0];[0,0,0,tx,1,1];[tx,ty,1,0,0,0];[0,0,0,tx,ty,1]];
X = H^-1 * Y ;
X = reshape(X,3,2)';

disp('Calcul des coordonnees UTM de chaque pixel et retour aux coordonnees WGS84 ')
%creation des matrices d'indice
[Col, Lin] = meshgrid(1:tx,1:ty);
x = reshape(Col,1,tx*ty);
y = reshape(Lin,1,tx*ty);
z = ones(1,tx*ty);
%Calcul des coordonnées utm de chauqe pixel
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
disp('fin du géoréférencement');
end
