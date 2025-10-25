package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) {

        Parser.InputData data = Parser.readInput();
        JsonArray result= new JsonArray();

        for (Parser.GraphInput g : data.graphs) {

            Graph graph = new Graph(g.nodes.toArray(new String[0]));
            for (Parser.EdgeInput e : g.edges) {
                graph.addEdge(e.from, e.to, e.weight);
            }

            Prim prim = new Prim(graph);
            Kruskal kruskal = new Kruskal(graph);

            JsonObject res = new JsonObject();
            res.addProperty("graph_id", g.id);

            JsonObject stats = new JsonObject();
            stats.addProperty("vertices", g.nodes.size());
            stats.addProperty("edges", g.edges.size());
            res.add("input_stats", stats);

            res.add("prim", makeAlgoJson(prim));
            res.add("kruskal", makeAlgoJson(kruskal));

            result.add(res);
        }

        JsonObject output = new JsonObject();
        output.add("results", result);
        Parser.writeOutput(output);
    }

    private static JsonObject makeAlgoJson(Object algo) {
        JsonObject o = new JsonObject();

        if (algo instanceof Prim p) {
            o.add("mst_edges", Parser.toJsonEdges(p.edges()));
            o.addProperty("total_cost", p.weight());
            o.addProperty("operations_count", p.getOperations());
            o.addProperty("execution_time_ms", p.getTime());

        } else if (algo instanceof Kruskal k) {
            o.add("mst_edges", Parser.toJsonEdges(k.edges()));
            o.addProperty("total_cost", k.weight());
            o.addProperty("operations_count", k.getOperations());
            o.addProperty("execution_time_ms", k.getTime());
        }

        return o;
    }
}

