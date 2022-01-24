package model;

import java.net.URLDecoder;

public class Consts {
	private Consts() {
		throw new AssertionError();
	}

	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	public static final String SQL_SELECT_GAMESCORE = "SELECT * FROM GameScore";
	public static final String SQL_INS_SCORE ="INSERT INTO  GameScore( winnerNickName, winnerScore, rivalNickName, rivalScore, dateApperance )\n" +
			  "VALUES(?,?,?,?,?);";

	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			// System.out.println(decoded) - Can help to check the returned path
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				System.out.println(decoded);

				return decoded + "/database/GameScore.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				return decoded + "src/model/GameScore.accdb";

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
