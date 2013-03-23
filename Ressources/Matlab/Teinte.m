function im2 = Teinte(im1,im2,R)
%fonction qui modifie la teinte de l'image en égalisant les niveaux de gris
%de chaque sous calque RVB
%on ouvre les différents canaux
%R
m11 = mean(mean(R .* im1(:,:,1)));
m21 = mean(mean(R .* im2(:,:,1)));
%V
m12 = mean(mean(R .* im1(:,:,2)));
m22 = mean(mean(R .* im2(:,:,2)));
%B
m13 = mean(mean(R .* im1(:,:,3)));
m23 = mean(mean(R .* im2(:,:,3)));

% on modifie l'image 2 pour égaliser les niveaux de gris moyen ie im2 *
% m_im1/m_im2
% on rajoutera un test pour tester le produit m_im1/m_im2*pixel afin de ne pas avoir d'abbération

if m11/m21 <= 1
    ZR1 = R .* im2(:,:,1) - m11/m21 * R .*im2(:,:,1);
    im2(:,:,1) = im2(:,:,1) - ZR1;
end
if m12/m22 <= 1
    ZR2 = R .* im2(:,:,2) - m12/m22 * R .*im2(:,:,2);
    im2(:,:,2) = im2(:,:,2) - ZR2;
end
if m13/m23 <= 1
    ZR3 = R .* im2(:,:,3) - m13/m23 * R .*im2(:,:,3);
    im2(:,:,3) = im2(:,:,3) - ZR3;
end

return
