/**
 * UnparseVisitor unparses an object by visiting it. 
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author Josh Berger, Khalid Ismail, Grace Houser
 * @date Friday, March 17th, 2023
 */
package input.visitor;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
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
		// Unpack the input object containing a StringBuilder and an indentation level
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();
		// Append the figure declaration line
		sb.append("Figure:\n");
		sb.append(StringUtilities.indent(level + 1) + "Description:" + node.getDescription() + "\n");


		// Unparse the points
		//node.getPointsDatabase().accept(this, o);
		visitPointNodeDatabase(node.getPointsDatabase(), o);

		// Unparse the segments
		visitSegmentDatabaseNode(node.getSegments(), o);

		return sb;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		sb.append(StringUtilities.indent(level + 1) + "Segments:\n");
		//create hashmap that stores the endpoints that are adjacent to the start point
		Map<PointNode, List<SegmentNode>> segmentsByEndpoint = new HashMap<>();
		for (SegmentNode segment : node.getSegments()) {
			PointNode endpoint = segment.getPoint1();
			//check if hashmap doesn't contains a canonical element
			if (!segmentsByEndpoint.containsKey(endpoint)) {
				//if not then crate new new hashmap to store it
				segmentsByEndpoint.put(endpoint, new ArrayList<>());
			}
			//add the adjacent elements
			segmentsByEndpoint.get(endpoint).add(segment);
		}
		//Traverse through the hashmap
		for (Map.Entry<PointNode, List<SegmentNode>> entry : segmentsByEndpoint.entrySet()) {
			PointNode endpoint = entry.getKey();
			// populate the string sb with the canonical 
			sb.append(StringUtilities.indent(level + 2) + endpoint.getName() + " : ");

			for (PointNode adjacent : node.getAdjacentPoints(endpoint)) {
				//add the adjacent elements to the sb
				sb.append(adjacent.getName() + " ");
			}
			sb.append("\n");
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

		sb.append(StringUtilities.indent(level + 1) + "Points:\n");
		for (PointNode point : node.getPoints()) {
			visitPointNode(point, o);
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
		sb.append(StringUtilities.indent(level+2));
		sb.append( "Point(" +node.getName() + ")" + " ");
		sb.append("(" + node.getX() + ", " + node.getY() + ")\n");
		return sb;
	}

}