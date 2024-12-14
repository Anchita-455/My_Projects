''' DO NOT FORGET TO ADD COMMENTS '''
# author: Anchita Dash
# CSE 30
# fifteen.py file that use Graph and Vertex 

import numpy as np
from random import choice
from graph import Graph,Vertex

class Fifteen:
    
    def __init__(self, size = 4):
        self.size = size
        self.tiles = np.array([i for i in range(1,self.size**2)] + [0])
        self.id = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
        self.adj = [[]for _ in range(self.size*self.size)]
        self.vertToTile = {}
        self.tileToVert = {}
        self.mapVertexToTile(self.id)
        
        # add vertices edges to the graph
        self.makeConnections(self.adj,self.id)
            
    # maps the vertex and to the corresponding tile
    def mapVertexToTile(self,id):
        self.vertToTile = {}
        self.tileToVert = {}
    
        for i in range(len(id)):
            self.vertToTile[id[i]] = self.tiles[i]
            self.tileToVert[self.tiles[i]] = self.id[i]
        
    # add edges and vertices to the graph
    def makeConnections(self,adj_list,ids):
        g = Graph()

        # loop through vertex id and add to the graph
        for i in ids:
            g.addVertex(i)

        #gets all ids of the verticess
        ids = g.getVertices()

        for i in ids:
            #gets specific vertex
            v = g.getVertex(i)
            #checking corners
            if(i == 0):
                g.addEdge(i,i+1)
                g.addEdge(i,i+4)
            elif(i == 12):
                g.addEdge(i,i+1)
                g.addEdge(i,i-4)
            elif(i == 3):
                g.addEdge(i,i-1)
                g.addEdge(i,i+4)
            elif(i == 15):
                g.addEdge(i,i-1)
                g.addEdge(i,i-4)
            #edge vertices 
            elif(i == 1 or i == 2):
                g.addEdge(i,i-1)
                g.addEdge(i,i+1)
                g.addEdge(i,i+4)
            elif(i == 4 or i == 8):
                g.addEdge(i,i-4)
                g.addEdge(i,i+1)
                g.addEdge(i,i+4)
            elif(i == 7 or i == 11):
                g.addEdge(i,i-4)
                g.addEdge(i,i-1)
                g.addEdge(i,i+4)
            elif(i == 13 or i == 14):
                g.addEdge(i,i-4)
                g.addEdge(i,i-1)
                g.addEdge(i,i+1)
            #center vertices
            else:
                g.addEdge(i,i-4)
                g.addEdge(i,i-1)
                g.addEdge(i,i+1)
                g.addEdge(i,i+4)

            #adds all the connections to adj_list
            for x in v.connectedTo:
                adj_list[i].append(x.id)       
        
    def update(self, move):
        if(move < 0 or move >= self.size**2):
            return
        
        if(self.is_valid_move(move) == False):
            return
        
        i = self.tileToVert[move]
        j = self.tileToVert[0]
        self.transpose(i,j)
        self.mapVertexToTile(self.id)
        
    # swaps two tiles
    def transpose(self, i, j):
        self.tiles[i],self.tiles[j] = self.tiles[j],self.tiles[i]

    # shuffles all the tiles by switching two different tiles throughout 
    # the board multiple times
    def shuffle(self, steps=100):
        index = np.where(self.tiles == 0)[0][0]
        for i in range(steps):
            move_index = choice(self.adj[index])
            self.tiles[index],self.tiles[move_index] = self.tiles[move_index],self.tiles[index]
            index = move_index
        self.mapVertexToTile(self.id)
        
    # checks whether the move is valid
    def is_valid_move(self, move):
        # figure out the current position of move 
        pos = self.tileToVert[move]
        # figure out all connections of move 
        connections = self.adj[pos]
       # if one of those is 0, then it is a valid move, otherwise it is not a valid move
        check_0 = False
        for vertex in connections:
            cor_tile = self.vertToTile[vertex]
            if(cor_tile == 0):
                check_0 = True
                break
        if(check_0 == True):
            return True
        return False
        
    # checks if the puzzle is arranged in chronological order from 
    # 1-15 and the last tile is a space. These two conditions means that 
    # the game is over. 
    def is_solved(self):
        index = 1
        for i in range(len(self.tiles)):
            if(self.tiles[i] != index and index <= 15):
                return False
            if(index == 16 and self.tiles[i] != 0):
                return False
            index = index + 1

        return True

    # draws the entire board
    def draw(self):
        index = 0
        for i in range(self.size):
            a,b,c,d = self.tiles[index:index+4]
            if(self.tiles[index] == 0):
                a  = "  "
            elif(self.tiles[index+1] == 0):
                b = "  "
            elif(self.tiles[index+2] == 0):
                c = "  "
            elif(self.tiles[index+3] == 0):
                d = "  "
            
            print(' +---+---+---+---+ ')
            print(' |{:^3}|{:^3}|{:^3}|{:^3}| '.format(a,b,c,d))
            index = index + 4
        print(' +---+---+---+---+ ')
        
    # prints all the tiles that are present on the board
    def __str__(self):
        index = 0
        my_str = ""
   
        for i in range(self.size):
            a,b,c,d = self.tiles[index:index+4]
            if(self.tiles[index] == 0):
                a  = "  "
            elif(self.tiles[index+1] == 0):
                b = "  "
            elif(self.tiles[index+2] == 0):
                c = "  "
            elif(self.tiles[index+3] == 0):
                d = "  "
            
            if(index >= 12):
                my_str += '{: >2} {: >2} {: >2} {: >2} \n'.format(a,b,c,d)
            else:
                my_str += '{: >2} {: >2} {: >2} {: >2} \n'.format(a,b,c,d)

            index = index + 4
        return my_str
    

if __name__ == '__main__':
    
    game = Fifteen()
    assert str(game) == ' 1  2  3  4 \n 5  6  7  8 \n 9 10 11 12 \n13 14 15    \n'
    assert game.is_valid_move(15) == True
    assert game.is_valid_move(12) == True
    assert game.is_valid_move(14) == False
    assert game.is_valid_move(1) == False
    game.update(15)
    assert str(game) == ' 1  2  3  4 \n 5  6  7  8 \n 9 10 11 12 \n13 14    15 \n'
    game.update(15)
    assert str(game) == ' 1  2  3  4 \n 5  6  7  8 \n 9 10 11 12 \n13 14 15    \n'
    assert game.is_solved() == True
    game.shuffle()
    game.is_valid_move(5)
    assert str(game) != ' 1  2  3  4 \n 5  6  7  8 \n 9 10 11 12 \n13 14 15    \n'
    assert game.is_solved() == False
    
    
    '''You should be able to play the game if you uncomment the code below'''
    game = Fifteen()
    game.shuffle()
    game.draw()
    while True:
        move = input('Enter your move or q to quit: ')
        if move == 'q':
            break
        elif not move.isdigit():
            continue
        game.update(int(move))
        game.draw()
        if game.is_solved():
            break
    print('Game over!')
    
    
        
