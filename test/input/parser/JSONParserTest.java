package input.parser;


import static org.junit.jupiter.api.Assertions.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import input.builder.GeometryBuilder;
import input.components.ComponentNode;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;
import input.visitor.ToJSONvisitor;
import input.visitor.UnparseVisitor;
import utilities.io.FileUtilities;

class JSONParserTest
{
	public static ComponentNode runFigureParseTest(String filename)
	{
		JSONParser parser = new JSONParser();

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

		return parser.parse(figureStr);
		
	}
	
	
	
	//unparse
	@Test
	void crossing_symmetric_triangle_test()
	{
		
		ComponentNode node = JSONParserTest.runFigureParseTest("crossing_symmetric_triangle.json");

		assertTrue(node instanceof FigureNode);
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		

		StringBuilder sb = new StringBuilder();
		 // visit the figure node with the unparse visitor
        AbstractMap.SimpleEntry<StringBuilder, Integer> input = new AbstractMap.SimpleEntry<>(sb, 0);
        node.accept(unparseVisitor, input);

        // print the result
        System.out.println(sb.toString());

	}
	@Test
	public void testToJSONvisitor() {
        // Create a FigureNode with some points and segments
		ComponentNode node = JSONParserTest.runFigureParseTest("crossing_symmetric_triangle.json");
		GeometryBuilder db = new GeometryBuilder();
		
		FigureNode figure = (FigureNode) node;
		FigureNode figBuilt = db.buildFigureNode(figure.getDescription(), figure.getPointsDatabase(),figure.getSegments());

        // Create a ToJSONvisitor and visit the FigureNode
        ToJSONvisitor visitor = new ToJSONvisitor();
        JSONObject jsonObject = (JSONObject) visitor.visitFigureNode(figBuilt, 0);

        // Check that the JSON object is correct
        assertEquals("Figure", jsonObject.getString("type"));
        assertEquals("Crossing symmetric triangle construction.", jsonObject.getString("description"));
        assertEquals(5, jsonObject.getJSONObject("pointDatabase").getJSONArray("points").length());
        assertEquals(16, jsonObject.getJSONObject("segmentDatabase").getJSONArray("segments").length());
    }
	
	@Test
	void collinear_line_segments_test()
	{
		
		ComponentNode node = JSONParserTest.runFigureParseTest("collinear_line_segments.json");

		assertTrue(node instanceof FigureNode);
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		

		StringBuilder sb = new StringBuilder();
		 // visit the figure node with the unparse visitor
        AbstractMap.SimpleEntry<StringBuilder, Integer> input = new AbstractMap.SimpleEntry<>(sb, 0);
        node.accept(unparseVisitor, input);

        // print the result
        System.out.println(sb.toString());

	}
	
	@Test
	void fully_connected_irregular_polygon_test()
	{
		
		ComponentNode node = JSONParserTest.runFigureParseTest("fully_connected_irregular_polygon.json");

		assertTrue(node instanceof FigureNode);
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		

		StringBuilder sb = new StringBuilder();
		 // visit the figure node with the unparse visitor
        AbstractMap.SimpleEntry<StringBuilder, Integer> input = new AbstractMap.SimpleEntry<>(sb, 0);
        node.accept(unparseVisitor, input);

        // print the result
        System.out.println(sb.toString());

	}
	
	@Test
	void square_shape_test()
	{
		
		ComponentNode node = JSONParserTest.runFigureParseTest("square_Shape.json");

		assertTrue(node instanceof FigureNode);
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		

		StringBuilder sb = new StringBuilder();
		 // visit the figure node with the unparse visitor
        AbstractMap.SimpleEntry<StringBuilder, Integer> input = new AbstractMap.SimpleEntry<>(sb, 0);
        node.accept(unparseVisitor, input);

        // print the result
        System.out.println(sb.toString());

	}
	
	@Test
	void octagon_shape_test()
	{
		
		ComponentNode node = JSONParserTest.runFigureParseTest("octagon.json");

		assertTrue(node instanceof FigureNode);
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		

		StringBuilder sb = new StringBuilder();
		 // visit the figure node with the unparse visitor
        AbstractMap.SimpleEntry<StringBuilder, Integer> input = new AbstractMap.SimpleEntry<>(sb, 0);
        node.accept(unparseVisitor, input);

        // print the result
        System.out.println(sb.toString());

	}
	
	@Test
	void star_test()
	{
		
		ComponentNode node = JSONParserTest.runFigureParseTest("star.json");

		assertTrue(node instanceof FigureNode);
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		

		StringBuilder sb = new StringBuilder();
		 // visit the figure node with the unparse visitor
        AbstractMap.SimpleEntry<StringBuilder, Integer> input = new AbstractMap.SimpleEntry<>(sb, 0);
        node.accept(unparseVisitor, input);

        // print the result
        System.out.println(sb.toString());
	}
	
	@Test
	void hex_tri_test()
	{
		
		ComponentNode node = JSONParserTest.runFigureParseTest("hex_tria.json");

		assertTrue(node instanceof FigureNode);
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		

		StringBuilder sb = new StringBuilder();
		 // visit the figure node with the unparse visitor
        AbstractMap.SimpleEntry<StringBuilder, Integer> input = new AbstractMap.SimpleEntry<>(sb, 0);
        node.accept(unparseVisitor, input);

        // print the result
        System.out.println(sb.toString());
        

	}
	
	

	@Test
	void crisscross_square_test() {
		ComponentNode node = JSONParserTest.runFigureParseTest("crisscross_square.json");
		
		assertTrue(node instanceof FigureNode);
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		
		StringBuilder sb = new StringBuilder();
		// visit the figure node with the unparse visitor
		AbstractMap.SimpleEntry<StringBuilder, Integer> input = new AbstractMap.SimpleEntry<>(sb, 0);
		node.accept(unparseVisitor,  input);
		
		// print the result
		System.out.println(sb.toString());
	}

}
