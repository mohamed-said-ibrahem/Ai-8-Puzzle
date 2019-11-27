import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS implements Search {
	// Forntier is the main data structure for the algorithm and explored is helpful in alot of side such as printing path and so on.
	Queue<Table> frontier = new LinkedList<Table>();
	ArrayList<Table> explored = new ArrayList<Table>();
	int cost = 0;
	int depth = 0;
	public void search(int[] initial_state, int[] goalState) {
    	final long startTimeNano = System.nanoTime();
    	final long startTimeMilli = System.currentTimeMillis();
    	
		frontier.add(new Table(initial_state));
		while (!frontier.isEmpty()) {
			Table state = frontier.poll();
			explored.add(state);
			cost++;
			if (state.isEqualPuzzle(new Table(goalState))) {
               	final long durationNano = System.nanoTime() - startTimeNano;
            	final long durationMilli = System.currentTimeMillis() - startTimeMilli;
            	
				printPath(state, explored, initial_state);
				System.out.println("Total Depth : "+ depth);
                System.out.println("Total Cost : "+ explored.size()); // explored nodes
                System.out.println("Run Time In Nano Seconds : "+ durationNano);
                System.out.println("Run Time In Milli Seconds : "+ durationMilli);
				return;
			}
			if (explored.size()>200000) {
                System.out.println("What are you doing :( .Early Stop,Explored nodes exceeded 20000 ");
                return;
            }
			ArrayList<Table> neigbours = state.getNeighbours();
			for (int i = 0; i < neigbours.size(); i++) {
				Table successor = neigbours.get(i);
				if (isInExplorer(successor) || isInFrontier(successor)) {
					continue;
				} else {
					successor.setPreState(state);
					frontier.add(successor);
				}
			}
		}
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
		while (!goalState.isEqualPuzzle(new Table(initalState))) { //Goal is at the bottom of Stack so it will be the last node printed.
			solutionStack.push(goalState.getPreState());
			goalState = goalState.getPreState();
		}
		Table destinationState;
		depth = solutionStack.size()-1;
		for (int i = solutionStack.size() - 1; i >= 0; i--) {
			destinationState = solutionStack.get(i);
			System.out.println(".............");
			destinationState.display();
		}
	}
}