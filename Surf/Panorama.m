function Panorama(photo,ext,gps)
%fonction qui permet de créer le panorama, les images sont rendu accessible
%via le code ci dessous. On indique le nom du dossier contenant les images
%et l'extension des images. La mosaique sera reprise lorsqu'elle existe
%afin d'y ajouter la nouvelle image
%si l'image mosaique existe en début de fonction, on la supprime pour pouvoir créer la nouvelle
%on désactive les warnings
warning off
%on regarde le nombre d'argument
switch nargin
    case 0
        dossier = 'Photo';
        dossier_gps = 'Gps';
        extension ='*.png';
    case 1
        dossier = photo;
        %dossier_gps = 'Gps';
        extension = '*.png';
    case 2
        dossier = photo;
        extension = ext;
        %dossier_gps = 'Gps';
    case 3
        dossier = photo;
        dossier_gps = gps;
        extension = ext;
    otherwise
        error('1 argument est requis au minimum');    
end

mosaique = fullfile(dossier,'mosaique.png');
if exist(mosaique)
	delete(mosaique);
end

if exist(dossier)
    %chemin des photos
    chemin = fullfile(dossier,extension);
    list = dir(chemin);
else
    error('Le dossier de photo renseigné n existe pas');
end

etat_gps = false;
if exist(dossier_gps)
%chemin des données GPS
chemin_gps = fullfile(dossier_gps,'*.txt');
list_gps = dir(chemin_gps);

    if numel(list) == numel(list_gps)
        etat_gps = true;
        gps_mosaique ='gps.mat';
    
        if exist('gps.mat')
            delete('gps.mat'); 
	   	end
    end
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%on va rajouter un peu de calcul permettant de trouver l'ordre d'ajout des
%images pour ce faire on va voir quelles images ont le plus de point
%d'intêret en commun
%plus tard on pourra modifier pour ne pas faire deux fois le même calcul
for i=1:numel(list)
    imi = fullfile(dossier,list(i).name);

    for j = i+1:numel(list)
        %on ouvre les images
        imj = fullfile(dossier,list(j).name);
        %on déroule la méthode pour trouver les points d'interet pas
        %optimal d'utiliser ces fonctions
        [F1, F2, pointsF1, pointsF2] = Detection(imi, imj);
        [features1, validPoints1, features2, validPoints2] = Extraction(F1,F2,pointsF1,pointsF2);
       
        %ici il va falloir stocker ces points car cela ne sert à rien de
        %les recalculer dans Surf
        %appariement
        [match1, match2] = Matching(features1, validPoints1, features2, validPoints2);
        S(i,j) = size(match1,1);
        S(j,i) = size(match1,1); 
    end  
end
%Si on a n images dans le dossier la matrice S d'appariement est alors carré
%de taille nxn avec des 0 sur la diagonale
%on somme sur la ligne pour connaitre le nombre de point d'interet avec les
%autres images
Somme = sum(S(:,1:size(S,2)));
[ordre,idx] = sort(Somme,2,'descend');
%comme on connait l'emplacement des données GPS ainsi que le nom de/des photos en cous on peut peut être 
%trouver le/les fichiers GPS contenant les données relaties à ces images afin de leur appliquer le même traitement 
%sans matching mais directement appliquer l'homographie
%on peut appliquer un sscanf sur le nom de l'image au cas ou
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
n=1;
while n ~= numel(list)+1
    %on fait en sorte de déformer le moins possible l'image finale
    if exist(mosaique)
        im1 = mosaique;
        if etat_gps == true
            if exist(gps_mosaique)
                chargement = true;
                %ici on obtient directement la matrice des données pas
                %besoin de parser un fichier de donnée gps
            end
        end
    else
        im1 = fullfile(dossier,list(idx(n)).name);
        
        if etat_gps == true
	    disp(n)
            gps1 = fullfile(dossier_gps,list_gps(idx(n)).name);
            %on créer une matrice contenant les lat/lon
            [Lat1, Lon1] = Gps(gps1);
            Im1=imread(im1);
            LAT1(:,:) = ones(size(Im1,1),size(Im1,2)) .* Lat1;
            LON1(:,:) = ones(size(Im1,1),size(Im1,2)) .* Lon1;
            chargement = false;
        end
        n = n + 1;
    end
    
    im2 = fullfile(dossier,list(idx(n)).name);
   
    if etat_gps == true
	disp(n)
	Im2=imread(im2);
        gps2 = fullfile(dossier_gps,list_gps(idx(n)).name);
        %on créer une matrice contenant les lat/lon
        [Lat2, Lon2] = Gps(gps2);
	
	whos LAT2
	whos LON2
        LAT2 = ones(size(Im2,1),size(Im2,2)) .* Lat2;
        LON2 = ones(size(Im2,1),size(Im2,2)) .* Lon2;
    end
    
   
    
    [Mosaique,H,bbox,MII,MIII] = Surf(im1,im2);
	imwrite(Mosaique,mosaique);
    n = n+1;
    
    %on créer la matrice GPS correspondante à la mosaique et on la
    %sauvegarde dans gps.mat
    %En argument de la fonction, on envoit le dossier gps, gps1 et/ou gps2
    %et ou gpsm (ou gpsm sera la matrice de coordonnées de la mosaique que
    %l'on a chargé
    if etat_gps == true
       %si le chargement à eu lieu ie on envoit la matrice
       %correspondant
       [LAT1, LON1] = MapData(LAT1,LON1,LAT2,LON2,H,bbox,MII,MIII);     
    end
end
%ici on peut afficher l'image finale
%imshow(mosaique);
%on peut aussi retourner LAT1 et LON1
%on réactive les warnings
warning on;
