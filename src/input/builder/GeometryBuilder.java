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

   private PointNodeDatabase pointNodeDB;
   private SegmentNodeDatabase segmentNodeDB;
   private FigureNode figureNode;

   public GeometryBuilder() {
       pointNodeDB = new PointNodeDatabase();
       segmentNodeDB = new SegmentNodeDatabase();
   }

   public void buildPointNode(String name, double x, double y) {
       PointNode pointNode = new PointNode(name, x, y);
       pointNodeDB.put(pointNode);
   }

   public void buildSegmentNode(String startPointName, List<String> endPointNames) throws ParseException {
       // Retrieve the start point node from the database
       PointNode startPointNode = pointNodeDB.getPoint(startPointName);

       // If the start point could not be found, throw an exception
       if (startPointNode == null) {
           throw new ParseException("Invalid segment: " + startPointName);
       }

       // Iterate over the array of end point names and create edges
       for (String endPointName : endPointNames) {
           PointNode endPointNode = pointNodeDB.getPoint(endPointName);

           // If the end point could not be found, throw an exception
           if (endPointNode == null) {
               throw new ParseException("Invalid segment: " + endPointName);
           }
           segmentNodeDB.addUndirectedEdge(startPointNode, endPointNode);
       }
   }

   public ComponentNode buildFigureNode(String description) {
       return figureNode = new FigureNode(description, pointNodeDB, segmentNodeDB);
   }

   public PointNodeDatabase getPointNodeDatabase() {
       return pointNodeDB;
   }

   public SegmentNodeDatabase getSegmentNodeDatabase() {
       return segmentNodeDB;
   }

   public FigureNode getFigureNode() {
       return figureNode;
   }
 
}