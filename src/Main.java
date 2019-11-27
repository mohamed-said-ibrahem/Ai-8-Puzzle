public class Main {

    public static void main(String[] args) {
    	
    	// 1 2 3 4 5 6 7 8 0  must be case for A* goal as the heuristic will give you a bad results
//        int[] ins = {1, 2,5, 3, 4, 0, 6, 7, 8};
//        
//        int [] goal = {1,2,3,4,5,6,7,8,0};
//        
        
//    	// GOOD TEST CASE FOR DFS I
//        int[] ins = {1, 2,5, 3, 4, 0, 6, 7, 8};
//        int [] goal = {2,7,8,1,4,0,5,6,3};
        
        
    	// GOOD TEST CASE FOR DFS II & BFS
//        int[] ins = {1, 2,5, 3, 4, 0, 6, 7, 8};
//        int [] goal = {1,5,8,3,2,7,6,4,0};
//        
    	
    	// DFS Test Case: BFS too
//       int [] ins = {1,2,3,8,0,4,7,6,5};
//      int [] goal = {0,1,3,8,2,4,7,6,5};
    
	  //DFS & BFS TOO
      int [] ins = {6,4,7,8,5,0,3,2,1};
      int [] goal = {0,1,2,3,4,5,6,7,8};
	
//      int [] ins = {1,2,3,8,0,4,7,6,5};
//      int [] goal = {1,2,3,0,8,4,7,6,5};
    	
//        System.out.println("A*");
//        AStare a = new AStare();
//        a.search(ins,goal);
        BFS bfs = new BFS();
        DFS dfs = new DFS();
        System.out.println("..............");
        System.out.println("BFS");
        bfs.search(ins,goal);
        System.out.println("..............");
        System.out.println("DFS");
        dfs.search(ins,goal);
        
    }
}
