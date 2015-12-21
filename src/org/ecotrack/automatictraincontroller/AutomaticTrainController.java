package org.ecotrack.automatictraincontroller;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.util.Timer;
import java.util.TimerTask;

import org.ecotrack.automatictraincontroller.trackcontroller.TrackControllerHandler;
import org.ecotrack.automatictraincontroller.trackmodel.Track;
import org.ecotrack.automatictraincontroller.ctc.CTC;
import org.ecotrack.automatictraincontroller.ctc.ui.CTCOfficeFrame;

public class AutomaticTrainController {
		private CTCOfficeFrame ctc;
		private int secondsPassed = 0;
		private int CLOCK = 10;
		private Timer timer = new Timer();

		TimerTask task = new TimerTask() {
						public void run() {
								ctc.update(CLOCK);
						}
				};

		public void start(){
				Track track = new Track();
				TrackControllerHandler tch = new TrackControllerHandler(track);
				//track.getLine().get(0).getBlocks().get(0).setHeater(false);
				CTC ctcBackend = new CTC(track, tch);
				this.ctc = new CTCOfficeFrame(ctcBackend, track);
				timer.scheduleAtFixedRate(task,0,CLOCK);
		}

		public static void main(String[] args){
				try {
						for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
								if ("Nimbus".equals(info.getName())) {
										UIManager.setLookAndFeel(info.getClassName());
										break;
								}
						}
						AutomaticTrainController programDriver = new AutomaticTrainController();
						programDriver.start();
				}
				catch (Exception e2) {
						System.err.println("Unable to set L&F to Nimbus. Trying System L&F.");
						try {
								UIManager.setLookAndFeel(
										UIManager.getSystemLookAndFeelClassName());
						} catch (Exception e3) {
								System.err.println("Unable to set L&F to System L&F.  Using default.");
						}
						finally {
								AutomaticTrainController programDriver = new AutomaticTrainController();
								programDriver.start();
						}
				}
		}
}
