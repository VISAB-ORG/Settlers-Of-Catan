package model.plan;

import model.RoadBuildPlace;

/**
 * Klasse, die die Aktion das Bauen von Straﬂen darstellt
 * 
 * @author Tjark Harjes
 */
public class BuildRoad extends Action {

	private int row, column;
	
	public BuildRoad() {
	}

	public BuildRoad(RoadBuildPlace randomRoadPlace) {
		this.row = randomRoadPlace.row;
		this.column = randomRoadPlace.column;
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
