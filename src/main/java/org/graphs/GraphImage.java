package org.graphs;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import java.util.List;

public class GraphImage {

    public static void showMST(org.graphs.Graph g, List<org.graphs.Edge> mstEdges, String filename) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph gsGraph = new SingleGraph("Graph");

        for (String name : g.names) {
            gsGraph.addNode(name).setAttribute("ui.label", name);
        }

        for (int v = 0; v < g.V(); v++) {
            for (org.graphs.Edge e : g.adj(v)) {
                String src = e.getSrcName();
                String dest = e.getDestName();
                String id = src + "-" + dest;
                if (gsGraph.getEdge(id) == null && gsGraph.getEdge(dest + "-" + src) == null) {
                    gsGraph.addEdge(id, src, dest);
                }
            }
        }

        gsGraph.setAttribute("ui.stylesheet",
                "node { fill-color: lightblue; size: 30px; text-size: 16; }" +
                        "edge { fill-color: gray; text-size: 14; }" +
                        "edge.mst { fill-color: red; size: 3px; }"
        );

        for (org.graphs.Edge e : mstEdges) {
            String id1 = e.getSrcName() + "-" + e.getDestName();
            String id2 = e.getDestName() + "-" + e.getSrcName();
            if (gsGraph.getEdge(id1) != null) gsGraph.getEdge(id1).setAttribute("ui.class", "mst");
            else if (gsGraph.getEdge(id2) != null) gsGraph.getEdge(id2).setAttribute("ui.class", "mst");
        }

        Viewer viewer = gsGraph.display();
    }
}
