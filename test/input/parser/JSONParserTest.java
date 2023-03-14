package input.parser;


import static org.junit.jupiter.api.Assertions.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import input.builder.GeometryBuilder;
import input.components.ComponentNode;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;
import input.visitor.UnparseVisitor;
import utilities.io.FileUtilities;

class JSONParserTest
{
	public static ComponentNode runFigureParseTest(String filename)
	{
		GeometryBuilder builder = new GeometryBuilder();

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);
		return builder.buildFigureNode(figureStr);
	}
	
	private void figureTest(String filename) {
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
		figureTest("crossing_symmetric_triangle.json");
	}
	
	@Test
	void collinear_line_segments_test()
	{
		figureTest("collinear_line_segments.json");
	}
	
	@Test
	void fully_connected_irregular_polygon_test()
	{
		figureTest("fully_connected_irregular_polygon.json");
	}
	
	@Test
	void square_shape_test()
	{
		figureTest("square_Shape.json");
	}
	
	@Test
	void octagon_shape_test()
	{
		figureTest("octagon.json");
	}
	
	@Test
	void star_test()
	{
		figureTest("star.json");
	}
	
	@Test
	void hex_tri_test()
	{
		figureTest("hex_tria.json");
	}
	
	

	@Test
	void crisscross_square_test() {
		figureTest("crisscross_square.json");
	}

}
