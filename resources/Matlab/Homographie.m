% HOMOGRAPHIE permet de trouver l'homographie entre deux images
%
% Utlisation: [H, ppositif ] = Homographie(match1, match2)
%
% Arguments:
%	match1	- les points de l'image 1
%	match2	- les points de l'image 2
%
% Returns:
%	H l'homographie entre les deux images
%	ppositif les points verifiant l'homographie
function [H, ppositif ] = Homographie(match1, match2)
%HOMOGRAPHIE fonction qui permet de trouver l'homographie en appliquant
%l'algorithme de RANSAC
%il y a différente transformation possible mais une seule donne de bons
%résultat : Projective. les autres produisent après warping un décallage
%gte = vision.GeometricTransformEstimator;
%gte.Transform = 'Projective';%type de transformation
%gte.NumRandomSamplingsMethod = 'Desired confidence';
%gte.MaximumRandomSamples = 2000;%5000 on pourra gagner du temps en diminuant le nombre d'itération
%gte.DesiredConfidence = 99.99;
%gte.ExcludeOutliers = true;
%gte.PixelDistanceThreshold = 0.2;
%on obtient la matrice d'homographie sous forme de Tform ie 3x3 pour une
%projective sinon 3x2
%[H ppositif] = step(gte, match2.Location, match1.Location);
%on créer un vecteur 2 colonne

V2=match2.Location;
V1=match1.Location;

[H, ppositif] = ransacfithomography(V2', V1', 0.8);

%on retourne le nombre de correspondances restant
points_apparies = size(ppositif);

%Retourner à une matrice 3*3
%a b tx
%c d ty
%0 0 1
%si affine ou non linear similarity 
%H = [ H [0;0;1]];

%j'ai trouvé ça alors que matlab refusait mes matrices d'homographie
%H = H';

return
