function Menu() 

%==============================%
%------------MENU--------------%
%            ----              %
%  Program to display a menu   %
%  for the main program.       %
%------------------------------%
%==============================%


%First, before anything else is done, the workspace and command window are
%cleared and cleaned

clc
clear

%Next, global variables are defined and then set

global menu players difficultylevel stop
stop=0;
difficultylevel=2;
menu=figure(); %A figure for the menu is created




%The axii on the figure are turned off and made into a square shape to
%allow for easier placement of buttons

axis off
axis square

%gcf stands for "Current Figure Handle" and works simply by applying the
%changes called for by the function set() to the figure most recently
%created

set(gcf, 'color', [0 0 0]) %Set color of the figure to black
text(.5,1,'CONNECT 4','Color','y','Fontsize',40,'horizontalalignment','center')
text(.5,.9,'BY: KEEYAN GHORESHI','Color','r','Fontsize',10,'horizontalalignment','center')
circles=cell(2,8);
for y=1:8
circles{1,y}=text(-.1,(y/10)-.1,'O','Color','y','Fontsize',30,'horizontalalignment','center');
end
for y=1:8
circles{2,y}=text(1.1,(y/10)-.1,'O','Color','y','Fontsize',30,'horizontalalignment','center');
end




%This creates a button that calls the main startup function when pressed,
%followed by two buttons that alter the state of a global variable
%"players" which will be accessed by the main program to know how many
%players are desired.  

start=uicontrol('Style','Pushbutton','String','Start!','Backgroundcolor',[0 0 0],'ForegroundColor','w','Position',[250,250,75,50],'Enable','off','Callback',{@Connect4});
button1=uicontrol('Style','Pushbutton','String','1 Player','Backgroundcolor',[0 0 0],'ForegroundColor',[1 1 1],'Position',[200,150,75,50],'Callback',@OnePlayer);
button2=uicontrol('Style','Pushbutton','String','2 Player','Backgroundcolor',[0 0 0],'ForegroundColor',[1 1 1],'Position',[300,150,75,50],'Callback',@TwoPlayer);
button3=uicontrol('Style','Pushbutton','String','Hard','Backgroundcolor',[0 0 0],'ForegroundColor',[1 1 1],'Visible','off','Position',[330,75,50,25],'Callback',{@Difficulty, 3});
button4=uicontrol('Style','Pushbutton','String','Medium','Backgroundcolor',[0 0 0],'ForegroundColor',[1 1 1],'Visible','off','Position',[255,75,50,25],'Callback',{@Difficulty, 2});
button5=uicontrol('Style','Pushbutton','String','Easy','Backgroundcolor',[0 0 0],'ForegroundColor',[1 1 1],'Visible','off','Position',[180,75,50,25],'Callback',{@Difficulty, 1});
button6=uicontrol('Style','Pushbutton','String','QUIT','Backgroundcolor',[0 0 0],'ForegroundColor',[1 1 1],'Visible','on','Position',[255,25,50,25],'Callback',@Quit);
buttons=cell(1,3);
buttons{1,1}=button5;
buttons{1,2}=button4;
buttons{1,3}=button3;

%Two functions are defined to be called by the buttons previously created.
%In order to work, the functions must accept two default parameters,
%hObject and  callbackdata that hold information about the button that was
%pressed.

    function OnePlayer(hObject,callbackdata)
        %If the first button is pressed, the variable 'players' is set to
        %one to indicate there should be a computer player and then the
        %"start" button is enabled, allowing it to be clicked.
        players=1;
        set(start,'Enable','on')
        set(button2,'Enable','on')
        set(button1,'Enable','off')
        set(buttons{1},'Enable','on')
        set(buttons{2},'Enable','on')
        set(buttons{3},'Enable','on')
        set(buttons{1},'Visible','on')
        set(buttons{2},'Visible','on')
        set(buttons{3},'Visible','on')
    end
    function TwoPlayer(hObject,callbackdata)
        %This function works the same way, but is called by the second
        %button.
        
        players=2;
        set(start,'Enable','on')
        set(button2,'Enable','off')
        set(button1,'Enable','on')
        set(buttons{1},'Enable','off')
        set(buttons{2},'Enable','off')
        set(buttons{3},'Enable','off')
        set(buttons{1},'Visible','on')
        set(buttons{2},'Visible','on')
        set(buttons{3},'Visible','on')
    end

    function Difficulty(hObject,callbackdata,diff)
        difficultylevel=diff;
        set(buttons{1},'Enable','on')
        set(buttons{2},'Enable','on')
        set(buttons{3},'Enable','on')
        set(buttons{diff},'Enable','off')
    end

    function Quit(h0bject,callbackdata)
        clear
        clc
        close all
        stop=1;
    end
        
while stop==0
    for c=1:8
        try
        set(circles{1,c},'Color','r')
        set(circles{2,c},'Color','r')
        pause(.2)
        set(circles{1,c},'Color','y')
        set(circles{2,c},'Color','y')
        catch
        end
        
    end
end
        

end

