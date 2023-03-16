package input.visitor;

import java.util.List;

import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import utilities.io.StringUtilities;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;

public class ToJSONvisitor implements ComponentNodeVisitor {

    @Override
    public Object visitFigureNode(FigureNode node, Object o) {
        JSONObject jsonObject = new JSONObject();
        //insert the all the tree stucture
        jsonObject.put("type", "Figure");
        jsonObject.put("description", node.getDescription());
        jsonObject.put("pointDatabase", node.getPointsDatabase().accept(this, new JSONObject()));
        jsonObject.put("segmentDatabase", node.getSegments().accept(this, new JSONArray()));
        return jsonObject;
    }

    @Override
    public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
        JSONArray jsonArray = new JSONArray();
        //traverse through segmentdatabase and insert it in the jsonArray
        List<SegmentNode> segments = node.getSegments();
        for (SegmentNode segmentNode : segments) {
            jsonArray.put(segmentNode.accept(this, new JSONObject()));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "segmentDatabase");
        //transfer the jsonArray to JsonObject
        jsonObject.put("segments", jsonArray);
        return jsonObject;
    }

    @Override
    public Object visitSegmentNode(SegmentNode node, Object o) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Segment");
        jsonObject.put("startPoint", node.getPoint1().getName());
        jsonObject.put("endPoint", node.getPoint2().getName());
        return jsonObject;
    }

    @Override
    public Object visitPointNode(PointNode node, Object o) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Point");
        jsonObject.put("name", node.getName());
        jsonObject.put("x", node.getX());
        jsonObject.put("y", node.getY());
        return jsonObject;
    }

    @Override
    public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
        JSONArray jsonArray = new JSONArray();
        List<PointNode> points = node.getPoints();
        for (PointNode pointNode : points) {
            jsonArray.put(pointNode.accept(this, new JSONObject()));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "pointDatabase");
        jsonObject.put("points", jsonArray);
        return jsonObject;
    }
}
