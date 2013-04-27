function stitch(dossier)
	% Fichier final
	Mosaique_finale = fullfile(dossier,'/mosaique.png');
	Gps_finale = fullfile(dossier,'/gps.mat');
	if exist(Mosaique_finale)
		delete(Mosaique_finale);			
	end
	if exist(Gps_finale)
		delete(Gps_finale);
	end

	% On liste les bandes contenues dans le dossier
	liste_bande = dir(dossier);
	taille = numel(liste_bande);
	
	% on parcourt les différentes bandes
	for i = 1:taille
		type = liste_bande(i).isdir;
		nom = liste_bande(i).name;
		
		if type == 1 
			if nom ~= '.' 
				if nom ~= '..'
					nom = fullfile(dossier,nom);
					Photo = fullfile(nom,'/Photo');
					Gps = fullfile(nom,'/Gps');
					ext = '*.png';
					disp(Photo)
					disp(Gps)
					%on recolle les images du dossier pour créer une bande
					Panorama(Photo,ext,Gps);
					bande = fullfile(Photo,'/mosaique.png');
					
					%on recolle la nouvelle bande à la mosaique
					if ~exist(Mosaique_finale)
						copyfile(bande,dossier);
						copyfile('gps.mat',dossier);
					else
						%l'image finale existe déjà on recolla la nouvelle bande avec la mosaique_finale			
						stitchBand(dossier,bande,'gps.mat');							
					end
				end
			end
		end	
	end
end
