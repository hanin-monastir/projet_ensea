function stitch(dossier,configuration)
%...............Configuration
	if exist(configuration)
		conf = load(configuration);
		res = [conf(1),conf(2)];
		num = num2str(conf(3));
		zone = [num ,' '];
		zone = [zone,char(conf(4))];
	
%...............Fichier final
		Mosaique_finale = fullfile(dossier,'/mosaique.png');
		Gps_finale = fullfile(dossier,'/gps.mat');
		latitude = fullfile(dossier,'latitude.txt');
		longitude = fullfile(dossier,'longitude.txt');

%...............enregistre les positions des centres 
		position = [];

%...............Nettoyage	
		if exist(Mosaique_finale)
			delete(Mosaique_finale);			
		end
		if exist(Gps_finale)
			delete(Gps_finale);
		end
		if exist(latitude)
		delete(latitude);
		end
		if exist(longitude)
			delete(longitude);
		end
	
%...............On liste les bandes contenues dans le dossier
		liste_bande = dir(dossier);
		%nombre de bande
		taille = numel(liste_bande);
		
%...............Redimenssionement des images si nécéssaire
		lowerR = resolution(dossier,liste_bande,res);
		
%...............Construction des bandes
		for i = 1:taille
			type = liste_bande(i).isdir;
			nom = liste_bande(i).name;
		
			if type == 1 
				if nom ~= '.' 
					if nom ~= '..'
						nom = fullfile(dossier,nom);
						if lowerR == false
							Photo = fullfile(nom,'/Photo');
						else
							Photo = fullfile(nom,'/Copie');
						end
						Gps = fullfile(nom,'/Gps');
						ext = '*.png';
						disp(Photo)
						disp(Gps)
						[position, nombre] = Panorama(Photo,ext,Gps,position);
						bande = fullfile(Photo,'/mosaique.png');
						
						%on recolle la nouvelle bande à la mosaique
						if ~exist(Mosaique_finale)
							copyfile(bande,dossier);
						else
							%l'image finale existe déjà on recolla la nouvelle bande avec la mosaique_finale
							%attention à modifier les positions des centres de la bandes pour les faire coller 
							%avec la mosaique finale			
							[Mosaique,position] = stitchBand(dossier,bande,position);							
						end
					end
				end	
			end
		end
		
%...............Vérification
		tab = [];
		for t = 1:size(position,2)
			tab = [tab;position{t}];
		end
		
%...............Géoréférencement
		[H,lat,lon]=georeferencement(tab,size(Mosaique),taille,nombre,zone);
		csvwrite(latitude,lat);
		csvwrite(longitude,lon);
	
		disp('Fin de la construction du panorama');
	else
		disp('Le fichier de configuration n"existe pas, operation annulee');
	end
end	
