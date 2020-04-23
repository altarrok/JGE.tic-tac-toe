import sys
import math

board_state_arr = []

def create_2d_array(string_arr):
    board_state = []
    string_arr_split = string_arr.split(" ")
    for i in range(3):
        row = []
        for j in range(3):
            row.append(string_arr_split[(3 * i) + j])
        board_state.append(row)
    return board_state



def is_win(board):
    # Check columns and rows
    for i in range(3):
        if board[i][0] == board[i][1] == board[i][2] and board[i][0] != "EMPTY":
            return board[i][0], True
        if board[0][i] == board[1][i] == board[2][i] and board[0][i] != "EMPTY":
            return board[0][i], True
    # Check diagonals
    if board[0][0] == board[1][1] == board[2][2] and board[0][0] != "EMPTY":
        return board[0][0], True
    if board[0][2] == board[1][1] == board[2][0] and board[1][1] != "EMPTY":
        return board[2][0], True
    for i in range(3):
        for j in range(3):
            if "EMPTY" == board[i][j]:
                return None, False
    return "Draw", True

def is_playable(i, j):
    return board_state_arr[i][j] == "EMPTY"

# @param i = x-axis of board_state
# @param i = y-axis of board_state
# @param str = X or O string
def play(i , j, str):
    if is_playable(i, j):
        board_state_arr[i][j] = str
    else:
        return False


def undo_play(i, j):
    board_state_arr[i][j] = "EMPTY"


def board_evaluator(board):
    score_arr = [4, 2, 4, 2, 5, 2, 4, 2, 4]
    score = 0
    for i in range(9):
        if board[i // 3][i % 3] == "X":
            score += score_arr[i]
        elif board[i // 3][i % 3] == "O":
            score -= score_arr[i]
    return score


def minimax(board, depth, alpha, beta, maximizing_player):
    winner, is_ended = is_win(board)
    if is_ended:
        if winner == "X":
            return 100 * depth, -1
        elif winner == "O":
            return -100 * depth, -1
        else:
            return 0, -1

    if depth == 0:
        return board_evaluator(board), -1
        # return 0, -1

    if maximizing_player:
        value = -math.inf
        best_move = -1
        for i in range(9):
            if not is_playable(i // 3, i % 3):
                continue
            # Do the move
            play(i // 3, i % 3, "X")
            minimax_score = minimax(board, depth - 1, alpha, beta, False)[0]
            # undo the move
            undo_play(i // 3, i % 3)
            if minimax_score > value:
                alpha = minimax_score
                value = minimax_score
                best_move = i
            if alpha >= beta:
                break
        return value, best_move
    else: # maximizing_player = False
        value = math.inf
        best_move = -1
        for i in range(9):
            if not is_playable(i // 3, i % 3):
                continue
            # Do the move
            play(i // 3, i % 3, "O")
            minimax_score = minimax(board, depth - 1, alpha, beta, True)[0]
            # undo the move
            undo_play(i // 3, i % 3)
            if minimax_score < value:
                beta = minimax_score
                value = minimax_score
                best_move = i
            if alpha >= beta:
                break
        return value, best_move


if __name__ == "__main__":
    board_state_input = str(sys.argv[1])
    # board_state_input = "X O EMPTY X EMPTY EMPTY EMPTY EMPTY EMPTY"
    board_state_arr = create_2d_array(board_state_input)
    depth = 3
    final_board, move = minimax(board_state_arr, depth, -math.inf, math.inf, sys.argv[2] == "X")
    print(move % 3, move // 3)
