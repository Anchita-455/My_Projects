''' DO NOT FORGET TO ADD COMMENTS '''
# author: Anchita Dash
# CSE 30
# game.py file that uses fifteen.py

from tkinter import *
import tkinter.font as font
from tkmacosx import Button # import for tkinter so that colors work on mac
from fifteen import Fifteen
from random import choice
          
# this method adds the button to the gui window
def addButton(gui,value,pos):
    return Button(gui, textvariable=value, name=str(pos), bg="orange", fg="black", 
        font=font1, height=100, width=200,command = lambda: clickButton(value,pos))

# button handler that checks whether the button clicked is a valid move or 
# not. If it is a valid move, then it swaps the empty button with the correct button.
def clickButton(value,pos):
    global empty
    # gets the text of button
    value = gui.nametowidget(pos).cget("text")

    if(value == " "):
        return

    move = int(value)
    valid = tiles.is_valid_move(move)
    if(not valid):
        return
    
    move_text = StringVar()
    move_text.set(str(move))
    empty_text = StringVar()
    empty_text.set(" ")
    tiles.update(move)
    gui.nametowidget(pos).configure(textvariable = empty_text,bg = "salmon")
    gui.nametowidget(empty).configure(textvariable = move_text,bg = "orange")
    empty = pos
    
def close():
    gui.destroy()

# shuffles the board by switching two different tiles throughout the 
# board multiple times. 
def shuffle(count,number):
    global empty
    global tiles

    tile_list = tiles.vertToTile
    neighbors = tiles.adj

    if(count < number):
        empty_neighbors = neighbors[empty]
        spot = choice(empty_neighbors)
        tile = tile_list[spot]
        clickButton(tile,spot)

        gui.after(200, lambda: shuffle(count+1, number))
       


if __name__ == '__main__':    

    # make tiles
    global tiles
    tiles = Fifteen()
    global empty
    empty = 15
    # make a window
    gui = Tk()
    gui.title("Fifteen")

    # make font
    global font1
    font1 = font.Font(family='Helveca', size='25', weight='bold')

    # add buttons to the window
    buttons = []
    labels = [StringVar() for i in range(16)]
    for i in range(15): 
        labels[i].set(i+1)
        buttons.append(addButton(gui,labels[i],i))
    labels[15].set(' ')
    buttons.append(addButton(gui,labels[15],15))
    gui.nametowidget(str(15)).configure(bg='salmon')

    index = 0
    for r in range(4):
        for c in range(4):
            buttons[index].grid(row = r,column = c)
            index += 1
    
    #creating a shuffle button
    shuffle_text = StringVar()
    shuffle_text.set("Shuffle")
    shuffle_button = Button(gui, textvariable=shuffle_text, name="shuffle",
                    bg='white', fg='black', font=font1, height=50, width=200,
                    command = lambda : shuffle(1,100))

    shuffle_button.grid(row = 5,column = 1)

    # quit button closes the window
    quit_text = StringVar()
    quit_text.set("Quit")
    quit_button = Button(gui, textvariable=quit_text, name= "quit",
                    bg='white', fg='black', font=font1, height=50, width=200,
                    command = close)

    quit_button.grid(row = 5,column = 2)

    # update the window
    gui.mainloop()
