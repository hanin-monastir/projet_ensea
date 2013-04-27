function [LAT1, LON1] = MapData(LAT1,LON1,LAT2,LON2,H,bbox,MII,MIII)
%MAPDATA
%la force de cette fonction est de pouvoir utiliser ce qui a déjà été fait
%pour le warping des images
size(LAT1);
size(LAT2);

%comme pour le warping des images, l'image 1 est juste agrandie
LAT1 = Warping(LAT1(:,:), eye(3), 'linear', bbox);
LON1 = Warping(LON1(:,:), eye(3), 'linear', bbox);

%l'image 2 est transformée
LAT2 = Warping(LAT2(:,:), H, 'linear', bbox);
LON2 = Warping(LON2(:,:), H, 'linear', bbox);

%on enlève les nan qui empêchent la somme
LAT1(isnan(LAT1)) = 0;
LON1(isnan(LON1)) = 0;
LAT2(isnan(LAT2)) = 0;
LON2(isnan(LON2)) = 0;

%on somme
LATA = LAT1; 
LATB = LAT2;
LONA = LON1; 
LONB = LON2;
LATC = (LAT1+LAT2)/2; 
LONC = (LON1+LON2)/2;

LATA(:,:) = MII .* LATA(:,:); 
LATB(:,:) = MII .* LATB(:,:);
LONA(:,:) = MII .* LONA(:,:); 
LONB(:,:) = MII .* LONB(:,:);
LATC(:,:) = MIII .* LATC(:,:);
LONC(:,:) = MIII .* LONC(:,:);

LAT1 = LATA + LATB + LATC;
LON1 = LONA + LONB + LONC;

save('gps.mat','LAT1','LON1');
end
