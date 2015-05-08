package src.main;

import com.arcadeengine.*;
import com.arcadeengine.gui.*;
import src.main.base.*;
import src.main.gui.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by 15bakerd on 10/28/2014.
 */
public class ProjectionMain extends AnimPanel {


	private double radChange = Math.PI / 256;

	private KeyBinding bindings = new KeyBinding("SystBinds") {

		@Override
		public void onPress(KeyEvent e, String key) {

		}

		@Override
		public void whilePressed(KeyEvent e, String key) {

			if(key.equals("X"))
				camera.setFocalLength(camera.getFocalLength() + 1);
			if(key.equals("Z"))
				camera.setFocalLength(camera.getFocalLength() - 1);

			if(key.equals("Up"))
				camera.setPitch(camera.getPitch() + radChange);
			if(key.equals("Down"))
				camera.setPitch(camera.getPitch() - radChange);

			if(key.equals("Left"))
				camera.setYaw(camera.getYaw() + radChange);
			if(key.equals("Right"))
				camera.setYaw(camera.getYaw() - radChange);

			if(key.equals("F"))
				cube.setYaw(cube.getYaw() + radChange);
			if(key.equals("H"))
				cube.setYaw(cube.getYaw() - radChange);

			double vel = 3;

			if(getKeyBoardHandler().isKeyHeld("Shift"))
				vel = 0.5;

			if(key.equals("Q"))
				camera.setZ(camera.getZ() - vel);
			if(key.equals("E"))
				camera.setZ(camera.getZ() + vel);

			if(key.equals("W"))
				camera.setY(camera.getY() - vel);
			if(key.equals("S"))
				camera.setY(camera.getY() + vel);

			if(key.equals("A"))
				camera.setX(camera.getX() - vel);
			if(key.equals("D"))
				camera.setX(camera.getX() + vel);

			if(key.equals("R"))
				cube.setRoll(cube.getRoll() + radChange);
			if(key.equals("Y"))
				cube.setRoll(cube.getRoll() - radChange);

			if(key.equals("T"))
				cube.setPitch(cube.getPitch() + radChange);
			if(key.equals("G"))
				cube.setPitch(cube.getPitch() - radChange);

			if(key.equals("F"))
				cube.setYaw(cube.getYaw() + radChange);
			if(key.equals("H"))
				cube.setYaw(cube.getYaw() - radChange);
		}

		@Override
		public void onRelease(KeyEvent e, String key) {
		}
	};

	private DebugLine debug = new DebugLine("Syst") {

		@Override
		public String[] getLines() {

			String lines[] = {
				"FPS: " + getFPS(),
				addBreak(),
				"Cam X: " + camera.getX(),
				"Cam Y: " + camera.getY(),
				"Cam Z: " + camera.getZ(),
				"Cam Focal Length: " + camera.getFocalLength(),
				"Cam Yaw: " + camera.getYaw(),
				"Cam Pitch: " + camera.getPitch(),
				addBreak(),
				"Cube Yaw: " + cube.getYaw(),
				"Cube Pitch: " + cube.getPitch(),
				"Cube Roll: " + cube.getRoll(),
			};
			return lines;
		}
	};

	private World world;

	private Camera camera;
	private Cube cube;

	private Face[] axes;



	public ProjectionMain() {

		this.createInstance("3D Projections", 800, 600);

		this.setResizable(true);
		this.getKeyBoardHandler().addBindings(bindings);
		this.createGuiHandler(new GuiMain(this));
		this.getGuiHandler().addDebugLine(debug);
		this.getGuiHandler().setDebugState(true);

		initEngine();

		cube = new Cube(0, 0, 0, 200, 200, 200);

		world.addShape(cube);

		Cube c1 = new Cube(250, 0, 0, 150, 150, 150);
		Cube c2 = new Cube(250, 160, 0, 100, 100, 100);

		c1.setPitch(3);
		c1.setYaw(5);

		c2.setPitch(6);
		c2.setYaw(2);

		world.addShape(c1);
		world.addShape(c2);
	}

	public void initEngine() {

		world = new World();

		camera = new Camera(world, 0, 0, 300, Math.PI / 2, Math.PI, 0, getPreferredSize().width, getPreferredSize().height, 200);
		axes = new Face[3];

		axes[0] = new Face(new Node[] { new Node(-1000, 1, 0), new Node(-1000, -1, 0), new Node(1000, -1, 0), new Node(1000, 1, 0) });
		axes[1] = new Face(new Node[] { new Node(1, -1000, 0), new Node(-1, -1000, 0), new Node(-1, 1000, 0), new Node(1, 1000, 0) });
		axes[2] = new Face(new Node[] { new Node(1, 1, -1000), new Node(-1, -1, -1000), new Node(-1, -1, 1000), new Node(1, 1, 1000) });

		axes[0].setColor(Color.RED.darker());
		axes[1].setColor(Color.GREEN.darker());
		axes[2].setColor(new Color(84, 114, 255));

		world.addFace(axes[0]);
		world.addFace(axes[1]);
		world.addFace(axes[2]);
	}

	@Override
	public Graphics renderFrame(Graphics g) {


		this.calculateRenderFPS();

		camera.drawRender(g);

		this.drawGui(g);
		return g;
	}

	@Override
	public void process() {

		if(isLeftClickHeld()) {
			double yawRad = (refMouse.x - getMousePosition().x) / 256d;
			double pitchRad = (refMouse.y - getMousePosition().y) / 256d;

			cube.setYaw(refAngles[0] - yawRad);
			cube.setPitch(refAngles[1] - pitchRad);
		}

		this.updateGui();
	}

	@Override
	public void initRes() {

	}

	private Point refMouse;
	private double[] refAngles;

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);

		refMouse = getMousePosition();
		refAngles = new double[] { cube.getYaw(), cube.getPitch(), cube.getRoll() };
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);

	}
}
