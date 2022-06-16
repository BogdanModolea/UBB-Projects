from configparser import ConfigParser

from game import Game, Move
from AI import AI

class UI:
    def __init__(self):
        parser = ConfigParser()
        parser.read("settings.properties")
        self.type = parser.get("options", "game")


    def start(self):
        game_state = Game()
        valid_moves = game_state.get_valid_moves()
        move_made = False
        game_running = True
        game_over = False
        square_selected = ()
        player_clicks = []


        for i in range(0, 8):
            print("     " + str(i), end ='', flush=True)
        print()
        index = 0
        for i in game_state.board:
            print('{0}: {1} :{2}'.format(index, i, index))
            index += 1
        print()

        if self.type[0] == "p":
             player1 = True
        else:
            player1 = False
        if self.type[2] == "p":
            player2 = True
        else:
            player2 = False

        while game_running:
            if game_state.whiteToMove == True:
                player = "white"
            else:
                player = "black"

            print("It's " + player + " turn!")

            human_turn = (game_state.whiteToMove and player1) or (not game_state.whiteToMove and player2)

            if not game_over and human_turn:
                row = int(input("Enter what you want to move (X): "))
                col = int(input("Enter what you want to move (Y): "))
                square_selected = (row, col)
                player_clicks.append(square_selected)

                row = int(input("Enter where you want to move (X): "))
                col = int(input("Enter where you want to move (Y): "))
                square_selected = (row, col)
                player_clicks.append(square_selected)

                if len(player_clicks) == 2:
                    move = Move(player_clicks[0], player_clicks[1], game_state.board)
                    for i in range(len(valid_moves)):
                        if move == valid_moves[i]:
                            print(move.get_chess_notation())
                            game_state.make_move(valid_moves[i])
                            move_made = True
                            square_selected = ()
                            player_clicks = []
                    if not move_made:
                        player_clicks = [square_selected]

            for i in range(0, 8):
                print("     " + str(i), end='', flush=True)
            print()
            index = 0
            for i in game_state.board:
                print('{0}: {1} :{2}'.format(index, i, index))
                index += 1
            print()


            if not game_over and not human_turn:
                AI_move = AI().find_best_move_minmax(game_state, valid_moves)
                if AI_move is None:
                    AI_move = AI().find_random_move(valid_moves)
                game_state.make_move(AI_move)
                move_made = True


            if move_made:
                valid_moves = game_state.get_valid_moves()
                move_made = False
                for i in range(0, 8):
                    print("     " + str(i), end='', flush=True)
                print()
                index = 0
                for i in game_state.board:
                    print('{0}: {1} :{2}'.format(index, i, index))
                    index += 1
                print()

            if game_state.check_mate:
                game_over = True
                if game_state.whiteToMove:
                    print("Black wins by checkmate")
                else:
                    print("White wins by checkkmate")

            elif game_state.stale_mate:
                game_over = True
                print("Stalemate")