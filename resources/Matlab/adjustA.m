function M = adjustA(A,C,box)
%ADJUSTA Summary of this function goes here
%   Detailed explanation goes here
%travail sur les colonnes
M=zeros(size(A));
M(box(3):box(4),box(1):box(2)) = C;

% traitemnt des colonnes, ajout si nécéssaire
if box(1) ~= 1
    F =  meshgrid(1:box(1)-1,box(3):box(4));
    F =  sort(F','descend')';
    I =  C(:,2) - C(:,1);
    for i = 1:box(1)-1
        M(box(3):box(4),i) = C(:,1) - F(:,i) .* I(:); 
    end
end
if box(2) ~= size(A,2)
    F =  meshgrid(box(2)+1:size(A,2),box(3):box(4));
    I =  C(:,end) - C(:,end-1);
    for i = box(2)+1:size(A,2)
        M(box(3):box(4),i) = C(:,end) + (F(:,i-box(2))-box(2)) .* I(:);
    end  
end

%Mise à jours de la box sur les colonnes
box(1) = 1;
box(2) = size(A,2);
%Mise à jours de C
C = M(box(3):box(4),box(1):box(2));

% traitement des lignes idem
if box(3) ~= 1
    F =  meshgrid(1:box(3)-1,box(1):box(2));
    F =  sort(F','descend');
    I =  C(2,:) - C(1,:);
    for i = 1:box(3)-1
        M(i,box(1):box(2)) = C(1,:) - F(i,:) .* I(1,:);
    end
end
if box(4) ~= size(A,1)
    disp('Reconstructio...')
    F = meshgrid(box(4)+1:size(A,1),box(1):box(2));
    F = sort(F');
    I = C(end,:) - C(end-1,:);

    for i = box(4)+1:size(A,1)
        M(i,box(1):box(2)) = C(end,:) + (F(i-box(4),:) - box(4)) .* I(1,:); 
    end  
end

end

