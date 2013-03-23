function [features1, validPoints1, features2, validPoints2] = Extraction(F1,F2,Points1,Points2)
%EXTRACTION permet d'extraire les descripteurs 
%on extrait les points d'interêt
[features1 validPoints1] = extractFeatures(F1, Points1);
[features2 validPoints2] = extractFeatures(F2, Points2);

return
