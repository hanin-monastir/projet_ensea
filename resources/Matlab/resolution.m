% RESOLUTION permet d'abaisser la taille des images si necessaire
%
% Utilisation: etat = resolution(dossier,liste,limit)
%
% Arguments:
%	dossier	- Le dossier contenant les bandes de photos
%	liste   - La liste des bandes de photos
%	limit	- Un tableau contenant les limites des images
%
% Returns:
%	etat = 1 si on abaisse la resolution
function etat = resolution(dossier,liste,limit)
larglimit = limit(1);
hautlimit = limit(2);
etat = false;

%..............TEST POUR SAVOIR SI IL FAUT REDIMENSIONNER
for i = 1:numel(liste)
	type = liste(i).isdir;
	nom = liste(i).name;
		
	%parcours des dossiers
	if type == 1 
		if nom ~= '.' 
			if nom ~= '..'
				nom = fullfile(dossier,nom);
				Photo = fullfile(nom,'/Photo');
				Copie = fullfile(nom,'/Copie');
				%on liste les images dans les dossiers
				
				photo = fullfile(Photo,'*.png');
				liste_photo = dir(photo);
				
				%on regarde la première images
				imi = fullfile(Photo,liste_photo(1).name);
				IMI = imread(imi);
				if size(IMI,1) < hautlimit || size(IMI,2) < larglimit
					etat = false;
					break;
				else
					etat = true;
					for t = 1:numel(liste_photo)
						imi = fullfile(Photo,liste_photo(t).name);
						IMI = imread(imi);
						
						coeff = hautlimit/size(IMI,1);
						IM2 = imresize(IMI,coeff);
						
						%on récupère le dossier et le nom de l'image'
						[Dossier,nm,ext] = fileparts(imi);
						destination = fullfile(nom,'/Copie');
						if ~exist(destination)
							disp(['creation du dossier ',destination]);
							mkdir(destination);
						end
						
						ima = [nm,ext];
						destination = fullfile(destination,ima);
						imwrite(IM2,destination);
					end
				end
			end
		end
	end
end
end
