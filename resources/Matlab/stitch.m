% STITCH permet de creer le panorama georeference
%
% Utilisation: stitch(dossier,configuration)
%
% Arguments: 
%	dossier		- Le dossier contenant les données gps et photos
%	configuration	- Le fichier de configuration
%
% Notes: le fichier de configuration est ainsi 
% 	ligne 1 	- coefficient de redimensionnement
%	ligne 3		- numero de la zone UTM
%	ligne 4		- code ASCII de la lettre de la zone UTM
%	ligne 5		- 1 si on utilise le recollement optimale
% 
function stitch(dossier,configuration)
%...............Configuration
	if exist(configuration)
		conf = load(configuration);
		coeff = conf(1);
		num = num2str(conf(2));
		zone = [num ,' '];
		zone = [zone,char(conf(3))];
		build = conf(4);
	
%...............Fichier final
		Mosaique_finale = fullfile(dossier,'/mosaique.png');
		latitude = fullfile(dossier,'latitude.txt');
		longitude = fullfile(dossier,'longitude.txt');

%...............enregistre les positions des centres 
		position = [];

%...............Nettoyage	
		if exist(Mosaique_finale)
			delete(Mosaique_finale);			
      		end
		if exist(latitude)
            		delete(latitude);
		end
		if exist(longitude)
			delete(longitude);
        	end
        	if exist('panorama.log')
        		delete('panorama.log'); 
        	end
%...............On liste les bandes contenues dans le dossier
		liste_bande = dir(dossier);
		%nombre de bande
		taille = numel(liste_bande);
		
%...............Redimenssionement des images si nécéssaire
		[lowerR,ext] = resolution(dossier,liste_bande,coeff);
		
		nombre_bande = 0;
%...............Construction des bandes
		for i = 1:taille
			type = liste_bande(i).isdir;
			nom = liste_bande(i).name;
		
			if type == 1 
				if ~strcmp(nom, '.') 
					if ~strcmp(nom, '..')
						nombre_bande = nombre_bande + 1;
						nom = fullfile(dossier,nom);
						if lowerR == false
							Photo = fullfile(nom,'/Photo');
						else
							Photo = fullfile(nom,'/Copie');
						end

						Gps = fullfile(nom,'/Gps');
                                            
						ext = ['*',ext];
						disp(Photo)
						disp(Gps)
						[position, nombre,logstate] = buildBand(Photo,ext,Gps,position,build);
						
						if(logstate == true)
							break;
						end
							
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
		
		if(logstate == false) 
%.......................conversion
			tab = [];
       			for t = 1:size(position,2)
				tab = [tab;double(position{t})];
			end
			
%.......................Géoréférencement
			[~,lat,lon]=georeferencement(tab,Mosaique,nombre_bande,nombre,zone);
			dlmwrite(latitude,lat,'precision',9);
			dlmwrite(longitude,lon,'precision',9);
        
        		[i0,j0] = find(Mosaique(:,:,1) == 0 & Mosaique(:,:,2) == 0 & Mosaique(:,:,3) == 0);
       			noir=size(i0,1)/(size(Mosaique,1)*size(Mosaique,2))*100;
        		disp(['pourcentage de déformation ',num2str(noir),'%']);
        
			disp('Fin de la construction du panorama');
		end
	else
		Log('Le fichier de configuration n"existe pas, operation annulee');
	end
end	
