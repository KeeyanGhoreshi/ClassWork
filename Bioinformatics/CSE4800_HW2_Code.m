function [align] = globalAlign(l_DNA)
s = 'ACTG';
numRands = length(s); 
sLength = l_DNA;
s1 = s( ceil(rand(1,sLength)*numRands));
s2 = s( ceil(rand(1,sLength)*numRands));
n = strlength(s1);
m = strlength(s2);
F = zeros(2,m+1);
s1 = char(s1);
s2 = char(s2);
tic
for i=2:n+1
    for j=2:m+1
        match = -1;
        if s1(i-1) == s2(j-1)
            match = 1;
        end
        a = [F(1,j-1)+match, F(2,j-1)-1, F(1,j)-1];
        F(2,j) = max(a);
    end
    F([1 2],:) = F([2 1],:);
    
end
toc
align = F(2,m+1);
disp("Maximum Alignment Score: " + align);
end
