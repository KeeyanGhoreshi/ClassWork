function MakeTheMove=CheckTheBoardHisTurn(board)

%===========================================================%
%---------------------MiniMax Algorithm---------------------%
%~ ~ ~ ~ ~ ~ ~ ~ ~ ~  -----------------  ~ ~ ~ ~ ~ ~ ~ ~ ~ ~%
%                    [   Written By    ]                    %
%~~~~~~~~~~~~~~~~~[]  -----------------  []~~~~~~~~~~~~~~~~~%
%===================={ KEEYAN GHORESHI }====================%
%~~~~~~~~~~~~~~~~~[]  -----------------  []~~~~~~~~~~~~~~~~~%
%                    [    CSE 1010     ]                    %
%                    -------------------                    %
%-----------------------------------------------------------%
%                      [   PURPOSE   ]                      %
%                       -------------                       %
%       ---------------------------------------------       %
%------{ Code written to simulate moves and rate the }------%
%------{ move based on the state of the board after  }------%
%------{ it has been made, taking into account the   }------%
%------{ situations in which the computer wins, the  }------%
%------{ situations in which the opponent wins, the  }------%
%------{ situations in which the computer has more   }------%
%------{ oppurtunities to win, and situations where  }------%
%------{ the opponent has more opportunities to win. }------%
%       ---------------------------------------------       %
%~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%
global difficultylevel

CanHeWin=0; %Fiirst all variables are initialized as false
CanIFork=0;
CanHeFork=0;
MakeBestMove=0;
MakeTheMove=0;

% The board is checked to see if the opponent has any winning moves after
% the computer's move is made.  If this is true, this means that making a
% move in the current column would cause the computer to lose, and the move
% is avoided regardless of anything else.
if difficultylevel>1
for b1=1:7 %Iterate through each column
    newboardb=updateboard1(board,b1,1); %Update the board 
    if checkwin(newboardb)==1 %Check if the opponent has won on that board
        CanHeWin=1;
    end
end

% If the opponent cannot win due to making the move, the next priority is to
% see if the computer can fork.  Forking the opponent guarentees a win on
% the next play.

if CanHeWin==0 
    resultsforfork=[];
    for c1=1:7
        newboardc=updateboard1(board,c1,2); %The board is simulated for another one of the computer's moves
        if checkwin(newboardc)==2
        resultsforfork=[resultsforfork,checkwin(newboardc)]; %If the computer has won the result is added to the matrix
        end
    end
    if length(resultsforfork)>1 %If the length of the matrix is greater than one, the computer can win with two separate moves, indicating a fork
        CanIFork=1;
    end
end

% If the computer cannot fork, the next priority is ensuring that the
% opponent cannot create a fork.  To do this, two of the opponent's moves
% are simulated consectively 
moves=[];
for f1=1:7 %Iterate through each column
    newboardf=updateboard1(board,f1,2); %Update the board 
    if checkwin(newboardf)==2 %Check if the computer has won on that board
        moves=[moves,f1]; %If the computer has three in a row and a winning move available, the opponent will obviously try to stop it
        %Thus, the amount of boards that must be searched is drastically
        %decreased, as the opponent's next move is already known.
    end
end
if isempty(moves)==1
    moves=[1 2 3 4 5 6 7]; %If the computer does not have a winning move, the opponent may make any of their 7 moves
end

if CanHeWin==0 & CanIFork==0
    resultsforfork2=[];
    for d1=1:7
        newboardd=updateboard1(board,d1,1); %The board is updated for every possible move the opponent can make
        for e1=1:7
            newboarde=updateboard1(newboardd,e1,1); %The board is updated again for another move of the opponent
            if checkwin(newboarde)>0 %Check if someone has won
            resultsforfork2=[resultsforfork2, checkwin(newboarde)]; %If yes, it must be the opponent, and a vector is appended to
            end
        end
        
        if length(resultsforfork2)>1 %If the vector is longer than one, the opponent can win with two separate moves, indicating that he has the opportunity to create a fork
        CanHeFork=1;
        end
        resultsforfork2=[];
    end
    
end
end

% If none of these conditions are true, the next priority is to make the
% best move possible based on a sort of brute force search of the possible
% moves and future boards



if CanHeFork==0 & CanIFork==0 & CanHeWin==0
    
    %=======================================================================%
    % Each line simulates every possible move by each player down the line,
    % four moves down.  In the process, the vector that holds the scores of
    % one line of possible gameboard states in the tree of all the possible
    % ways the game could end up, is reset after its max or min value is
    % placed into the vector one line on the tree above it.  Of all the
    % possible child boards at the bottom of the tree, the best, most
    % reasonable move bubbles up to the top and is chosen.
    %=======================================================================%
    
    %~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    % A typical search of just the winning game boards takes about 8
    % seconds while a search of all the winning opportunities as well takes
    % about 18 seconds.  Decreasing the search depth decreases the time it
    % takes to search all the game boards exponentially.
    %~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    scoresa=[0 0 0 0 0 0 0]; 
    for a1=moves
        newboarda=updateboard1(board,a1,1); %Simulate all the opponent's next moves
        scoresb=[0 0 0 0 0 0 0];
        for b1=1:7
            newboardb=updateboard1(newboarda,b1,2); %Simulate all the possible moves the computer can make after that
            scoresc=[0 0 0 0 0 0 0];
            for c1=1:7
                newboardc=updateboard1(newboardb,c1,1); %Do the same thing, using the board from the previous level of the tree to build on
                scoresd=[0 0 0 0 0 0 0];
                
                for d1=1:7
                    newboardd=updateboard1(newboardc,c1,2); %Finish at a depth of 4 (or 5 to include the original move made outside this function)
                    if (checkwin(newboardd))==2 %Essentially, this checks every generated board to see if the computer has won in it
                    MakeTheMove=MakeTheMove+.05; %If the computer has won in a particular gamestate 5 moves down the road, the move in the current colomn gains some points
                    
                    end
                    if checkwin(newboardd)==1 %If the opponent has won in a particular gamestate, the score is decreased
                        MakeTheMove=MakeTheMove-.05;
                        
                    end
                    if difficultylevel==3
                    MakeTheMove=MakeTheMove+checkwinningmoves(newboardd); %Finally, the board is checked for each player to see who has more opportunities to get 4 in a row
                    end
                    
                    scoresd(d1)=MakeTheMove; %Create a vector with all 7 child boards of the current parent board
                end
                scoresc(c1)=min(scoresd); %Take the minimum from the previous vector and append it to the parent vector
            end
            scoresb(b1)=max(scoresc); %Do the same, but take the max
        end
        scoresa(a1)=min(scoresb); %Finally, at the top of the tree, seven scores remain.
    end
    MakeTheMove=MakeTheMove+max(scoresa); %The max of these scores is the overall score of playing in the current column
    
    %====================================================================%
    %                       How does this work?                          %
    %                       -------------------                          %
    % The alternation of min and max represents the opponent and the     %
    % computer's turn.  The vectors contain positive values if the move  %
    % is beneficial for the computer player.  If it is the opponent's    %
    % turn, they will most likely pick the move with the lowest score,   %
    % hence why sometimes the minimum of the vector is taken.  If it is  %
    % the computer's turn, the maximum of the vector will be taken. This %
    % should cause the most probable 7 gamestates to bubble up to the    %
    % top of the tree, where the maximum is selected.  The score returned%
    % represents how good of a move it is to play in the selected column %
    %====================================================================%
    
end

        

% If one of the first three initial conditions is true, their adjustment to
% the score of the move will override whatever score has been calculated
if difficultylevel>1
if CanHeFork==1
    MakeTheMove=MakeTheMove-100;
end
if CanIFork==1
    MakeTheMove=MakeTheMove+100;
end
if CanHeWin==1
    MakeTheMove=MakeTheMove-250;
end
end
end

function verdict=checkwinningmoves(board)
%===============================================%
%-----Function to Check Winning Opportunity-----%
%~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%
% This function checks the board in a similar   %
% fashion to checkwin, but it checks groups of  %
% four for three tokens of the same value and   %
% one open spot, indicating opportunity to win. %
%-----------------------------------------------%
%                INPUT VARIABLES                %
%               -----------------               %
%      [ board - the matrix being checked ]     %
%- - - - - - - - - - - - - - - - - - - - - - - -%
%-----------------------------------------------%
%- - - - - - - - - - - - - - - - - - - - - - - -%
%               OUTPUT VARIABLES                %
%               ----------------                %
%  [ verdict - the score given to the board ]   %
%-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --%
%===============================================%
verdict=0;
for col = 1:4
    for row = 1:6
        threerow1=length(find(board(row,col:(col+3))==1)); %Find all the values in a row of 4 equal to 1
        threerow2=length(find(board(row,col:(col+3))==2)); %Same for a value of 2
        openspace=length(find(board(row,col:(col+3))==0)); %Same for a vlue of 1
        if threerow1==3 & openspace==1 %If there are three 1's in a row and an open space, there is a winning opportunity for the opponent
            verdict=verdict-.01; %Decrese the score of the board
            break
        elseif threerow2 == 3 & openspace == 1 %If there are three 2's in a row and an open space, there is a winning opportunity for the computer
            verdict=verdict+.01; %Increase the score of the board
            break
            
        end
    end
end

%This same exact process is repeated for vertical groups of four and
%diagonal groups of four.

for col = 1:7
    for row = 1:3
        threerow1=length(find(board(row:(row+3),col)==1)); %Check rows vertically
        threerow2=length(find(board(row:(row+3),col)==2));
        openspace=length(find(board(row:(row+3),col)==0));
        if threerow1==3 & openspace==1
            verdict=verdict-.01;            
            break
        elseif threerow2 == 3 & openspace == 1
            verdict=verdict+.01;
            break
            
        end
    end
end

    for col=1:4
        for row = 1:3
            diagonalFour1=[board(row,col),board(row+1,col+1),board(row+2,col+2), board(row+3,col+3)]; 
            threerow1=length(find(diagonalFour1)==1); %Check diagonal groups of four
            threerow2=length(find(diagonalFour1)==2);
            openspace=length(find(diagonalFour1)==0);
                                   
            if threerow1==3 & openspace==1
                
                verdict=verdict-.01 ;
                
                            
            elseif(threerow2 == 3) & openspace==1
                               
                verdict=verdict+.01;
                
                
            end
        end
    end
            for col1=7:-1:4
                for row1=1:3
                     diagonalFour2=[board(row1,col1),board(row1+1,col1-1),board(row1+2,col1-2), board(row1+3,col1-3)];
                     threerow1=length(find(diagonalFour2)==1); %Check diagonal groups of four pointing in the opposite direction
                     threerow2=length(find(diagonalFour2)==2);
                     openspace=length(find(diagonalFour2)==0); 
                     if threerow1==3 & openspace==1
                
                verdict=verdict-.01 ;
               
                            
            elseif(threerow2 == 3) & openspace==1
                               
                verdict=verdict-.01;
               
                     end
                end
            
        end
            
%This function behaves very similar to checkwin.  To see documentation on
%the code here, look at the function "checkwin.m".
end




function newboard1=updateboard1(newboard1,coll,turn)
%This function is a clone of the subroutine found in SkyNet().
%Documentation is included in the original version.  I found the size of
%this code to be too small to warrant its own file, and decided a clone
%would work well enough, even though it isn't as modular to do it this way.

    top=newboard1(1,coll);
    if top==0
        for c=6:-1:2
    dspot1=newboard1(c,coll);
    dspotup2=newboard1(c-1,coll);
    if dspot1~=0 & dspotup2==0
        newboard1(c-1,coll)=turn;
        break
    end
     if dspot1==0 & c==6
        newboard1(c,coll)=turn;
        break
     end
        end
    end
    
        
    
    
end

