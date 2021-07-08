package pouryapb.dooz;

import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Records implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3094346193583044679L;
	private ArrayList<PlayerData> recs = new ArrayList<>();
	
	// new row of records
	public void newRecord(int rec, String name, JLabel avatar) {
		recs.add(new PlayerData(rec, name, avatar));
	}

	// makes some labels and prints them on a (records) panel. (sorted by records value)
	public void printRecords(JPanel panel) {		
		Collections.sort(recs);
		
		for (int i = 4; i < recs.size(); i++) {
			recs.remove(i);
		}
		
		JLabel firstAvatar = new JLabel("");
		firstAvatar.setBounds(119, 88, 70, 70);
		panel.add(firstAvatar);
		
		JLabel secondAvatar = new JLabel("");
		secondAvatar.setBounds(119, 169, 70, 70);
		panel.add(secondAvatar);
		
		JLabel thirdAvatar = new JLabel("");
		thirdAvatar.setBounds(119, 250, 70, 70);
		panel.add(thirdAvatar);
		
		JLabel fourthAvatar = new JLabel("");
		fourthAvatar.setBounds(119, 331, 70, 70);
		panel.add(fourthAvatar);
		
		JLabel firstName = new JLabel("");
		firstName.setBounds(199, 88, 113, 23);
		panel.add(firstName);
		
		JLabel secondName = new JLabel("");
		secondName.setBounds(199, 169, 113, 23);
		panel.add(secondName);
		
		JLabel thirdName = new JLabel("");
		thirdName.setBounds(199, 250, 113, 23);
		panel.add(thirdName);
		
		JLabel fourthName = new JLabel("");
		fourthName.setBounds(199, 331, 113, 23);
		panel.add(fourthName);
		
		JLabel firstRec = new JLabel("");
		firstRec.setFont(new Font("SansSerif", Font.BOLD, 18));
		firstRec.setHorizontalAlignment(SwingConstants.CENTER);
		firstRec.setBounds(496, 108, 88, 29);
		panel.add(firstRec);
		
		JLabel secondRec = new JLabel("");
		secondRec.setFont(new Font("SansSerif", Font.BOLD, 18));
		secondRec.setHorizontalAlignment(SwingConstants.CENTER);
		secondRec.setBounds(496, 189, 88, 29);
		panel.add(secondRec);
		
		JLabel thirdRec = new JLabel("");
		thirdRec.setFont(new Font("SansSerif", Font.BOLD, 18));
		thirdRec.setHorizontalAlignment(SwingConstants.CENTER);
		thirdRec.setBounds(496, 270, 88, 29);
		panel.add(thirdRec);
		
		JLabel fourthRec = new JLabel("");
		fourthRec.setFont(new Font("SansSerif", Font.BOLD, 18));
		fourthRec.setHorizontalAlignment(SwingConstants.CENTER);
		fourthRec.setBounds(496, 351, 88, 29);
		panel.add(fourthRec);
		
		// try catch block is in case there are less than 4 rows in records
		try {
			firstAvatar.setIcon(recs.get(0).avatar.getIcon());
			firstName.setText(recs.get(0).name);
			firstRec.setText(String.valueOf(recs.get(0).rec));

			secondAvatar.setIcon(recs.get(1).avatar.getIcon());
			secondName.setText(recs.get(1).name);
			secondRec.setText(String.valueOf(recs.get(1).rec));

			thirdAvatar.setIcon(recs.get(2).avatar.getIcon());
			thirdName.setText(recs.get(2).name);
			thirdRec.setText(String.valueOf(recs.get(2).rec));

			fourthAvatar.setIcon(recs.get(3).avatar.getIcon());
			fourthName.setText(recs.get(3).name);
			fourthRec.setText(String.valueOf(recs.get(3).rec));
		} catch (Exception e) {}
		
	}
}
