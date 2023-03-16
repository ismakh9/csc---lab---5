/**
 * PointNode creates an object with three attributes: double _x for the x coordinate, double 
 * _y for the y coordinate, and String _name for the name of the PointNode. The default _name is 
 * the final String ANONYMOUS that holds the value of "__UNNAMED".
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author Abby Dumke, Alex Gardner, Grace Houser
 * @date Friday, January 27th, 2023
 */

package input.components.point;
import input.components.ComponentNode;
import input.visitor.ComponentNodeVisitor;
import utilities.math.MathUtilities;


public class PointNode implements ComponentNode {

	protected static final String ANONYMOUS = "__UNNAMED";

	protected double _x;
	public double getX() { return this._x; }

	protected double _y; 
	public double getY() { return this._y; }

	protected String _name; 

	public String getName() { return this._name; }

	/**
	 * Create a new Point with the specified coordinates.
	 * Names the new Point with the final String, ANONYMOUS
	 * @param x -- The X coordinate
	 * @param y -- The Y coordinate
	 */
	public PointNode(double x, double y)
	{
		_x = x;
		_y = y; 
		_name = ANONYMOUS;
	}

	/**
	 * Create a new Point with the specified coordinates.
	 * name input must be letters, numbers, or underscores 
	 * Invalid characters are replaced with an underscore 
	 * @param name -- The name of the point. (Assigned by the UI)
	 * @param x -- The X coordinate
	 * @param y -- The Y coordinate
	 */
	public PointNode(String name, double x, double y)
	{
		_x = x;
		_y = y;
		_name = validInput(name);
	}

	/* Checks if String input consists of underscores, letters, and numbers
	 * If null or empty, return the final String ANONYMOUS
	 * Otherwise, replace invalid characters with an underscore 
	 * 
	 * @param str
	 */
	public String validInput(String str) {

		// check if the given String was null or empty
		if ((str == null) || (str.isEmpty())) return ANONYMOUS;

		String newName = "";

		// iterates through name to make sure there are no special characters 
		for (int i=0; i<str.length(); i++) {
			int char_num = str.charAt(i);

			// if the character is an underscore, number, or letter, add the character to newName
			if ((char_num == 95) || 
					(48 <= char_num && char_num <= 57) || 
					(65 <= char_num && char_num <= 90) || 
					(97 <= char_num && char_num <= 122)) {
				newName += str.charAt(i);
			}

			// otherwise, add an underscore to newName
			else {
				newName += "_";
			}
		}
		return newName;
	}

	@Override
	public int hashCode()
	{
		return Double.valueOf(_x).hashCode() + Double.valueOf(_y).hashCode();
	}

	/**
	 * Compares this PointNode with a given Object 
	 * @param obj -- The Object that PointNode is compared with 
	 */
	//@Override
	//public boolean equals(Object obj)
	//{	
	//	// return false if the object is null
	//	if (obj == null) return false;
	//	
	//	// create PointNode object if obj is an instance of PointNode
	//	if (!(obj instanceof PointNode)) return false;
	//	PointNode point = (PointNode) obj;
	//	
	//	// check if x and y values are the same using epsilon
	//	return (MathUtilities.doubleEquals(this._x, point._x) && 
	//			MathUtilities.doubleEquals(this._y, point._y));
	//}

	@Override
	public String toString()
	{
		return _name + "(" + _x + ", " + _y + ")"; 
	}

	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o)
	{
		return visitor.visitPointNode(this, o);
	}
}