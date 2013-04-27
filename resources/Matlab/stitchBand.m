function [Mosaique,position] = stitchBand(dossier,bande,position)
	mosaique_finale = fullfile(dossier,'/mosaique.png')
	%On applique la méthode de recollement sur la mosaique finale
	[Mosaique,H,bbox,MII,MIII] = Surf(mosaique_finale,bande);
	imwrite(Mosaique,mosaique_finale);
	
	for t = 1:size(position,2)-1
		pos = position{t};
		%on déplace les centres des bandes précédente pour les faire coller à la nouvelle
		for i = 1:size(pos,1)
			%on met à jours les coordonnées des anciens points dans la nouvelles box
			y1 = pos(i,1);
			x1 = pos(i,2);
			%mise à jours des coonnes
			x1 = abs(bbox(1)-x1)+1;
			%des lignes
			y1 = abs(bbox(3)-y1)+1;
			pos(i,1) = y1;
			pos(i,2) = x1;
		end	
		position{t} = pos;
	end
	
	%on met à jours les centres de la dernières bande qui ont été déplacé
	pos = position{size(position,2)};	
	for i = 1:size(pos,1);
		u = pos(i,1);%ligne
		v = pos(i,2);%colonne
		R = H*[v;u;1];
		%colonne
		x1 = round(R(1)/R(3));
		%ligne
		y1 = round(R(2)/R(3));	

		x1 = abs(bbox(1)-x1)+1;
		y1 = abs(bbox(3)-y1)+1;
		pos(i,:) = [y1 x1 pos(i,3) pos(i,4)]; 
	end
	position{size(position,2)} = pos;		
end
