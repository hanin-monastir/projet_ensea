function Showhomogrphie(F1, F2, match1, match2, ppositif)

cvexShowMatches(F1,F2,match1(ppositif),match2(ppositif),'Points image1','Points image2');
title('Points correspondants sans les faux positifs');
