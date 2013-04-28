% EXTRACTION permet d'extraire les points d'interêt
%
% Utilisation: [features1, validPoints1, features2, validPoints2] = Extraction(F1,F2,Points1,Points2)
%
% Arguments:
%	F1	- descripteurs de l'image 1
%	F2	- descripteurs de l'image 2
%	Points1	- les points de l'image 1
% 	Points2	- les points de l'image 2
%
% Returns:
% 	features1 descripteurs de l'image 1
%	features2 descripteurs de l'image 2
%	validPoints1 les points de l'image 1
%	validPoints2 les points de l'image 2
function [features1, validPoints1, features2, validPoints2] = Extraction(F1,F2,Points1,Points2)
%EXTRACTION permet d'extraire les descripteurs 
%on extrait les points d'interêt
[features1 validPoints1] = extractFeatures(F1, Points1);
[features2 validPoints2] = extractFeatures(F2, Points2);

return
