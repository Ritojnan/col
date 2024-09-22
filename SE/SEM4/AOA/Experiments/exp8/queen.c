#include <stdio.h>
#include <stdbool.h>

bool isSafe(int board[8][8], int row, int col) {
    for (int i = 0; i < col; i++)
        if (board[row][i])
            return false;
    
    for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
        if (board[i][j])
            return false;
    
    for (int i = row, j = col; j >= 0 && i < 8; i++, j--)
        if (board[i][j])
            return false;
    
    return true;

}
bool solve(int board[8][8], int col) {
    if (col >= 8)
        return true;
    for (int i = 0; i < 8; i++) {
        if (isSafe(board, i, col)) {
            board[i][col] = 1;
            if (solve(board, col + 1))
                return true;    
            board[i][col] = 0;
        }
    }
    return false;
}

int main() {
    int board[8][8] = {0};

if (solve(board, 0)) {
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            if(board[i][j])
                printf("Q ");
            else
                printf(". ");
        }
        printf("\n");
    }
}
    return 0;
}