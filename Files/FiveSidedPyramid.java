/**
 * FiveSidedPyramid.java - <code>SceneObject</code>-extending class defining pyramid shape
 * Begun 09/07/18
 * @author Andrew Eissen
 */
package graphicsprojecttwo;

import java.awt.Color;

/**
 * This class extends <code>SceneObject</code> and defines the five-sided pyramid shape.
 *
 * @see graphicsprojecttwo.SceneObject
 * @author Andrew Eissen
 */
final class FiveSidedPyramid extends SceneObject {

  /**
   * Parameterized constructor
   *
   * @param color <code>Color</code>
   * @param scale <code>double</code>
   * @param translateX <code>double</code>
   * @param translateY <code>double</code>
   * @param translateZ <code>double</code>
   */
  protected FiveSidedPyramid(Color color, double scale, double translateX, double translateY,
      double translateZ) {

    super(color, scale, translateX, translateY, translateZ);
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Vertex 0: left-bottom-front
   * Vertex 1: right-bottom-front
   * Vertex 2: right-bottom-back
   * Vertex 3: left-bottom-back
   * Vertex 4: top
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected double[][] getVertices() {
    return new double[][] {
      {-1.0, -1.0, 1.0},
      {1.0, -1.0, 1.0},
      {1.0, -1.0, -1.0},
      {-1.0, -1.0, -1.0},
      {0.0, 1.0, 0.0}
    };
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Face 0: front
   * Face 1: right
   * Face 2: bottom
   * Face 3: left
   * Face 4: back
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected int[][] getFaces() {
    return new int[][] {
      {0, 1, 4},
      {1, 2, 4},
      {0, 3, 2, 1},
      {3, 0, 4},
      {2, 3, 4}
    };
  }
}