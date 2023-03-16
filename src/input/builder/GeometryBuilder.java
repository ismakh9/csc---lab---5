/**
 * This file builds a geometry figure and extends DefaultBuilder. 
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author Josh Berger, Grace Houser, Khalid Ismail
 * @date Friday, March 17th, 2023
 */
package input.builder;

import java.util.List;

import input.components.ComponentNode;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;
import input.visitor.UnparseVisitor;

public class GeometryBuilder extends DefaultBuilder {

	public GeometryBuilder() { }
	
	public FigureNode buildFigureNode(String description, 
										PointNodeDatabase points, 
										SegmentNodeDatabase segments)
	{
		return new FigureNode(description, points, segments);
	}
	
	
	public SegmentNodeDatabase buildSegmentNodeDatabase() 
	{
		return new SegmentNodeDatabase();
	}
	
	
    public SegmentNode buildSegmentNode(PointNode pt1, PointNode pt2) 
    {
    	return new SegmentNode(pt1, pt2);
    }
    
    
    public PointNodeDatabase buildPointNodeDatabase(List<PointNode> points) 
    {
    	return new PointNodeDatabase(points);
    }
    
    
    public PointNode buildPointNode(String name, double x, double y) 
    {
    	return new PointNode(name, x, y);
    }
}