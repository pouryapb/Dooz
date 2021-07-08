package pouryapb.dooz;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public class TimerCount {

	private int time = 0;
	private int sec = 0;
	private int min = 0;
	private boolean stop;
	
	// starts a timer
	// gets two labels, minute and second then prints passed time from the start on those (updates each second)
	public void start(JLabel minute, JLabel second) {
		
		stop = false;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				if (min < 10) {
					minute.setText("0" + String.valueOf(min));
				}
				else {
					minute.setText(String.valueOf(min));
				}
				if (sec < 10) {
					second.setText("0" + String.valueOf(sec));
				}
				else {
					second.setText(String.valueOf(sec));
				}
				
				minute.repaint();
				second.repaint();
				time++;
				if (sec < 59) {
					sec++;
				}
				else {
					sec = 0;
					min++;
				}
				if (stop) {
					timer.cancel();
					timer.purge();
					return;
				}
			}
		}, 0, 1000);
	}
	
	// a method for stopping the time!!
	public void setStop(boolean e) {
		stop = e;
	}
	
	// getting the time for calculating records 
	public int getTime() {
		return time;
	}
}
