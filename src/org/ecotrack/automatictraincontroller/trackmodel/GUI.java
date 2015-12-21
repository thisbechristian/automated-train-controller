package org.ecotrack.automatictraincontroller.trackmodel;

import javax.swing.*;


class GUI{

	GUIPanel panel1;

	public GUI(Track track){
		JFrame window = new JFrame("Track Module");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.panel1 = new GUIPanel(track);
		window.add(panel1);
		window.setSize(1400,1000);
        window.setVisible(true);
    }

	public void update(int CLOCK){
		this.panel1.update(CLOCK);
	}
}
