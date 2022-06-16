import random
import unittest


class AI:
    def __init__(self):
        self.piece_score = {"K": 0, "Q": 10, "R": 5, "B": 3, "N": 3, "p": 1}
        self.checkmate = 1000
        self.stalemate = 0
        self.depth = 2


    def find_random_move(self, valid_moves):
        """
        Makes a random move
        :param valid_moves:
        :return:
        """
        return valid_moves[random.randint(0, len(valid_moves) - 1)]

    # def find_best_move(self, game_state, valid_moves):
    #     turn = 1 if game_state.whiteToMove else -1
    #     opponent_minmax_score = self.checkmate
    #     best_player_move = None
    #     random.shuffle(valid_moves)
    #     for player_move in valid_moves:
    #         game_state.make_move(player_move)
    #         opponent_moves = game_state.get_valid_moves()
    #         if game_state.stale_mate:
    #             opponent_max_score = self.stalemate
    #         elif game_state.check_mate:
    #             opponent_max_score = -self.checkmate
    #         else:
    #             opponent_max_score = -self.checkmate
    #             for each_move in opponent_moves:
    #                 game_state.make_move(each_move)
    #                 game_state.get_valid_moves()
    #                 if game_state.check_mate:
    #                     score = self.checkmate
    #                 elif game_state.stale_mate:
    #                     score = self.stalemate
    #                 else:
    #                     score = -turn * self.score_material(game_state.board)
    #                 if score > opponent_max_score:
    #                     opponent_max_score = score
    #                 game_state.undo()
    #         if opponent_max_score < opponent_minmax_score:
    #             opponent_minmax_score = opponent_max_score
    #             best_player_move = player_move
    #         game_state.undo()
    #     return best_player_move


    def find_best_move_minmax(self, game_state, valid_moves):
        """
        Makes an optimal move
        :param game_state:
        :param valid_moves:
        :return:
        """
        global next_move
        next_move = None
        self.find_move_minmax(game_state, valid_moves, self.depth, game_state.whiteToMove)
        return next_move



    def find_move_minmax(self, game_state, valid_moves, depth, white_to_move):
        """
        AI algorithm -> Minmax
        Checks for the best score that AI can get after {depth} steps ahead
        :param game_state:
        :param valid_moves:
        :param depth:
        :param white_to_move:
        :return:
        """
        global next_move
        if depth == 0:
            return self.score_material(game_state.board)

        random.shuffle(valid_moves)

        if white_to_move:
            max_score = -self.checkmate
            for each_move in valid_moves:
                game_state.make_move(each_move)
                next_moves = game_state.get_valid_moves()
                score = self.find_move_minmax(game_state, next_moves, depth - 1, False)
                if score > max_score:
                    max_score = score
                    if depth == self.depth:
                        next_move = each_move
                game_state.undo()

            return max_score

        else:
            min_score = self.checkmate
            for each_move in valid_moves:
                game_state.make_move(each_move)
                next_moves = game_state.get_valid_moves()
                score = self.find_move_minmax(game_state, next_moves, depth - 1, True)
                if score < min_score:
                    min_score = score
                    if depth == self.depth:
                        next_move = each_move
                game_state.undo()

            return min_score


    def score_board(self, game_state):
        """
        Gets the score for current game state
        :param game_state:
        :return:
        """
        if game_state.check_mate:
            if game_state.whiteToMove:
                return -self.checkmate
            else:
                return self.checkmate
        elif game_state.stale_mate:
            return self.stalemate

        score = 0
        for row in game_state.board:
            for square in row:
                if square[0] == "w":
                    score += self.piece_score[square[1]]
                elif square[0] == "b":
                    score -= self.piece_score[square[1]]

        return score


    def score_material(self, board):
        """
        Returns the score of the current board state
        :param board:
        :return:
        """
        score = 0
        for row in board:
            for square in row:
                if square[0] == "w":
                    score += self.piece_score[square[1]]
                elif square[0] == "b":
                    score -= self.piece_score[square[1]]

        return score






