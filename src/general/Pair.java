/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

/**
 * An arbitrary pair of two objects.
 *
 * @author bendy
 * @param <leftType> type for left value
 * @param <rightType> type for right value
 */
public class Pair<leftType, rightType> {

	/**
	 * left value
	 */
	public leftType left;
	/**
	 * right value
	 */
	public rightType right;

	/**
	 * constructs new pair with given right and left values
	 *
	 * @param left left value
	 * @param right right value
	 */
	public Pair(leftType left, rightType right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * returns left value
	 *
	 * @return left value
	 */
	public leftType getLeft() {
		return left;
	}

	/**
	 * returns right value
	 *
	 * @return right value
	 */
	public rightType getRight() {
		return right;
	}
}
