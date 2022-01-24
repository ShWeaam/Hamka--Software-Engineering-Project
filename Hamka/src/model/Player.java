package model;

import Utils.PlayerColor;

public class Player {

	private String nick_name;
	private int score;
	private PlayerColor color;
	
	public Player(String nick_name, PlayerColor color) {
		super();
		this.nick_name = nick_name;
		this.score = 0;
		this.color = color;
	}
	
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void updateScore(int toAdd) {
		this.score = score+toAdd;
	}
	public PlayerColor getColor() {
		return color;
	}
	public void setColor(PlayerColor color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "Player [nick_name=" + nick_name + ", score=" + score + ", color=" + color + "]";
	}
	
}
