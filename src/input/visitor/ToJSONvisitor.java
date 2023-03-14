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
	    sb.append("{\n")
	            .append("\"type\": \"figure\",\n")
	            .append("\"description\": \"").append(node.getDescription()).append("\",\n")
	            .append("\"pointDatabase\": ").append(node.getPointsDatabase().accept((ComponentNodeVisitor) this, new StringBuilder())).append(",\n")
	            .append("\"segmentDatabase\": ").append(node.getSegments().accept((ComponentNodeVisitor) this, new StringBuilder())).append("\n")
	            .append("}");
	    return sb;
	}

	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
	    StringBuilder sb = (StringBuilder) o;
	    sb.append("{\n")
	            .append("\"type\": \"segmentDatabase\",\n")
	            .append("\"segments\": [\n");
	    List<SegmentNode> segments = node.getSegments();
	    for (int i = 0; i < segments.size(); i++) {
	        SegmentNode segmentNode = segments.get(i);
	        sb.append(segmentNode.accept((ComponentNodeVisitor) this, new StringBuilder()));
	        if (i < segments.size() - 1) {
	            sb.append(",\n");
	        }
	    }
	    sb.append("\n]\n")
	            .append("}");
	    return sb;
	}

	public Object visitSegmentNode(SegmentNode node, Object o) {
	    StringBuilder sb = (StringBuilder) o;
	    sb.append("{\n")
	            .append("\"type\": \"segment\",\n")
	            .append("\"startPoint\": \"").append(node.getPoint1().getName()).append("\",\n")
	            .append("\"endPoint\": \"").append(node.getPoint2().getName()).append("\"\n")
	            .append("}");
	    return sb;
	}


	public Object visitPointNode(PointNode node, Object o) {
	    StringBuilder sb = (StringBuilder) o;
	    sb.append("{\n")
	            .append("\"type\": \"point\",\n")
	            .append("\"name\": \"").append(node.getName()).append("\",\n")
	            .append("\"x\": ").append(node.getX()).append(",\n")
	            .append("\"y\": ").append(node.getY()).append("\n")
	            .append("}");
	    return sb;
	}

	
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
	    StringBuilder sb = (StringBuilder) o;
	    sb.append("{\n")
	            .append("\"type\": \"pointDatabase\",\n")
	            .append("\"points\": [\n");
	    List<PointNode> points = node.getPoints();
	    for (int i = 0; i < points.size(); i++) {
	        PointNode pointNode = points.get(i);
	        sb.append(pointNode.accept((ComponentNodeVisitor) this, new StringBuilder()));
	        if (i < points.size() - 1) {
	            sb.append(",\n");
	        }
	    }
	    sb.append("\n]\n")
	            .append("}");
	    return sb;
	}


}
