import java.util.ArrayList;

public interface Search {
	//Interface for multilple search algorithms
    void search(int[] initial_state,int[] goalState);
    void printPath(Table goalState, ArrayList<Table> exploredStates, int[] initalState);
}
