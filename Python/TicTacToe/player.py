# author: Anchita Dash
# date: April 6,2024
# file: player.py 
# input: user responses (strings) from the game and an instance of board
#output: prints the board on the terminal

class Player:
      def __init__(self, name, sign, board=None):
            self.name = name  # player's name
            self.sign = sign  # player's sign O or X
            self.board = board
      def get_sign(self):
            # return an instance variable sign
            return self.sign
      def get_name(self):
            # return an instance variable name
            return self.name
      
      def choose(self, board):
            # prompt the user to choose a cell
            # if the user enters a valid string and the cell on the board is empty, update the board
            # otherwise print a message that the input is wrong and rewrite the prompt
            # use the methods board.isempty(cell), and board.set(cell, sign)
            valid_choices = ["A1","A2","A3","B1","B2","B3","C1","C2","C3"]
            while True: 
                  print()
                  cell = input(f'{self.name}, {self.sign}: Enter a cell [A-C][1-3]: ').upper()
                  print()
                  if cell in valid_choices:
                        if board.isempty(cell):
                              board.set(cell, self.sign)
                        else:
                             print("You did not choose correctly.")
                             print()
                             cell = input(f'{self.name}, {self.sign}: Enter a cell [A-C][1-3]: ').upper()
                             print()
                             board.set(cell, self.sign)
                  else:
                     print("You did not choose correctly.")
                     print()
                     cell = input(f'{self.name}, {self.sign}: Enter a cell [A-C][1-3]: ').upper()
                     print()
                     board.set(cell, self.sign)   
                  break

from random import choice
class AI(Player):
    def __init__(self, name, sign, board=None):
        super().__init__(name, sign)
        self.board = board
        self.moves = self._moves()

    def _moves(self):
        if self.board != None:
            n = self.board.get_size()
        else:
            n = 3
        cells = []
        for char in range(65, 65+n):
            for i in range(1, 1+n):
                cells.append(chr(char) + str(i))
        return cells

    def choose(self, board=None):
        print(f"{self.name}, {self.sign}: Enter a cell [A-C][1-3]: ", end="")
        while True:
            cell = choice(self.moves)
            if(board.isempty(cell)):
                board.set(cell, self.sign)
            self.moves.remove(cell)
            break
        print(cell)

class MiniMax(AI):
    def __init__(self, name, sign, board=None):
          super().__init__(name, sign, board)

    #asking user to select a cell
    def choose(self,board):
        print(f"{self.name}, {self.sign}: Enter a cell [A-C][1-3]: ", end="")
        # self_player checks whether it is AI or whether it is opponent
        #start - checks whether it is the first 
        move = MiniMax.minimax(self,board,True,True)
        board.set(move, self.sign)

    #checking all base cases
    def minimax(self,board,self_player,start):
        if board.isdone():
          #print(board.show())
          if(board.get_winner() == self.sign):
               return 1
          elif(board.get_winner() == ""):
               return 0
          else:
               return -1
        
        max_score = -99999999
        min_score = 99999999
        best_cell = ""

        valid_choices = ["A1","B1","C1","A2","B2","C2","A3","B3","C3"]

        for i in range(9):
            if(board.isempty(valid_choices[i])):
                if(self_player == True):
                    board.set(valid_choices[i],self.sign)
                    route_score = MiniMax.minimax(self,board,False,False)
                    if(route_score > max_score):
                        max_score = route_score
                        best_cell = valid_choices[i]
                else:
                    if self.sign == "X":
                        board.set(valid_choices[i],"O")
                    else:
                        board.set(valid_choices[i],"X")
                    route_score = MiniMax.minimax(self,board,True,False)
                    if(route_score < min_score):
                            min_score = route_score
                            best_cell = valid_choices[i]
                
                board.set(valid_choices[i]," ")

        if start == True:
            return best_cell
        elif self_player == True:
            return max_score
        else:
            return min_score

# Smart AI algorithm:
# if there are two marks either vertically, diagonally or horizontally, put the missing
#third mark so that it wins the game

class SmartAI(AI):
    def __init__(self, name, sign, board=None):
        super().__init__(name, sign)
        self.board = board
        

    def choose(self,b):
        print(f"{self.name}, {self.sign}: Enter a cell [A-C][1-3]: ", end="")

        valid_choices = ["A1","B1","C1","A2","B2","C2","A3","B3","C3"]

        # win if possible

        #check all three rows
        for x in [0,3,6]:
            if(b.board[x] == b.board[x+1] == self.sign):
                if(b.isempty(valid_choices[x+2])):
                    b.set(valid_choices[x+2],self.sign)
                    return
            if(b.board[x] == b.board[x+2] == self.sign):
                if(b.isempty(valid_choices[x+1])):
                    b.set(valid_choices[x+1],self.sign)
                    return
            if(b.board[x+1] == b.board[x+2] == self.sign):
                if(b.isempty(valid_choices[x])):
                    b.set(valid_choices[x],self.sign)
                    return

        #check all three columns
        for x in range(0,3):
            if(b.board[x] == b.board[x+3] == self.sign):
                if(b.isempty(valid_choices[x+6])):
                    b.set(valid_choices[x+6],self.sign)
                    return
            if(b.board[x] == b.board[x+6] == self.sign):
                if(b.isempty(valid_choices[x+3])):
                    b.set(valid_choices[x+3],self.sign)
                    return
            if(b.board[x+3] == b.board[x+6] == self.sign):
                if(b.isempty(valid_choices[x])):
                    b.set(valid_choices[x],self.sign)
                    return

        #check diagonal 1
        if(b.board[0] == b.board[4] == self.sign):
            if(b.isempty(valid_choices[8])):
                b.set(valid_choices[8],self.sign)
                return
        if(b.board[0] == b.board[8] == self.sign):
            if(b.isempty(valid_choices[4])):
                b.set(valid_choices[4],self.sign)
                return
        if(b.board[4] == b.board[8] == self.sign):
            if(b.isempty(valid_choices[0])):
                b.set(valid_choices[0],self.sign)
                return

        #check diagonal 2
        if(b.board[2] == b.board[4] == self.sign):
            if(b.isempty(valid_choices[6])):
                b.set(valid_choices[6],self.sign)
                return
        if(b.board[2] == b.board[6] == self.sign):
            if(b.isempty(valid_choices[4])):
                b.set(valid_choices[4],self.sign)
                return
        if(b.board[4] == b.board[6] == self.sign):
            if(b.isempty(valid_choices[2])):
                b.set(valid_choices[2],self.sign)
                return


        #prevent a loss 
        #check all three rows
        if(self.sign == "X"):
            opp_sign = "O"
        else:
            opp_sign = "X"

        for x in [0,3,6]:
            if(b.board[x] == b.board[x+1] == opp_sign):
                if(b.isempty(valid_choices[x+2])):
                    b.set(valid_choices[x+2],self.sign)
                    return
            if(b.board[x] == b.board[x+2] == opp_sign):
                if(b.isempty(valid_choices[x+1])):
                    b.set(valid_choices[x+1],self.sign)
                    return
            if(b.board[x+1] == b.board[x+2] == opp_sign):
                if(b.isempty(valid_choices[x])):
                    b.set(valid_choices[x],self.sign)
                    return

        #check all three columns
        for x in range(0,3):
            if(b.board[x] == b.board[x+3] == opp_sign):
                if(b.isempty(valid_choices[x+6])):
                    b.set(valid_choices[x+6],self.sign)
                    return
            if(b.board[x] == b.board[x+6] == opp_sign):
                if(b.isempty(valid_choices[x+3])):
                    b.set(valid_choices[x+3],self.sign)
                    return
            if(b.board[x+3] == b.board[x+6] == opp_sign):
                if(b.isempty(valid_choices[x])):
                    b.set(valid_choices[x],self.sign)
                    return

        #check diagonal 1
        if(b.board[0] == b.board[4] == opp_sign):
            if(b.isempty(valid_choices[8])):
                b.set(valid_choices[8],self.sign)
                return
        if(b.board[0] == b.board[8] == opp_sign):
            if(b.isempty(valid_choices[4])):
                b.set(valid_choices[4],self.sign)
                return
        if(b.board[4] == b.board[8] == opp_sign):
            if(b.isempty(valid_choices[0])):
                b.set(valid_choices[0],self.sign)
                return

        #check diagonal 2
        if(b.board[2] == b.board[4] == opp_sign):
            if(b.isempty(valid_choices[6])):
                b.set(valid_choices[6],self.sign)
                return
        if(b.board[2] == b.board[6] == opp_sign):
            if(b.isempty(valid_choices[4])):
                b.set(valid_choices[4],self.sign)
                return
        if(b.board[4] == b.board[6] == opp_sign):
            if(b.isempty(valid_choices[2])):
                b.set(valid_choices[2],self.sign)
                return

        # choose the center if its open
        if(b.isempty(valid_choices[4])):
            b.set(valid_choices[4],self.sign)
            return

        # choosing opposite corner
        if(b.board[6] == opp_sign):
            if(b.isempty(valid_choices[2])):
                b.set(valid_choices[2],self.sign)
                return
        if(b.board[2] == opp_sign):
            if(b.isempty(valid_choices[6])):
                b.set(valid_choices[6],self.sign)
                return
        if(b.board[0] == opp_sign):
            if(b.isempty(valid_choices[8])):
                b.set(valid_choices[8],self.sign)
                return
        if(b.board[8] == opp_sign):
            if(b.isempty(valid_choices[0])):
                b.set(valid_choices[0],self.sign)
                return
        
        # put it in a corner
        if(b.isempty(valid_choices[0])):
            b.set(valid_choices[0],self.sign)
            return
        if(b.isempty(valid_choices[2])):
            b.set(valid_choices[2],self.sign)
            return
        if(b.isempty(valid_choices[6])):
            b.set(valid_choices[6],self.sign)
            return
        if(b.isempty(valid_choices[8])):
            b.set(valid_choices[8],self.sign)
            return
        
        # checking four sides
        if(b.isempty(valid_choices[3])):
            b.set(valid_choices[3],self.sign)
            return
        if(b.isempty(valid_choices[7])):
            b.set(valid_choices[7],self.sign)
            return
        if(b.isempty(valid_choices[5])):
            b.set(valid_choices[5],self.sign)
            return
        if(b.isempty(valid_choices[1])):
            b.set(valid_choices[1],self.sign)
            return