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
		JSONParser parser = new JSONParser(new GeometryBuilder());

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

		return parser.parse(figureStr);
		
	}
	
	private void unparseTest(String filename) {
		ComponentNode node = JSONParserTest.runFigureParseTest(filename);

		assertTrue(node instanceof FigureNode);
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		

		StringBuilder sb = new StringBuilder();
		 // visit the figure node with the unparse visitor
        AbstractMap.SimpleEntry<StringBuilder, Integer> input = new AbstractMap.SimpleEntry<>(sb, 0);
        node.accept(unparseVisitor, input);

        // print the result
        System.out.println(sb.toString());
	}
	
	
	
	//unparse
	@Test
	void crossing_symmetric_triangle_test()
	{
		unparseTest("crossing_symmetric_triangle.json");

	}
	@Test
	public void testToJSONvisitor() {
        // Create a FigureNode with some points and segments
		ComponentNode node = JSONParserTest.runFigureParseTest("crossing_symmetric_triangle.json");
		
		FigureNode figure = (FigureNode) node;

        JSONObject jsonObject = (JSONObject) node.accept(new ToJSONvisitor(), new JSONObject());
        
        System.out.println(jsonObject.toString(5));
    }
	
	@Test
	void collinear_line_segments_test()
	{
		unparseTest("collinear_line_segments.json");
	}
	
	@Test
	void fully_connected_irregular_polygon_test()
	{
		unparseTest("fully_connected_irregular_polygon.json");
	}
	
	@Test
	void square_shape_test()
	{
		unparseTest("square_Shape.json");
	}
	
	@Test
	void octagon_shape_test()
	{
		unparseTest("octagon.json");
	}
	
	@Test
	void star_test()
	{
		unparseTest("star.json");
	}
	
	@Test
	void hex_tri_test()
	{
		unparseTest("hex_tria.json");
	}
	
	

	@Test
	void crisscross_square_test() {
		unparseTest("crisscross_square.json");
	}

}
