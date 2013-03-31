function Nimage = SubImage(F, IM)
%SUBIMAGE Summary of this function goes here
%   Detailed explanation goes here

image = fullfile(IM);
Dossier = fileparts(IM)

Nimage = fullfile(Dossier,'subImage.png');

if exist(Nimage)
    %le fichier existe on le supprime
   delete(Nimage); 
end

%on lit l'image 
Image = imread(image);


newImage = imresize(Image,F);

imwrite(newImage,Nimage);

end

