package pouryapb.dooz;

import java.io.Serializable;

import javax.swing.JLabel;

public class PlayerData implements Comparable<PlayerData>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4050480654579565940L;
	public int rec;
	public String name;
	public JLabel avatar;
	
	// storing player's data
	public PlayerData(int rec, String name, JLabel avatar) {
		this.rec = rec;
		this.name = name;
		this.avatar = avatar;
	}

	// making it comparable :|
	@Override
	public int compareTo(PlayerData arg0) {
		if (arg0.rec > this.rec) {
			return 1;
		}
		if (arg0.rec < this.rec) {
			return -1;
		}
		return 0;
	}
}
