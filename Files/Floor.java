/**
 * Floor.java - <code>SceneObject</code>-extending class defining floor shape
 * Begun 09/07/18
 * @author Andrew Eissen
 */
package graphicsprojecttwo;

import java.awt.Color;

/**
 * This class extends <code>SceneObject</code> and defines the floor shape. As this and
 * <code>Cube</code> are both basically the same shape with some minor modifications, the author
 * counts them as one shape of the rubric-required six.
 *
 * @see graphicsprojecttwo.SceneObject
 * @author Andrew Eissen
 */
final class Floor extends SceneObject {

  /**
   * Parameterized constructor
   *
   * @param color <code>Color</code>
   * @param scale <code>double</code>
   * @param translateX <code>double</code>
   * @param translateY <code>double</code>
   * @param translateZ <code>double</code>
   */
  protected Floor(Color color, double scale, double translateX, double translateY,
      double translateZ) {

    super(color, scale, translateX, translateY, translateZ);
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Vertex 0: left-bottom-front
   * Vertex 1: right-bottom-front
   * Vertex 2: right-top-front
   * Vertex 3: left-top-front
   * Vertex 4: left-bottom-back
   * Vertex 5: right-bottom-back
   * Vertex 6: right-top-back
   * Vertex 7: left-top-back
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected double[][] getVertices() {
    return new double[][] {
      {-1.0, -0.1, 1.0},
      {1.0, -0.1, 1.0},
      {1.0, 0.1, 1.0},
      {-1.0, 0.1, 1.0},
      {-1.0, -0.1, -1.0},
      {1.0, -0.1, -1.0},
      {1.0, 0.1, -1.0},
      {-1.0, 0.1, -1.0}
    };
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Face 0: front
   * Face 1: top
   * Face 2: left
   * Face 3: bottom
   * Face 4: right
   * Face 5: back
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected int[][] getFaces() {
    return new int[][] {
      {0, 1, 2, 3},
      {3, 2, 6, 7},
      {0, 3, 7, 4},
      {1, 0, 4, 5},
      {2, 1, 5, 6},
      {6, 5, 4, 7}
    };
  }
}