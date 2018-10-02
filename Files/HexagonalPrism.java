/**
 * HexagonalPrism.java - <code>SceneObject</code>-extending class defining hexagonal cylinder shape
 * Begun 09/07/18
 * @author Andrew Eissen
 */
package graphicsprojecttwo;

import java.awt.Color;

/**
 * This class extends <code>SceneObject</code> and defines the hexagonal cylinder shape.
 *
 * @see graphicsprojecttwo.SceneObject
 * @author Andrew Eissen
 */
final class HexagonalPrism extends SceneObject {

  /**
   * Parameterized constructor
   *
   * @param color <code>Color</code>
   * @param scale <code>double</code>
   * @param translateX <code>double</code>
   * @param translateY <code>double</code>
   * @param translateZ <code>double</code>
   */
  protected HexagonalPrism(Color color, double scale, double translateX, double translateY,
      double translateZ) {

    super(color, scale, translateX, translateY, translateZ);
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Vertex 0: left-bottom-front
   * Vertex 1: right-bottom-front
   * Vertex 2: right-front
   * Vertex 3: right-top-front
   * Vertex 4: left-top-front
   * Vertex 5: left-front
   * Vertex 6: left-bottom-back
   * Vertex 7: right-bottom-back
   * Vertex 8: right-back
   * Vertex 9: right-top-back
   * Vertex 10: left-top-back
   * Vertex 11: left-back
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected double[][] getVertices() {
    return new double[][] {
      {-0.5, -1.0, 1.0},
      {0.5, -1.0, 1.0},
      {1.0, 0.0, 1.0},
      {0.5, 1.0, 1.0},
      {-0.5, 1.0, 1.0},
      {-1.0, 0.0, 1.0},
      {-0.5, -1.0, -1.0},
      {0.5, -1.0, -1.0},
      {1.0, 0.0, -1.0},
      {0.5, 1.0, -1.0},
      {-0.5, 1.0, -1.0},
      {-1.0, 0.0, -1.0}
    };
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Face 0: front
   * Face 1: top
   * Face 2: left-top
   * Face 3: left-bottom
   * Face 4: bottom
   * Face 5: right-bottom
   * Face 6: right-top
   * Face 7: back
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected int[][] getFaces() {
    return new int[][] {
      {0, 1, 2, 3, 4, 5},
      {4, 3, 9, 10},
      {5, 4, 10, 11},
      {0, 5, 11, 6},
      {1, 0, 6, 7},
      {2, 1, 7, 8},
      {3, 2, 8, 9},
      {8, 7, 6, 11, 10, 9}
    };
  }
}