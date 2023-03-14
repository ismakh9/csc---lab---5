package input.visitor;

import java.util.List;

import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;

public class ToJSONvisitor {
	
	public Object visitFigureNode(FigureNode node, Object o) {
	    StringBuilder sb = (StringBuilder) o;
	    
	    sb.append("{\n");
	    sb.append("\"type\": \"figure\",\n");
	  
	    sb.append("\"description\": \"");
	    sb.append(node.getDescription());
	    sb.append("\",\n");
	    
	    sb.append("\"pointDatabase\": ")
	    sb.append(node.getPointsDatabase().accept((ComponentNodeVisitor) this, new StringBuilder()));
	    sb.append(",\n");
	            
	    sb.append("\"segmentDatabase\": ");
	    sb.append(node.getSegments().accept((ComponentNodeVisitor) this, new StringBuilder()));
	    sb.append("\n");
	    sb.append("}");
	    
	    return sb;
	}

	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
	    StringBuilder sb = (StringBuilder) o;
	    
	    sb.append("{\n");
	    sb.append("\"type\": \"segmentDatabase\",\n");
	    sb.append("\"segments\": [\n");
	    List<SegmentNode> segments = node.getSegments();
	    for (int i = 0; i < segments.size(); i++) {
	        SegmentNode segmentNode = segments.get(i);
	        sb.append(segmentNode.accept((ComponentNodeVisitor) this, new StringBuilder()));
	        if (i < segments.size() - 1) {
	            sb.append(",\n");
	        }
	    }
	    sb.append("\n]\n");
	    sb.append("}");
	    return sb;
	}

	public Object visitSegmentNode(SegmentNode node, Object o) {
	    StringBuilder sb = (StringBuilder) o;
	    
	    sb.append("{\n");
	    sb.append("\"type\": \"segment\",\n");
	    sb.append("\"startPoint\": \"");
	    sb.append(node.getPoint1().getName());
	    sb.append("\",\n");
	    sb.append("\"endPoint\": \"");
	    sb.append(node.getPoint2().getName());
	    sb.append("\"\n");
	    sb.append("}");
	    
	    return sb;
	}


	public Object visitPointNode(PointNode node, Object o) {
	    StringBuilder sb = (StringBuilder) o;
	    
	    sb.append("{\n");
	    sb.append("\"type\": \"point\",\n");
	    sb.append("\"name\": \"");
	    sb.append(node.getName());
	    sb.append("\",\n");
	    sb.append("\"x\": ");
	    sb.append(node.getX());
	    sb.append(",\n");
	    sb.append("\"y\": ");
	    sb.append(node.getY());
	    sb.append("\n");
	    sb.append("}");
	    
	    return sb;
	}

	
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
	    StringBuilder sb = (StringBuilder) o;
	    sb.append("{\n");
	    sb.append("\"type\": \"pointDatabase\",\n");
	    sb.append("\"points\": [\n");
	    List<PointNode> points = node.getPoints();
	    for (int i = 0; i < points.size(); i++) {
	        PointNode pointNode = points.get(i);
	        sb.append(pointNode.accept((ComponentNodeVisitor) this, new StringBuilder()));
	        if (i < points.size() - 1) {
	            sb.append(",\n");
	        }
	    }
	    sb.append("\n]\n");
	    sb.append("}");
	    return sb;
	}


}
