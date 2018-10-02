/**
 * SceneObject.java - Abstract class containing methods for rendering objects in scene
 * Begun 09/07/18
 * @author Andrew Eissen
 */
package graphicsprojecttwo;

import com.jogamp.opengl.GL2;
import java.awt.Color;

/**
 * This abstract class contains the base methods used by all 3D objects included in the scene to
 * render, draw, and paint themselves therein. For the most part, this class is the most important
 * part of the scene objects, with the individual extending classes simply containing the required
 * vertices and faces two-dimensional arrays used to actually sculpt the shape designs. Originally,
 * the author had those arrays in a static <code>interface</code> called <code>ShapeData</code>,
 * with the included static methods used to return the arrays as needed. However, as that felt less
 * than ideal to the author, he just made a series of extending classes instead.
 * <br />
 * <br />
 * As far as the process of populating the two two-dimensional arrays was concerned, the author
 * followed the <code>UnlitCube.java</code> file's recommendations to use graph paper to determine
 * which vertices go where and which faces make use of which vertices. The entire process was
 * perhaps the most trying and time-consuming part of the entire process, causing the author no end
 * of headaches when negative signs were misplaced and shapes ended up rendered in bizarre and often
 * illogical ways. These arrays are accessed external via the abstract methods defined in extending
 * classes, namely <code>getVertices</code> and <code>getFaces</code>.
 * <br />
 * <br />
 * Originally, the extending classes also implemented a third method related to returning a second
 * hand-built <code>double[][]</code> two-dimensional array, itself containing color code data to
 * be applied to each face. However, the author discovered that passing a single hue as a parameter
 * to the class and then using a helper method like <code>SceneObject.buildColorArray</code> to
 * build such an array was a more efficient approach. Further, to provide some semblance of
 * pseudo-lighting in an unlit scene, the <code>Color</code> class's <code>Color.brighter</code> and
 * <code>Color.darker</code> methods were used to color certain faces facing towards and away from
 * the camera differently to give the impression of shadows and an unseen light source.
 *
 * @author Andrew Eissen
 */
abstract class SceneObject {

  // Class variables
  private Color color;
  private double[][] colorArray;
  private double translateX, translateY, translateZ, scale;

  /**
   * Parameterized constructor
   *
   * @param color <code>Color</code>
   * @param scale <code>double</code>
   * @param translateX <code>double</code>
   * @param translateY <code>double</code>
   * @param translateZ <code>double</code>
   */
  protected SceneObject(Color color, double scale, double translateX, double translateY,
      double translateZ) {

    // Set fields
    this.setColor(color);
    this.setColorArray(this.buildColorArray());
    this.setScale(scale);
    this.setTranslateX(translateX);
    this.setTranslateY(translateY);
    this.setTranslateZ(translateZ);
  }

  // Setters

  /**
   * Setter for <code>SceneObject.color</code>
   *
   * @param color <code>Color</code>
   * @return void
   */
  private void setColor(Color color) {
    this.color = color;
  }

  /**
   * Setter for <code>SceneObject.colorArray</code>
   *
   * @param colorArray <code>double[][]</code>
   * @return void
   */
  private void setColorArray(double[][] colorArray) {
    this.colorArray = colorArray;
  }

  /**
   * Setter for <code>SceneObject.scale</code>
   *
   * @param scale <code>double</code>
   * @return void
   */
  private void setScale(double scale) {
    this.scale = scale;
  }

  /**
   * Setter for <code>SceneObject.translateX</code>
   *
   * @param translateX <code>double</code>
   * @return void
   */
  private void setTranslateX(double translateX) {
    this.translateX = translateX;
  }

  /**
   * Setter for <code>SceneObject.translateY</code>
   *
   * @param translateY <code>double</code>
   * @return void
   */
  private void setTranslateY(double translateY) {
    this.translateY = translateY;
  }

  /**
   * Setter for <code>SceneObject.translateZ</code>
   *
   * @param translateZ <code>double</code>
   * @return void
   */
  private void setTranslateZ(double translateZ) {
    this.translateZ = translateZ;
  }

  // Getters

  /**
   * Getter for <code>SceneObject.color</code>
   *
   * @return color <code>Color</code>
   */
  protected Color getColor() {
    return this.color;
  }

  /**
   * Getter for <code>SceneObject.colorArray</code>
   *
   * @return colorArray <code>double[][]</code>
   */
  protected double[][] getColorArray() {
    return this.colorArray;
  }

  /**
   * Getter for <code>SceneObject.scale</code>
   *
   * @return scale <code>double</code>
   */
  protected double getScale() {
    return this.scale;
  }

  /**
   * Getter for <code>SceneObject.translateX</code>
   *
   * @return translateX <code>double</code>
   */
  protected double getTranslateX() {
    return this.translateX;
  }

  /**
   * Getter for <code>SceneObject.translateY</code>
   *
   * @return translateY <code>double</code>
   */
  protected double getTranslateY() {
    return this.translateY;
  }

  /**
   * Getter for <code>SceneObject.translateZ</code>
   *
   * @return translateZ <code>double</code>
   */
  protected double getTranslateZ() {
    return this.translateZ;
  }

  // Utility methods

  /**
   * This is the primary method used to draw/render/paint the objects that appear in the GUI scene.
   * After caching <code>SceneObject.colorArray</code> and <code>SceneObject.scale</code> values
   * among others so that excess calls to accessors are minimized, the program clones and creates a
   * new <code>GL2</code> matrix on top and applies the <code>SceneObject</code>'s own scaling and
   * translation values to it before iterating through the object's faces. The utility method
   * <code>SceneObject.drawShape</code> is used expressly within this iterative <code>for</code>
   * loop-driven approach to draw black borders and fill in the space between with the selected hue.
   * <br />
   * <br />
   * The author debated the inclusion of a second cloned copy of the matrix in the <code>for</code>
   * loop for each face instance, simply for the purposes of keeping manipulations separate from the
   * main matrix copy in this method. After some waffling, he eventually decided to include this
   * functionality based on the fact that <code>UnlitCube.java</code> made use of such matrix copies
   * for each <code>UnlitCube.square</code> face assembled.
   *
   * @see <code>UnlitCube.java</code>
   * @see <a href="https://stackoverflow.com/a/23971843">Relevant SO Thread</a>
   * @param gl2 <code>GL2</code> instance, from <code>ScenePanel.SceneGLEventListener</code>
   * @return void
   */
  protected void constructObject(GL2 gl2) {

    // Declarations
    double tempScale;
    double[][] tempColorArray;
    int[][] tempFaces;
    double[] arrayColorBlack;

    // Definitions (cache values)
    tempScale = this.getScale();
    tempColorArray = this.getColorArray();
    tempFaces = this.getFaces();
    arrayColorBlack = this.convertColorToDoubleArray(Color.BLACK);

    // Push new matrix to the top
    gl2.glPushMatrix();

    // Apply default scaling to this matrix
    gl2.glScaled(tempScale, tempScale, tempScale);

    // Apply translation to matrix using object's provided translation coordinates
    gl2.glTranslated(this.getTranslateX(), this.getTranslateY(), this.getTranslateZ());

    // Iterate through the object's faces
    for (int i = 0; i < tempFaces.length; i++) {

      // New matrix copy on top for each face (as per UnlitCube.cube)
      gl2.glPushMatrix();

      // Draw the faces (GL2.GL_TRIANGLE_FAN colors triangle primative slices between vertices)
      this.drawShape(gl2, tempFaces, tempColorArray[i], GL2.GL_TRIANGLE_FAN, i);

      // Draw black borders between vertices
      this.drawShape(gl2, tempFaces, arrayColorBlack, GL2.GL_LINE_LOOP, i);

      // Remove face matrix copy
      gl2.glPopMatrix();
    }

    // Delete this master matrix copy/restore to original matrix copy
    gl2.glPopMatrix();
  }

  /**
   * This method is <code>SceneObject.constructObject</code>'s helper method, used to actually
   * connect the vertices and either paint a line between them or paint the spaces that lie therein
   * (the faces). It applies the desired color via <code>GL2.glColor3d</code>, providing the rgb
   * aspects, before applying an immediate mode (either <code>GL2.GL_TRIANGLE_FAN</code> for
   * triangular primitive strips or <code>GL2.GL_LINE_LOOP</code> for black borders) and beginning
   * the assembly of the shape in question via connection of vertices.
   *
   * @see <a href="//polaris.umuc.edu/~jroberts/CMSC405/c3/s1.html">Reading on immediate modes</a>
   * @param gl2 <code>GL2</code> from <code>ScenePanel.SceneGLEventListener</code>
   * @param faces <code>int[][]</code> cached value from <code>SceneObject.constructObject</code>
   * @param array <code>double[]</code> cached value from <code>SceneObject.constructObject</code>
   * @param immediateMode <code>int</code>
   * @param counter <code>int</code>
   * @return void
   */
  private void drawShape(GL2 gl2, int[][] faces, double[] array, int immediateMode, int counter) {

    // Declarations
    int vertex;
    double[][] tempVertices;

    // Definition (cache)
    tempVertices = this.getVertices();

    // Set OpenGL float color values for red, green, blue
    gl2.glColor3d(array[0], array[1], array[2]);

    // Provide mode (GL2.GL_TRIANGLE_FAN suggested by UnlitCube.java for faces)
    gl2.glBegin(immediateMode);

    // Build vertices
    for (int i = 0; i < faces[counter].length; i++) {
      vertex = faces[counter][i];
      gl2.glVertex3dv(tempVertices[vertex], 0);
    }

    // Complete primitive assembly
    gl2.glEnd();
  }

  /**
   * The author is admittedly quite proud of this method's central idea. Making use of some utility
   * methods contained within the <code>Color</code> class, this method takes the shape's assigned
   * color hue (a <code>Color</code> instance) and creates a two-dimensional <code>double</code>
   * array for use in coloring the shape's faces. However, rather than cover the entire shape with
   * the same color, the <code>Color.darker</code> and <code>Color.brighter</code> methods are used
   * to add some variance to the colors used, inserting slightly lighter and darker hues of the
   * chosen color into the array to paint the front and back in different shades. In the absence of
   * shadows and lighting, this can make the scene come alive. The face facing the camera at the
   * start (the "front face") is lighter than the standard hue while the side facing away at the
   * start is darker to simulate shadows falling away from the imagined light source.
   * <br />
   * <br />
   * Not only did this method help add some zing to the scene objects, it also made the author's
   * life significantly easier, as it removed the previous need to hand-create a two-dimensional
   * <code>double</code> array of colors by automating the process. The color array is only created
   * once, during the object's initialization at the program start.
   *
   * @return array <code>double[][]</code>
   */
  private double[][] buildColorArray() {

    // Declarations
    int numberFaces;
    double[][] array;
    Color base, brighter, darker;
    double[] baseArray, brighterArray, darkerArray;

    // Definitions
    numberFaces = this.getFaces().length;
    array = new double[numberFaces][3];

    // Color definitions
    base = this.getColor();
    brighter = base.brighter();
    darker = base.darker();

    // Color array definitions
    baseArray = this.convertColorToDoubleArray(base);
    brighterArray = this.convertColorToDoubleArray(brighter);
    darkerArray = this.convertColorToDoubleArray(darker);

    // First and last faces should be hue'd (is that a verb?) differently
    for (int i = 0; i < numberFaces; i++) {
      if (i == 0) { // Camera-facing side is brighter
        array[i] = brighterArray;
      } else if (i + 1 == numberFaces) { // Furthest side is darker
        array[i] = darkerArray;
      } else {
        array[i] = baseArray;
      }
    }

    return array;
  }

  /**
   * This helper method is simply used to convert an input <code>Color</code> instance to an Open GL
   * float color value, with each rgb aspect a <code>double</code> lying between <code>0.0</code>
   * and <code>1.0</code>, as per the Week 4 reading.
   *
   * @param color <code>Color</code> instance
   * @return <code>double[][]</code>
   */
  private double[] convertColorToDoubleArray(Color color) {
    return new double[] {color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0};
  }

  // Abstract methods

  /**
   * Used to return a pre-built, hand-assembled two-dimensional <code>double</code> array containing
   * <code>double</code> arrays of three-dimensional vertices of the shape in question. These
   * vertices are used to define the shape of the object and permit the assembly and painting of all
   * associated element faces.
   *
   * @return <code>double[][]</code>
   */
  abstract protected double[][] getVertices();

  /**
   * Used to return a pre-built, hand-assembled two-dimensional <code>int</code> array containing
   * <code>int</code> arrays denoting which vertices comprise which sides/faces of the shape. These
   * faces are then colored by an identically-sized <code>double[][]</code> array of
   * <code>Color</code>s used to paint the object.
   *
   * @return <code>int[][]</code>
   */
  abstract protected int[][] getFaces();
}