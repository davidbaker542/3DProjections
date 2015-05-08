package src.runner;

import com.arcadeengine.*;
import src.main.*;

import javax.swing.*;
import java.awt.event.*;

public class ProjectionRunner {

	AnimPanel panel = new ProjectionMain();

	// ==============================================================================
	// --- Typically you will never need to edit any of the code below this ---
	// ==============================================================================


	private JFrame myFrame;

	public ProjectionRunner() {

		this.myFrame = new JFrame();
		this.myFrame.addWindowListener(new Closer());

		addFrameComponents();

		this.myFrame.pack();

		this.myFrame.setVisible(true);
		this.myFrame.setResizable(panel.isResizable());

		this.myFrame.setLocationRelativeTo(null);

		startAnimation();
	}

	public void addFrameComponents() {

		this.myFrame.setTitle(this.panel.getMyName());

		this.myFrame.add(this.panel);
	}

	public void startAnimation() {

		Thread anim = new Thread() {
			@Override
			public void run() {

				Timer timer = new Timer(1000 / 60, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						if(!ProjectionRunner.this.myFrame.isResizable())
							ProjectionRunner.this.myFrame.pack();
						ProjectionRunner.this.myFrame.getComponent(0).repaint();
					}
				});
				timer.start();
			}
		};
		
		Thread process = new Thread() {
			
			@Override
			public void run() {
			
				Timer timer = new Timer(1000 / ProjectionRunner.this.panel.getTimerDelay(), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						ProjectionRunner.this.panel.runProcess();
						ProjectionRunner.this.panel.setComponentClicked(false);
					}
				});
				timer.start();
			}
		};
		
		anim.start();
		process.start();
	}

	public static void main(String[] args) {

		new ProjectionRunner();
	}

	private static class Closer extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {

			System.out.println("Closing...");
			System.exit(0);
		} // ======================
	}

}
