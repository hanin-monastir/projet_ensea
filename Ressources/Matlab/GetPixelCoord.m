function [lat,lon] = GetPixelCoord(x,y)
%GETPIXEL Retourne les coordonnées d'un pixel de l'image finale
if exist('gps.mat')
    load gps.mat; 
end

%attention x correspond à l'abscisse
%et y correspond à l'ordonnée ie nombre de ligne
if x < 0 || y < 0 || size(LAT1,2) < x || size(LAT1,1) < y
    lat = 0;
    lon = 0;
else
    lat = LAT1(x,y);
    lon = LON1(x,y);
end

return

