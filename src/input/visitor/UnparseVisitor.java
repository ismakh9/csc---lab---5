package input.visitor;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import input.components.*;
import input.components.point.*;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;
import utilities.io.StringUtilities;

//
// This file implements a Visitor (design pattern) with 
// the intent of building an unparsed, String representation
// of a geometry figure.
//
public class UnparseVisitor implements ComponentNodeVisitor
{
	@Override
	public Object visitFigureNode(FigureNode node, Object o)
	{
		// Unpack the input object containing a Stringbuilder and an indentation level
		 @SuppressWarnings("unchecked")
	        AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
	        StringBuilder sb = pair.getKey();
	        int level = pair.getValue();
	        // Append the figure declaration line
	        sb.append(StringUtilities.indent(level + 1) + node.getDescription() + "\n");

	        
	     // Unparse the points
	        for (PointNode point : node.getPointsDatabase().getPoints()) {
	            sb.append(StringUtilities.indent(level + 2) +"point(" + point.getName() + ")" + " " + point.getX() + " " + point.getY() + "\n");
	        }

	        
	        // Unparse the segments
	        for (PointNode point : node.getPointsDatabase().getPoints()) {
	            sb.append(StringUtilities.indent(level + 2) + point.getName() + " : ");
	            for (PointNode endpoint : node.getPointsDatabase().getPoints()) {
	                if (!point.equals(endpoint)) {
	                    sb.append(endpoint.getName() + " ");
	                }
	            }
	            sb.append("\n");
	        }

	        return sb;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o)
	{
		@SuppressWarnings("unchecked")
        AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
        StringBuilder sb = pair.getKey();
        int level = pair.getValue();
        
        sb.append(StringUtilities.indent(level + 1) + "Segments:\n");
        List<SegmentNode> segments = node.getSegments();
        for (SegmentNode segment : segments) {
            segment.accept(this, new AbstractMap.SimpleEntry<>(sb, level + 1));
        }
	    return null;
	}

	/**
	 * This method should NOT be called since the segment database
	 * uses the Adjacency list representation
	 */
	@Override
	public Object visitSegmentNode(SegmentNode node, Object o)
	{
		throw new ParseException("visitSegmentNode should not be called");
	}

	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o)
	{
		@SuppressWarnings("unchecked")
        AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
        StringBuilder sb = pair.getKey();
        int level = pair.getValue();
        
        // Visit all sub-nodes of the point database node
        List<PointNode> pointNames = node.getPoints();
    	for (PointNode pointName : pointNames) {
    		PointNode pointNode = node.getPoint(pointName);
    		pointNode.accept(this, new AbstractMap.SimpleEntry<>(sb, level));
    	}

	    return null;
	}
	
	@Override
	public Object visitPointNode(PointNode node, Object o)
	{
		@SuppressWarnings("unchecked")
        AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
        StringBuilder sb = pair.getKey();
        int level = pair.getValue();

		// Unparse the points
        sb.append(StringUtilities.indent(level));
    	sb.append(node.getName() + " ");
    	sb.append("(" + node.getX() + ", " + node.getY() + ")\n");
    	return null;
	}

}