% WARPING permet d'appliquer une homographie a une image
% 
% Utilisation: nim = Warping(im, H, interp_mode, bbox_mode, verbose)
% 
% Arguments:
%     im  - l'image qui va subir l'homographie
%     H   - l'homographie
%     inter_mode  - le mode d'interpolation
%     bbox_mode   - les dimensions de l'image finale permettant d'obtenir le moins de bande noire
%     verbose     - future option
% 
% Returns:
%     nim nouvelle image
function nim = Warping(im, H, interp_mode, bbox_mode)
%fonction qui permet de redimensionner et placer l'image correctement avant
%le recollement
im=im2double(im);
[~,~,l] = size(im);

%on crée une image du même type qu'en entrée
switch class(im)
 case 'double',  nim = double([]);
 case 'uint8',  nim = uint8([]);
end

%on assigne les coins de l'image
bb = bbox_mode;
if (bb(2) <= bb(1)) || (bb(4) <= bb(3))
    error('bbox should be [xmin xmax ymin ymax]')
end

bb_xmin = bb(1);
bb_xmax = bb(2);
bb_ymin = bb(3);
bb_ymax = bb(4);

%on interpole dans le plan bb
%X'=H*X
%x' = H(1,1)*x+H(1,2)*y+H(1,3)
%y' = H(2,1)*x+H(2,2)*y+H(2,3)
% interpolation linéaire
[U,V] = meshgrid(bb_xmin:bb_xmax,bb_ymin:bb_ymax);
[nrows, ncols] = size(U);

Hi = inv(H);

if 1
  u = U(:);
  v = V(:);
  x1 = Hi(1,1) * u + Hi(1,2) * v + Hi(1,3);
  y1 = Hi(2,1) * u + Hi(2,2) * v + Hi(2,3);
  w1 = 1./(Hi(3,1) * u + Hi(3,2) * v + Hi(3,3));
  U(:) = x1 .* w1;
  V(:) = y1 .* w1;
end



if 1
    if l == 3
        nim(nrows, ncols, 3) = 1;
        nim(:,:,1) = interp2(im(:,:,1),U,V,interp_mode);
        nim(:,:,2) = interp2(im(:,:,2),U,V,interp_mode);
        nim(:,:,3) = interp2(im(:,:,3),U,V,interp_mode);
    else
        nim(nrows, ncols) = 1;
        nim(:,:) = interp2(im(:,:),U,V,interp_mode);
    end
end

return
