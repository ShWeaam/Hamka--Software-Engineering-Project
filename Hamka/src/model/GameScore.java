package model;

import java.sql.Date;
import java.time.LocalDateTime;

public class GameScore {

	private String winnerNickName;
	private int winnerScore;
	private String rivalNickName;
	private int rivalScore;
	private String gameDate;
	/**
	 * @param winnerNickName
	 * @param winnerScore
	 * @param rivalNickName
	 * @param rivalScore
	 * @param dateApperance
	 */
	public GameScore(String winnerNickName, int winnerScore, String rivalNickName, int rivalScore, String gameDate) {
		super();
		this.winnerNickName = winnerNickName;
		this.winnerScore = winnerScore;
		this.rivalNickName = rivalNickName;
		this.rivalScore = rivalScore;
		this.gameDate = gameDate;
	}
	/**
	 * @return the winnerNickName
	 */
	public String getWinnerNickName() {
		return winnerNickName;
	}
	/**
	 * @param winnerNickName the winnerNickName to set
	 */
	public void setWinnerNickName(String winnerNickName) {
		this.winnerNickName = winnerNickName;
	}
	/**
	 * @return the winnerScore
	 */
	public int getWinnerScore() {
		return winnerScore;
	}
	/**
	 * @param winnerScore the winnerScore to set
	 */
	public void setWinnerScore(int winnerScore) {
		this.winnerScore = winnerScore;
	}
	/**
	 * @return the rivalNickName
	 */
	public String getRivalNickName() {
		return rivalNickName;
	}
	/**
	 * @param rivalNickName the rivalNickName to set
	 */
	public void setRivalNickName(String rivalNickName) {
		this.rivalNickName = rivalNickName;
	}
	/**
	 * @return the rivalScore
	 */
	public int getRivalScore() {
		return rivalScore;
	}
	/**
	 * @param rivalScore the rivalScore to set
	 */
	public void setRivalScore(int rivalScore) {
		this.rivalScore = rivalScore;
	}
	/**
	 * @return the dateApperance
	 */
	public String getGameDate() {
		return gameDate;
	}
	/**
	 * @param dateApperance the dateApperance to set
	 */
	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameScore [winnerNickName=" + winnerNickName + ", winnerScore=" + winnerScore + ", rivalNickName="
				+ rivalNickName + ", rivalScore=" + rivalScore + ", gameDate=" + gameDate + "]";
	}
	

	
}
