% MATCHING permet d'apparier les points d'interet entre deux images
%
% Utilisation: [match1, match2] = Matching(features1, validPoints1, features2, validPoints2)
%
% Arguments:
%	features1	- les descripteurs de l'image 1
%	features2	- les descripteurs de l'image 2
%	validPoints1	- les points de l'image 1
%	validPoints2	- les points de l'image 2
%
% Returns:
%	match1 les points de l'image 1 ayant une correspondance dans l'image 2
%	match2 les points de l'image 1 ayant une correspondance dans l'image 1
function [match1, match2] = Matching(features1, validPoints1, features2, validPoints2)
%MATCHING fonction qui permet d'apparier les points d'interêt

%on recherche les points appariés
index_pairs = matchFeatures(features1, features2);

%on retourne les points identiques
match1 = validPoints1(index_pairs(:,1));
match2 = validPoints2(index_pairs(:,2));

return
