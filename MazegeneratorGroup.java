/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazegeneratorgroup;

import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
/**
 *
 * @author Butmeng
 */
public class MazegeneratorGroup {
    private final int x; 
    private final int y;
    private final String[][] mazeWall;
    private final int[][] mazeGroup;
    Random rand = new Random();
    
    public MazegeneratorGroup(int x, int y){
        int init = 1;
        this.x = x;
        this.y = y;
        mazeWall = new String[this.x][this.y];
        mazeGroup = new int[this.x][this.y];
        for(int lx = 0; lx < x; lx++){
            for(int ly = 0; ly < y; ly++){
                mazeWall[lx][ly] = "1111";
                mazeGroup[lx][ly] = init;
                init += 1; 
            }
        }
        int sX = rand.nextInt(x), sY = rand.nextInt(y);
        theMaze(sX,sY);
    }
    
    private enum DIR{
        U(0, -1, 0), D(2, 1, 0), R(1, 0, 1), L(3, 0, -1);
        
        private final int move;
        private final int dirX;
        private final int dirY;
        private DIR opposite;
        
        private DIR(int move, int dirX, int dirY){
            this.move = move;
            this.dirX = dirX;
            this.dirY = dirY;
        }
        
        static {
            U.opposite = D;
            D.opposite = U;
            R.opposite = L;
            L.opposite = R;
        }
        
        public String changeString(String manipulate, int dir){
            StringBuilder vessel = new StringBuilder(manipulate);
            vessel.setCharAt(dir, '0');
            return vessel.toString();
        }
    }
    
    private static boolean between(int cN, int upper){
        return (cN >= 0) && (cN < upper);
    }
    
    public void theMaze(int oX, int oY){
        DIR[] directions = DIR.values();
        Collections.shuffle(Arrays.asList(directions));
        for(DIR e : directions){
            int nX = oX + e.dirX;
            int nY = oY + e.dirY;
            if((between(nX,x)) && (between(nY,y)) && (mazeGroup[oX][oY] != mazeGroup[nX][nY])){
                mazeWall[oX][oY] = e.changeString(mazeWall[oX][oY], e.move);
                mazeWall[nX][nY] = e.changeString(mazeWall[nX][nY], e.opposite.move);
                int compare = mazeGroup[nX][nY];
                for(int i = 0; i < x; i++){
                    for(int j = 0; j < y; j++){
                        if(mazeGroup[i][j] == compare){
                            mazeGroup[i][j] = mazeGroup[oX][oY];
                        }
                    }
                }
                int tX = rand.nextInt(x), tY = rand.nextInt(y);
                theMaze(tX,tY);
            }
        }
    }
    
    public void printMaze(){
        for(int printX = 0; printX < x; printX++){
            for(int printTop = 0; printTop < y; printTop++){
                System.out.print(mazeWall[printX][printTop].charAt(0) == '0' ? "+   " : "+---");
            }
            System.out.print("+\n");
            for(int printLeft = 0; printLeft < y; printLeft++){
                System.out.print(mazeWall[printX][printLeft].charAt(3) == '0' ? "    " : "|   ");
            }
            System.out.print("|\n");
        }
        for(int printBottom = 0; printBottom < y; printBottom++){
            System.out.print("+---");
        }
        System.out.print("+\n");
    }
    
    public static void main(String[] args) {
        MazegeneratorGroup leMaze = new MazegeneratorGroup(12,12);
        leMaze.printMaze();
    }
    
}
