function Drop(hObject, Callbackdata, a)
%===============================%
%---------Drop Function---------%
%        ---------------        %
%  Purpose: To determine which  %
%  space on the board to place  %
%           the token           %
%-------------------------------%
%  Created by: Keeyan Ghoreshi  %
%-------------------------------%
%- - - - - - - - - - - - - - - -%
%         INPUT VARIABLES       %
%         ---------------       %
%   [ a - Represents column ]   %
%- - - - - - - - - - - - - - - -%
%===============================%

%First, all the global variables are declared

global buttonholder turn board fig error moves players

%The top spot on the board in the column that the player wants to drop a
%token into is checked.  If a token is already there, an error message is
%displayed in the textbox called "error", but otherwise the rest of the
%function is run as normal

topspot=board(1,a);
if topspot~=0
    %The current string is stored in a variable
    
    previous=get(error,'String');
    
    %The text box is changed to say "error" and then the game pauses for .8
    %seconds before the next command is run
    
    error=uicontrol(fig,'style','text','string','Error');
    pause(.8)
    
    %Set the error textbox back to its original text
    
    set(error,'String',previous)
else

if turn == 1 %Check to see if it is Player 1's turn
    colora='y'; %Set color of token to yellow
    num=1; %Set token value to be placed on the matrix board to 1
    turn=2; %Switch the turn value to 2, making it the other players turn
    if players==2 %Check to see how many human players there are
    set(error,'String','Player 2') %If there are two, update the error text box appropriately
    elseif players==1 %If there is only 1 human player
        set(error,'String','Computer') %Update the textbox to say "Computer" 
    end
    
elseif turn== 2 %If it is Player 2's (or the computer's) turn 
    colora='r'; %Set token color to red
    turn=1; %Set the turn variable to Player 1's turn
    num=2; %Set token value to 2
    set(error,'String','Player 1') %Update error textbox to indicate it is Player 1's turn
end
        


for c=6:-1:2 %Iterate through the rows bottom to top
    spot=board(c,a); %Save the value of the current spot on the board in a variable
    spotup=board(c-1,a); %Save the value of the spot above the current spot in a different variable
    if spot~=0 & spotup==0 %If 'spot' does not equal 0, there is a token there already, and if 'spotup' equals 0, there is no token there, meaning the move is valid 
        board(c-1,a)=num; %If the move is valid, a token is placed above the occupied space
        for p=1:40 %To create a visual token, different sizes of the letter 0 are drawn at the same position
            text(a-.5,8-c-.5,'O','Color',colora,'Fontsize',p,'horizontalalignment','center') %Drawing 40 'O' shapes of increasing size in the same position creates a solid circle shape
        end
        break %If a valid move has already been found, there is no need to continue to check the other spots
    end
    
    if spot==0 & c==6 %If spot in  the bottom (sixth) row is empty, a token can be placed there
        board(c,a)=num;
         for p=1:40 %The same procedure is followed
            text(a-.5,7-c-.5,'O','Color',colora,'Fontsize',p,'horizontalalignment','center')
         end   
         break
    end
    
end
moves=moves+1; %Now that a valid move has been made, the move variable is updated
result = checkwin(board); %The board is checked to see if someone has won every turn
if result>0 %If the result is greater than zero, somebody has won
    if result==2 
        if players==2 %If there are two human players and result equals 2, Player 2 has won
        text(3.5,4,'Player 2 Wins!','Color','g','Fontsize',40,'horizontalalignment','center');
        elseif players==1 %If there is only one human player and result equals 2, the computer has won
            text(3.5,4,'Computer Wins!','Color','g','Fontsize',40,'horizontalalignment','center');
        end
    elseif result==1 %If the result is 1 it indicates Player 1 has won
       text(3.5,4,'Player 1 Wins!','Color','g','Fontsize',40,'horizontalalignment','center');
    end  
    for a=1:7 %Once somebody has won the game, all the buttons are disabled to avoid any problems
        set(buttonholder{1,a},'Enable','off')
    end
    
end

if result==0 & moves==42 %If nobody has won after 42 moves, then the game is a tie
    text(3.5,4,'Tie Game!','Color','g','Fontsize',40,'horizontalalignment','center');
end

%If nobody has won and the user has selected on player mode, the computer
%player makes a move

if result==0 & players==1
    pause(.5)
    SkyNet()
end

end
end