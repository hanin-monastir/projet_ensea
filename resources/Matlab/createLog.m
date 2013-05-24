% Cette fonction permet d'arreter le programme en cours si une erreru
% intervient lors de la construction du panorama géoréférencé.
function logstate = createLog(message)
%LOG permet de quitter le programme si un probleme survient
disp(message);

logfile='panorama.log';
f=fopen(logfile,'w');
fprintf(f,'%s\n',message);

logstate = true;
end

