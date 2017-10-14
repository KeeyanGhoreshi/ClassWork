function SkyNet()
%=====================================%%==================================%
%------Computer AI for Connect 4------%%            ------------          %
%      -------------------------      %%-----------|How it Works|---------%
%     Created By: Keeyan Ghoreshi     %%            ------------          %
%-------------------------------------%% The program simulates moves and  % 
%   The AI uses a simple version of   %% evaluates the new state of the   %
% the MiniMax algorithim to find the  %% board.  It will simulate 5 moves %
%          best move to make.         %% ahead and evaluate every one of  %
%-------------------------------------%% the 117,649 possible states of   %
%-------------------------------------%% the board, giving a score to     %
%~~~~~~~~Possible Improvements~~~~~~~~%% each of its available moves.     %
%  1) Identification of odd and even  %%                                  %
%              threats.               %% The program takes the move with  %
%-------------------------------------%% with the best score using a sort %
%  2) Identification of ability to    %% of MiniMax algorithm. This means %
%     force the opponent's move       %% that it will take the move that  %
%-------------------------------------%% has the max score, while         %
%  3) Pruning of gameboard options to %% simulating that the opponent     %
%        reduce search time           %% will take the move with the min  % 
%-------------------------------------%% score, to predict their move.    %
%=====================================%%==================================%

%CodeStart-----------------------------------------------------------------

global buttonholder turn board error moves difficultylevel
%-------------------------------------------------------------------------%
% The very first thing the computer does is check if it can win, and if it%
% can make that move.  If it can't do that, it checks to see if the       %
% opponent has a winning move, and if they do, blocks that move.          %
%-------------------------------------------------------------------------%
for a=1:7 %Disable buttons at beginning of computer's turn
        set(buttonholder{1,a},'Enable','off')
end
    
for a=1:7
    moved=dummydropwin(a,2,board); %Check if there are any winning moves
    if moved==1 %If there are, the move is made in the subroutine and the loop ends
        break
    end
    if moved==0
        moved=dummydropwin(a,1,board); %Check if the opponenet has any winning moves
        if moved==1
            break
        end
    end
    
end 

if moved==0 %If there are no winning moves, the program runs the search functions
    thebestmove=checkmoves(board); 
    SkyNetDrop(thebestmove)
    moved=1;
end

for a=1:7 %Enable buttons when the computer's turn is over
    set(buttonholder{1,a},'Enable','on')
end





%Subroutines--------------------------------------------------------------%


function movemade=dummydropwin(a,turn,dummyboard)
    %===================================================%
    %---------------------------------------------------%
    %----Function created to check for winning moves----%
    %---------------------------------------------------%
    %                  INPUT VARIABLES                  %
    %                  ---------------                  %
    %    [ a - Represents the column on the board ]     %
    %- - - - - - - - - - - - - - - - - - - - - - - - - -%
    %  [ turn - Represents which player's turn it is ]  %
    %- - - - - - - - - - - - - - - - - - - - - - - - - -%
    %    [ dummyboard - Copy of the current board ]     %
    %- - - - - - - - - - - - - - - - - - - - - - - - - -%
    %---------------------------------------------------%
    %- - - - - - - - - - - - - - - - - - - - - - - - - -%
    %                 OUTPUT VARIABLES                  %
    %                ------------------                 %
    %  [ movemade - Indicates if a move has been made ] %
    %---------------------------------------------------%
    %===================================================%
    
    movemade=0; % Default the output variable to no move made
    makemove=1; % Variable to indicate that the move is valid
    topspotd=dummyboard(1,a); %Check the top spot
    if topspotd~=0 %If the top spot is occupied, the move is not valid
        makemove=0;
    end
    if makemove~=0
    for c=6:-1:2 % This function mirrors the Drop function, but uses a copy of the board instead
    dspot=dummyboard(c,a); %Everything proceeds the same as with the drop function
    dspotup=dummyboard(c-1,a);
    if dspot~=0 & dspotup==0
        dummyboard(c-1,a)=turn; %No token is drawn, only the copied board is updated
    end
     if dspot==0 & c==6
        dummyboard(c,a)=turn;
     end
     makemove=checkwin(dummyboard);
     if makemove>0 % If there is a winning move, make it and update the output variable to indicate that a move has been made
         
         %If this function is run to check if the opponent has any winning
         %moves, the computer will make their winning move (provided there
         %is one), effectively blocking that space.
         SkyNetDrop(a)
         movemade=1;
     end
     
     %This block resets the copied board so that it can be checked again in
     %the next column.
     
     if dspot~=0 & dspotup==0
     dummyboard(c-1,a)=0;
     end
     if dspot==0 & c==6
         dummyboard(c,a)=0;
     end
    end
    end
end

%=========================================================================%
%-------------------------------------------------------------------------%
%=========================================================================%

function newboard=updateboard(newboard,coll,turn)
    %==================================================%
    %-----------Function to Update the Board-----------%
    %           ----------------------------           %
    %                  INPUT VARIABLES                 %
    %                  ---------------                 %
    %      [ newboard - The board to be updated ]      %
    % - - - - - - - - - - - - - - - - - - - - - - - - -%
    %  [ coll - represents which column to play in ]   %
    % - - - - - - - - - - - - - - - - - - - - - - - - -%
    %    [ turn - indicates whose turn to simulate ]   %
    % - - - - - - - - - - - - - - - - - - - - - - - - -%
    %--------------------------------------------------%
    % - - - - - - - - - - - - - - - - - - - - - - - - -%
    %                 OUTPUT VARIABLES                 % 
    %                ------------------                %
    %     [ newboard - The updated board matrix ]      %
    %--------------------------------------------------%
    %==================================================%
    
    % This function does the same exact thing as Drop and dummydropwin
    % except for the fact that it only updates the board.
    
    top=newboard(1,coll); %Check the top spot to see if this is a valid move
    if top~=0
        newboard=9000;
    end
    if top==0
        for c=6:-1:2
    dspot1=newboard(c,coll); %Update the board in the appropriate row
    dspotup2=newboard(c-1,coll);
    if dspot1~=0 & dspotup2==0
        newboard(c-1,coll)=turn;
        break
    end
     if dspot1==0 & c==6
        newboard(c,coll)=turn;
        break
     end
        end
    end
    
    %Unlike dummydropwin, the board is not reset, but returned, and there
    %is no action taken or move made based on the new board created.
        
    
    
end

%=========================================================================%
%-------------------------------------------------------------------------%
%=========================================================================%

function movetomake=checkmoves(board)
    %=======================================================%
    %---------Function to Check Every Available Move--------%
    %         --------------------------------------        %
    % Description: The function rates each gameboard based  %
    % a set criteria and then picks the best move based on  %
    %                    those numbers.                     %
    %-------------------------------------------------------%
    %                     INPUT VARIABLES                   %
    %                     ---------------                   %
    % [ board - a copy of the current state of the board ]  %
    %- - - - - - - - - - - - - - - - - - - - - - - - - - - -%
    %-------------------------------------------------------%
    %- - - - - - - - - - - - - - - - - - - - - - - - - - - -%
    %                    OUTPUT VARIABLES                   %
    %                    ----------------                   %
    %  [ movetomake - Indicates which column to play in ]   % 
    %- - - - - - - - - - - - - - - - - - - - - - - - - - - -%
    %-------------------------------------------------------%
    %=======================================================%
    
    if difficultylevel~=1
    availablemoves=[0 0 0 0 0 0 0]; %Each column represents a possible move
    for firstmove=1:7 % Each of the 7 possible moves that can be made this turn are considered
        
        %A very small edge is given to positions near the middle of the
        %board, meaning that if two spaces have the same score, the
        %computer will favor the one closer to the middle.
        
        if firstmove==1
            availablemoves(firstmove)=availablemoves(firstmove)+.0001; %A value of .0001 is added to the score of the first move
        elseif firstmove==2
             availablemoves(firstmove)=availablemoves(firstmove)+.0002; %The same thing happens for each move
        elseif firstmove==3
             availablemoves(firstmove)=availablemoves(firstmove)+.0003;
        elseif firstmove==4
             availablemoves(firstmove)=availablemoves(firstmove)+.0004;
        elseif firstmove==5
             availablemoves(firstmove)=availablemoves(firstmove)+.0003;
        elseif firstmove==6
             availablemoves(firstmove)=availablemoves(firstmove)+.0002;
        elseif firstmove==7
             availablemoves(firstmove)=availablemoves(firstmove)+.0001;
        end             
        firstmoveboard=updateboard(board,firstmove,2); %Simulate the first move, or the move to be made
        if firstmoveboard==9000 %Check to see if the move is being made in a column that is already full
            availablemoves(firstmove)=availablemoves(firstmove)-8000; %If it is then the move is given a value to ensure it won't be picked
        else
        Makethemove=CheckTheBoardHisTurn(firstmoveboard); %Generate a score for the move
        availablemoves(firstmove)=availablemoves(firstmove)+Makethemove; %Add that score to the moves value in the master vector
        end
            
        
    end
    end
    if difficultylevel~=1
[number,movetomake]=max(availablemoves); %After every available move is scored, the one with the maximum value is chosen to be played
    end
    if difficultylevel==1
        randomnumber=randi(7,1,1);
        movetomake=randomnumber;
    end
       
end


function SkyNetDrop(a)
    
    %==================================%
    %---------AI Drop Function---------%
    %        ------------------        %
    %  Description: Acts as a simple   %
    %  clone of the drop function for  %
    %  the use of the AI.              %
    %----------------------------------%
    %          INPUT VARIABLE          %
    %          --------------          %
    %  [ a - Represents the column ]   %
    %- - - - - - - - - - - - - - - - - %
    %----------------------------------%
    %==================================%
    
    colora='r'; %Since this function can only be called by the computer, variables can just be preset
    num=2; %All variables are defaulted to the values needed for player 2
    turn=1;
set(error,'String','Player 1')   

%This function works the same way as Drop, iterating through the rows until
%it finds an open space above a filled space in the given column to place a
%token
for c=6:-1:2
    spot=board(c,a); %Take a spot
    spotup=board(c-1,a); %Take the spot above it
    if spot~=0 & spotup==0 %Check to see if there is a valid move
        board(c-1,a)=num; %If there is update the board
        for p=1:40 %And create a token in that spot on the board
            text(a-.5,8-c-.5,'O','Color',colora,'Fontsize',p,'horizontalalignment','center')
        end
        break
    end
    
    if spot==0 & c==6 %If the bottom spot of a column is open, the token will take that spot
        board(c,a)=num;
         for p=1:40
            text(a-.5,7-c-.5,'O','Color',colora,'Fontsize',p,'horizontalalignment','center')
         end   
         break
    end
    
end
moves=moves+1;
result = checkwin(board); %Check to see if somebody has won
if result>0 %Since the computer is the only one using this function, it is only possible for it to win if the result is above 0
            text(3.5,4,'Computer Wins!','Color','g','Fontsize',40,'horizontalalignment','center');         
    for a=1:7
        set(buttonholder{1,a},'Enable','off') %Disable the buttons
    end
    
end

if result==0 & moves==42 %Check for tie game conditions
    text(3.5,4,'Tie Game!','Color','g','Fontsize',40,'horizontalalignment','center');
end
end
end

