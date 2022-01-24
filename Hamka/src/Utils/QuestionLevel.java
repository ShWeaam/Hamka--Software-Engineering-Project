package Utils;

public enum QuestionLevel {
	EASY(100, 250), INTERMEDIATE(200, 100), HARD(500, 50);

	private final int pointsToAdd;
	private final int pointsToRemove;

	private QuestionLevel(int pointsToAdd, int pointsToRemove) {
		this.pointsToAdd = pointsToAdd;
		this.pointsToRemove = pointsToRemove;

	}

	public int getPointsToRemove() {
		return pointsToRemove;
	}

	public int getPointsToAdd() {
		return pointsToAdd;
	}

}
