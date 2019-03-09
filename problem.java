import java.util.Arrays;

class problem {

    public static byte[][] destTable;

    public static void main(String[] args) {

        // -----------------state representation-------------
        // 0 for empty, 1 for white, 2 for black

        // ----------------initial state---------------------
        byte[] board = { 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 
                       0, 0, 1, 1, 1, 0, 0, 
                     0, 0, 0, 0, 0, 0, 0, 0, 
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 
                     0, 0, 0, 0, 0, 0, 0, 0, 
                      0, 0, 2, 2, 2, 0, 0, 
                        2, 2, 2, 2, 2, 2,
                         2, 2, 2, 2, 2 };

        // -----------------------action----------------------
        // 0 left, 5 right, 1 topLeft, 2 top Right, 4 bottomRight, 3 bottomLeft

        // give every location an index from 0 - 60 (left to right, top to bottom) 
        // use -1 for out of bound
        // Use a matrix table to save the destination index after each possible move for
        // each original index
        destTable = problem.initTable();

        // if original location is 40, go left, we can find destination location index
        // using destTable[40][3]

        // --------------------Transition model------------------
        // Assume Marble k in location a, make move b, the next state => board[a] = 0,
        // board[destTable[a][b]]= k

        // #### How to check possible all possible move for initial states

        long startTime = System.currentTimeMillis();

        int counter = 0;
        for(int i=0; i< 61; ++i){
            if(board[i] != 0){
                counter += moveOne(i, board);
            }            
        }        
        System.out.println("Counter: "+ counter); 
  
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Time takes " + elapsedTime);


    }

    private static byte[][] initTable() {
        byte[][] mapTable = new byte[61][6];
        // for example, position 0, go left is out of bound -1, go right is 1 and so on
        mapTable[0] = new byte[] { -1, -1, -1, 5, 6, 1 };
        mapTable[1] = new byte[] { 0, -1, -1, 6, 7, 2 };
        mapTable[2] = new byte[] { 1, -1, -1, 7, 8, 3 };
        mapTable[3] = new byte[] { 2, -1, -1, 8, 9, 4 };
        mapTable[4] = new byte[] { 3, -1, -1, 9, 10, -1 };
        mapTable[5] = new byte[] { -1, -1, 0, 11, 12, 6 };
        mapTable[6] = new byte[] { 5, 0, 1, 12, 13, 7 };
        mapTable[7] = new byte[] { 6, 1, 2, 13, 14, 8 };
        mapTable[8] = new byte[] { 7, 2, 3, 14, 15, 9 };
        mapTable[9] = new byte[] { 8, 3, 4, 15, 16, 10 };
        mapTable[10] = new byte[] { 9, 4, -1, 16, 17, -1 };
        mapTable[11] = new byte[] { -1, -1, 5, 18, 19, 12 };
        mapTable[12] = new byte[] { 11, 5, 6, 19, 20, 13 };
        mapTable[13] = new byte[] { 12, 6, 7, 20, 21, 14 };
        mapTable[14] = new byte[] { 13, 7, 8, 21, 22, 15 };
        mapTable[15] = new byte[] { 14, 8, 9, 22, 23, 16 };
        mapTable[16] = new byte[] { 15, 9, 10, 23, 24, 17 };
        mapTable[17] = new byte[] { 16, 10, -1, 24, 25, -1 };
        mapTable[18] = new byte[] { -1, -1, 11, 26, 27, 19 };
        mapTable[19] = new byte[] { 18, 11, 12, 27, 28, 20 };
        mapTable[20] = new byte[] { 19, 12, 13, 28, 29, 21 };
        mapTable[21] = new byte[] { 20, 13, 14, 29, 30, 22 };
        mapTable[22] = new byte[] { 21, 14, 15, 30, 31, 23 };
        mapTable[23] = new byte[] { 22, 15, 16, 31, 32, 24 };
        mapTable[24] = new byte[] { 23, 16, 17, 32, 33, 25 };
        mapTable[25] = new byte[] { 24, 17, -1, 33, 34, -1 };
        mapTable[26] = new byte[] { -1, -1, 18, -1, 35, 27 };
        mapTable[27] = new byte[] { 26, 18, 19, 35, 36, 28 };
        mapTable[28] = new byte[] { 27, 19, 20, 36, 37, 29 };
        mapTable[29] = new byte[] { 28, 20, 21, 37, 38, 30 };
        mapTable[30] = new byte[] { 29, 21, 22, 38, 39, 31 };
        mapTable[31] = new byte[] { 30, 22, 23, 39, 40, 32 };
        mapTable[32] = new byte[] { 31, 23, 24, 40, 41, 33 };
        mapTable[33] = new byte[] { 32, 24, 25, 41, 42, 34 };
        mapTable[34] = new byte[] { 33, 25, -1, 42, -1, -1 };
        mapTable[35] = new byte[] { -1, 26, 27, -1, 43, 36 };
        mapTable[36] = new byte[] { 36, 27, 28, 43, 44, 37 };
        mapTable[37] = new byte[] { 36, 28, 29, 44, 45, 38 };
        mapTable[38] = new byte[] { 37, 29, 30, 45, 46, 39 };
        mapTable[39] = new byte[] { 38, 30, 31, 46, 47, 40 };
        mapTable[40] = new byte[] { 39, 31, 32, 47, 48, 41 };
        mapTable[41] = new byte[] { 40, 32, 33, 48, 49, 42 };
        mapTable[42] = new byte[] { 41, 33, 34, 49, -1, -1 };
        mapTable[43] = new byte[] { -1, 35, 36, -1, 50, 44 };
        mapTable[44] = new byte[] { 43, 36, 37, 50, 51, 45 };
        mapTable[45] = new byte[] { 44, 37, 38, 51, 52, 46 };
        mapTable[46] = new byte[] { 45, 38, 39, 52, 53, 47 };
        mapTable[47] = new byte[] { 46, 39, 40, 53, 54, 48 };
        mapTable[48] = new byte[] { 47, 40, 41, 54, 55, 49 };
        mapTable[49] = new byte[] { 48, 41, 42, 55, -1, -1 };
        mapTable[50] = new byte[] { -1, 43, 44, -1, 56, 51 };
        mapTable[51] = new byte[] { 50, 44, 45, 56, 57, 52 };
        mapTable[52] = new byte[] { 51, 45, 46, 57, 58, 53 };
        mapTable[53] = new byte[] { 52, 46, 47, 58, 59, 54 };
        mapTable[54] = new byte[] { 53, 47, 48, 59, 60, 55 };
        mapTable[55] = new byte[] { 54, 48, 49, 60, -1, -1 };
        mapTable[56] = new byte[] { -1, 50, 51, -1, -1, 57 };
        mapTable[57] = new byte[] { 56, 51, 52, -1, -1, 58 };
        mapTable[58] = new byte[] { 57, 52, 53, -1, -1, 59 };
        mapTable[59] = new byte[] { 58, 53, 54, -1, -1, 60 };
        mapTable[60] = new byte[] { 59, 54, 55, -1, -1, -1 };

        return mapTable;
    }

    private static int moveOne(int m1, byte[] board){
        int counter = 0;
        for (int i = 0; i < 6; ++i) { // check 6 directions
            int m2 = destTable[m1][i];
            if (m2 != -1) { // not out of bound
                if (board[m2] == 0) {
                    System.out.println("Possible move for " + m1 + " -> " + m2);
                    counter++;
                } else if (board[m2] == board[m1]) {
                    // if same color, check if two marbles can move together
                    counter += moveTwo(m1, m2, i, board);
                }
            }            
        }
        return counter;
    }

    private static int moveTwo(int m1, int m2, int direction, byte[] board) {
        if(m1 >= m2) {
            return 0;
        }

        int counter = 0;
        // same direction
        int m3 = destTable[m2][direction];

        if (m3 != -1) { // not out of bound
            if (board[m3] == 0) {
                System.out.println("Possible move for " + m1+ "&" + m2 + " -> " + m3);
                counter++;
            } else if (board[m3] == board[m1]) {
                // if same color, check if three marbles can move together
                counter += moveThree(m1, m2, m3, direction, board);

            } else {
                // if not same color, check if the opponent marble = 1
            }
        }

        //opposite direction
        int prev = destTable[m1][5-direction];

        if(prev != -1){
            if(board[prev] == 0) {
                System.out.println("Possible move for " + m1 + "&" + m2 + " -> " + prev);
                counter++;
            } else if(board[prev] != board[m1]){
                // check if we can push
            }
        } 
        

        //other direction
        for (int j = 0; j < 6; ++j){
            if(j != direction && j != 5-direction){ // exclude previous two direction
                if(destTable[m1][j] != -1 && destTable[m2][j] != -1 && board[destTable[m1][j]] == 0 && board[destTable[m2][j]] == 0){
                    System.out.println("Possible move for " + m1 + "&" + m2 + " -> " + destTable[m1][j] + "&"+ destTable[m2][j]);
                    counter++;
                }
            }
        } 

        return counter;
    }

    private static int moveThree(int m1, int m2, int m3, int direction, byte[] board) {
        if(m1 >=m2 || m2>=m3){
            return 0;
        }

        int counter = 0 ;
        // same direction
        int m4 = destTable[m3][direction];

        if (m4 != -1) { // not out of bound
            if (board[m4] == 0) {
                System.out.println("Possible move for " + m1 + "&" + m2 + "&" + m3 + " -> " + m4);
                counter++;
            } else if (board[m3] != board[m4]) {
                // if not same color, check if we can push
            }
        }

        //opposite direction
        int prev = destTable[m1][5-direction];
        if(prev != -1 ){
            if( board[prev] == 0) {
                System.out.println("Possible move for " + m1 + "&" + m2 + "&" + m3 + " -> " + prev);
                counter++;
            } else if(board[m3] != board[prev]){
                // if not same color, check if we can push
            }
        }
        
        //other direction
        for (int j = 0; j < 6; ++j){
            if(j != direction && j != 5-direction){ // exclude previous two direction
                if(destTable[m1][j] != -1 && destTable[m2][j] != -1 && destTable[m3][j] != -1 &&
                    board[destTable[m1][j]] == 0 && board[destTable[m2][j]] == 0 && board[destTable[m3][j]] == 0){
                    System.out.println("Possible move for " + m1 + "&" + m2 + "&" + m3 + " -> " + destTable[m1][j] + "&" + destTable[m2][j] + "&" + destTable[m3][j]);
                    counter++;
                }
            }
        } 
        return counter;
    }

}