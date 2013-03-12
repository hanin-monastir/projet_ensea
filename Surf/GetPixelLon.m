function out = GetPixelLon(x,y)
%GETPIXELLAT retourne la latitutde

if exist('gps.mat')
    load gps.mat; 
end

%attention x correspond à l'abscisse
%et y correspond à l'ordonnée ie nombre de ligne
if x < 0 || y < 0 || size(LON1,2) < x || size(LON1,1) < y
    lon = 0;
else
    lon = LON1(x,y);
end

out = lon;