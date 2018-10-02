/**
 * TriangularPrism.java - <code>SceneObject</code>-extending class defining triangle cylinder shape
 * Begun 09/07/18
 * @author Andrew Eissen
 */
package graphicsprojecttwo;

import java.awt.Color;

/**
 * This class extends <code>SceneObject</code> and defines the triangular cylinder shape.
 *
 * @see graphicsprojecttwo.SceneObject
 * @author Andrew Eissen
 */
final class TriangularPrism extends SceneObject {

  /**
   * Parameterized constructor
   *
   * @param color <code>Color</code>
   * @param scale <code>double</code>
   * @param translateX <code>double</code>
   * @param translateY <code>double</code>
   * @param translateZ <code>double</code>
   */
  protected TriangularPrism(Color color, double scale, double translateX, double translateY,
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
   * Vertex 4: top-back
   * Vertex 5: bottom-back
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected double[][] getVertices() {
    return new double[][] {
      {-1.0, -1.0, 1.0},
      {1.0, -1.0, 1.0},
      {1.0, 1.0, 1.0},
      {-1.0, 1.0, 1.0},
      {0, 1.0, -1.0},
      {0, -1.0, -1.0}
    };
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Face 0: front
   * Face 1: right
   * Face 2: left
   * Face 3: bottom
   * Face 4: top
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected int[][] getFaces() {
    return new int[][] {
      {0, 1, 2, 3},
      {1, 5, 4, 2},
      {0, 3, 4, 5},
      {5, 1, 0},
      {4, 3, 2}
    };
  }
}