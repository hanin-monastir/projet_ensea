function [match1, match2] = Matching(features1, validPoints1, features2, validPoints2)
%MATCHING fonction qui permet d'apparier les points d'interêt

%on recherche les points appariés
index_pairs = matchFeatures(features1, features2);

%on retourne les points identiques
match1 = validPoints1(index_pairs(:,1));
match2 = validPoints2(index_pairs(:,2));

return
