# author: Anchita Dash
# date: April 6,2024
# file: Board.py

class Board:
      def __init__(self):
            # board is a list of cells that are represented 
            # by strings (" ", "O", and "X")
            # initially it is made of empty cells represented 
            # by " " strings
            self.sign = " "
            self.size = 3
            self.board = list(self.sign * self.size**2)
            # the winner's sign O or X
            self.winner = ""

      def get_size(self): 
            # optional, return the board size (an instance size)
            return self.size
             
      def get_winner(self):
            # return the winner's sign O or X (an instance winner) 
            return self.winner
               
      def set(self, cell, sign):
            # mark the cell on the board with the sign X or O
            valid_choices = ["A1","B1","C1","A2","B2","C2","A3","B3","C3"]
            index = valid_choices.index(cell)
            self.board[index] = sign
            
      def isempty(self, cell):
            index = None
            if (cell == " "):
                  return True
            elif isinstance(cell,str):
                  valid_choices = ["A1","B1","C1","A2","B2","C2","A3","B3","C3"]
                  index = valid_choices.index(cell)
            else:
                  index = cell

            # return True if the cell is empty (not marked with X or O)
            if(self.board[index] == " "):
                  return True
            else:
                  return False
            
      def isdone(self):
            # check all game terminating conditions, if one of them is present, assign the var done to True
            # depending on conditions assign the instance var winner to O or X
            self.winner = None
            done = False

            #checking rows (iterating through a list that has indices 0,3,6)
            for x in [0,3,6]:
                  if(self.board[x] == self.board[x+1] == self.board[x+2]):
                        if(not (self.isempty(x))):
                              self.winner = self.board[x]
                              return True

            #checking columns
            for x in range(0,3):
                  if(self.board[x] == self.board[x+3] == self.board[x+6]):
                        if(not (self.isempty(x))):
                              self.winner = self.board[x]
                              return True
            
            #checking diagonals 
            if(self.board[0] == self.board[4] == self.board[8] != self.sign):
                  self.winner = self.board[0]
                  return True
            elif(self.board[2] == self.board[4] == self.board[6] != self.sign):
                  self.winner = self.board[2]
                  return True
            
            #checking for tie
            if self.sign not in self.board:
                  self.winner = ""
                  return True
            
            return False
           
      def show(self):
            # draw the board
            # need to complete the code
            print()
            print('   A   B   C  ') 
            print(' +---+---+---+')
            print('1| {} | {} | {} |'.format(self.board[0], self.board[1], self.board[2]))
            print(' +---+---+---+')
            print("2| {} | {} | {} |".format(self.board[3], self.board[4], self.board[5]))
            print(" +---+---+---+")
            print("3| {} | {} | {} |".format(self.board[6], self.board[7], self.board[8]))
            print(" +---+---+---+")
