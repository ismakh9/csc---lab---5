/**
 * SegmentNodeDatabase holds a Set of SegmentNodes. 
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author Abby Dumke, Alex Gardner, Grace Houser
 * @date Friday, January 27th, 2023
 */

package input.components.segment;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import input.components.ComponentNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.visitor.ComponentNodeVisitor;
import utilities.io.StringUtilities;

public class SegmentNodeDatabase implements ComponentNode {

	protected Map<PointNode, Set<PointNode>> _adjLists;

	public SegmentNodeDatabase() {
		_adjLists = new HashMap<PointNode, Set<PointNode>>();
	}

	public SegmentNodeDatabase(Map<PointNode, Set<PointNode>> _map) {
		_adjLists= new HashMap<PointNode, Set<PointNode>>(_map);
	}


	public int numUndirectedEdges() {

		// iterates through the values of _adjLists map and returns the size
		int numEdges = 0;

		for (Set<PointNode> adjacents : _adjLists.values()) {
			numEdges += adjacents.size();
		}
		return numEdges/2;
	}


	private void addDirectedEdge(PointNode pN1, PointNode pN2) {
		Set<PointNode> EdgePointSet = _adjLists.get(pN1);
		if (EdgePointSet == null) {
			EdgePointSet = new HashSet<PointNode>();
			EdgePointSet.add(pN2);
			_adjLists.put(pN1, EdgePointSet);
		}
		else {
			EdgePointSet.add(pN2);
		}

	}


	public void addUndirectedEdge(PointNode pN1, PointNode pN2) {
		addDirectedEdge(pN1, pN2);
		addDirectedEdge(pN2, pN1);

	}


	public void addAdjacencyList(PointNode pN, List<PointNode> pNList) {

		Set<PointNode> adjacentPoints = _adjLists.get(pN);
		if (adjacentPoints != null) {
			for(PointNode point : adjacentPoints ) {
				pNList.add(point);	
			}
		}
	}


	public List<SegmentNode> asSegmentList() {
		//could be a set so you dont have to check containment
		List<SegmentNode> segments = new ArrayList<SegmentNode>();
		List<PointNode> keys = new ArrayList<PointNode>();

		for (Entry<PointNode, Set<PointNode>> entry : _adjLists.entrySet()) { 

			keys.add(entry.getKey());

			for (PointNode p : entry.getValue()) {

				if (!keys.contains(p)) {

					segments.add(new SegmentNode(entry.getKey(),p));

				}

			}

		}

		return segments;

	}


	public List<SegmentNode> asUniqueSegmentList(){

		//System.out.println("testing");

		// creates a new empty list and adds only unique values to the list.
		List<SegmentNode> segments = new ArrayList<>();
		for(PointNode point : _adjLists.keySet()) {
			for(PointNode adjacent : _adjLists.get(point)) {
				if(!(segments.contains(new SegmentNode(point,adjacent))
						|| (segments.contains(new SegmentNode(adjacent, point))))) {
					segments.add(new SegmentNode(point,adjacent));
				}
			}
		}
		return segments;
	}

	public List<SegmentNode> getSegments() {
		List<SegmentNode> segments = new ArrayList<SegmentNode>();
		Set<SegmentNode> visited = new HashSet<SegmentNode>();

		for (PointNode p : _adjLists.keySet()) {
			for (PointNode q : _adjLists.get(p)) {
				SegmentNode segment = new SegmentNode(p, q);
				if (!visited.contains(segment)) {
					segments.add(segment);
					visited.add(segment);
				}
			}
		}

		return segments;
	}
	
	public List<PointNode> getAdjacentPoints(PointNode point) {
	    List<PointNode> adjacentPoints = new ArrayList<>();
	    Set<PointNode> connectedPoints = _adjLists.get(point);
	    for (PointNode connectedPoint : connectedPoints) {
	        adjacentPoints.add(connectedPoint);
	    }
	    return adjacentPoints;
	}
	

	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitSegmentDatabaseNode(this, o);
	}

}



