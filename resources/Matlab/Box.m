% BOX permet de limiter la zone de reconstruction
% 
% Utilisation: bb = Box(im1,im2,H)
% 
% Arguments: 
%	im1	- La premiere image
%	im2	- La seconde images
%	H	- L'homographie permettant de recoller l'image 2 à l'image 1
%
% Returns:
%	bb La zone permettant la reconstruction de l'image en limitant les bandes noires autour de l'image
function bb = Box(im1,im2,H)
%BOX Cette fonction permet de calculer la taille optimale de l'image finale pour
%cela on calcul où se trouveront les coins des images après transformation
%on obtient la taille des images
[m1,n1,~] = size(im1);
[m2,n2,~] = size(im2);

%on detecte les coins de la boite qui contiendra l'image 1
H1 = eye(3);
H2 = H;

%on calcul la tansformée que va subir im1   
y1 = H1*[[1;1;1], [1;m1;1], [n1;m1;1] [n1;1;1]];
y1(1,:) = y1(1,:)./y1(3,:);
y1(2,:) = y1(2,:)./y1(3,:);

%idem pour im2
y2 = H2*[[1;1;1], [1;m2;1], [n2;m2;1] [n2;1;1]];
y2(1,:) = y2(1,:)./y2(3,:);
y2(2,:) = y2(2,:)./y2(3,:);

xmin = ceil(min(min(y1(1,:)),min(y2(1,:))));
xmax = ceil(max(max(y1(1,:)),max(y2(1,:))));
ymin = ceil(min(min(y1(2,:)),min(y2(2,:))));
ymax = ceil(max(max(y1(2,:)),max(y2(2,:))));

%on calcul la taille optimale de l'image
bb = [xmin xmax ymin ymax];
return
