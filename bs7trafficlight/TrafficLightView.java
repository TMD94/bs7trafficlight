package bs7trafficlight;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * View of the MVC pattern. It shows the traffic light and allows to 
 * switch on/off the lights based on the traffic light models.
 * @author Maik Aicher
 *
 */
public class TrafficLightView extends JPanel {
	/** default */
	private static final long serialVersionUID = 1L;
	
	/** Size of the complete window frame. It is slightly larger than the png's */
	public static final int SIZE_X = 726;
	
	/** Size of the view in Y direction */
	public static final int SIZE_VIEW_Y = 711;

	/** Size of the buttons in Y direction */
	public static final int SIZE_BTT_Y = 89;
	
	/** Size of the complete window frame in Y direction */
	public static final int SIZE_Y = SIZE_VIEW_Y + SIZE_BTT_Y;
	
	/** Names of all images in the project */
	private static final String[] IMG_NAMES = {
			"traffic_light.png",
			"car_rd.png",
			"car_yl.png",
			"car_gn.png",
			"pers_rd.png",
			"pers_gn.png"
	};
	
	/** positions of the images in the IMG_NAMES array. This will be needed
	 * also in the images Array which holds the preloaded files
	 */
	private static final int IMG_BACKGROUND = 0;
	private static final int IMG_CAR_RED = 1;
	private static final int IMG_CAR_YEL = 2;
	private static final int IMG_CAR_GRN = 3;
	private static final int IMG_PER_RED = 4;
	private static final int IMG_PER_GRN = 5;
	
	/** location of the images within the project */
	private static final String IMG_PATH = "/bs7trafficlight/img/";
	
	/** Preloaded images as BufferedImage objects */
	private BufferedImage[] images;
	
	/** holds all images that will be shown in the repaint */
	private ArrayList<BufferedImage> visibleImages = new ArrayList<>();
	
	/** controller - only needed for the url generation in the image loader*/
	private TrafficLightControl myControl;

	/**
	 * Constructor for preparing the object
	 * @param myControl Reference to the controller
	 */
	public TrafficLightView(TrafficLightControl myControl) {
		this.myControl = myControl;
		validate();
		initializeView();
	}
	
	/**
	 * Basic initialization
	 */
	private void initializeView() {
		// load images into the buffer array
		getImages();
		
		// place the background on position 0
		visibleImages.add(images[IMG_BACKGROUND]);
	}

	/**
	 * Load the images based on the IMG_NAMES array into the
	 * image buffer
	 */
	private void getImages() {
		images = new BufferedImage[IMG_NAMES.length];
		for (int i = 0; i < IMG_NAMES.length; i++) {
			images[i] = getImage(IMG_PATH + IMG_NAMES[i]);
		}
	}
	
	/**
	 * Load a single image from the given (relative) path
	 * @param fileName File name including path
	 * @return File loaded as BufferedImage object
	 */
	public BufferedImage getImage(String fileName) {
		// generate a url based on the location of the main class
		// (with the main method) and the relative path
		URL url = myControl.getClass().getResource(fileName);
		BufferedImage myImage = null;
		try {
			myImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myImage;
	}

	/**
	 * Overwritten paintComponent method. This will be called if the
	 * system sees a need for repaint or the program requested a repaint
	 * by calling the repaint method.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		// only the images in the visibleImages ArrayList will be drawn
		for (BufferedImage myImg : visibleImages) {
			g2D.drawImage(myImg, null, 0, 0);
		}
	}
	
	/**
	 * Requests the active lights from the models and places the corresponding images
	 * into the visibleImages ArrayList.
	 * @param carLight Model of the car traffic light
	 * @param persLight Model of the pedestrian traffic light
	 */
	public void setImages(CarTrafficLightModel carLight, PersonTrafficLightModel persLight) {
		// delete all except background (which is always on index 0)
		while(visibleImages.size() > 1) {
			visibleImages.remove(1);
		}
		
		// check which lights should be active and place the images into the ArrayList
		if (carLight.isGreenOn()) {
			visibleImages.add(images[IMG_CAR_GRN]);
		}
		if (carLight.isYellowOn()) {
			visibleImages.add(images[IMG_CAR_YEL]);
		}
		if (carLight.isRedOn()) {
			visibleImages.add(images[IMG_CAR_RED]);
		}
		if (persLight.isGreenOn()) {
			visibleImages.add(images[IMG_PER_GRN]);
		}
		if (persLight.isRedOn()) {
			visibleImages.add(images[IMG_PER_RED]);
		}
		// trigger the view to repaint
		repaint();
	}
}
