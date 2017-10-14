function Connect4(hObject,callbackdata)

%##################################################%
%-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --%
%~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%
%--------------------CONNECT 4---------------------%
%                                                  %
%-------------------VERSION 1.40-------------------%
%                                                  %
%--------------------CHANGELOG---------------------%
%                                                  %
%         Version 1.10: Added gui gameboard        %
%                                                  %
%       Version 1.20: Basic Computer AI created    %
%                                                  %
%      Version 1.30: Computer AI with a minimax    % 
%       function and search depth of 4 created     %
%                                                  %
%     Version 1.40: Separate, improved function    %
%       created to rate moves with search depth    %
%          of 6 while checking for winning         %
%           oppurtunities as well as wins          %
%- - - - - - - - - - - - - - - - - - - - - - - - - %
%--------------------------------------------------%
%- - - - - - - - - - - - - - - - - - - - - - - - - %
%                                                  %
%-- -- -- -- -- -- -[CREATED BY]- -- -- -- -- -- --%
%                                                  %
%~~~~~~~~~~~= = = = = = = = = = = = = = =~~~~~~~~~~%
%----------||::::  KEEYAN GHORESHI  ::::||---------%
%~~~~~~~~~~~= = = = = = = = = = = = = = =~~~~~~~~~~%
%                                                  %
%--------------------------------------------------%
%                                                  %
%-------[ Coded for CSE 1010 Final Project ]-------%
%                                                  %
%                   -----------                    %
%-------------------[Professor]--------------------%
%                   -----------                    %
%                                                  %
%                 ----------------                 %
%-----------------{Sahar Al Seesi}-----------------%
%                 ----------------                 %
%                                                  %
%-+-+-+-+-+-[University of Connecticut]-+-+-+-+-+-+%
%            -------------------------             %
%                                                  %
%  ---------------------------------------------   %
%  | Purpose: The game of Connect 4 for Matlab |   %
%  |       complete with functioning AI        |   %
%  ---------------------------------------------   %
%                                                  %
%============Due Date: December 5th 2014===========%
%                                                  %
%__________________________________________________%
%                                                  %
%                  -------------                   %
%****************** How to Play *******************%
%                  -------------                   %
%                                                  %
%   Click the white button above the column you    %
%   want to drop a token in.  The object is to     %
%   get four of your color token in a row while    %
%   not allowing your opponent to do the same.     %
%                                                  %
%   If you are playing against a human opponent    %
%    select the 2 player option from the menu.     %
%   Select the 1 player option to play against     %
%                the computer AI.                  %
%~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%
%                  Please enjoy!                   %  
%    Any constructive criticism is appreciated.    %
%            Thank you for your time!              %
%~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%
%-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --%
%##################################################%

%CodeStart~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

%Define all needed global variables to be used by the other programs

global buttonholder turn board fig error moves menu stop
stop=1;

close(menu)

%Create a 6x7 matrix to hold all the information about the board as
%number, define a variable to keep track of the amount of moves that have
%occured, and create a figure to serve as the main playing board

board = zeros(6,7);
moves=0;
fig = figure();

%This huge block simply defines the lines that should be plotted to create
%the grid that will serve as the board.  The hlinex variables define the
%length of the line horizontally, while the hliney variables define the
%length of the line vertically and its position on the verticle axis.  For
%example, if hlinex#=[0,7] and hliney=[1,1] the line from the point [0,1]
%to the point [7,1] would be plotted, giving a horizontal line.


hlinex0 = [0, 7]; 
hliney0 = [0, 0];
hlinex1 = [0, 7]; 
hliney1 = [1, 1];
hlinex2 = [0, 7]; 
hliney2 = [2, 2];
hlinex3 = [0, 7]; 
hliney3 = [3, 3];
hlinex4 = [0, 7]; 
hliney4 = [4, 4];
hlinex5 = [0, 7]; 
hliney5 = [5, 5];
hlinex6 = [0, 7]; 
hliney6 = [6, 6];
hlinex7 = [0, 7]; 
hliney7 = [7, 7];
vlinex0 = [0, 0]; 
vliney0 = [0, 6];
vlinex1 = [1, 1]; 
vliney1 = [0, 6];
vlinex2 = [2, 2]; 
vliney2 = [0, 6];
vlinex3 = [3, 3]; 
vliney3 = [0, 6];
vlinex4 = [4, 4];
vliney4 = [0, 6];
vlinex5 = [5, 5]; 
vliney5 = [0, 6];
vlinex6 = [6, 6]; 
vliney6 = [0, 6];
vlinex7 = [7, 7]; 
vliney7 = [0, 6];

%Just like the workspace, the most recent figure is cleared.

clf

%Every single one of the previously defined point pairs is plotted to
%create a 6x7 grid that will serve as the visual gameboard being updated
%and displayed to the user

plot(hlinex0, hliney0,'r',hlinex1, hliney1,'y',hlinex2, hliney2,'y',hlinex3, hliney3, 'y',hlinex4, hliney4, 'y',...
hlinex5, hliney5, 'y', hlinex6, hliney6, 'y', hlinex7,hliney7,'y', ...
vlinex0, vliney0, 'y', vlinex1, vliney1, 'y', vlinex2, vliney2, 'y', vlinex3, vliney3,'y', vlinex4, vliney4, 'y', vlinex5, ...
vliney5,'y', vlinex6, vliney6,'y', ...
vlinex7, vliney7, 'y')

%The axii on the figure are set off to avoid them disrupting the image of
%the board and the axii are set to a square configuration to compliment the
%shape of the gameboard

axis off
axis square

%The gameboard figure has its color set to black so that the yellow lines
%that were just plotted pop out.

set(gcf,'color',[0 0 0])

%A cell is defined to hold all of the buttons so that they can be organized
%and easily accessed such that their properties can be changed in other
%functtions

buttonholder=cell(1,7);

%Each button is individually stored into the buttonholder corresponding to
%its position on the board.  Each one calls the same function "Drop()", but
%inputs a different number, corresponding to the column the button is over.

a=7;
buttonholder{1,1}=uicontrol('Style','pushbutton','String','','position',[413,345,50,50],'backgroundcolor',[1,1,1],'Callback',{@Drop, a});
a=6;
buttonholder{1,2}=uicontrol('Style','pushbutton','String','','position',[364,345,50,50],'backgroundcolor',[1,1,1],'Callback',{@Drop, a});
a=5;
buttonholder{1,3}=uicontrol('Style','pushbutton','String','','position',[315,345,50,50],'backgroundcolor',[1,1,1],'Callback',{@Drop, a});
a=4;
buttonholder{1,4}=uicontrol('Style','pushbutton','String','','position',[266,345,50,50],'backgroundcolor',[1,1,1],'Callback',{@Drop, a});
a=3;
buttonholder{1,5}=uicontrol('Style','pushbutton','String','','position',[217,345,50,50],'backgroundcolor',[1,1,1],'Callback',{@Drop, a});
a=2;
buttonholder{1,6}=uicontrol('Style','pushbutton','String','','position',[168,345,50,50],'backgroundcolor',[1,1,1],'Callback',{@Drop, a});
a=1;
buttonholder{1,7}=uicontrol('Style','pushbutton','String','','position',[119,345,50,50],'backgroundcolor',[1,1,1],'Callback',{@Drop, a});

%A text box is created next to the gameboard to indicate who's turn it is,
%which defaults to "Player 1" at the beginning of each game.  The text box
%is aptly named "error" as it was originally intended to only display when
%there was an error with the players choice of move.

error=uicontrol(fig,'style','text','string','Player 1');

%The turn variable is initizlized, defaulting to 1, signifying that it is
%player 1's turn.

turn=1;


%For all practical purposes, the game has been completely setup.  By
%pressing one of the buttons, the function "drop" is activated, causing a
%chain reaction that involves every other program.  But until a button is
%pressed, nothing is being done or processed.  
end