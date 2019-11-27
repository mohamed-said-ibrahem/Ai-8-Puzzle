import java.awt.Point;
import java.util.ArrayList;

public class Table {

	private Table preState;
	private int table_size = 3;
	private int[][] content;
	int cost =0 ;

	public Table(int[] initial_state) {
		content = from_1d_to_2d(initial_state);
		preState = null;
	}

	private int[][] from_1d_to_2d(int[] list) {
		int[][] res = new int[table_size][table_size];
		for (int i = 0; i < table_size; i++) {
			for (int j = 0; j < table_size; j++) {
				res[i][j] = list[i * table_size + j];
			}
		}
		return res;
	}

	public int[] from_2d_to_1d(int[][] list) {
		int[] res = new int[table_size * table_size];
		for (int i = 0; i < table_size; i++) {
			for (int j = 0; j < table_size; j++) {
				res[i * table_size + j] = list[i][j];
			}
		}
		return res;
	}

	public boolean move(int row, int col, Direction direction) {

		// Out of boundaries
		if (row < 0 || row >= table_size || col < 0 || col >= table_size)
			return false;

		switch (direction) {
		case Left:
			if (col == 0)
				return false;
			else
				swap(row, col, row, col - 1);
			break;
		case Right:
			if (col == (table_size - 1))
				return false;
			else
				swap(row, col, row, col + 1);
			break;
		case Top:
			if (row == 0)
				return false;
			else
				swap(row, col, row - 1, col);
			break;
		case Down:
			if (row == (table_size - 1))
				return false;
			else
				swap(row, col, row + 1, col);
			break;
		}
		this.cost++;
		return true;
	}

	private void swap(int row, int col, int row1, int col1) {

		preState = (Table) this.clone();
		int temp = content[row][col];
		content[row][col] = content[row1][col1];
		content[row1][col1] = temp;
	}

	public void display() {
		for (int i = 0; i < table_size; i++) {
			for (int j = 0; j < table_size; j++) {
				System.out.print(content[i][j] + "  ");
			}
			System.out.println("");
		}
	}

	public Table getPreState() {
		return preState;
	}

	public void setPreState(Table preState) {
		this.preState = preState;
	}

	public int[][] getContent() {
		return content;
	}

	public void setContent(int[][] content) {
		this.content = content;
	}

	
	@Override
	public Table clone() {
		int[] l = from_2d_to_1d(content);
		Table copy = new Table(l);
		copy.setPreState(this.preState);
		copy.cost =this.cost;
		return copy;
	}
		
	//index of 0 (space)
	public Point spaceIndex() {
		for (int i = 0; i < this.table_size; i++) {
			for (int j = 0; j < this.table_size; j++) {
				if (this.content[i][j] == 0) {
					return (new Point(i, j));
				}
			}
		}
		return null;
	}
	
	// Check if there is an available move from the Space( Zero 0) 
	public ArrayList<Table> getNeighbours() {
		Point index = this.spaceIndex();
		ArrayList<Table> neighbours = new ArrayList<Table>();
		for (Direction dir : Direction.values()) {  // Enum for all the directions
			Table currentTable = this.clone();
			if (currentTable.move(index.x, index.y, dir)) {
				currentTable.setPreState(this); // Current Table is the Pre State.
				if(!currentTable.equals(this.preState))
					neighbours.add(currentTable);
			}
		}
		return neighbours;
	}

	// Check if the current state is the goal state
	public boolean isEqualPuzzle(Table table) {
		for (int i = 0; i < this.table_size; i++) {
			for (int j = 0; j < this.table_size; j++) {
				if (this.content[i][j] != table.content[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public int totalCost() {
		return cost;
	}
	
}
