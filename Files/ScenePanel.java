/**
 * ScenePanel.java - Class handles all animation logic related to the 3D scene
 * Begun 09/07/18
 * @author Andrew Eissen
 */
package graphicsprojecttwo;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.sun.xml.internal.ws.util.StringUtils;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Though <code>Application</code> is technically the main class as it handles the display of all
 * visible objects from the user's perspective, this class, <code>ScenePanel</code>, is arguably
 * more important, as it contains the logic code that governs all interaction with the main element
 * of <code>Application</code>'s GUI, the 3D scene itself. As per the often-cited paradigm known as
 * the <a href="https://en.wikipedia.org/wiki/Single_responsibility_principle">single responsibility
 * principle</a>, the author saw fit to divide the multiple interfaces implemented by the progenitor
 * <code>UnlitCube.java</code> template file into their own individual subclasses or inner classes,
 * containing all the applicable logic needed therein and making use of external access to the
 * superclass to manipulate class fields as needed to complete transformations. This way, users
 * could look at the code related to the scene in a single place, freely able to determine what code
 * does what based on its place in the navigational system.
 * <br />
 * <br />
 * In many respects, this class is really just a glorified expansion of the aforementioned template
 * file <code>UnlitCube.java</code>, whose logic served as the bedrock of this class and its inner
 * classes. The author simply tacked on new functionality as needed while reorganizing that class's
 * contents to be more easily navigated by new viewers. The only new transformations included by
 * the author were a few scaling operations and some translation operations. The default
 * <code>UnlitCube.java</code> operations selected via <code>KeyListener</code>-mediated keystrokes
 * &mdash;that is, the rotation operations and reset operation&mdash;retained their original
 * keystrokes, with their default values moved to global constants in the <code>ScenePanel</code>
 * class body instead of being left hard-coded into the <code>switch</code> statement's
 * <code>case</code> bodies.
 * <br />
 * <br />
 * <pre>
 * Class table of contents:
 * - Constants                  Line 068
 * - Class fields/constructor   Line 124
 * - Setters                    Line 152
 * - Getters                    Line 264
 * - Utility methods            Line 365
 * - Inner helper classes       Line 603
 *   - SceneGLEventListener     Line 636
 *   - SceneKeyListener         Line 837
 *   - TimerListener            Line 951
 * </pre>
 *
 * @see com.jogamp.opengl.awt.GLJPanel
 * @author Andrew Eissen
 */
final class ScenePanel extends GLJPanel {

  // Constants

  /** Default <code>Timer</code> interval period, set by default to 1600 milliseconds */
  private final static int TIMER_DELAY = 1600;

  /** Magic number; initial default <code>ScenePanel.counter</code> value of 1 */
  private final static int DEFAULT_COUNTER = 1;

  /** Standard <code>ScenePanel.rotateX</code> value, set to 45.0 by default */
  private final static double DEFAULT_ROTATE_X = 45.0;

  /** Standard <code>ScenePanel.rotateY</code> value, set to 15.0 by default */
  private final static double DEFAULT_ROTATE_Y = 15.0;

  /** Standard <code>ScenePanel.rotateZ</code> value, set to 0.0 by default */
  private final static double DEFAULT_ROTATE_Z = 0.0;

  /** Standard <code>ScenePanel.translateX</code> value, set to 0.0 by default */
  private final static double DEFAULT_TRANSLATE_X = 0.0;

  /** Standard <code>ScenePanel.translateY</code> value, set to 0.0 by default */
  private final static double DEFAULT_TRANSLATE_Y = 0.0;

  /** Standard <code>ScenePanel.translateZ</code> value, set to 0.0 by default */
  private final static double DEFAULT_TRANSLATE_Z = 0.0;

  /** Standard <code>ScenePanel.scale</code> value, set to 1.5 by default */
  private final static double DEFAULT_SCALE = 1.5;

  /** Degree amount by which scene is rotated along x, y, z-axes, set to 15.0 */
  private final static double ROTATION_INCREMENT = 15.0;

  /** Increment by which scene is scaled relative to the camera, set to 0.1 */
  private final static double SCALING_INCREMENT = 0.1;

  /** Increment by which scene is translated along x, y, z-axes, set to 0.1 */
  private final static double TRANSLATION_INCREMENT = 0.1;

  /** Olive <code>Color</code> (gold-ish), used to color <code>FiveSidedPyramid</code> instance */
  private final static Color OLIVE = new Color(128, 128, 0);

  /** Purple <code>Color</code>, used to color <code>TriangularPrism</code> instance */
  private final static Color PURPLE = new Color(128, 0, 128);

  /** Teal <code>Color</code>, used to color <code>TenSidedPolygon</code> instance */
  private final static Color TEAL = new Color(0, 128, 128);

  /** Maroon <code>Color</code>, used to color <code>Cube</code> instance */
  private final static Color MAROON = new Color(128, 0, 0);

  /** Mint <code>Color</code>, used to color <code>HexagonalPrism</code> instance */
  private final static Color MINT = new Color(0, 128, 0);

  /** Gold <code>Color</code>, used to color <code>Star</code> instance */
  private final static Color GOLD = new Color(212, 175, 55);

  // Class fields/constructor

  private Application parent;
  private Timer animationTimer;
  private int counter;
  private boolean isAnimationPlaying;
  private double scale, rotateX, rotateY, rotateZ, translateX, translateY, translateZ;

  /**
   * Parameterized constructor
   *
   * @param parent <code>Application</code>
   */
  protected ScenePanel(Application parent) {
    super(new GLCapabilities(null));

    // Add new listeners
    this.addGLEventListener(new ScenePanel.SceneGLEventListener());
    this.addKeyListener(new ScenePanel.SceneKeyListener());

    // Set default transforms and parent
    this.setApplication(parent);
    this.setAnimationTimer(new Timer(ScenePanel.TIMER_DELAY, new ScenePanel.TimerListener()));
    this.setCounter(ScenePanel.DEFAULT_COUNTER);
    this.setIsAnimationPlaying(false);
    this.setTransformationsToDefaults();
  }

  // Setters

  /**
   * Setter for <code>ScenePanel.parent</code>
   *
   * @param parent <code>Application</code>
   * @return void
   */
  private void setApplication(Application parent) {
    this.parent = parent;
  }

  /**
   * Setter for <code>ScenePanel.animationTimer</code>
   *
   * @param animationTimer <code>Timer</code>
   * @return void
   */
  private void setAnimationTimer(Timer animationTimer) {
    this.animationTimer = animationTimer;
  }

  /**
   * Setter for <code>ScenePanel.counter</code>
   *
   * @param counter <code>int</code>
   * @return void
   */
  private void setCounter(int counter) {
    this.counter = counter;
  }

  /**
   * Setter for <code>ScenePanel.isAnimationPlaying</code>
   *
   * @param isAnimationPlaying <code>boolean</code>
   * @return void
   */
  private void setIsAnimationPlaying(boolean isAnimationPlaying) {
    this.isAnimationPlaying = isAnimationPlaying;
  }

  /**
   * Setter for <code>ScenePanel.scale</code>
   *
   * @param scale <code>double</code>
   * @return void
   */
  private void setScale(double scale) {
    this.scale = scale;
  }

  /**
   * Setter for <code>ScenePanel.rotateX</code>
   *
   * @param rotateX <code>double</code>
   * @return void
   */
  private void setRotateX(double rotateX) {
    this.rotateX = rotateX;
  }

  /**
   * Setter for <code>ScenePanel.rotateY</code>
   *
   * @param rotateY <code>double</code>
   * @return void
   */
  private void setRotateY(double rotateY) {
    this.rotateY = rotateY;
  }

  /**
   * Setter for <code>ScenePanel.rotateZ</code>
   *
   * @param rotateZ <code>double</code>
   * @return void
   */
  private void setRotateZ(double rotateZ) {
    this.rotateZ = rotateZ;
  }

  /**
   * Setter for <code>ScenePanel.translateX</code>
   *
   * @param translateX <code>double</code>
   * @return void
   */
  private void setTranslateX(double translateX) {
    this.translateX = translateX;
  }

  /**
   * Setter for <code>ScenePanel.translateY</code>
   *
   * @param translateY <code>double</code>
   * @return void
   */
  private void setTranslateY(double translateY) {
    this.translateY = translateY;
  }

  /**
   * Setter for <code>ScenePanel.translateZ</code>
   *
   * @param translateZ <code>double</code>
   * @return void
   */
  private void setTranslateZ(double translateZ) {
    this.translateZ = translateZ;
  }

  // Getters

  /**
   * Getter for <code>ScenePanel.parent</code>
   *
   * @return parent <code>Application</code>
   */
  private Application getApplication() {
    return this.parent;
  }

  /**
   * Getter for <code>ScenePanel.animationTimer</code>
   *
   * @return animationTimer <code>Timer</code>
   */
  private Timer getAnimationTimer() {
    return this.animationTimer;
  }

  /**
   * Getter for <code>ScenePanel.counter</code>
   *
   * @return counter <code>int</code>
   */
  private int getCounter() {
    return this.counter;
  }

  /**
   * Getter for <code>ScenePanel.isAnimationPlaying</code>
   *
   * @return isAnimationPlaying <code>boolean</code>
   */
  private boolean getIsAnimationPlaying() {
    return this.isAnimationPlaying;
  }

  /**
   * Getter for <code>ScenePanel.scale</code>
   *
   * @return scale <code>double</code>
   */
  protected double getScale() {
    return this.scale;
  }

  /**
   * Getter for <code>ScenePanel.rotateX</code>
   *
   * @return rotateX <code>double</code>
   */
  protected double getRotateX() {
    return this.rotateX;
  }

  /**
   * Getter for <code>ScenePanel.rotateY</code>
   *
   * @return rotateY <code>double</code>
   */
  protected double getRotateY() {
    return this.rotateY;
  }

  /**
   * Getter for <code>ScenePanel.rotateZ</code>
   *
   * @return rotateZ <code>double</code>
   */
  protected double getRotateZ() {
    return this.rotateZ;
  }

  /**
   * Getter for <code>ScenePanel.translateX</code>
   *
   * @return translateX <code>double</code>
   */
  protected double getTranslateX() {
    return this.translateX;
  }

  /**
   * Getter for <code>ScenePanel.translateY</code>
   *
   * @return translateY <code>double</code>
   */
  protected double getTranslateY() {
    return this.translateY;
  }

  /**
   * Getter for <code>ScenePanel.translateZ</code>
   *
   * @return translateZ <code>double</code>
   */
  protected double getTranslateZ() {
    return this.translateZ;
  }

  // Utility methods

  /**
   * This glorified accessor method simply serves to permit the addition of new status log entries
   * to the GUI log from within this class, calling the parent <code>Application.addLogEntry</code>
   * method and passing on the message accordingly.
   *
   * @param message <code>String</code>
   * @return void
   */
  private void addLogEntry(String message) {
    this.getApplication().addLogEntry(message);
  }

  /**
   * This helper method is used by the parameterized <code>ScenePanel</code> constructor and the
   * <code>ScenePanel.resetScene</code> method to set all the rotation, translation, and scale
   * fields back to the default constants as defined at the head of the program.
   * <br />
   * <pre>
   * - scale        ->  1.5
   * - rotateX      -> 15.0
   * - rotateY      -> 15.0
   * - rotateZ      ->  0.0
   * - translateX   ->  0.0
   * - translateY   -> -0.1
   * - translateZ   ->  0.0
   * </pre>
   *
   * @return void
   */
  private void setTransformationsToDefaults() {
    this.setScale(ScenePanel.DEFAULT_SCALE);
    this.setRotateX(ScenePanel.DEFAULT_ROTATE_X);
    this.setRotateY(ScenePanel.DEFAULT_ROTATE_Y);
    this.setRotateZ(ScenePanel.DEFAULT_ROTATE_Z);
    this.setTranslateX(ScenePanel.DEFAULT_TRANSLATE_X);
    this.setTranslateY(ScenePanel.DEFAULT_TRANSLATE_Y);
    this.setTranslateZ(ScenePanel.DEFAULT_TRANSLATE_Z);
  }

  /**
   * This method is called from within this class and <code>Application</code>, serving as the click
   * handler for <code>Application.resetButton</code> and as the <code>default</code> option within
   * the <code>ScenePanel.runAnimation</code> method's <code>switch</code> block. It is used to
   * simply reset the scene to its original dimensions and transformation operator values, allowing
   * the user to reverse any janky manipulations he/she may have undertaken without having to close
   * and restart the program.
   *
   * @return void
   */
  protected void resetScene() {
    if (!this.getIsAnimationPlaying()) {
      this.addLogEntry("Scene reset");
    }

    this.setTransformationsToDefaults();
    this.repaint();
  }

  /**
   * This overloaded version of the above <code>ScenePanel.resetScene</code> method is simply used
   * by <code>Application.toggleButtonHandler</code> to pass along the <code>boolean</code> value
   * indicating whether or not the predetermined animation is playing. The main version of this
   * method is then called to reset the values to the chosen defaults.
   *
   * @param isPlaying <code>boolean</code>
   * @return void
   */
  protected void resetScene(boolean isPlaying) {
    this.setIsAnimationPlaying(isPlaying);
    this.resetScene();
  }

  /**
   * This method is the main utility method of the "Video" <code>JToggleButton</code> handler, used
   * in both click cases to pass the <code>String</code> representation of a <code>Timer</code>
   * method name (either <code>Timer.start</code> or <code>Timer.stop</code>) as a parameter.
   * Depending on the user's actions, the class <code>Timer</code> instance will be started or
   * stopped in the course of playing the prerecorded animation of default transformation operations
   * found in <code>ScenePanel.runAnimation</code>. As with the below method
   * <code>ScenePanel.performTransformation</code>, the <code>String</code> representation of the
   * method name signature is also used for the purposes of constructing the user status log entry
   * to remove the need to include another <code>String</code> parameter during invocation
   * specifically containing a log message.
   * <br />
   * <pre>
   * Examples:
   * - "stop"  -> "Stop"  + "p" + "ing animation" -> "Stopping animation"
   * - "start" -> "Start" + ""  + "ing animation" -> "Starting animation"
   * </pre>
   *
   * @see java.lang.reflect
   * @param methodName <code>String</code> either "start" or "stop"
   * @return void
   */
  protected void toggleAnimation(String methodName) {
    try {

      // Declarations
      boolean isStop;
      String capitalizedMethodName;
      Method timerMethod;

      // Definitions
      isStop = methodName.equals("stop");
      capitalizedMethodName = StringUtils.capitalize(methodName);

      // Either Timer.start() or Timer.stop()
      timerMethod = Timer.class.getDeclaredMethod(methodName);

      // Add log entry, making use of methodName String extract to form the verb
      this.addLogEntry(capitalizedMethodName + ((isStop) ? "p" : "") + "ing animation");

      // Either animationTimer.start() or animationTimer.stop()
      timerMethod.invoke(this.getAnimationTimer());

      // Reset the animation to the start if stop button is pressed
      if (isStop) {
        this.setCounter(ScenePanel.DEFAULT_COUNTER);
      }

    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
      this.addLogEntry("Error: " + ex);
    }
  }

  /**
   * As per the Project 2 design rubrics, the program is expected to demonstrate six (6) total
   * unique transformation operations. However, as the scene was deliberately designed by the author
   * to be interactive, undertaking a series of set transformations required a unique approach that
   * also helpfully had the benefit of playing into the author's unit testing plan. By implementing
   * a predefined animation on presses of the "Video" <code>JToggleButton</code>, the user can play
   * through a series of six standard animations demonstrating the rotate, scale, and translate
   * operations implemented by the author in the program. Building off the first project, this
   * animation makes use of the same <code>Timer</code>-based functionality as Project 1, employing
   * a <code>EventListener</code> inner class called <code>TimerListener</code> to set the intervals
   * by which the animation progresses between frames.
   * <br />
   * <br />
   * By automatically defining which operations followed which in the course of the animation, the
   * author was able to unit test to ensure that each operation worked as expected. Storyboarding
   * out the expected animation on paper before testing it allowed the author to make sure that the
   * scene was being manipulated properly by the program, providing the author some further
   * reassurance that it was coded properly.
   *
   * @return void
   */
  private void runAnimation() {

    switch (this.getCounter()) {
      case 1: // -45.0
        this.performTransformation("RotateY", -ScenePanel.ROTATION_INCREMENT * 3.0);
        break;
      case 2: // 30.0
        this.performTransformation("RotateX", ScenePanel.ROTATION_INCREMENT * 2.0);
        break;
      case 3: // 0.2
        this.performTransformation("Scale", ScenePanel.SCALING_INCREMENT * 2.0);
        break;
      case 4: // 75.0
        this.performTransformation("RotateY", ScenePanel.ROTATION_INCREMENT * 5.0);
        break;
      case 5: // -0.2
        this.performTransformation("TranslateZ", -ScenePanel.TRANSLATION_INCREMENT * 2.0);
        break;
      case 6: // -0.5
        this.performTransformation("Scale", -ScenePanel.SCALING_INCREMENT * 5.0);
        break;
      default:
        this.addLogEntry("Resetting animation video");
        this.resetScene();
        this.setCounter(0);
        break;
    }

    this.repaint();
    this.setCounter(this.getCounter() + 1);
  }

  /**
   * One of two methods in this class making use of the reflection technique/paradigm, this method
   * is used as the primary means by which modifications to the class fields used to interact with
   * the scene and change transformations is undertaken. As the author is a big fan of accessors
   * (getters and setters alike), he made use of these in the program to restrict access to class
   * fields as needed. As these are all named with the same suffix and simply prepended with either
   * "get" or "set" depending on their functions, the author figured this system of naming could be
   * used in this method to get both getter and setter for an included parameter <code>String</code>
   * containing the suffix extract. Further, rather than waste bits passing as a separate parameter
   * a <code>String</code> message for the user log, the author made use of some regex to place a
   * space before capital letters in the method suffix to use the modified method name as the log
   * entry itself, thus minimizing the parameters needed and automating the logging process.
   * <br />
   * <pre>
   * Example: methodSuffix = "RotateX"
   * - "get" + "RotateX" -> getRotateX()
   * - "set" + "RotateX" -> setRotateX()
   * - "RotateX" -> [regex] -> "Rotate X" + " by " + amount
   * </pre>
   *
   * @see java.lang.reflect
   * @see <a href="https://stackoverflow.com/a/20677443">SO Regex thread reply</a>
   * @param methodSuffix <code>String</code> representation of getter/setter suffix (ie. "-RotateX")
   * @param amount <code>double</code> transformation amount to be added to current field value
   * @return void
   */
  private void performTransformation(String methodSuffix, double amount) {
    try {

      // Declarations
      Method setterMethod, getterMethod;
      double currentGetterValue, newSetterValue;
      String logMessage;

      // Define appropriate methods
      setterMethod = ScenePanel.class.getDeclaredMethod("set" + methodSuffix, double.class);
      getterMethod = ScenePanel.class.getDeclaredMethod("get" + methodSuffix);

      // Get current field value from getter
      currentGetterValue = (double) getterMethod.invoke(this);

      // Perform transformation by input amount
      newSetterValue = currentGetterValue + amount;

      // Make use of method substring extract in user status log entry (space before capital letter)
      logMessage = methodSuffix.replaceAll("(.)([A-Z])", "$1 $2");

      // Add entry to user log
      this.addLogEntry(logMessage + " by " + amount);

      // Set new field amount using setter
      setterMethod.invoke(this, newSetterValue);

    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
      this.addLogEntry("Error: " + ex);
    }
  }

  // Inner helper classes

  /**
   * This class contains the majority of the code related to the rendering and painting of the scene
   * and its assorted <code>SceneObject</code> elements. Its most important components are two of
   * its default required methods, namely <code>GLEventListener.init</code> and
   * <code>GLEventListener.display</code>, both of which are relied upon to handle repaints and
   * re-renders of the scene on detection of either user input via the keyboard or the start of the
   * predetermined transformation animation video. This listener, though implemented by the main
   * class in the <code>UnlitCube.java</code> template file, was moved to its own inner class due
   * to the <a href="https://en.wikipedia.org/wiki/Single_responsibility_principle">single
   * responsibility principle</a>, which requires division of functionality into separate units
   * based on purpose, as evidenced in the other classes of the program.
   * <br />
   * <br />
   * It is within this class that the rubric-required six (6) distinct shapes are added to the
   * scene. In reality, there are seven shapes in the default scene; however, as the shapes
   * <code>Cube</code> and <code>Floor</code> are both 3D rectangular boxes, they are treated as a
   * single shape and count as one towards the six required.
   * <br />
   * <pre>
   * Implemented shapes:
   * - Shape 1: Box (<code>Cube</code> and <code>Floor</code>)
   * - Shape 2: Pyramid (<code>FiveSidedPyramid</code>)
   * - Shape 3: Sideways hexagonal cylinder (<code>HexagonalPrism</code>)
   * - Shape 4: Triangular cylinder (<code>TriangularPrism</code>)
   * - Shape 5: Star (<code>Star</code>)
   * - Shape 6: ...Polygon thingie (<code>TenSidedPolygon</code>)
   * </pre>
   *
   * @see com.jogamp.opengl.GLEventListener
   * @author Andrew Eissen
   */
  private final class SceneGLEventListener implements GLEventListener {

    // Declarations
    private GL2 gl2;
    private ArrayList<SceneObject> sceneObjectArrayList;

    /**
     * Default constructor
     */
    private SceneGLEventListener() {

      // Declaration
      ArrayList<SceneObject> tempSceneObjectArrayList;

      // Assignment and definition
      this.setSceneObjectArrayList(new ArrayList<>());
      tempSceneObjectArrayList = this.getSceneObjectArrayList();

      // Add new SceneObject elements (6x, as per rubric requirements) to ArrayList
      // ArrayList addition            Shape type       Painting hue       Scale   trX  trY   trZ
      tempSceneObjectArrayList.add(new Floor(           Color.DARK_GRAY,   0.5,    0.0, 0.0,  0.0));
      tempSceneObjectArrayList.add(new Cube(            ScenePanel.MAROON, 0.125,  0.0, 1.4,  0.0));
      tempSceneObjectArrayList.add(new FiveSidedPyramid(ScenePanel.OLIVE,  0.125,  2.5, 1.4,  0.0));
      tempSceneObjectArrayList.add(new HexagonalPrism(  ScenePanel.MINT,   0.125, -2.5, 1.4,  0.0));
      tempSceneObjectArrayList.add(new TriangularPrism( ScenePanel.PURPLE, 0.125,  0.0, 1.4, -2.5));
      tempSceneObjectArrayList.add(new TenSidedPolygon( ScenePanel.TEAL,   0.125,  0.0, 1.4,  2.5));
      tempSceneObjectArrayList.add(new Star(            ScenePanel.GOLD,   0.125,  0.0, 4.4,  0.0));
    }

    // Setters

    /**
     * Setter for <code>SceneGLEventListener.gl2</code>
     *
     * @param gl2 <code>GL2</code>
     * @return void
     */
    private void setGl2(GL2 gl2) {
      this.gl2 = gl2;
    }

    /**
     * Setter for <code>SceneGLEventListener.sceneObjectArrayList</code>
     *
     * @param sceneObjectArrayList <code>ArrayList</code>
     * @return void
     */
    private void setSceneObjectArrayList(ArrayList<SceneObject> sceneObjectArrayList) {
      this.sceneObjectArrayList = sceneObjectArrayList;
    }

    // Getters

    /**
     * Getter for <code>SceneGLEventListener.gl2</code>
     *
     * @return gl2 <code>GL2</code>
     */
    protected GL2 getGl2() {
      return this.gl2;
    }

    /**
     * Getter for <code>SceneGLEventListener.sceneObjectArrayList</code>
     *
     * @return sceneObjectArrayList <code>ArrayList</code>
     */
    protected ArrayList<SceneObject> getSceneObjectArrayList() {
      return this.sceneObjectArrayList;
    }

    // Required methods

    /**
     * Much of this method's contents were taken from two places in particular, the Project 2
     * template files package's <code>UnlitCube.java</code> and <code>JoglStarter.java</code> files
     * and a <a href="https://www.tutorialspoint.com/jogl/jogl_3d_graphics.htm">TutorialsPoint</a>
     * series on JOGL graphics. Basically, it starts the drawing process, defining and setting the
     * <code>GL2</code> class instance and applying models and modes as needed for optimum viewing
     * and rendering. It is first called when the scene panel is first created.
     * <br />
     * <br />
     * As the Project 2 rubric file states that the scene must possess dimensions of at least
     * 640:480 (4:3), the author implemented the desired size of the scene panel in the body of the
     * <code>Application</code> class. However, the resultant scene was skewed heavily, requiring
     * that its dimensions be adjusted to accommodate this fact. This was done by adjusting the
     * first two parameters of <code>GL2.glOrtho</code>, passing a <code>double</code> quotient
     * derived from the division of 4 by 3.
     *
     * @see <code>UnlitCube.java</code>
     * @see <a href="https://www.tutorialspoint.com/jogl/jogl_3d_graphics.htm">TutorialsPoint</a>
     * @see <a href="https://stackoverflow.com/a/45458876">SO preserving aspect ratio thread</a>
     * @param glad <code>GLAutoDrawable</code>
     * @return void
     */
    @Override
    public void init(GLAutoDrawable glad) {

      // Declaration
      double aspect;
      final GL2 newGl2;

      // Definitions and assignment
      aspect = 4.0 / 3.0; // Preserve 640:480 scene aspect ratio
      newGl2 = glad.getGL().getGL2();
      this.setGl2(newGl2);

      // Mostly from UnlitCube.java, with modifications from TutorialsPoint
      newGl2.glMatrixMode(GL2.GL_PROJECTION);           // Modify projection matrix
      newGl2.glOrtho(-aspect, aspect, -1, 1, -10, 100); // Alter coord system on projection matrix
      newGl2.glMatrixMode(GL2.GL_MODELVIEW);            // Switch to modifying modelview matrix
      newGl2.glShadeModel(GL2.GL_SMOOTH);               // Value representing shading technique
      newGl2.glClearColor(0, 0, 0, 0);                  // Set scene background color (black)
      newGl2.glClearDepth(1.0);                         // B/w 0 & 1, amount of depth buffer cleared
      newGl2.glEnable(GL2.GL_DEPTH_TEST);               // Used for 3D drawing
      newGl2.glDepthFunc(GL2.GL_LEQUAL);                // GL_LEQUAL recommended for shaders
      newGl2.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // Really nice quality
    }

    /**
     * This method, one of the required implemented methods of the <code>GLEventListener</code>
     * interface, is largely based on the similar method found in the Project 2 template files
     * package file <code>UnlitCube.java</code>, with the initial transforms taken directly from
     * that file's own <code>display</code> method. As its name implies, it is called whenever the
     * scene requires a repaint/redraw due to transformations being undertaken on its contents. The
     * newly calculated values of the fields related to the translation, scale, and rotation
     * operations are used to render the scene as it is supposed to appear.
     * <br />
     * <br />
     * In one of the author's more embarrassing moments, the six <code>SceneObject</code> instances
     * were initially defined in this method prior to the author realizing that it was called with
     * every keystroke, meaning that the program was creating and initializing countless new
     * class instances with every transformation. Brilliant work.
     *
     * @param glad <code>GLAutoDrawable</code> (unused)
     * @return void
     */
    @Override
    public void display(GLAutoDrawable glad) {

      // Declarations
      final GL2 tempGl2;
      double tempScale;

      // Definitions (cache values, limit method calls)
      tempGl2 = this.getGl2();
      tempScale = ScenePanel.this.getScale();

      // Taken from UnlitCube.java, with modifications
      tempGl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
      tempGl2.glLoadIdentity();

      tempGl2.glRotated(ScenePanel.this.getRotateZ(), 0, 0, 1);
      tempGl2.glRotated(ScenePanel.this.getRotateY(), 0, 1, 0);
      tempGl2.glRotated(ScenePanel.this.getRotateX(), 1, 0, 0);
      tempGl2.glScaled(tempScale, tempScale, tempScale);
      tempGl2.glTranslated(ScenePanel.this.getTranslateX(), ScenePanel.this.getTranslateY(),
        ScenePanel.this.getTranslateZ());

      // Draw new SceneObject-extending subclass objects
      this.getSceneObjectArrayList().forEach((SceneObject sceneObject) -> {
        sceneObject.constructObject(tempGl2);
      });
    }

    /**
     * Noop method required by <code>GLEventListener</code>
     *
     * @param glad <code>GLAutoDrawable</code>
     * @return void
     */
    @Override
    public void dispose(GLAutoDrawable glad) {}

    /**
     * Noop method required by <code>GLEventListener</code>
     *
     * @param glad <code>GLAutoDrawable</code>
     * @param i <code>int</code>
     * @param i1 <code>int</code>
     * @param i2 <code>int</code>
     * @param i3 <code>int</code>
     * @return void
     */
    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {}
  }

  /**
   * As seen in the Project 2 template files package's <code>UnlitCube.java</code> file, a subclass
   * of the <code>ScenePanel</code> class implementing the <code>KeyListener</code> interface is
   * used to allow the user to interact with the scene via the keyboard and a listing of accepted
   * keystrokes. The means by which this is accomplished is <code>KeyListener.keyPressed</code>, a
   * method powered primarily by a <code>switch</code> statement that determines which key the user
   * has pressed and responds by either throwing an error message or changing the assorted
   * <code>ScenePanel</code> transformation field to change the display in accordance with the
   * user's expectations.
   *
   * @see java.awt.event.KeyListener
   * @author Andrew Eissen
   */
  private final class SceneKeyListener implements KeyListener {

    /**
     * One of the required methods of the <code>KeyListener</code> interface, this method was
     * largely based on the similar <code>switch</code>-driven method evidenced in the author's
     * Project 1 submission. Like that method, this method logs an entry in the user GUI status log
     * indicating what transformation has been undertaken. The transformations themselves are not
     * made by this method; instead, this method simply gets and resets a certain class field
     * related to the types of legitimate transformations by a predetermined constant amount. At the
     * end, the method repaints the scene.
     * <br />
     * <br />
     * The keybindings associated with each transformation were derived from the Project 2 templates
     * package's <code>UnlitCube.java</code>, in particular its own <code>keyPressed</code> method.
     * A few minor modifications were made, but the only part removed was the "Home" key, which in
     * effect reset the scene. In this project, the "Reset" button is used to accomplish this
     * instead. Further, the author saw fit to add some translate and scale functionality as well to
     * provide the user with more options with which to manipulate the scene.
     *
     * @param e <code>KeyEvent</code>
     * @return void
     */
    @Override
    public void keyPressed(KeyEvent e) {

      // User shouldn't be able to interact with the scene while animation is playing
      if (ScenePanel.this.getIsAnimationPlaying()) {
        ScenePanel.this.addLogEntry("Error: Keystrokes disabled while animation is running.");
        return;
      }

      switch (e.getKeyCode()) {
        case KeyEvent.VK_R:
          ScenePanel.this.performTransformation("Scale", ScenePanel.SCALING_INCREMENT);
          break;
        case KeyEvent.VK_E:
          ScenePanel.this.performTransformation("Scale", -ScenePanel.SCALING_INCREMENT);
          break;
        case KeyEvent.VK_PAGE_UP:
          ScenePanel.this.performTransformation("RotateZ", ScenePanel.ROTATION_INCREMENT);
          break;
        case KeyEvent.VK_PAGE_DOWN:
          ScenePanel.this.performTransformation("RotateZ", -ScenePanel.ROTATION_INCREMENT);
          break;
        case KeyEvent.VK_RIGHT:
          ScenePanel.this.performTransformation("RotateY", ScenePanel.ROTATION_INCREMENT);
          break;
        case KeyEvent.VK_LEFT:
          ScenePanel.this.performTransformation("RotateY", -ScenePanel.ROTATION_INCREMENT);
          break;
        case KeyEvent.VK_DOWN:
          ScenePanel.this.performTransformation("RotateX", ScenePanel.ROTATION_INCREMENT);
          break;
        case KeyEvent.VK_UP:
          ScenePanel.this.performTransformation("RotateX", -ScenePanel.ROTATION_INCREMENT);
          break;
        case KeyEvent.VK_X:
          ScenePanel.this.performTransformation("TranslateZ", ScenePanel.TRANSLATION_INCREMENT);
          break;
        case KeyEvent.VK_Z:
          ScenePanel.this.performTransformation("TranslateZ", -ScenePanel.TRANSLATION_INCREMENT);
          break;
        case KeyEvent.VK_W:
          ScenePanel.this.performTransformation("TranslateY", ScenePanel.TRANSLATION_INCREMENT);
          break;
        case KeyEvent.VK_S:
          ScenePanel.this.performTransformation("TranslateY", -ScenePanel.TRANSLATION_INCREMENT);
          break;
        case KeyEvent.VK_D:
          ScenePanel.this.performTransformation("TranslateX", ScenePanel.TRANSLATION_INCREMENT);
          break;
        case KeyEvent.VK_A:
          ScenePanel.this.performTransformation("TranslateX", -ScenePanel.TRANSLATION_INCREMENT);
          break;
        default:
          ScenePanel.this.addLogEntry("Error: '" + KeyEvent.getKeyText(e.getKeyCode())
            + "' not supported. Press 'About' for supported keystrokes.");
          break;
      }

      ScenePanel.this.repaint();
    }

    /**
     * Noop method required by <code>KeyListener</code>
     *
     * @param e <code>KeyEvent</code>
     * @return void
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Noop method required by <code>KeyListener</code>
     *
     * @param e <code>KeyEvent</code>
     * @return void
     */
    @Override
    public void keyTyped(KeyEvent e) {}
  }

  /**
   * This utility inner class was simply used to handle <code>Timer</code> events, and to more
   * easily allow for instantiation of a new <code>Timer</code> object to assign to the global
   * <code>animationTimer</code> field. This class contains a single required method, namely
   * <code>actionPerformed</code>, which itself invokes the outer class's own
   * <code>ScenePanel.runAnimation</code> method.
   *
   * @see java.awt.event.ActionListener
   * @see <a href="https://stackoverflow.com/questions/23894689">Relevant SO Thread #1</a>
   * @see <a href="https://stackoverflow.com/questions/11438048">Relevant SO Thread #2</a>
   * @author Andrew Eissen
   */
  private final class TimerListener implements ActionListener {

    /**
     * This method implements the only required method of the <code>ActionListener</code> class, and
     * simply invokes the outer class's <code>ScenePanel.runAnimation</code> method as stated above.
     *
     * @param e <code>ActionEvent</code>
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      ScenePanel.this.runAnimation();
    }
  }
}