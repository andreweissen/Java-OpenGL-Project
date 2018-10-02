/**
 * TenSidedPolygon.java - <code>SceneObject</code>-extending class defining ten-sided polygon shape
 * Begun 09/07/18
 * @author Andrew Eissen
 */
package graphicsprojecttwo;

import java.awt.Color;

/**
 * This class extends <code>SceneObject</code> and defines the ten-sided polygon shape.
 *
 * @see graphicsprojecttwo.SceneObject
 * @author Andrew Eissen
 */
final class TenSidedPolygon extends SceneObject {

  /**
   * Parameterized constructor
   *
   * @param color <code>Color</code>
   * @param scale <code>double</code>
   * @param translateX <code>double</code>
   * @param translateY <code>double</code>
   * @param translateZ <code>double</code>
   */
  protected TenSidedPolygon(Color color, double scale, double translateX, double translateY,
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
   * Vertex 4: right-bottom-middle
   * Vertex 5: right-top-middle
   * Vertex 6: left-top-middle
   * Vertex 7: left-bottom-middle
   * Vertex 8: right-bottom-back
   * Vertex 9: right-top-back
   * Vertex 10: left-top-back
   * Vertex 11: left-bottom-back
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected double[][] getVertices() {
    return new double[][] {
      {-0.5, -0.5, 1.0},
      {0.5, -0.5, 1.0},
      {0.5, 0.5, 1.0},
      {-0.5, 0.5, 1.0},
      {1.0, -1.0, 0.0},
      {1.0, 1.0, 0.0},
      {-1.0, 1.0, 0.0},
      {-1.0, -1.0, 0.0},
      {0.5, -0.5, -1.0},
      {0.5, 0.5, -1.0},
      {-0.5, 0.5, -1.0},
      {-0.5, -0.5, -1.0}
    };
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Face 0: front
   * Face 1: top-front
   * Face 2: left-front
   * Face 3: bottom-front
   * Face 4: right-front
   * Face 5: middle
   * Face 6: top-back
   * Face 7: left-back
   * Face 8: back-bottom
   * Face 9: right-back
   * Face 10: top-back
   * Face 11: back
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected int[][] getFaces() {
    return new int[][] {
      {0, 1, 2, 3},
      {3, 2, 5, 6},
      {0, 3, 6, 7},
      {1, 0, 7, 4},
      {2, 1, 4, 5},
      {7, 4, 5, 6},
      {6, 5, 9, 10},
      {7, 6, 10, 11},
      {4, 7, 11, 8},
      {5, 4, 8, 9},
      {6, 5, 9, 10},
      {8, 11, 10, 9}
    };
  }
}