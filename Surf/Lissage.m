function ima = Lissage(ima,R)
%fonction qui permet d'effectuer un lissage de la zone de superposition
%on applique un filtre de lissage sur la zone de recouvrement
%Je l'ai implémenté avant de réussir à écrire une fonction Distance
%fonctionnelle peut-être n'est elle plus nécéssaire
for i = 1:3
    imf = R .* ima(:,:,i) ;
    ima(:,:,i) = ima(:,:,i) - imf + R .* medfilt2(imf);
end


return
