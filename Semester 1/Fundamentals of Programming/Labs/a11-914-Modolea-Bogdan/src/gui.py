import pygame as pg
from game import Game, Move
from AI import AI
from configparser import ConfigParser

class GUI:
    def __init__(self):
        parser = ConfigParser()
        parser.read("settings.properties")
        self.width = int(parser.get("options", "width"))
        self.height = int(parser.get("options", "height"))
        self.dimension = int(parser.get("options", "dimension"))
        self.square_size = self.height // self.dimension
        self.images = {}

        self.type = parser.get("options", "game")

    def load_images(self):
        pieces = ["wp", "wR", "wN", "wB", "wQ", "wK", "bp", "bR", "bN", "bB", "bQ", "bK"]

        for each_piece in pieces:
            self.images[each_piece] = pg.transform.scale(pg.image.load("sprites/" + each_piece + ".png"), (self.square_size, self.square_size))


    def draw_game_state(self, screen, game_state, valid_moves, square_selected):
        self.draw_board(screen)
        self.highlight_squares(screen, game_state, valid_moves, square_selected)
        self.draw_pieces(screen, game_state.board)

    def draw_board(self, screen):
        global colors
        WHITE = (238, 238, 210)
        GREEN = (118, 150, 86)
        colors = [pg.Color(WHITE), pg.Color(GREEN)]
        for row in range(self.dimension):
            for col in range(self.dimension):
                now_color = colors[((row + col) % 2)]
                pg.draw.rect(screen, now_color, pg.Rect(col * self.square_size, row * self.square_size, self.square_size, self.square_size))


    def draw_pieces(self, screen, board):
        for row in range(self.dimension):
            for col in range(self.dimension):
                piece = board[row][col]
                if piece != "--":
                    screen.blit(self.images[piece], pg.Rect(col * self.square_size, row * self.square_size, self.square_size, self.square_size))


    def highlight_squares(self, screen, game_state, valid_moves, square_selected):
        ORANGE = (255, 89, 0)
        if square_selected != ():
            row, col = square_selected
            if game_state.board[row][col][0] == ("w" if game_state.whiteToMove else "b"):
                surface = pg.Surface((self.square_size, self.square_size))
                surface.set_alpha(100)
                surface.fill(pg.Color('yellow'))
                screen.blit(surface, (col * self.square_size, row * self.square_size))
                surface.fill(pg.Color(ORANGE))
                for move in valid_moves:
                    if move.start_row == row and move.start_col == col:
                        screen.blit(surface,(move.finish_col * self.square_size, move.finish_row * self.square_size))



    def animate_move(self, move, screen, board, clock):
        global colors
        coords = []
        dR = move.finish_row - move.start_row
        dC = move.finish_col - move.start_col
        frames_per_square = 10
        frame_count = (abs(dR) + abs(dC)) * frames_per_square
        for each_frame in range(frame_count + 1):
            row, col = (move.start_row + dR * each_frame / frame_count, move.start_col + dC * each_frame / frame_count)
            self.draw_board(screen)
            self.draw_pieces(screen, board)
            color = colors[(move.finish_row + move.finish_col) % 2]
            finish_square = pg.Rect(move.finish_col * self.square_size, move.finish_row * self.square_size, self.square_size, self.square_size)
            pg.draw.rect(screen, color, finish_square)

            if move.piece_captured != "--":
                if move.is_enpassant:
                    enpassant_row = move.finish_row + 1 if move.piece_captured[0] == "b" else move.finish_row - 1
                    finish_square = pg.Rect(move.finish_col * self.square_size, enpassant_row * self.square_size, self.square_size, self.square_size)
                screen.blit(self.images[move.piece_captured], finish_square)

            screen.blit(self.images[move.piece_moved], pg.Rect(col * self.square_size, row * self.square_size, self.square_size, self.square_size))
            pg.display.flip()
            clock.tick(60)



    def draw_text(self, screen, text):
        font = pg.font.SysFont("Helvetica", 32, True, False)
        text_object = font.render(text, 0, pg.Color('Gray'))
        text_location = pg.Rect(0, 0, self.width, self.height).move(self.width / 2 - text_object.get_width() / 2, self.height / 2 - text_object.get_height() / 2)
        screen.blit(text_object, text_location)
        text_object = font.render(text, 0, pg.Color("Black"))
        screen.blit(text_object, text_location.move(2, 2))



    def start(self):
        pg.init()
        screen = pg.display.set_mode((self.width, self.height))
        clock = pg.time.Clock()
        screen.fill(pg.Color("white"))
        game_state = Game()
        valid_moves = game_state.get_valid_moves()
        move_made = False
        animate = False
        self.load_images()
        square_selected = ()
        player_clicks = []
        game_running = True
        game_over = False


        if self.type[0] == "p":
             player1 = True
        else:
            player1 = False
        if self.type[2] == "p":
            player2 = True
        else:
            player2 = False


        while game_running:
            human_turn = (game_state.whiteToMove and player1) or (not game_state.whiteToMove and player2)
            for event in pg.event.get():
                if event.type == pg.QUIT:
                    game_running = False
                elif event.type == pg.MOUSEBUTTONDOWN:
                    if not game_over and human_turn:
                        location = pg.mouse.get_pos()
                        col = location[0] // self.square_size
                        row = location[1] // self.square_size
                        if square_selected == (row, col):
                            square_selected = ()
                            player_clicks = []
                        else:
                            square_selected = (row, col)
                            player_clicks.append(square_selected)
                        if len(player_clicks) == 2:
                            move = Move(player_clicks[0], player_clicks[1], game_state.board)
                            for i in range(len(valid_moves)):
                                if move == valid_moves[i]:
                                    print(move.get_chess_notation())
                                    game_state.make_move(valid_moves[i])
                                    move_made = True
                                    animate = True
                                    square_selected = ()
                                    player_clicks = []
                            if not move_made:
                                player_clicks = [square_selected]

                elif event.type == pg.KEYDOWN:
                    if event.key == pg.K_z:
                        game_state.undo()
                        move_made = True
                        animate = False
                        game_over = False
                    if event.key == pg.K_r:
                        game_state = Game()
                        valid_moves = game_state.get_valid_moves()
                        square_selected = ()
                        player_clicks = []
                        move_made = False
                        animate = False
                        game_over = False


            if not game_over and not human_turn:
                AI_move = AI().find_best_move_minmax(game_state, valid_moves)
                if AI_move is None:
                    AI_move = AI().find_random_move(valid_moves)
                game_state.make_move(AI_move)
                move_made = True
                animate = True

            if move_made:
                if animate:
                    self.animate_move(game_state.move_history[-1], screen, game_state.board, clock)
                valid_moves = game_state.get_valid_moves()
                move_made = False
                animate = False


            self.draw_game_state(screen, game_state, valid_moves, square_selected)

            if game_state.check_mate:
                game_over = True
                if game_state.whiteToMove:
                    self.draw_text(screen, "Black wins by checkmate")
                else:
                    self.draw_text(screen, "White wins by checkmate")

            elif game_state.stale_mate:
                game_over = True
                self.draw_text(screen, "Stalemate")


            clock.tick(15)
            pg.display.flip()
