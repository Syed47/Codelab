import random as rnd

class Board:

	def __init__(self, size, symbols):
		self.size = size
		self.symbols = symbols
		self.move = rnd.choice(self.symbols)
		self.total_moves = 0
		self.board = []
		for i in range(size):
			self.board.append(["*" for _ in range(size)])

	def put(self, move) -> bool:
		if move[0] in self.symbols:
			self.board[move[1]][move[2]] = move[0]
			self.switch_move()
			self.total_moves += 1
			return True 
		return False

	def complete(self) -> bool:
		return False

	def switch_move(self):
		if self.move == self.symbols[0]:
			self.move = self.symbols[1]
		elif self.move == self.symbols[1]:
			self.move = self.symbols[0]

	def read_move(self) -> bool:
		print("\n[ Player {} Move ]".format(self.move))
		row = int(input("Row: "))
		col = int(input("Col: "))
		if row in range(0, self.size) and col in range(0, self.size):
			return self.put((self.move, row, col))


	def is_empty(self) -> bool:
		return self.total_moves == 0

	def print(self):
		for row in self.board:
			for val in row:
				print(val, end = " ")
			print()
