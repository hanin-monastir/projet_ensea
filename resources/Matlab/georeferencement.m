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
for j = 1:tx
    for i = 1:ty
        V = X*[j;i;1];
        %conversion
        [lat(i,j),lon(i,j)] = utm2deg(V(1),V(2),zone); 
    end
end

disp('fin du géoréférencement');
end
