import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStare implements Search {
	
	int depth = 0;
	int maxCost = 0;
	Comparator<Table> tableComparator = new Comparator<Table>() {
		@Override
		public int compare(Table t1, Table t2) {
			// cost g  heuristic h 
			if(t1.cost > maxCost) maxCost = t1.cost;
			if(t2.cost > maxCost) maxCost = t2.cost;
			return (heurestic(t1.getContent())+t1.cost) - (heurestic(t2.getContent())+t2.cost);
		}
	};
	
	Comparator<Table> tableComparator2 = new Comparator<Table>() {
		@Override
		public int compare(Table t1, Table t2) {
			// cost g  heuristic h 
			if(t1.cost > maxCost) maxCost = t1.cost;
			if(t2.cost > maxCost) maxCost = t2.cost;
			return (heurestic2(t1.getContent())+t1.cost) - (heurestic2(t2.getContent())+t2.cost);
		}
	};

	// ((nu - 1) / 3) --> X
	// ((nu - 1) % 3) --> Y
	// Manhattan Distance Heuristic
	int heurestic(int[][] t) {
		int sum = 0;
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t.length; j++) {
				int nu = t[i][j];
				if (nu == 0)
					nu = 9;
				sum += Math.abs(((nu - 1) / 3) - i);
				sum += Math.abs(((nu - 1) % 3) - j);
			}
			
		}
		return sum;
	}
	
	// ((nu - 1) / 3) --> X
	// ((nu - 1) % 3) --> Y
	// Euclidean Distance Heurestic
	int heurestic2(int[][] t) {
		int sum = 0;
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t.length; j++) {
				int nu = t[i][j];
				if (nu == 0)
					nu = 9;
				sum += Math.pow(((nu - 1) / 3) - i, 2);
				sum += Math.pow(((nu - 1) % 3) - j, 2);
			}
		}
		return (int) Math.sqrt(sum);
	}
	
	
	// Forntier is the main data structure for the algorithm and explored is helpful in alot of side such as printing path and so on.
	PriorityQueue<Table> frontier = new PriorityQueue<Table>(tableComparator);
	ArrayList<Table> explored = new ArrayList<Table>();

	public void  search(int[] initial_state, int[] goalState) {
		
    	final long startTimeNano = System.nanoTime();
    	final long startTimeMilli = System.currentTimeMillis();

		frontier.add(new Table(initial_state));

		while (!frontier.isEmpty()) {
			Table state = frontier.poll();

//			System.out.println("CHECK I : "+state.cost+(heurestic(state.getContent())));
			explored.add(state);

			if (state.isEqualPuzzle(new Table(goalState))) {
				final long durationNano = System.nanoTime() - startTimeNano;
            	final long durationMilli = System.currentTimeMillis() - startTimeMilli;
            	
				printPath(state, explored, initial_state);
				
				System.out.println("Total Cost : "+ (depth - 1));
				System.out.println("Search Depth : "+ maxCost);
                System.out.println("Nodes Expanded : "+ explored.size()); // explored nodes
                System.out.println("Run Time In Nano Seconds : "+ durationNano);
                System.out.println("Run Time In Milli Seconds : "+ durationMilli);
                
				return;
			}
			if (explored.size()>200000){
				System.out.println("What are you doing :( Early Stop,Explored nodes exceeded 20000 ");
				return;
			}
			ArrayList<Table> neigbours = state.getNeighbours();
			for (int i = 0; i < neigbours.size(); i++) {
				Table successor = neigbours.get(i);
				if (!(isInExplorer(successor) || isInFrontier(successor))) {
					successor.setPreState(state);
					frontier.add(successor);
				} else if (isInFrontier(successor)) {
					updateFrontier(successor);
				}
			}
		}
	}

	// To start again with the new Frontier Such as in City Paths Problem in Lect (Assume that you will start again from scratch from the current node Frontier).
	// Start over repeat every thing.
	private boolean updateFrontier(Table table) {
		for (Table state : frontier) {
			if (table.isEqualPuzzle(state)) {
				if(table.cost<state.cost){
					frontier.remove(state);
					frontier.add(table);
				}
				return true;
			}
		}
		return false;
	}

	private boolean isInFrontier(Table table) {
		for (Table state : frontier) {
			if (table.isEqualPuzzle(state))
				return true;
		}
		return false;
	}

	private boolean isInExplorer(Table table) {
		for (Table state : explored) {
			if (table.isEqualPuzzle(state))
				return true;
		}
		return false;
	}

	public void printPath(Table goalState, ArrayList<Table> exploredStates, int[] initalState) {

		Stack<Table> solutionStack = new Stack<Table>();
		solutionStack.push(goalState);
		while (!goalState.isEqualPuzzle(new Table(initalState))) {  //Goal is at the bottom of Stack so it will be the last node printed.
			solutionStack.push(goalState.getPreState());
			goalState = goalState.getPreState();
		}
		depth = solutionStack.size();
		Table destinationState;
		for (int i = solutionStack.size() - 1; i >= 0; i--) {
			destinationState = solutionStack.get(i);
			System.out.println(".............");
			destinationState.display();
		}

	}
}




//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.PriorityQueue;
//import java.util.Stack;
//
//public class AStare implements Search {
//	
//	int depth = 0;
//	
//	Comparator<Table> tableComparator = new Comparator<Table>() {
//		@Override
//		public int compare(Table t1, Table t2) {
//			// cost g  heuristic h 
//			return (heurestic(t1.getContent())+t1.cost) - (heurestic(t2.getContent())+t2.cost);
//		}
//	};
//	
//	Comparator<Table> tableComparator2 = new Comparator<Table>() {
//		@Override
//		public int compare(Table t1, Table t2) {
//			// cost g  heuristic h 
//			return (heurestic2(t1.getContent())+t1.cost) - (heurestic2(t2.getContent())+t2.cost);
//		}
//	};
//
//	// ((nu - 1) / 3) --> X
//	// ((nu - 1) % 3) --> Y
//	// Manhattan Distance Heurestic
//	int heurestic(int[][] t) {
//		int sum = 0;
//		for (int i = 0; i < t.length; i++) {
//			for (int j = 0; j < t.length; j++) {
//				int nu = t[i][j];
//				if (nu == 0)
//					nu = 9;
//				sum += Math.abs(((nu - 1) / 3) - i);
//				sum += Math.abs(((nu - 1) % 3) - j);
//			}
//			
//		}
//		return sum;
//	}
//	
//	// ((nu - 1) / 3) --> X
//	// ((nu - 1) % 3) --> Y
//	// Euclidean Distance Heurestic
//	int heurestic2(int[][] t) {
//		int sum = 0;
//		for (int i = 0; i < t.length; i++) {
//			for (int j = 0; j < t.length; j++) {
//				int nu = t[i][j];
//				if (nu == 0)
//					nu = 9;
//				sum += Math.pow(((nu - 1) / 3) - i, 2);
//				sum += Math.pow(((nu - 1) % 3) - j, 2);
//			}
//		}
//		return (int) Math.sqrt(sum);
//	}
//	
//	
//	// Forntier is the main data structure for the algorithm and explored is helpful in alot of side such as printing path and so on.
//	PriorityQueue<Table> frontier = new PriorityQueue<Table>(tableComparator);
//	ArrayList<Table> explored = new ArrayList<Table>();
//
//	public void  search(int[] initial_state, int[] goalState) {
//		
//    	final long startTimeNano = System.nanoTime();
//    	final long startTimeMilli = System.currentTimeMillis();
//
//		frontier.add(new Table(initial_state));
//
//		while (!frontier.isEmpty()) {
//			Table state = frontier.poll();
//
////			System.out.println("CHECK I : "+state.cost+(heurestic(state.getContent())));
//			explored.add(state);
//
//			if (state.isEqualPuzzle(new Table(goalState))) {
//				final long durationNano = System.nanoTime() - startTimeNano;
//            	final long durationMilli = System.currentTimeMillis() - startTimeMilli;
//            	
//				printPath(state, explored, initial_state);
//				
//				System.out.println("Total Cost : "+ depth);
//                System.out.println("Total Depth : "+ explored.size()); // explored nodes
//                System.out.println("Run Time In Nano Seconds : "+ durationNano);
//                System.out.println("Run Time In Milli Seconds : "+ durationMilli);
//                
//				return;
//			}
//			if (explored.size()>20000){
//				System.out.println("What are you doing :( Early Stop,Explored nodes exceeded 20000 ");
//				return;
//			}
//			ArrayList<Table> neigbours = state.getNeighbours();
//			for (int i = 0; i < neigbours.size(); i++) {
//				Table successor = neigbours.get(i);
//				if (!(isInExplorer(successor) || isInFrontier(successor))) {
//					successor.setPreState(state);
//					frontier.add(successor);
//				} else if (isInFrontier(successor)) {
//					updateFrontier(successor);
//				}
//			}
//		}
//	}
//
//	// To start again with the new Frontier Such as in City Paths Problem in Lect (Assume that you will start again from scratch from the current node Frontier).
//	// Start over repeat every thing.
//	private boolean updateFrontier(Table table) {
//		for (Table state : frontier) {
//			if (table.isEqualPuzzle(state)) {
//				if(table.cost<state.cost){
//					frontier.remove(state);
//					frontier.add(table);
//				}
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private boolean isInFrontier(Table table) {
//		for (Table state : frontier) {
//			if (table.isEqualPuzzle(state))
//				return true;
//		}
//		return false;
//	}
//
//	private boolean isInExplorer(Table table) {
//		for (Table state : explored) {
//			if (table.isEqualPuzzle(state))
//				return true;
//		}
//		return false;
//	}
//
//	public void printPath(Table goalState, ArrayList<Table> exploredStates, int[] initalState) {
//
//		Stack<Table> solutionStack = new Stack<Table>();
//		solutionStack.push(goalState);
//		while (!goalState.isEqualPuzzle(new Table(initalState))) {  //Goal is at the bottom of Stack so it will be the last node printed.
//			solutionStack.push(goalState.getPreState());
//			goalState = goalState.getPreState();
//		}
//		depth = solutionStack.size();
//		Table destinationState;
//		for (int i = solutionStack.size() - 1; i >= 0; i--) {
//			destinationState = solutionStack.get(i);
//			System.out.println(".............");
//			destinationState.display();
//		}
//
//	}
//}
