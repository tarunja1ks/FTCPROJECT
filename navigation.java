import java.io.*;
import java.util.*;
class Main {
  public static void main(String[] args) {
    int[][] seen = {{0, 0, 1, 1, 0, 0}, // Team hub
                    {1, 0, 1, 1, 0, 0}, // Team hub
                    {1, 0, 0, 0, 0, 0},
                    {1, 1, 1, 0, 0, 0},// Alliance hub
                    {0, 0, 0, 0, 0, 0}, 
                    {1, 0, 0, 0, 0, 1}}; // Carousels
   /* int[][] seen = {{0, 0, 1}, // Team hub
                    {0, 0, 1}, // Team hub
                    {0, 0, 0},
                    {0, 1, 1},// Alliance hub
                    {0, 0, 0}, 
                    {1, 0, 0}}; If we only want to go one on side*/
    // Grid of cells visited and obstacles
    // Blue carousel is the bottom left corner
    // 1's are an obstacle
    // So far, we blocked out the shipping hubgs, and the carousels. We can block out the path of our alliance's autonmous program as well
    // North is side of warehouse, South is carousel side, West/East are allieance sides
    // The function takes in the side that robot is facing (so we always drive in the correct direction)
    findPath(seen, 4, 0, "E", 1, 1);
    
  
  }
  public static void findPath(int[][] seen, int xStart, int yStart, String curDirection, int xTarget, int yTarget){
    // We will use the BFS to find the shortest path
    Queue<Tile> q = new LinkedList<Tile>(); 
    // We have a queue of type Tile
    q.add(new Tile(xStart, yStart, new String()));
    Tile cur;
    String moves = "";
    while(!q.isEmpty()){ // While the queue has more nodes
      cur = q.remove();
      seen[cur.x][cur.y] = 1;
      // We take the current node, set it to seen
      // If we reached the target position, we can stop and set the moves we took to get there
      if(cur.x == xTarget && cur.y == yTarget){
        System.out.println("yo");
        moves = cur.moves;
        break;
      }
      // Go through all the adjacent tiles and check if it works by calling doesWork
      // If so, add to queue
      
      if(doesWork(cur.x + 1, cur.y, seen)){ 
        q.add(new Tile(cur.x + 1, cur.y, cur.moves+"D"));
      }
      if(doesWork(cur.x - 1, cur.y, seen)){ 
        q.add(new Tile(cur.x - 1, cur.y, cur.moves+"U"));
      }
      if(doesWork(cur.x, cur.y + 1, seen)){ 
        q.add(new Tile(cur.x, cur.y + 1, cur.moves+"R"));
      }
      if(doesWork(cur.x, cur.y - 1, seen)){ 
        q.add(new Tile(cur.x, cur.y - 1, cur.moves+"L"));
      }
      
    }
    //telemetry.addData("Please be correct: ", moves);
    //telemetry.update();
    // We have figured out the path to get to the target
    // Now, we need to do the driving aspect of this
    // We keep track of the offset depending on which way the robot is facing when it starts
    System.out.println(moves);
    int curX = xStart;
    int curY = yStart;
    int preX;
    int preY;
    char move;
    int travel;
    int dist = 18;
    int curAngle = 0;
    for(int i = 0; i < moves.length(); i++){
      // Based on the moves & offset, we drive/turn to get to the desired position (U is up, D is down, L is left, R is right)
      preX = curX;
      preY = curY;
      travel = 0;
      move = moves.charAt(i);
      System.out.println(curX + " " + curY + " " + move);
      while(i < moves.length()){
        if(moves.charAt(i) == move){
          travel += dist;
        }
        else{
          break;
        }
        i++;
      }
      i--;
      if(move == 'U'){
        curAngle = 90;
        curX -= travel/dist;
      }
      if(move == 'D'){
        curAngle = -90;
        curX += travel/dist;
      }
      if(move == 'L'){
        curAngle = 180;
        curY -= travel/dist;
      }
      if(move == 'R'){
        curAngle = 0;
        curY += travel/dist;
      }
      gyroTurn(0.3, curAngle);
      
      if(preX > 1 && curX <= 1){
        // Go fast over the barrier
      }
      else{
        gyroDrive(0.3, travel, curAngle);
      }
    }
    System.out.println(curX + " " + curY);
    }
    
    
  
  public static boolean doesWork(int x, int y, int[][] grid){
    // If node is out of bounds or already seen, return false
    if(x < 0 || x >= 6 || y < 0 || y >= 6){ 
      return false;
    }
    if(grid[x][y] == 1){
      return false;
    }
    // otherwise, return true
    return true;
  }
  public static void gyroDrive(double speed, double distance, double angle){
    System.out.println(speed + " " + distance + " " + angle);
  }
  public static void gyroTurn(double speed, double angle){
   System.out.println(speed  + " " + angle);
  }
  
}
class Tile{
  int x;
  int y;
  String moves;
  Tile(int x, int y, String moves){
    this.x = x;
    this.y = y;
    this.moves = moves;
    // object tile with coords x, y, and moves so far
  }
}

