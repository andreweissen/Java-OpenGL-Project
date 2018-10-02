/**
 * Star.java - <code>SceneObject</code>-extending class defining star shape
 * Begun 09/07/18
 * @author Andrew Eissen
 */
package graphicsprojecttwo;

import java.awt.Color;

/**
 * This class extends <code>SceneObject</code> and defines the star shape.
 *
 * @see graphicsprojecttwo.SceneObject
 * @author Andrew Eissen
 */
final class Star extends SceneObject {

  /**
   * Parameterized constructor
   *
   * @param color <code>Color</code>
   * @param scale <code>double</code>
   * @param translateX <code>double</code>
   * @param translateY <code>double</code>
   * @param translateZ <code>double</code>
   */
  protected Star(Color color, double scale, double translateX, double translateY,
      double translateZ) {

    super(color, scale, translateX, translateY, translateZ);
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Vertex 0: front-point
   * Vertex 1: left-point
   * Vertex 2: back-point
   * Vertex 3: right-point
   * Vertex 4: top-point
   * Vertex 5: bottom-point
   * Vertex 6: left-bottom-front-square
   * Vertex 7: right-bottom-front-square
   * Vertex 8: right-top-front-square
   * Vertex 9: left-top-front-square
   * Vertex 10: left-bottom-back-square
   * Vertex 11: right-bottom-back-square
   * Vertex 12: right-top-back-square
   * Vertex 13: left-top-back-square
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected double[][] getVertices() {
    return new double[][] {
      {0.0, 0.0, 1.0},
      {-1.0, 0.0, 0.0},
      {0.0, 0.0, -1.0},
      {1.0, 0.0, 0.0},
      {0.0, 1.0, 0.0},
      {0.0, -1.0, 0.0},
      {-0.2, -0.2, 0.2},
      {0.2, -0.2, 0.2},
      {0.2, 0.2, 0.2},
      {-0.2, 0.2, 0.2},
      {-0.2, -0.2, -0.2},
      {0.2, -0.2, -0.2},
      {0.2, 0.2, -0.2},
      {-0.2, 0.2, -0.2}
    };
  }

  /**
   * {@inheritDoc}
   * <pre>
   * Face 0: front-bottom
   * Face 1: front-right
   * Face 2: front-top
   * Face 3: front-left
   * Face 4: right-bottom
   * Face 5: right-right
   * Face 6: right-top
   * Face 7: right-left
   * Face 8: top-front
   * Face 9: top-right
   * Face 10: top-back
   * Face 11: top-left
   * Face 12: bottom-front
   * Face 13: bottom-right
   * Face 14: bottom-back
   * Face 15: bottom-left
   * Face 16: left-bottom
   * Face 17: left-right
   * Face 18: left-top
   * Face 19: left-left
   * Face 20: back-right
   * Face 21: back-top
   * Face 22: back-left
   * Face 23: back-bottom
   * </pre>
   *
   * @return {@inheritDoc}
   */
  @Override
  protected int[][] getFaces() {
    return new int[][] {
      {0, 6, 7},
      {0, 7, 8},
      {0, 8, 9},
      {0, 9, 6},
      {3, 7, 11},
      {3, 11, 12},
      {3, 12, 8},
      {3, 8, 7},
      {9, 8, 4},
      {8, 12, 4},
      {12, 13, 4},
      {13, 9, 4},
      {5, 7, 6},
      {5, 11, 7},
      {5, 10, 11},
      {5, 6, 10},
      {1, 10, 6},
      {1, 6, 9},
      {1, 9, 13},
      {1, 13, 10},
      {2, 12, 11},
      {2, 13, 12},
      {2, 10, 13},
      {2, 11, 10}
    };
  }
}