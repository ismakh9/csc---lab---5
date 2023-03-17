package input.parser;


import static org.junit.jupiter.api.Assertions.*;

import java.util.AbstractMap;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import input.builder.DefaultBuilder;
import input.builder.GeometryBuilder;
import input.components.ComponentNode;
import input.components.FigureNode;
import input.visitor.ToJSONvisitor;
import input.visitor.UnparseVisitor;

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

		System.out.println("Unparsed version of " + filename);
        System.out.println(sb.toString() + "\n");
	}
	
	public static void toJSONVisitorTest(String filename) {
        // Create a FigureNode with some points and segments
		ComponentNode node = JSONParserTest.runFigureParseTest(filename);
		
		FigureNode figure = (FigureNode) node;

        JSONObject jsonObject = (JSONObject) node.accept(new ToJSONvisitor(), new JSONObject());
        
        System.out.println("Reconstructed JSON version of " + filename);
        System.out.println(jsonObject.toString(5) + "\n");
    }
	
	@Test
	void defaultBuilderTest() {
		JSONParser parser = new JSONParser(new DefaultBuilder());

		String figureStr = utilities.io.FileUtilities.readFileFilterComments("crossing_symmetric_triangle.json");

		FigureNode node = (FigureNode) parser.parse(figureStr);
		
		assertEquals(null, node);
	}
	
	@Test
	void crossing_symmetric_triangle_test()
	{
		unparseTest("crossing_symmetric_triangle.json");
		toJSONVisitorTest("crossing_symmetric_triangle.json");
	}
	
	@Test
	void collinear_line_segments_test()
	{
		unparseTest("collinear_line_segments.json");
		toJSONVisitorTest("collinear_line_segments.json");
	}

	@Test
	void fully_connected_irregular_polygon_test()
	{
		unparseTest("fully_connected_irregular_polygon.json");
		toJSONVisitorTest("fully_connected_irregular_polygon.json");
	}

	@Test
	void square_shape_test()
	{
		unparseTest("single_square.json");
		toJSONVisitorTest("single_square.json");
	}

	@Test
	void Snake_shape_test()
	{
		unparseTest("snake.json");
		toJSONVisitorTest("snake.json");
	}

	@Test
	void star_test()
	{
		unparseTest("star.json");
		toJSONVisitorTest("star.json");
	}

	@Test
	void hex_tri_test()
	{
		unparseTest("hex_tria.json");
		toJSONVisitorTest("hex_tria.json");
	}

	@Test
	void crisscross_square_test() {
		unparseTest("crisscross_square.json");
		toJSONVisitorTest("crisscross_square.json");
	}

}