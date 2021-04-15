package model.plan;

import model.VillageBuildPlace;

/**
 * Klasse, die die Aktion das Bauen von Siedlungen darstellt
 * 
 * @author Tjark Harjes
 */
public class BuildVillage extends Action {

	private int row, column;

	public BuildVillage() {}

	
	public BuildVillage(VillageBuildPlace place) {
		this.setRow(place.row);
		this.setColumn(place.column);
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}
	
	
}
