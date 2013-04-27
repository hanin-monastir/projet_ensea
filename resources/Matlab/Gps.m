function [Latitude, Longitude] = Gps(fgps)
%GPS permet de trouver les coordonnées de chaque pixel
% Pour cela, on lit le fichier gps associé à la photo
%on ouvre le fichier
fichier=fopen(fgps);

%on parse les infos
%Heure: $heure\nLatitude: $deg_N°$min_N'N\nLongitude: $deg_E°$min_E'E\nSatellites: $satellites
for i=1:4
    ligne = fgets(fichier);
    if i ~= 2 && i ~= 3
        champ = sscanf(ligne, '%*s %f');%on capte ici l'heure et les satellites
    elseif i == 2
        champ = sscanf(ligne, '%*s %f°%f%*s');
        Latitude=champ(1)+champ(2)/60;
    elseif i == 3
        champ = sscanf(ligne, '%*s %f°%f%*s');
        Longitude=champ(1)+champ(2)/60;
    end
end



return

