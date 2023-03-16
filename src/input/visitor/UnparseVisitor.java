/**
 * This file implements a Visitor (design pattern) with 
 * the intent of building an unparsed, String representation
 * of a geometry figure.
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author Josh Berger, Grace Houser, Khalid Ismail
 * @date Friday, March 17th, 2023
 */
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


public class UnparseVisitor implements ComponentNodeVisitor
{
	@Override
	public Object visitFigureNode(FigureNode node, Object o)
	{
		// Unpack the input object containing a StringBuilder and an indentation level
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		// Append the figure declaration line
		sb.append(StringUtilities.indent(level + 1) + node.getDescription() + "\n");

		// Unparse the points
		visitPointNodeDatabase(node.getPointsDatabase(), o);

		// Unparse the segments
		visitSegmentDatabaseNode(node.getSegments(), o);

		return sb;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o)
	{
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		List<SegmentNode> segments = node.getSegments();

		for (SegmentNode segment : segments) {
			sb.append(StringUtilities.indent(level + 2) + segment.getPoint1().getName() + " : ");
			if (!segment.getPoint2().equals(segment.getPoint2())) {
				sb.append(segment.getPoint2().getName() + " ");
			}

			sb.append("\n");
			segment.accept(this, new AbstractMap.SimpleEntry<>(sb, level + 1));
		}
		return sb;
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
			sb.append(visitPointNode(pointName, o));
			pointNode.accept(this, new AbstractMap.SimpleEntry<>(sb, level));
		}

		return sb;
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
		return sb;
	}

}