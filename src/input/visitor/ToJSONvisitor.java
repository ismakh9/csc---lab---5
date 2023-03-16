package input.visitor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;

import org.json.*;
public class ToJSONvisitor implements ComponentNodeVisitor {
	
	public Object visitFigureNode(FigureNode node, Object o) {
		JSONObject j = (JSONObject) o;
		
		JSONObject figure = new JSONObject();
		
		figure.put("Description", node.getDescription());
		
		JSONArray points = new JSONArray();
		this.visitPointNodeDatabase(node.getPointsDatabase(), points);
		figure.put("Points", points);
		
		JSONArray segments = new JSONArray();
		this.visitSegmentDatabaseNode(node.getSegments(), segments);
		figure.put("Segments", segments);
		
		j.put("Figure", figure);
		
		return j;
	}
	
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
		JSONArray segments = (JSONArray) o;
		
		Set<String> names = new HashSet();
		ArrayList<JSONArray> adjacencies = new ArrayList<JSONArray>();
		
		List<SegmentNode> segmentList = node.asSegmentList();
		
		for (SegmentNode segment : segmentList) {
			if (names.add(segment.getPoint1().getName())) {
				JSONArray arr = new JSONArray();
				arr.put(segment.getPoint2().getName());
				adjacencies.add(arr);
			}
			else {
				adjacencies.get(adjacencies.size()-1).put(segment.getPoint2().getName());
			}
		}
		
		int i = 0;
		for (String name : names) {
			JSONObject pair = new JSONObject();
			pair.put(name, adjacencies.get(i++));
			segments.put(pair);
		}
		
		
		return segments;
	}
	
	/**
	 * This method should NOT be called since the segment database
	 * uses the Adjacency list representation
	 */
	@Override
	public Object visitSegmentNode(SegmentNode node, Object o)
	{
		throw new ParseException("visitSegmentNode should not be called");
		/* JSONArray segments = (JSONArray) o;
		
		JSONObject segment = new JSONObject();
		segment.put(node.getPoint1().getName(), node.getPoint2().getName());
		
		segments.put(segment);
		
		return segments;
		
		*/
	}
	
	public Object visitPointNode(PointNode node, Object o) {
		JSONArray points = (JSONArray) o;
		
		JSONObject point = new JSONObject();
		point.put("name", node.getName());
		point.put("x", node.getX());
		point.put("y", node.getY());
		
		points.put(point);
		
		return points;
	}
	
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
		JSONArray points = (JSONArray) o;
		
		for (PointNode point : node.getPoints()) {
			this.visitPointNode(point, points);
		}
		
		return points;
	}
	
}