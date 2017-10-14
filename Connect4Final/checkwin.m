function result = checkwin(board)
%==========================================%
%             Checkwin Function            %
%            -------------------           %
%- - - - - - - - - - - - - - - - - - - - - %   
% Function to check for winning conditions %
%- - - - - - - - - - - - - - - - - - - - - %
%              INPUT VARIABLES             %
%             -----------------            %
%[ board - Matrix of current board state ] %
%- - - - - - - - - - - - - - - - - - - - - %
%             OUTPUT VARIABLES             %
%             ----------------             %
%    [ result - Represents who has won ]   %
%- - - - - - - - - - - - - - - - - - - - - %
%==========================================%


result=0; %Set the default value of 'result' to indicate that nobody has won
for col = 1:4 %Check the board for a win horizontally 
    for row = 1:6 %Check every row
        %If a set of four spots in the matrix board equal 1, player 1 has
        %won, but if it equals 2, player 2 has won.  Each function checks a
        %set of four in a different orientation.  This is a columnwise, or
        %horizontal, check for a set of four.
        if board(row, col:(col+3))==1 %This checks four spots on the board that are next to each other in a certain row
            result=1;
            break %If someone has won, do not check the other options
        elseif board(row,col:(col+3)) == 2
            result = 2;            
            break
            
        end
    end
end

%Rowwise, or vertical, check for sets of four following the same procedure.
for col = 1:7
    for row = 1:3
        if board(row:(row+3), col)==1
            result=1;            
            break
        elseif board(row:(row+3),col) ==2
            result = 2;
            break
            
        end
    end
end
if result==0 %If there is no result after the first two tests, test diagonally 
    
    %The diagonal tests follow the same exact procedure, checking each
    %valid diagonal on the board using variable positions on the matrix
    %board following a pattern
    for col=1:4
        for row = 1:3
            diagonalFour1=[board(row,col),board(row+1,col+1),board(row+2,col+2),board(row+3,col+3)];
                                   
            if diagonalFour1==1
                
                result= 1 ;
                
                            
            elseif(diagonalFour1 == 2)
                               
                result=2;
                
                
            end
        end
    end
            for col1=7:-1:4
                for row1=1:3
                     diagonalFour2=[board(row1,col1),board(row1+1,col1-1),board(row1+2,col1-2), board(row1+3,col1-3)];   
                     if diagonalFour2==1
                
                result= 1 ;
               
                            
            elseif(diagonalFour2 == 2)
                               
                result=2;
               
                     end
                end
            
           end
            
    
end
%This program will return the variable "result", which indicates which
%player has won, or if nobody has won yet.
end


