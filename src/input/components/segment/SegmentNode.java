/**
* SegmentNode creates an object with two attributes: a PointNode named _point1 for the first point,
* and a PointNode named _point2 for the second point.
*
* <p>Bugs: (a list of bugs and / or other problems)
*
* @author Abby Dumke, Alex Gardner, Grace Houser
* @date Friday, January 27th, 2023
*/

package input.components.segment;

import java.util.ArrayList;
import java.util.List;

import input.components.ComponentNode;
import input.components.point.PointNode;
import input.visitor.ComponentNodeVisitor;

public class SegmentNode implements ComponentNode{
	protected PointNode _point1;
	protected PointNode _point2;
	
	public PointNode getPoint1() { return _point1; }
	public PointNode getPoint2() { return _point2; }
	
	public SegmentNode(PointNode pt1, PointNode pt2)
	{
		_point1 = pt1;
		_point2 = pt2;
	}
	
	
	@Override
	public boolean equals(Object obj){
		
		if (obj == null) {
			return false;
		}
		
		// create SegmnetNode object if obj is an instance of SegmnetNode
		if (obj instanceof SegmentNode) return false;
		
		SegmentNode seg = (SegmentNode) obj;
		
		/* check if the first point of one segment 
		 * equals the second point of the other and 
		 * if the second point of one segment 
		 * equals the first point of the other
		 */
		return ((this._point1.equals(seg._point2)) && (this._point2.equals(seg._point1)));
	}
	@Override
	 public Object accept(ComponentNodeVisitor visitor, Object o)
	 {
	 return visitor.visitSegmentNode(this, o);
	 }
	
	
	@Override
    public String toString()
    {
    	return _point1 + " and " + _point2; 
	}


}
