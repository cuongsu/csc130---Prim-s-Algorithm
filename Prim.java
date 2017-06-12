import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;
 
public class Prim {
		public static void main(String[] args) throws FileNotFoundException {
        	//make a scanner that lets you use the text in the .txt file set in the arguments
         //this was done using Eclipse, won't work with jGrasp because of args[0]
			Scanner input = new Scanner(new File(args[0]));           
            new Prim(input);
        }
        
        int[] pointsArr;
        public Prim(Scanner input) {
        	//create the tree
        	List<Node> allNodes = new ArrayList<Node>();
            {
            	//make the 2 first numbers in the txt file the number of edges and vertices
            	int nPoints = Integer.parseInt(input.nextLine());
                int nVertices = Integer.parseInt(input.nextLine());
                for(int i = 0; i < nPoints; i++)
                	allNodes.add(new Node(i));
                for(int i = 2; i < nVertices+2; i++) {
                	//split each number by the space in between them
                	String[] arr = input.nextLine().split(" ");
                	int a = Integer.parseInt(arr[0]);
                	int b = Integer.parseInt(arr[1]);
                	float weight = Float.parseFloat(arr[2]);
                	{
                		Node aNode = allNodes.get(a);
                		Node bNode = allNodes.get(b);
                		aNode.addVertex(new Vertex(aNode, bNode, weight));
                		bNode.addVertex(new Vertex(bNode, aNode, weight));
                	}
                }
            }
            
            {
            	Comparator<Vertex> c = new VertexComparator();
                PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(c);
                List<Node> openNodes = new ArrayList<Node>();
                DecimalFormat df = new DecimalFormat("0.00");
                float totalWeight = 0;
                       
                openNodes.add(allNodes.get(0));
                for(Vertex v : openNodes.get(0).vertices)
                 	pq.add(v);   
                while(openNodes.size() != allNodes.size()) {
                	Vertex smallestAdjVertex = pq.remove();
                    pq.clear();
                    openNodes.add(smallestAdjVertex.to);
                    System.out.print(smallestAdjVertex);
                    System.out.println(df.format(smallestAdjVertex.weight));
                    totalWeight+=smallestAdjVertex.weight;
                    for(Node openNode : openNodes) {
                    	for(Vertex v : openNode.vertices)
                    		if(!openNodes.contains(v.to))
                    			pq.add(v);
                    }
                }
                System.out.print("Total weight: " + df.format(totalWeight));                                             
            }
        }
       
        class Node {
                public int id;
                public List<Vertex> vertices;
                public Node(int _id) {
                        id = _id;
                        vertices = new ArrayList<Vertex>();
                }
                public void addVertex(Vertex vertex) {
                        vertices.add(vertex);
                }
                public String toString() {
                        return "[Node: "+id+"]";
                }
        }
        
        class Vertex {
                public Node from;
                public Node to;
                public float weight;
                
                public Vertex(Node _from, Node _to, float _weight) {
                        from = _from;
                        to = _to;
                        weight = _weight;
                }
                            
                public String toString() {
                        return from.toString()+" --> "+to.toString()+ " Weight: " ;
                }
        }
        
        //compare the weights between 2 vertices
        class VertexComparator implements Comparator<Vertex> {
        	public int compare(Vertex o1, Vertex o2) {
        		if(o1.weight < o2.weight)
        			return -1;
                else if(o1.weight > o2.weight)
                	return  1;
                else
                	return 0;
            }
        }
}