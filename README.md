# 2d_game

# Game Overview
“A dark evil has arrived in your hometown. A group of government scientists have opened a gate in their laboratory to another dimension called the Over Under. The Over Under is ruled by Navec (an evil creature of immense power) and his henchmen are called demons. The scientists thought they could control these creatures but alas they failed and are being held captive in the Over Under. Navec has created sinkholes that will destroy the lab and is planning on eventually destroying your world.
The player’s name is Fae, the daughter of one of the scientists. In order to save your father and your town, you need to avoid the sinkholes, find the gate in the lab and defeat Navec & his demons in the Over Under...”
The game features two levels : Level 0 is in the lab and Level 1 is in the Over Under. In Level 0, the player will be able to control Fae who has to move around the walls and avoid any sinkholes that are in the lab. If the player falls into a sinkhole, the player will lose health points. To finish the level, the player has to get to the gate, located in the bottom right of the window. If the player’s health reduces to 0, the game ends. You have already implemented Level 0 in Project 1 (the only change required is to the winning message screen which is explained later).
When the player finishes Level 0, Level 1 starts - Fae is now in the Over Under. To win the level and the game, the player must fight and defeat Navec. However, the player has to deal with more sinkholes and demons too. The player can cause damage to the demons and Navec when the player presses a certain key. Likewise, the demons and Navec can cause damage to the player. The player will also have to move around trees (they don’t cause any damage like the walls in Level 0).

Level 1 will also give the player the opportunity to change the timescale so that the difficulty of the game can be changed. Pressing a certain key will increase/decrease the speed of the demons and Navec (this is explained in more detail later). Like in Level 0, the game will end if the player’s health reduces to 0 or less.

<img width="492" alt="Screen Shot 2022-11-30 at 23 48 20" src="https://user-images.githubusercontent.com/74129398/204800337-40418b4c-e09d-4fc7-b1b0-366edcb1f56f.png">


# The Game Engine
The Basic Academic Game Engine Library (Bagel) is a game engine that you will use to develop your game. You can find the documentation for Bagel here.

# Coordinates
Every coordinate on the screen is described by an (x,y) pair. (0,0) represents the top-left of the screen, and coordinates increase towards the bottom-right. Each of these coordinates is called a pixel. The Bagel Point class encapsulates this.

# Frames
Bagel will refresh the program’s logic at the same refresh rate as your monitor. Each time, the screen will be cleared to a blank state and all of the graphics are drawn again. Each of these steps is called a frame. Every time a frame is to be rendered, the update() method in ShadowDimension is called. It is in this method that you are expected to update the state of the game.
The refresh rate is typically 60 times per second (Hz) but newer devices might have a higher rate. In this case, when your game is running, it may look different to the demo videos as the constant values in this specification have been chosen for a refresh rate of 60Hz. For your convenience, when writing and testing your code, you may either change these values to make your game playable or lower your monitor’s refresh rate to 60Hz. If you do change the values, remember to change them back to the original specification values before submitting, as your code will be marked on 60Hz screens.
It is highly recommended that you store this refresh rate as a constant in your code, which can
be used to calculate timers and cooldowns. For example, let’s say we want the player’s attack
cooldown to be 500 milliseconds. To check whether it’s been 500ms since the last attack, we can
have a counter variable that gets incremented by 1 in each update() call, and by dividing this
counter value with the number of frames per millisecond, we can calculate the exact number of
seconds it’s been. Thus, if it’s been 30 frames since the player’s last attack, and the refresh rate is
60 frames per second, we can calculate that it has been 30/ 60 = 500 milliseconds since that last 1000
attack, so the player should be able to attack again in the next frame. Note that this is purely an example, and there are different ways to implement the cooldowns, so we’ll allow some room for error in these calculations. As long as the game still plays as intended, it’s okay if you miss some cooldowns by a couple milliseconds!

# Collisions
It is sometimes useful to be able to tell when two images are overlapping. This is called collision detection and can get quite complex. For this game, you can assume images are rectangles. Bagel contains the Rectangle class to help you.

# The Levels
Our game will have two levels, each with messages that would be rendered at the start and end of the level.

# Window and Background
In Level 0, the background (background0.png) should be rendered on the screen to completely fill up your window throughout the game. In Level 1, the image background1.png should be used for the background. The default window size should be 1024 * 768 pixels.

# Level Messages
All messages should be rendered with the font provided in res folder, in size 75 (unless otherwise specified). All messages should be centered both horizontally and vertically (unless otherwise specified).
Hint: The drawString() method in the Font class uses the given coordinates as the bottom left of the message. So to center the message, you will need to calculate the coordinates us- ing the Window.getWidth(), Window.getHeight() and Font.getWidth() methods, and also the font size.

# Level Start
When the game is run, Level 0 should start with a title message that reads SHADOW DIMENSION should be rendered in the font provided. The bottom left corner of this message should be located at (260, 250).
Additionally, an instruction message consisting of 2 lines:
                               PRESS SPACE TO START
                           USE ARROW KEYS TO FIND GATE
should be rendered below the title message, in the font provided, in size 40. The bottom left of the first line in the message should be calculated as follows: the x-coordinate should be increased by 90 pixels and the y-coordinate should be increased by 190 pixels.
There must be adequate spacing between the 2 lines to ensure readability (you can decide on the value of this spacing yourself, as long as it’s not small enough that the text overlaps or too big that it doesn’t fit within the screen). You can align the lines as you wish. Nothing else, not even the background, should be rendered in the window before the game has begun.
At the start of Level 1, the following instruction message with these 3 lines should be shown:
                               PRESS SPACE TO START
                                PRESS A TO ATTACK
                               DEFEAT NAVEC TO WIN
This message should be rendered in the font provided in size 40 and the bottom left of the first line in the message should be located at (350, 350). The spacing and alignment of the lines is the same as described above. Nothing else, not even the background, should be rendered in the window at this time.
Each level begins once the start key (space bar) is pressed. To help when testing your game, you can allow the user to skip ahead to the Level 1 start screen by pressing the key ’W’ (this is not assessed but will help you when coding, especially when working on Level 1).


# World File
All the actors will be defined in a world file, describing the types and their positions in the window. The world file for Level 0 is level0.csv and Level 1 is level1.csv. Both world files will contain the level bounds at the end of each file. A world file is a comma-separated value (CSV) file with rows in the following format:
    Type, x-coordinate, y-coordinate
An example of a world file:
    Player,5,696
    Tree,120,680
    Demon,50,400
    Sinkhole,255,655
    TopLeft,0,20
    BottomRight,1000,640
You must actually load both files—copying and pasting the data, for example, is not allowed. Note: You can assume that the player is always the first entry in both files, the Level 0 world file will have a maximum of 60 entries and the Level 1 world file will have a maximum of 29 entries.

# Level Bounds
This is a rectangular perimeter that represents the edges of the level, which will be provided in the level’s CSV file (TopLeft for the top-left (x, y) coordinate of the perimeter, BottomRight for the bottom-right). You can assume that all entities provided will have a starting location within this perimeter. Moving entities, like the player and the enemies, should not be able to move outside of this perimeter. For example, if the player tries to move past the left boundary, they should simply remain at the position they were at when they tried to cross this perimeter until the player is moved in a different direction.
For enemies, they should simply start moving in the opposite direction if they collide with the level bounds (e.g. if they were moving up when they reached the top edge, they should start moving down after the collision).

# Win Conditions
For Level 0, once the player reaches the gate, this is the end of the level. To reach the gate, the player’s x coordinate must be greater than or equal to 950 and the y coordinate must be greater than or equal to 670. A winning message that reads LEVEL COMPLETE! should be rendered as described earlier in the Level Messages section. Note that nothing else (not even the background) must be displayed in the window at this time and this message should be rendered for 3 seconds before displaying the start screen for Level 1.
In Level 1, once the player defeats Navec (Navec’s health reduces to 0 or below), this is considered a win. A winning message that reads CONGRATULATIONS! should be rendered as described in the Level Messages section. Once again, nothing else (not even the background) must be displayed in the window at this time, and there’s no need to terminate the game window while this is being displayed.

# Lose Conditions
On either level, while there is no win, the game will continue running until it ends. As described earlier, the game can only end if the player’s health points reduce to 0. A message of GAME OVER! should be rendered as described in the Level Messages section. Note that nothing else (not even the background) must be displayed in the window at this time, and there’s no need to terminate the game window while this is being displayed.
If the player terminates the game window at any point (by pressing the Escape key or by clicking the Exit button), the window will simply close and no message will be shown.

Log
• Every time an entity inflicts damage on another entity, a sentence detailing this is printed on the command line, in the following format:
Entity A inflicting damage on entity B:
     A inflicts x damage points on B. B’s current health:  y/z
where x is the amount of damage, y is the health points value after the damage was inflicted and z is the maximum health points value. When fire inflicts damage on the player, the name of the entity that shot it (either Demon or Navec) should be used as shown below.
For example:
     Sinkhole inflicts 30 damage points on Fae.  Fae’s current health:  70/100
     Fae inflicts 20 damage points on Demon.  Demon’s current health:  20/40
     Navec inflicts 20 damage points on Fae.  Fae’s current health:  50/100
• When a timescale control is used, a sentence detailing this is printed as follows: Sped up, Speed: x Slowed down, Speed: x
where x is the timescale value after the key has been pressed. For example:
Sped up, Speed: 2
Slowed down, Speed: 0
Slowed down, Speed: -3
 
