/**
 * Application.java - Handles user GUI, status log posting, button presses
 * Begun 09/07/18
 * @author Andrew Eissen
 */
package graphicsprojecttwo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class serves as the interface handler, creating the user GUI on initialization by the
 * <code>main</code> method and handling all button presses and log entry posts. As per the author's
 * Project 1 submission, the GUI is hand-coded and comes complete with a status GUI informing the
 * users as to what actions are being performed and alerting them of any possible errors. Similarly,
 * a set of buttons are included, ranging from a default log-clearing function to a scene reset
 * button to an "About" button rendering a popup modal that indicates which keystrokes perform which
 * functions. The only button handler not included in this class is the "Reset" button's, namely
 * <code>ScenePanel.resetScene</code>, which resets the private transformation class fields back
 * to their original values. As it is needed for use in that class by various helper methods, that
 * method was left in its original position.
 * <br />
 * <br />
 * Originally, in one of the first implementations of this project, the author had a fair bit of
 * scene animation logic contained in this method and almost nothing in the <code>ScenePanel</code>
 * class. As per the <a href="https://en.wikipedia.org/wiki/Single_responsibility_principle">single
 * responsibility principle</a>, this trend was reversed, and all logic code moved to that class
 * along with the applicable fields and accessor methods. This class was subsequently focused back
 * on its main responsibility of handling the GUI.
 *
 * @see javax.swing.JFrame
 * @author Andrew Eissen
 */
final class Application extends JFrame {

  /** Constant used to implement unit testing functionality more easily */
  private final static boolean DEBUG = false;

  // Window fields
  private int windowHeight, windowWidth;
  private String windowTitle;

  // GUI fields
  private JFrame mainFrame;
  private JPanel mainPanel, interfacePanel, scenePanel, buttonPanel, logPanel;
  private JButton aboutButton, resetButton, clearButton;
  private JToggleButton toggleButton;
  private JTextArea logTextArea;
  private JScrollPane logScrollPane;
  private ScenePanel scene;

  /**
   * Default constructor
   * <br />
   * <br />
   * Note: Parameterized constructor option removed due to the need to preserve 4:3 scene aspect
   * ratio required by rubric. As the author is not sure how to keep this ratio preserved while
   * still making use of a layout manager, this constructor, which hardcodes values as needed, was
   * the only version implemented.
   */
  protected Application() {

    // Window fields
    super("Java OpenGL Project");
    this.setWindowHeight(661);  // 480 + 181
    this.setWindowWidth(656);   // 640 + 16
    this.setWindowTitle("Java OpenGL Project");

    // Construct interface
    this.constructGUI();
  }

  // Setters

  /**
   * Setter for <code>Application.windowHeight</code>
   *
   * @param windowHeight <code>int</code>
   * @return void
   */
  private void setWindowHeight(int windowHeight) {
    this.windowHeight = windowHeight;
  }

  /**
   * Setter for <code>Application.windowWidth</code>
   *
   * @param windowWidth <code>int</code>
   * @return void
   */
  private void setWindowWidth(int windowWidth) {
    this.windowWidth = windowWidth;
  }

  /**
   * Setter for <code>Application.windowTitle</code>
   *
   * @param windowTitle <code>String</code>
   * @return void
   */
  private void setWindowTitle(String windowTitle) {
    this.windowTitle = windowTitle;
  }

  /**
   * Setter for <code>Application.scene</code>
   *
   * @param scene <code>ScenePanel</code>
   * @return void
   */
  private void setScene(ScenePanel scene) {
    this.scene = scene;
  }

  // Getters

  /**
   * Getter for <code>Application.windowHeight</code>
   *
   * @return windowHeight <code>int</code>
   */
  protected int getWindowHeight() {
    return this.windowHeight;
  }

  /**
   * Getter for <code>Application.windowWidth</code>
   *
   * @return windowWidth <code>int</code>
   */
  protected int getWindowWidth() {
    return this.windowWidth;
  }

  /**
   * Getter for <code>Application.windowTitle</code>
   *
   * @return windowTitle <code>String</code>
   */
  protected String getWindowTitle() {
    return this.windowTitle;
  }

  /**
   * Getter for <code>Application.scene</code>
   *
   * @return scene <code>ScenePanel</code>
   */
  protected ScenePanel getScene() {
    return this.scene;
  }

  // GUI method

  /**
   * As with the author's Project 1 submission, this project makes use of a hand-coded user GUI to
   * display both the 3D scene and all the user elements used to navigate the program. This includes
   * as before a status log for the posting of status updates and errors encountered, and a set of
   * buttons allowing the user to either display a popup modal containing directions for how to
   * manipulate the scene, reset the scene to its original state, clear the GUI status log of
   * previous entries, or play through a predetermined set of transformation animations.
   * <br />
   * <br />
   * The author experimented with a few different layouts over the course of the assignment.
   * Initially, the GUI looked much like that from the first assignment, the only difference being
   * the replacement of the three <code>ImagePanel</code> instances with the <code>ScenePanel</code>
   * object. However, as this was determined to be insufficient to display the scene as desired, the
   * author replaced this with a more compact interface design that squished the buttons together to
   * the left of the shrunken status log.
   * <br />
   * <br />
   * <pre>
   * Buttons and their functions:
   * - About -> Displays a popup modal with legitimate keystrokes listing
   * - Reset -> Resets the scene to original transformation defaults
   * - Clear -> Clears the log of messages
   * - Video -> Plays a premade animation of several transforms (6, as per rubric)
   * </pre>
   *
   * @return void
   */
  private void constructGUI() {

    // JPanel definitions
    this.mainPanel = new JPanel(new BorderLayout());
    this.interfacePanel = new JPanel(new BorderLayout());
    this.scenePanel = new JPanel(new GridLayout(1, 1, 5, 5));
    this.buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
    this.logPanel = new JPanel(new GridLayout(1, 1, 5, 5));

    // ScenePanel definition
    this.scene = new ScenePanel(this);
    this.scene.setPreferredSize(new Dimension(640, 480));
    this.setScene(scene);

    // Apply borders and backgrounds
    this.mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    this.scenePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    this.buttonPanel.setBorder(BorderFactory.createTitledBorder("Options"));
    this.scenePanel.setBorder(BorderFactory.createTitledBorder("Scene"));
    this.logPanel.setBorder(BorderFactory.createTitledBorder("Status log"));

    // JButton definitions
    this.aboutButton = new JButton("About");
    this.resetButton = new JButton("Reset");
    this.clearButton = new JButton("Clear");
    this.toggleButton = new JToggleButton("Video");

    // Status log elements
    this.logTextArea = new JTextArea();
    this.logTextArea.setEditable(false);
    this.logTextArea.setFont(new Font("Monospaced", 0, 11));
    this.logTextArea.setLineWrap(true);
    this.logScrollPane = new JScrollPane(this.logTextArea);

    // Add scene to scenePanel JPanel
    this.scenePanel.add(this.scene);
    this.scenePanel.setPreferredSize(new Dimension(640, 480));

    // Add JButtons to buttonPanel
    this.buttonPanel.add(this.aboutButton);
    this.buttonPanel.add(this.resetButton);
    this.buttonPanel.add(this.clearButton);
    this.buttonPanel.add(this.toggleButton);

    // Add log elements to logPanel
    this.logPanel.add(this.logScrollPane);

    // Add mini-panels to mainPanel
    this.interfacePanel.add(this.buttonPanel, BorderLayout.WEST);
    this.interfacePanel.add(this.logPanel, BorderLayout.CENTER);
    this.mainPanel.add(this.scenePanel, BorderLayout.CENTER);
    this.mainPanel.add(this.interfacePanel, BorderLayout.SOUTH);

    // Information popup button handler
    this.aboutButton.addActionListener((ActionEvent e) -> {
      this.displayDetails();
      this.getScene().requestFocusInWindow();
    });

    // Reset scene button handler
    this.resetButton.addActionListener((ActionEvent e) -> {
      this.getScene().resetScene();
      this.getScene().requestFocusInWindow();
    });

    // Clear old log entries button handler
    this.clearButton.addActionListener((ActionEvent e) -> {
      this.logTextArea.setText("");
      this.getScene().requestFocusInWindow();
    });

    // Switch button for automatic animation vs user transformation
    this.toggleButton.addItemListener((ItemEvent e) -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        this.toggleButtonHandler(true);
      } else if(e.getStateChange() == ItemEvent.DESELECTED){
        this.toggleButtonHandler(false);
      }

      this.scene.requestFocusInWindow();
    });

    // Placement/sizing details for main JFrame element
    this.mainFrame = new JFrame(this.getWindowTitle());
    this.mainFrame.setContentPane(this.mainPanel);
    this.mainFrame.setSize(this.getWindowWidth(), this.getWindowHeight());
    this.mainFrame.setResizable(false);
    this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.mainFrame.setVisible(true);

    // Check if scene is 640x480
    if (Application.DEBUG) {
      this.listDimensions();
    }

    // Focus on scene
    this.scene.requestFocusInWindow();
  }

  // Utility methods

  /**
   * This method is a printing method used by the author to determine the exact size the main window
   * should be to achieve a 4:3 aspect ratio of 640px to 480px. There is probably a better way to do
   * this, but the author is currently ignorant of any such approaches.
   *
   * @return void
   */
  private void listDimensions() {
    this.addLogEntry("Window frame height: " + this.mainFrame.getHeight());
    this.addLogEntry("Scene panel height:  " + this.scenePanel.getHeight());
    this.addLogEntry("Non-scene height:    " + this.determineNonSceneDimension("getHeight"));

    this.addLogEntry("Window frame width:  " + this.mainFrame.getWidth());
    this.addLogEntry("Scene panel width:   " + this.scenePanel.getWidth());
    this.addLogEntry("Non-scene width:     " + this.determineNonSceneDimension("getWidth"));
  }

  /**
   * This method may be used to calculate how much of the <code>JFrame</code> window is currently
   * not part of the <code>scenePanel</code> element displaying the 3D scene. This allows the author
   * to know how large the entire window should be to incorporate the necessary user UI interface
   * while maintaining the 4:3 aspect ratio. The method makes use of the reflection paradigm in
   * order to pass the name of the right <code>Component</code> method, either <code>getWidth</code>
   * or <code>getHeight</code>.
   *
   * @see java.lang.reflect
   * @param methodName <code>String</code>
   * @return result <code>int</code>
   */
  private int determineNonSceneDimension(String methodName) {
    try {

      // Declaration
      Method dimensionMethod;
      int result, mainFrameDimension, scenePanelDimension;

      // Either Component.getHeight or Component.getWidth
      dimensionMethod = Component.class.getDeclaredMethod(methodName);

      // Get mainFrame width/height
      mainFrameDimension = (int) dimensionMethod.invoke(this.mainFrame);

      // Get scenePanel width/height
      scenePanelDimension = (int) dimensionMethod.invoke(this.scenePanel);

      // Get subtraction result
      result = mainFrameDimension - scenePanelDimension;

      return result;

    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
      this.addLogEntry("Error: " + ex);
      return -1;
    }
  }

  /**
   * This method is basically the same as the one from the author's Project 1 submission, used to
   * post new entries to the GUI's status log from this class and from <code>ScenePanel</code> via
   * an identically-named accessor method. If the <code>Application.DEBUG</code> flag is set to
   * true, the message will also be printed to the console.
   *
   * @param message <code>String</code>
   * @return void
   */
  protected void addLogEntry(String message) {
    this.logTextArea.append(message + "\n");

    if (Application.DEBUG) {
      System.out.println(message);
    }
  }

  // Button click handlers

  /**
   * This method serves the click handler for the "About" button. It is used to display a popup
   * modal informing the user of the controls to use to manipulate the scene by hand. As the
   * <code>JOptionPane</code> permits the use of HTML in message <code>String</code>s, the author
   * saw fit to use this construct to display an unordered list of list elements indicating which
   * keystrokes perform what functions.
   *
   * @return void
   */
  private void displayDetails() {

    // Declarations
    String title, message;

    // Definitions
    title = "Transformation commands";
    message = // Properly indented for Increased Readability™
      "<html>"
        + "<div style='font-weight:normal; margin:0 10px 0 10px;'>"
          + "Basic scene manipulation keyboard commands:"
          + "<br />"
          + "<ul>"
            + "<li>W, A, S, and D keys control x-axis and y-axis translation</li>"
            + "<li>Z and X keys control z-axis translation</li>"
            + "<li>Arrow keys control x-axis and y-axis rotation</li>"
            + "<li>PgUp and PgDn keys control z-axis rotation</li>"
            + "<li>E and R keys control scaling</li>"
          + "</ul>"
        + "</div>"
      + "</html>";

    // Open popup window with contents
    JOptionPane.showMessageDialog(this.mainFrame, message, title, JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * This button handler method, used for presses of the <code>JToggleButton</code> was originally
   * located in the body of <code>ScenePanel</code> until the author determined this was a violation
   * of the <a href="https://en.wikipedia.org/wiki/Single_responsibility_principle">single
   * responsibility principle</a>. As a result, it was slightly refactored to call a series of
   * <code>protected</code> helper methods in <code>ScenePanel</code> rather than set that class's
   * fields directly from this class.
   *
   * @param isSelected <code>boolean</code>
   * @return void
   */
  private void toggleButtonHandler(boolean isSelected) {
    this.getScene().resetScene(isSelected);

    if (isSelected) {
      this.getScene().toggleAnimation("start");
    } else {
      this.getScene().toggleAnimation("stop");
    }
  }
}