from board import Board
from brain import Brain

size = int(input("Enter size of the board: "))
board = Board(size, ("X", "O"))

while not board.complete():
	board.read_move()
	board.print()

