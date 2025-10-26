package org.graphs;

import com.google.gson.*;
import java.io.*;
import java.util.List;

public class Parser {

    public static class GraphInput {
        public int id;
        public List<String> nodes;
        public List<EdgeInput> edges;
    }

    public static class EdgeInput {
        String from;
        String to;
        int weight;
    }

    public static class InputData {
        public List<GraphInput> graphs;
    }

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static InputData readInput() {
        try {
            File file = new File("input.json");
            return gson.fromJson(new FileReader(file), InputData.class);
        } catch (Exception e) {
            throw new RuntimeException("ERROR " + e.getMessage());
        }
    }

    public static void writeOutput(JsonObject output) {
        try {
            FileWriter writer = new FileWriter("output.json");
            gson.toJson(output, writer);
            writer.close();
            System.out.println("output.json is created");
        } catch (Exception e) {
            throw new RuntimeException("output.json: " + e.getMessage());
        }
    }

    public static JsonArray toJsonEdges(Iterable<Edge> edges) {
        JsonArray arr = new JsonArray();
        for (Edge e : edges) {
            JsonObject obj = new JsonObject();
            obj.addProperty("from", e.getSrcName());
            obj.addProperty("to", e.getDestName());
            obj.addProperty("weight", e.weight());
            arr.add(obj);
        }
        return arr;
    }
}
