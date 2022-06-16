import unittest


class Game:
    def __init__(self):
        self.board = [
            ["bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR"],
            ["bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"],
            ["wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR"]]

        self.move_functions = {"p": self.get_pawn_moves, "R": self.get_rook_moves, "N": self.get_knight_moves, "B": self.get_bishop_moves, "Q": self.get_queen_moves, "K": self.get_king_moves}
        self.whiteToMove = True
        self.move_history = []
        self.white_king_location = (7, 4)
        self.black_king_location = (0, 4)
        self.check_mate = False
        self.stale_mate = False
        self.enpassant = ()
        self.enpassant_history = [self.enpassant]
        self.current_castling_right = CastleRight(True, True, True, True)
        self.castle_right_history = [CastleRight(self.current_castling_right.wks, self.current_castling_right.bks,
                                                 self.current_castling_right.wqs, self.current_castling_right.bqs)]



    def make_move(self, move):
        """
        It allows us to make a "move"
        :param move:
        :return:
        """
        self.board[move.start_row][move.start_col] = "--"
        self.board[move.finish_row][move.finish_col] = move.piece_moved
        self.move_history.append(move)
        self.whiteToMove = not self.whiteToMove
        if move.piece_moved == "wK":
            self.white_king_location = (move.finish_row, move.finish_col)
        elif move.piece_moved == "bK":
            self.black_king_location = (move.finish_row, move.finish_col)


        if move.is_pawn_promotion:
            self.board[move.finish_row][move.finish_col] = move.piece_moved[0] + "Q"


        if move.is_enpassant:
            self.board[move.start_row][move.finish_col] = "--"

        if move.piece_moved[1] == "p" and abs(move.start_row - move.finish_row) == 2:
            self.enpassant = ((move.start_row + move.finish_row) // 2, move.start_col)
        else:
            self.enpassant = ()



        if move.is_castle_move:
            if move.finish_col - move.start_col == 2:
                self.board[move.finish_row][move.finish_col - 1] = self.board[move.finish_row][move.finish_col + 1]
                self.board[move.finish_row][move.finish_col + 1] = "--"
            else:
                self.board[move.finish_row][move.finish_col + 1] = self.board[move.finish_row][move.finish_col - 2]
                self.board[move.finish_row][move.finish_col - 2] = "--"


        self.enpassant_history.append(self.enpassant)

        self.update_castle_rights(move)
        self.castle_right_history.append(CastleRight(self.current_castling_right.wks, self.current_castling_right.bks,
                                                 self.current_castling_right.wqs, self.current_castling_right.bqs))





    def undo(self):
        """
        Returning to the last game state
        :return:
        """
        if len(self.move_history) != 0:
            move = self.move_history.pop()
            self.board[move.start_row][move.start_col] = move.piece_moved
            self.board[move.finish_row][move.finish_col] = move.piece_captured
            self.whiteToMove = not self.whiteToMove
            if move.piece_moved == "wK":
                self.white_king_location = (move.start_row, move.start_col)
            elif move.piece_moved == "bK":
                self.black_king_location = (move.start_row, move.start_col)


            if move.is_enpassant:
                self.board[move.finish_row][move.finish_col] = "--"
                self.board[move.start_row][move.finish_col] = move.piece_captured
                #self.enpassant = (move.finish_row, move.finish_col)

            #if move.piece_moved[1] == "p" and abs(move.start_row - move.finish_row) == 2:
                #self.enpassant = ()

            self.enpassant_history.pop()
            self.enpassant = self.enpassant_history[-1]


            self.castle_right_history.pop()
            castle_rights = self.castle_right_history[-1]
            self.current_castling_right = castle_rights

            if move.is_castle_move:
                if move.finish_col - move.start_col == 2:
                    self.board[move.finish_row][move.finish_col + 1] = self.board[move.finish_row][move.finish_col  - 1]
                    self.board[move.finish_row][move.finish_col - 1] = "--"
                else:
                    self.board[move.finish_row][move.finish_col - 2] = self.board[move.finish_row][move.finish_col + 1]
                    self.board[move.finish_row][move.finish_col + 1] = "--"

            self.check_mate = False
            self.stale_mate = False






    def update_castle_rights(self, move):
        """
        If rooks / kings / queens are moved, than we can no longer perform a castling move
        :param move:
        :return:
        """
        if move.piece_moved == "wK":
            self.current_castling_right.wks = False
            self.current_castling_right.wqs = False

        elif move.piece_moved == "bK":
            self.current_castling_right.bks = False
            self.current_castling_right.bqs = False

        elif move.piece_moved == "wR":
            if move.start_row == 7:
                if move.start_col == 0:
                    self.current_castling_right.wqs = False
                elif move.start_col == 7:
                    self.current_castling_right.wks = False

        elif move.piece_moved == "bR":
            if move.start_row == 0:
                if move.start_col == 0:
                    self.current_castling_right.bqs = False
                elif move.start_col == 7:
                    self.current_castling_right.bks = False

        if move.piece_moved == "wR":
            if move.finish_row == 7:
                if move.finish_col == 0:
                    self.current_castling_right.wqs = False
                elif move.finish_col == 7:
                    self.current_castling_right.wks = False

            elif move.piece_moved == "bR":
                if move.finish_row == 0:
                    if move.finish_col == 0:
                        self.current_castling_right.bqs = False
                    elif move.finish_row == 7:
                        self.current_castling_right.bks = False



    def get_valid_moves(self):
        """
        Returning all possible moves that a player can perform
        :return:
        """
        temporary_enpassant = self.enpassant
        temporary_castle_rights = CastleRight(self.current_castling_right.wks, self.current_castling_right.bks,
                                              self.current_castling_right.wqs, self.current_castling_right.bqs)
        moves = self.get_all_possible_moves()

        if self.whiteToMove:
            self.get_castle_moves(self.white_king_location[0], self.white_king_location[1], moves)
        else:
            self.get_castle_moves(self.black_king_location[0], self.black_king_location[1], moves)

        for i in range(len(moves) - 1, -1, -1):
            self.make_move(moves[i])
            self.whiteToMove = not self.whiteToMove
            if self.in_check():
                moves.remove(moves[i])
            self.whiteToMove = not self.whiteToMove
            self.undo()
        if len(moves) == 0:
            if self.in_check():
                self.check_mate = True
            else:
                self.stale_mate = True

        self.enpassant = temporary_enpassant
        self.current_castling_right = temporary_castle_rights
        return moves


    def in_check(self):
        """
        Checks if the king's cell is under attack
        :return:
        """
        if self.whiteToMove:
            return self.square_under_attack(self.white_king_location[0], self.white_king_location[1])
        else:
            return self.square_under_attack(self.black_king_location[0], self.black_king_location[1])


    def square_under_attack(self, row, col):
        """
        Checks if a specific cell is attacked by the opponent
        :param row:
        :param col:
        :return:
        """
        self.whiteToMove = not self.whiteToMove
        opponent_moves = self.get_all_possible_moves()
        self.whiteToMove = not self.whiteToMove
        for each_move in opponent_moves:
            if each_move.finish_row == row and each_move.finish_col == col:
                return True

        return False


    def get_all_possible_moves(self):
        """
        Returning all moves
        :return:
        """
        moves = []
        for row in range(len(self.board)):
            for col in range(len(self.board[row])):
                turn = self.board[row][col][0]
                if (turn == "w" and self.whiteToMove) or (turn == "b" and not self.whiteToMove):
                    piece = self.board[row][col][1]
                    self.move_functions[piece](row, col, moves)

        # if len(moves) == 0:
        #     if self.in_check():
        #         self.check_mate = True
        #     else:
        #         self.stale_mate = True
        # else:
        #     self.check_mate = False
        #     self.stale_mate = False


        return moves


    def get_pawn_moves(self, row, col, moves):
        """
        All moves for a pawn
        :param row:
        :param col:
        :param moves:
        :return:
        """
        if self.whiteToMove:
            start_row = 6
            enemy_color = "b"
            king_row, king_col = self.white_king_location

        else:
            start_row = 1
            enemy_color = "w"
            king_row, king_col = self.black_king_location


        if self.whiteToMove:
            if self.board[row - 1][col] == "--":
                moves.append(Move((row, col), (row - 1, col), self.board))
                if row == 6 and self.board[row - 2][col] == "--":
                    moves.append(Move((row, col), (row - 2, col), self.board))

            if col - 1 >= 0:
                if self.board[row - 1][col - 1][0] == "b":
                    moves.append(Move((row, col), (row - 1, col - 1), self.board))
                elif (row - 1, col - 1) == self.enpassant:
                    attacking_piece = blocking_piece = False
                    if king_row == row:
                        if king_col < col:
                            inside_range = range(king_col + 1, col - 1)
                            outside_range = range(col + 1, 8)
                        else:
                            inside_range = range(king_col - 1, col, -1)
                            outside_range = range(col - 2, -1, -1)

                        for i in inside_range:
                            if self.board[row][i] != "--":
                                blocking_piece = True
                        for i in outside_range:
                            square = self.board[row][i]
                            if square[0] == enemy_color and (square[1] == "R" or square[1] == "Q"):
                                attacking_piece = True
                            elif square != "--":
                                blocking_piece = True

                    if not attacking_piece or blocking_piece:
                        moves.append(Move((row, col), (row - 1, col - 1), self.board, enpassant=True))

            if col + 1 <= 7:
                if self.board[row - 1][col + 1][0] == "b":
                    moves.append(Move((row, col), (row - 1, col + 1), self.board))
                elif (row - 1, col + 1) == self.enpassant:
                    attacking_piece = blocking_piece = False
                    if king_row == row:
                        if king_col < col:
                            inside_range = range(king_col + 1, col)
                            outside_range = range(col + 2, 8)
                        else:
                            inside_range = range(king_col - 1, col + 1, -1)
                            outside_range = range(col - 1, -1, -1)

                        for i in inside_range:
                            if self.board[row][i] != "--":
                                blocking_piece = True
                        for i in outside_range:
                            square = self.board[row][i]
                            if square[0] == enemy_color and (square[1] == "R" or square[1] == "Q"):
                                attacking_piece = True
                            elif square != "--":
                                blocking_piece = True

                    if not attacking_piece or blocking_piece:
                        moves.append(Move((row, col), (row - 1, col + 1), self.board, enpassant=True))
        else:
            if self.board[row + 1][col] == "--":
                moves.append(Move((row, col), (row + 1, col), self.board))
                if row == 1 and self.board[row + 2][col] == "--":
                    moves.append(Move((row, col), (row + 2, col), self.board))

            if col - 1 >= 0:
                if self.board[row + 1][col - 1][0] == "w":
                    moves.append(Move((row, col), (row + 1, col - 1), self.board))
                elif (row + 1, col - 1) == self.enpassant:
                    attacking_piece = blocking_piece = False
                    if king_row == row:
                        if king_col < col:
                            inside_range = range(king_col + 1, col - 1)
                            outside_range = range(col + 1, 8)
                        else:
                            inside_range = range(king_col - 1, col, -1)
                            outside_range = range(col - 2, -1, -1)

                        for i in inside_range:
                            if self.board[row][i] != "--":
                                blocking_piece = True
                        for i in outside_range:
                            square = self.board[row][i]
                            if square[0] == enemy_color and (square[1] == "R" or square[1] == "Q"):
                                attacking_piece = True
                            elif square != "--":
                                blocking_piece = True

                    if not attacking_piece or blocking_piece:
                        moves.append(Move((row, col), (row + 1, col - 1), self.board, enpassant=True))

            if col + 1 <= 7:
                if self.board[row + 1][col + 1][0] == "w":
                    moves.append(Move((row, col), (row + 1, col + 1), self.board))
                elif (row + 1, col + 1) == self.enpassant:
                    attacking_piece = blocking_piece = False
                    if king_row == row:
                        if king_col < col:
                            inside_range = range(king_col + 1, col)
                            outside_range = range(col + 2, 8)
                        else:
                            inside_range = range(king_col - 1, col + 1, -1)
                            outside_range = range(col - 1, -1, -1)

                        for i in inside_range:
                            if self.board[row][i] != "--":
                                blocking_piece = True
                        for i in outside_range:
                            square = self.board[row][i]
                            if square[0] == enemy_color and (square[1] == "R" or square[1] == "Q"):
                                attacking_piece = True
                            elif square != "--":
                                blocking_piece = True

                    if not attacking_piece or blocking_piece:
                        moves.append(Move((row, col), (row + 1, col + 1), self.board, enpassant=True))


    def get_rook_moves(self, row, col, moves):
        """
        All moves for a rook
        :param row:
        :param col:
        :param moves:
        :return:
        """
        direction = ((-1, 0), (0, 1), (1, 0), (0, -1))
        if self.whiteToMove:
            enemy_color = "b"
        else:
            enemy_color = "w"

        for d in direction:
            for k in range(1, 8):
                finish_row = row + d[0] * k
                finish_col = col + d[1] * k
                if 0 <= finish_row < 8 and 0 <= finish_col < 8:
                    finish_piece = self.board[finish_row][finish_col]
                    if finish_piece == "--":
                        moves.append(Move((row, col), (finish_row, finish_col), self.board))
                    elif finish_piece[0] == enemy_color:
                        moves.append(Move((row, col), (finish_row, finish_col), self.board))
                        break
                    else:
                        break
                else:
                    break



    def get_knight_moves(self, row, col, moves):
        """
        All moves for a knight
        :param row:
        :param col:
        :param moves:
        :return:
        """
        direction = ((-2, -1), (-2, 1), (-1, -2), (-1, 2), (1, -2), (1, 2), (2, -1), (2, 1))
        if self.whiteToMove:
            ally_color = "w"
        else:
            ally_color = "b"
        for d in direction:
            finish_row = row + d[0]
            finish_col = col + d[1]
            if 0 <= finish_row <= 7 and 0 <= finish_col <= 7:
                finish_piece = self.board[finish_row][finish_col]
                if finish_piece[0] != ally_color:
                    moves.append(Move((row, col), (finish_row, finish_col), self.board))



    def get_bishop_moves(self, row, col, moves):
        """
        All moves for a bishop
        :param row:
        :param col:
        :param moves:
        :return:
        """
        direction = ((-1, -1), (-1, 1), (1, -1), (1, 1))
        if self.whiteToMove:
            enemy_color = "b"
        else:
            enemy_color = "w"
        for d in direction:
            for k in range(1, 8):
                finish_row = row + d[0] * k
                finish_col = col + d[1] * k
                if 0 <= finish_row < 8 and 0 <= finish_col < 8:
                    finish_piece = self.board[finish_row][finish_col]
                    if finish_piece == "--":
                        moves.append(Move((row, col), (finish_row, finish_col), self.board))
                    elif finish_piece[0] == enemy_color:
                        moves.append(Move((row, col), (finish_row, finish_col), self.board))
                        break
                    else:
                        break
                else:
                    break



    def get_queen_moves(self, row, col, moves):
        """
        All moves for a queen
        Queen = Rook + Bishop
        :param row:
        :param col:
        :param moves:
        :return:
        """
        self.get_rook_moves(row, col, moves)
        self.get_bishop_moves(row, col, moves)



    def get_king_moves(self, row, col, moves):
        """
        All moves for a king
        :param row:
        :param col:
        :param moves:
        :return:
        """
        direction = ((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
        if self.whiteToMove:
            ally_color = "w"
        else:
            ally_color = "b"
        for i in range(8):
            finish_row = row + direction[i][0]
            finish_col = col + direction[i][1]
            if 0 <= finish_row < 8 and 0 <= finish_col < 8:
                finish_piece = self.board[finish_row][finish_col]
                if finish_piece[0] != ally_color:
                    moves.append(Move((row, col), (finish_row, finish_col), self.board))




    def get_castle_moves(self, row, col, moves):
        """
        Move for castling
        :param row:
        :param col:
        :param moves:
        :return:
        """
        if self.square_under_attack(row, col):
            return
        if (self.whiteToMove and self.current_castling_right.wks) or (not self.whiteToMove and self.current_castling_right.bks):
            self.get_king_side_castle_moves(row, col, moves)

        if (self.whiteToMove and self.current_castling_right.wqs) or (not self.whiteToMove and self.current_castling_right.bqs):
            self.get_queen_side_castle_moves(row, col, moves)



    def get_king_side_castle_moves(self, row, col, moves):
        """
        Moves for a king castling
        :param row:
        :param col:
        :param moves:
        :return:
        """
        if self.board[row][col + 1] == "--" and self.board[row][col + 2] == "--":
            if not self.square_under_attack(row, col + 1) and not self.square_under_attack(row, col + 2):
                moves.append(Move((row, col), (row, col + 2), self.board, is_castle_move = True))




    def get_queen_side_castle_moves(self, row, col, moves):
        """
        Moves for a queen castling
        :param row:
        :param col:
        :param moves:
        :return:
        """
        if self.board[row][col - 1] == "--" and self.board[row][col - 2] == "--" and self.board[row][col - 3] == "--":
            if not self.square_under_attack(row, col - 1) and not self.square_under_attack(row, col - 2):
                moves.append(Move((row, col), (row, col - 2), self.board), is_castle_move=True)



class CastleRight:
    def __init__(self, white_king_side, black_king_side, white_queen_side, black_queen_side):
        self.wks = white_king_side
        self.bks = black_king_side
        self.wqs = white_queen_side
        self.bqs = black_queen_side







class Move:
    ranks_to_rows = {"1": 7, "2": 6, "3": 5, "4": 4, "5": 3, "6": 2, "7": 1, "8": 0}
    rows_to_ranks = {v: k for k, v in ranks_to_rows.items()}
    files_to_cols = {"a": 0, "b": 1, "c": 2, "d": 3, "e": 4, "f": 5, "g": 6, "h": 7}
    cols_to_files = {v: k for k, v in files_to_cols.items()}

    def __init__(self, start_square, finish_square, board, enpassant = False, is_castle_move = False):
        self.start_row = start_square[0]
        self.start_col = start_square[1]
        self.finish_row = finish_square[0]
        self.finish_col = finish_square[1]
        self.piece_moved = board[self.start_row][self.start_col]
        self.piece_captured = board[self.finish_row][self.finish_col]

        self.is_pawn_promotion = False
        if (self.piece_moved == "wp" and self.finish_row == 0) or (self.piece_moved == "bp" and self.finish_row == 7):
            self.is_pawn_promotion = True

        self.is_enpassant = enpassant
        if self.is_enpassant:
            self.piece_captured = "wp" if self.piece_moved == "bp" else "bp"

        self.is_castle_move = is_castle_move

        self.is_captured = self.piece_captured != "--"

        self.moveID = self.start_row * 1000 + self.start_col * 100 + self.finish_row * 10 + self.finish_col

    def __eq__(self, other):
        """
        To check 2 objects that belonging to the class "Move" are the same
        :param other:
        :return:
        """
        if isinstance(other, Move):
            return self.moveID == other.moveID
        return False

    def get_chess_notation(self):
        """
        Notations like e4e5
        :return:
        """
        return self.get_rank_file(self.start_row, self.start_col) + self.get_rank_file(self.finish_row, self.finish_col)

    def get_rank_file(self, row, col):
        return self.cols_to_files[col] + self.rows_to_ranks[row]





class TestChess(unittest.TestCase):
    def setUp(self) -> None:
        self.game = Game()

    def tearDown(self) -> None:
        pass

    def test_move(self):
        move = Move((6, 0), (5, 0), self.game.board)
        self.game.make_move(move)
        self.assertEqual(self.game.board, [['bR', 'bN', 'bB', 'bQ', 'bK', 'bB', 'bN', 'bR'], ['bp', 'bp', 'bp', 'bp', 'bp', 'bp', 'bp', 'bp'], ['--', '--', '--', '--', '--', '--', '--', '--'], ['--', '--', '--', '--', '--', '--', '--', '--'], ['--', '--', '--', '--', '--', '--', '--', '--'], ['wp', '--', '--', '--', '--', '--', '--', '--'], ['--', 'wp', 'wp', 'wp', 'wp', 'wp', 'wp', 'wp'], ['wR', 'wN', 'wB', 'wQ', 'wK', 'wB', 'wN', 'wR']])

    def test_in_check(self):
        self.assertEqual(self.game.in_check(), False)
        self.game.board = [
            ["bR", "bN", "bB", "bQ", "--", "bB", "bN", "bR"],
            ["bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "bp", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "wK", "--", "--"],
            ["wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"],
            ["wR", "wN", "wB", "wQ", "--", "wB", "wN", "wR"]]

        move = Move((3, 4), (4, 4), self.game.board)
        self.game.make_move(move)
        self.assertEqual(self.game.in_check(), False)


    def test_square_under_attack(self):
        self.game.board = [
            ["bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR"],
            ["bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"],
            ["wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR"]]

        move = Move((6, 0), (4, 0), self.game.board)
        self.game.make_move(move)

        move = Move((1, 1), (3, 1), self.game.board)
        self.game.make_move(move)

        self.assertEqual(self.game.square_under_attack(4, 0), True)


    def test_get_king_moves(self):
        moves = []
        self.game.get_king_moves(7, 4, moves)
        self.assertEqual(len(moves), 0)

    def test_get_queen_moves(self):
        moves = []
        self.game.get_queen_moves(7, 3, moves)
        self.assertEqual(len(moves), 0)

    def test_get_rook_moves(self):
        moves = []
        self.game.get_rook_moves(7, 0, moves)
        self.assertEqual(len(moves), 0)

    def test_get_knight_moves(self):
        moves = []
        self.game.get_knight_moves(7, 1, moves)
        self.assertEqual(len(moves), 2)

    def test_get_bishop_moves(self):
        moves = []
        self.game.get_bishop_moves(7, 3, moves)
        self.assertEqual(len(moves), 0)

    def test_get_issei_move(self):
        moves = []
        self.game.get_pawn_moves(6, 0, moves)
        self.assertEqual(len(moves), 2)