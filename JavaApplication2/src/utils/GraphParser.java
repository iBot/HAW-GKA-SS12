/**
 * HAW Hamburg - GKA SS 2012 - Gruppe 2 - Team 04
 *
 * @author Tobi
 * @author Nidal
 */
package utils;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

/**
 *
 * @author Tobi
 */
public class GraphParser {

    private GraphParser() {
    };
    
    /**
     * This methode should be called to create a Graph, based on a File, that 
     *   should be parsed
     * 
     * @param graphFile: path of the file, that should be parsed
     * @return graph with weighted edges
     */
    public static <E> Graph<String, E> parse(String graphFile) {
        WeightedGraph result = null;    //retur-value
        Set<String> vertices = new HashSet<>(); //collection of String-Names for vertices
        Set<E[]> edges = new HashSet<>();  //collection of String-Data for edges
        boolean isDigraph = true;
        try {
            FileInputStream fstream = new FileInputStream(graphFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine; //current line
            String firstLine = br.readLine(); //first line
            if (firstLine.matches("#ungerichtet")) {    //create undirected graph
                result = new ListenableUndirectedWeightedGraph(DefaultWeightedEdge.class);
                isDigraph = false;
            } else if (firstLine.matches("#gerichtet")) { //create digraph
                result = new DefaultDirectedWeightedGraph(DefaultWeightedEdge.class);
                isDigraph = true;
            } else {
                throw new Exception("Fehler in Datei: Erste Zeile enthält nicht korrekten Graph-Typ.");
            }
            int lineNo = 2;
            while ((strLine = br.readLine()) != null) { //Read File Line By Line
                //Split String by ","-limiter and put Substrings to array
                // e.g.:
                //  "Augsburg,München,70" -> array["Augsburg","München","70"]
                //  array[0] = start; array[1] = end; array[2] = value;
                String[] line = strLine.split(",");
                if (line.length!=3) throw new Exception(String.format("Fehler in Datei: Zeile %d hat %d statt 3 Werte.", lineNo,line.length));
//                vertices.add(line[0]);
//                vertices.add(line[1]);
//                if (!isDigraph) {};
//                edges.add(line);
//                V a = GFactory.vertex(line[0]);
                String v1 = line[0];
                result.addVertex(v1);
//                V b = GFactory.vertex(line[1]);
                String v2 = line[1];
                result.addVertex(v2);
//                result.addVertex(a);
//                result.addVertex(b);
                DefaultWeightedEdge edge = new DefaultWeightedEdge();
                result.addEdge(v1, v2, edge);
                result.setEdgeWeight(edge, Integer.parseInt(line[2]));
                
                //if (!isDigraph) {result.addEdge(b, a, GFactory.edge(Integer.parseInt(line[2]), b.name()+"-"+a.name()));};
                System.out.println(strLine);
                lineNo++;
            }
            in.close(); //Close the input stream
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        
//        //Add all vertices to graph
//        for (String vertex : vertices) {
//            result.addVertex(vertex);
//        }
//        
//        //Add all edges to graph
//        for (E[] edge : edges) {
//            if (!result.addEdge(edge[0], edge[1], GFactory.edge(Integer.parseInt(edge[2])))) {
//                System.out.println(Arrays.toString(edge));
//            }
//        }
//        if (!isDigraph) for (E[] edge : edges) {
//            System.out.println(result.addEdge(edge[1], edge[0], GFactory.edge(Integer.parseInt(edge[2]))));
//        }
        return result;
    }
}
