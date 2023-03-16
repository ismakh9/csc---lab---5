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

public class GeometryBuilder {

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
    
    public void addSegmentToDatabase(SegmentNodeDatabase segments, PointNode from, PointNode to)
    {
    	if (segments != null) segments.addUndirectedEdge(from, to);
    }
    
    public SegmentNode buildSegmentNode(PointNode pt1, PointNode pt2)
    {
        return new SegmentNode(pt1, pt2);
    }
    
    public PointNodeDatabase buildPointDatabaseNode(List<PointNode> points)
    {
    	 PointNodeDatabase pointNodeDB = new PointNodeDatabase();
         for (PointNode pointNode : points) {
             pointNodeDB.put(pointNode);
         }
         return pointNodeDB;
    }
    
    public PointNode buildPointNode(String name, double x, double y)
    {
    	return new PointNode(name, x, y);
    }
 
}