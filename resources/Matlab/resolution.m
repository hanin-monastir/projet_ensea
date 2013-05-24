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
%   ext      extension des photos
function [etat, ext] = resolution(dossier,liste,coeff)
etat = false;

%..............TEST POUR SAVOIR SI IL FAUT REDIMENSIONNER
for i = 1:numel(liste)
	type = liste(i).isdir;
	nom = liste(i).name;
		
%.......parcours des dossiers
	if type == 1 
		if ~strcmp(nom, '.') 
			if ~strcmp(nom, '..')
				nom = fullfile(dossier,nom);
				Photo = fullfile(nom,'/Photo');
				%Copie = fullfile(nom,'/Copie');
%...............................on liste les images dans les dossiers				
				photo = fullfile(Photo,'*.png');
				liste_photo = dir(photo);
				
%...............................on regarde la première images, pour connaittre la taille des images
				imi = fullfile(Photo,liste_photo(1).name);
				IMI = imread(imi);
				if size(IMI,1) <= coeff*size(IMI,1) || size(IMI,2) <= coeff*size(IMI,2)
					etat = false;
                    %on récupère l'extension des images
                    [~,~,ext] = fileparts(imi);
					break;
				else
					etat = true;
					for t = 1:numel(liste_photo)
						if ~strcmp(liste_photo(t).name,'mosaique.png')
							imi = fullfile(Photo,liste_photo(t).name);
							IMI = imread(imi);
							
							%coeff = hautlimit/size(IMI,1);
							IM2 = imresize(IMI,coeff);
						
%.......................................................on récupère le dossier et le nom de l'image
							[~,nm,ext] = fileparts(imi);
							destination = fullfile(nom,'/Copie');
							if ~exist(destination)
								disp(['creation du dossier ',destination]);
								mkdir(destination);
							end
						
							ima = [nm,ext];
							destination = fullfile(destination,ima);
%.......................................................ecriture de la copie
							imwrite(IM2,destination);
						end
					end
				end
			end
		end
	end
end
end
