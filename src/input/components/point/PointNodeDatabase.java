/**
 * PointNodeDatabase holds a Set of PointNodes. 
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author Abby Dumke, Alex Gardner, Grace Houser
 * @date Friday, January 27th, 2023
 */

package input.components.point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import input.components.ComponentNode;
import input.visitor.ComponentNodeVisitor;
import utilities.io.StringUtilities;
import utilities.math.MathUtilities;




public class PointNodeDatabase implements ComponentNode {

	protected Set<PointNode> _points;

	public PointNodeDatabase() {
		_points = new HashSet<PointNode>();
	}

	public PointNodeDatabase(List<PointNode> pNL) {	
		_points = new HashSet<PointNode>(pNL);
	}


	public void put(PointNode pN) {	
		_points.add(pN);
	}


	public boolean contains(PointNode pN) {
		return _points.contains(pN);
	}


	//public boolean contains(double x, double y) {
	//	
	//	for(PointNode point : _points) {
	//		if ((MathUtilities.doubleEquals(x, point.getX()) && 
	//			(MathUtilities.doubleEquals(point.getY(),y)))) {
	//			return true;
	//		}	
	//	}
	//	return false;
	//}
	//
	//
	//public String getName(PointNode pN) {return pN.getName();}
	//
	//
	//public String getName(double x, double y) {
	//	
	//	// searching _points for the (x, y) point and returns the object's name 
	//	for(PointNode point : _points) {
	//		if (MathUtilities.doubleEquals(x, point.getX()) && 
	//				(MathUtilities.doubleEquals(point.getY(),y))) {
	//			return point.getName();
	//		}
	//	}
	//	return null;
	//}
	//
	//
	public PointNode getPoint(PointNode pN) {return getPoint(pN.getX(),pN.getY());}


	public PointNode getPoint(double x, double y) {
	//	
	//	// searching _points for the (x, y) point and returns the point object 
		for (PointNode point : _points) {
			if (MathUtilities.doubleEquals(x, point.getX()) &&
					(MathUtilities.doubleEquals(point.getY(),y))) {
				return point;
			}
		}
		return null;
	}

	public List<PointNode> getPoints() {
		List<PointNode> points = new ArrayList<PointNode>();

		for(PointNode point : _points) {
			points.add(point);
		}

		return points;
	}
	public PointNode getPoint(String name) {
	    for (PointNode point : _points) {
	        if (point.getName().equals(name)) {
	            return point;
	        }
	    }
	    return null;
	}

	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitPointNodeDatabase(this, o);
	}


}
