% ATTENUATION attenuer les bande noires entre recollement
%
% Utilisation: imc = Attenuation(imc,bande,M)
%
% Arguments:
%	imc	- La mosaique finale
%	bande	- Le nombre du pixel a prendre en compte
%	M	- Matrice donnant la zone de recouvrement entre deux images
%
% Returns:
%	imc l'image finale
function imc = Attenuation(imc,bande,M)
%ATTENUATION atténuer les bandes noires qui peuvent apparaitre
% on définit ici l'épaisseur sur lesquelle sera réalisé le moyennage
largeur = bande;%en pixel
[idl,idc] = find(M>0);
%pour toute les colonnes dans la bande on effectue la moyenne sur la ligne 
for i = 1:largeur
    for j=idl(1):idl(end)
    imc(j,idc(i),:) = mean(imc(j,idc(1):idc(largeur),:));
    imc(j,idc(end)-i+1,:) = mean(imc(j,idc(end)-largeur:idc(end),:));
    end
end


end

