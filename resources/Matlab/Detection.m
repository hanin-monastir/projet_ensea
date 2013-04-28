% DETECTION permet de detecter les points d'intêrets dans deux images
%
% Utilisation: [F1,F2,pointsF1,pointsF2] = Detection(image1,image2)
%
% Arguments:
%	image1	- premiere image
%	image2	- deuxieme image  
%		
% Returns:
%	F1 descripteurs de l'image 1
%	F2 descripteurs de l'image 2
%	pointsF1 points de l'image 1
%	pointsF2 points de l'image 2
%
function [F1, F2, pointsF1, pointsF2] = Detection(image1,image2)
%DETECTION fonction pour détecter les points d'intêret
%cette fonction permet de détecter les points d'înteret surf

F1 = rgb2gray(imread(image1)); 
pointsF1 = detectSURFFeatures(F1);

F2 = rgb2gray(imread(image2));
pointsF2 = detectSURFFeatures(F2);

return
