function [position, nombre] = Panorama(photo,ext,gps,pos)
%on désactive les warnings
warning off
position = pos;
dossier = photo;
dossier_gps = gps;
extension = ext;
chargement = true;


%...............test sur les images
if exist(dossier)
    %chemin des photos
    mosaique = fullfile(dossier,'mosaique.png');
    if exist(mosaique)
    	delete(mosaique);
    	disp('suppression de la mosaique existante');
    end
    chemin = fullfile(dossier,extension);
    list = dir(chemin);
    nombre = numel(list);
else
    error('Le dossier de photo renseigné n existe pas');
    chargement = false;
end

%................test sur les fichier gps
if exist(dossier_gps)
	%chemin des données GPS
	chemin_gps = fullfile(dossier_gps,'*.txt');
	list_gps = dir(chemin_gps);

	if numel(list) ~= numel(list_gps)
   		chargement = false;
   	end
else
	chargment = false;
end

%................Construction de la bande
if chargement == true
	n = 1;
	%Travail sur les images
	while n ~= numel(list)+1
		if exist(mosaique)
			%on réutilise la mosaique
			im1 = mosaique;
			position{size(position,2)+1} = [];
		else
			%on prend une nouvelle images
			im1 = fullfile(dossier,list(n).name);
			absente = true;
			n = n + 1;
		end
		
		im2 =  fullfile(dossier,list(n).name);	
		
		%on recolle les image
		[Mosaique,H,bbox,MII,MIII] = Surf(im1,im2);

		%on enregistre la mosaique
		imwrite(Mosaique,mosaique);
	
		%on récupère les nouvelles positions des centres d'images' et on les ajoute au tab de position
		if absente == true
			%La première image ne bouge pas, ie H = id3
			IM1 = imread(im1);
			%colonne
			u = size(IM1,2)/2;
			%ligne
			v = size(IM1,1)/2;
			R = eye(3)*[u;v;1];
			x1 = round(R(1)/R(3));
			y1 = round(R(2)/R(3));
			x1 = abs(bbox(1)-x1)+1;
			y1 = abs(bbox(3)-y1)+1;
			gps1 = fullfile(dossier_gps,list_gps(n-1).name);
			[lat1, lon1] = Gps(gps1);
			
			position{size(position,2)+1} = [y1 x1 lat1 lon1];
		end
	
		%On mets à jours les anciennes images
		if n >= 3
			tab = position{size(position,2)};
			
			for i = 1:size(tab,1)
				%on met à jours les coordonnées des anciens points dans la nouvelles box
				y1 = tab(i,1);
				x1 = tab(i,2);
				%mise à jours des coonnes
				x1 = abs(bbox(1)-x1)+1;
				%des lignes
				y1 = abs(bbox(3)-y1)+1;
				tab(i,1) = y1;
				tab(i,2) = x1;
			end			
			position{size(position,2)} = tab;
		end
		
		IM2 = imread(im2);
		gps2 = fullfile(dossier_gps,list_gps(n).name); 
		[lat2,lon2] = Gps(gps2);
		
		u = size(IM2,2)/2;
		v = size(IM2,1)/2;
		R = H*[u;v;1];
		x1 = round(R(1)/R(3));
		y1 = round(R(2)/R(3));
		x1 = abs(bbox(1)-x1)+1;
		y1 = abs(bbox(3)-y1)+1;
		position{size(position,2)} = [position{size(position,2)};[y1 x1 lat1 lon1]];
			
		n = n+1;		
	end
else
	disp('Une erreur est survenue, il n"a pas le même nombre de fichier gps et photo"');
end
end
