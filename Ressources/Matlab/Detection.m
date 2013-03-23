function [F1, F2, pointsF1, pointsF2] = Detection(image1,image2)
%DETECTION fonction pour détecter les points d'intêret
%cette fonction permet de détecter les points d'înteret surf

F1 = rgb2gray(imread(image1)); 
pointsF1 = detectSURFFeatures(F1);

F2 = rgb2gray(imread(image2));
pointsF2 = detectSURFFeatures(F2);

return
