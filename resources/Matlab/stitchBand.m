function stitchBand(dossier,bande,gps)
	gps_finale = fullfile(dossier,'/gps.mat')
	mosaique_finale = fullfile(dossier,'/mosaique.png')
	
	%on charge les fichiers nécéssaire;
	load(gps);
	LATF = LAT1;
	LONF = LON1;
	
	load(gps_finale);
	
	%On applique la méthode de recollement sur la mosaique finale
	[Mosaique,H,bbox,MII,MIII] = Surf(mosaique_finale,bande);
	%traitement des données gps1
	[LAT1, LON1] = MapData(LATF,LONF,LAT1,LON1,H,bbox,MII,MIII); 
	
	%on sauvegarde les données au bon endroits
	imwrite(Mosaique,mosaique_finale);
	copyfile('gps.mat',dossier);	
end
