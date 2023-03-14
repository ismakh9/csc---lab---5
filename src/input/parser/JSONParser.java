package input.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;

public class JSONParser
{
	protected ComponentNode  _astRoot;

	public JSONParser()
	{
		_astRoot = null;
	}

	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}
	
	public static ComponentNode parse(String str) throws ParseException {
	    try {
	        // Parsing is accomplished via the JSONTokener class.
	        JSONTokener tokenizer = new JSONTokener(str);
	        JSONObject JSONroot = (JSONObject) tokenizer.nextValue();

	        // Check if the root object has a "Figure" key
	        if (!JSONroot.has("Figure")) {
	            throw new ParseException("Invalid JSON: missing 'Figure' object");
	        }

	        // Extracting figure object
	        JSONObject figureObj = JSONroot.getJSONObject("Figure");

	        // Extracting figure description
	        String description = figureObj.getString("Description");

	        // Extracting points
	        PointNodeDatabase pointNodeDB = pointNodeExtraction(figureObj);

	        // Extracting segments
	        SegmentNodeDatabase segmentNodeDB = segmentNodeExtraction(figureObj, pointNodeDB);

	        // Extracting figure
	        FigureNode figureNode = new FigureNode(description, pointNodeDB, segmentNodeDB);

	        return figureNode;

	    } catch (JSONException e) {
	        // If there was an error while parsing the JSON, throw a ParseException
	        throw new ParseException("Error parsing JSON: " + e.getMessage());
	    }
	}

	private static PointNodeDatabase pointNodeExtraction(JSONObject figureObj) throws JSONException {
		//copy the points into pointArray
	    JSONArray pointsArray = figureObj.getJSONArray("Points");
	    PointNodeDatabase pointNodeDB = new PointNodeDatabase();

	    // Iterate over each point object and add to the database
	    for (int i = 0; i < pointsArray.length(); i++) {
	    	//get the points at given index
	        JSONObject pointObj = pointsArray.getJSONObject(i);
	        String pointName = pointObj.getString("name");
	        //coordinates of each point
	        double pointX = pointObj.getDouble("x"); 
	        double pointY = pointObj.getDouble("y"); 
	        
	        PointNode pointNode = new PointNode(pointName, pointX, pointY);
	        //store the points in the point node database
	        pointNodeDB.put(pointNode);
	    }
	    return pointNodeDB;
	}

	private static SegmentNodeDatabase segmentNodeExtraction(JSONObject figureObj, PointNodeDatabase pointNodeDB) throws JSONException, ParseException {
		//copy the segments into segmentsarray
		JSONArray segmentsArray = figureObj.getJSONArray("Segments");
	    SegmentNodeDatabase segmentNodeDB = new SegmentNodeDatabase();

	    // Iterate over each segment object and add to the database
	    for (int i = 0; i < segmentsArray.length(); i++) {
	        JSONObject segmentObj = segmentsArray.getJSONObject(i);
	        //get the first point that represent the canonical
	        String startPointName = segmentObj.keys().next();
	        //leading points after the canonical
	        JSONArray endPointNames = segmentObj.getJSONArray(startPointName);

	        // Retrieve the start point node from the database
	        PointNode startPointNode = pointNodeDB.getPoint(startPointName);

	        // If the start point could not be found, throw an exception
	        if (startPointNode == null) {
	            throw new ParseException("Invalid segment: " + startPointName);
	        }

	        // Iterate over the array of end point names and create edges
	        for (int j = 0; j < endPointNames.length(); j++) {
	            String endPointName = endPointNames.getString(j);
	            PointNode endPointNode = pointNodeDB.getPoint(endPointName);

	            // If the end point could not be found, throw an exception
	            if (endPointNode == null) {
	                throw new ParseException("Invalid segment: " + endPointName);
	            }
	            //store the segments
	            segmentNodeDB.addUndirectedEdge(startPointNode, endPointNode);
	        }
	    }

	    return segmentNodeDB;
	}

}

	

	




