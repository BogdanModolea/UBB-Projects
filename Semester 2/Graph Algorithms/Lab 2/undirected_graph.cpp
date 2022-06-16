//
// Created by bogdan on 4/1/2022.
//

#include "undirected_graph.h"
#define intmax 2147483647

// some graph constructors
UndirectedGraph::UndirectedGraph() = default;

UndirectedGraph::UndirectedGraph(int n){
    if(n < 0)
        throw std::exception();
    for(int i = 0; i < n; i++)
        add_vertex(i);
}

UndirectedGraph::UndirectedGraph(int n, int m) {
    if(m < 0 || m > n * (n + 1) / 2)
        throw std::exception();
    if(n < 0)
        throw std::exception();

    for(int i = 0; i < n; i++)
        add_vertex(i);

    std::mt19937 rng(std::chrono::steady_clock::now().time_since_epoch().count());
    for(int i = 0; i < m; i++){
        int vertex1, vertex2;
        do{
            vertex1 = int(rng() % intmax)%n;
            vertex2 = int(rng() % intmax)%n;
        } while (is_edge(vertex1, vertex2));
        add_edge(vertex1, vertex2);
    }
}

UndirectedGraph::UndirectedGraph(const UndirectedGraph &g){
    vertices = g.vertices;
    edges = g.edges;
}

/*========================  VERTICES  ========================*/

// returns the number of vertices
int UndirectedGraph::get_no_vertices(){
    return vertices.size();
}

// add a vertex to the graph
void UndirectedGraph::add_vertex(int vertex) {
    if (is_vertex(vertex))
        throw std::exception();
    vertices.insert(vertex);
    edges[vertex] = usi();
}

// checks if a vertex exists in the graph or not
bool UndirectedGraph::is_vertex(int vertex) {
    return vertices.find(vertex) != vertices.end();
}

// remove a vertex
void UndirectedGraph::remove_vertex(int vertex) {
    if (!is_vertex(vertex))
        throw std::exception();
    std::vector<int> removed;

    for (auto x: edges[vertex])
        removed.emplace_back(x);

    for (auto x: removed)
        remove_edge(vertex, x);

    removed.clear();

    edges.erase(vertex);
    vertices.erase(vertices.find(vertex));
}

/*========================  EDGES  ========================*/


// checks if between 2 vertices there is an edge or not
bool UndirectedGraph::is_edge(int vertex1, int vertex2) {
    return edges.find(vertex1) != edges.end() && edges[vertex1].find(vertex2) != edges[vertex1].end();
}

bool UndirectedGraph::is_edge(pi edge) {
    return edges.find(edge.first) != edges.end() && edges[edge.first].find(edge.second) != edges[edge.first].end();
}

// adds an edge between 2 vertices
void UndirectedGraph::add_edge(int vertex1, int vertex2) {
    if (is_edge(std::make_pair(vertex1, vertex2)) || is_edge(std::make_pair(vertex2, vertex1)))
        throw std::exception();
    if (!is_vertex(vertex1) || !is_vertex(vertex2))
        throw std::exception();

    edges[vertex1].insert(vertex2);
    edges[vertex2].insert(vertex1);
}


// removes an edge
void UndirectedGraph::remove_edge(int vertex1, int vertex2) {
    if (!is_edge(std::make_pair(vertex1, vertex2)))
        throw std::exception();

    edges[vertex1].erase(edges[vertex1].find(vertex2));
    edges[vertex2].erase(edges[vertex2].find(vertex1));

}


//Edge::Edge(int node1, int node2, int new_cost) {
//    vertex1 = node1;
//    vertex2 = node2;
//    cost = new_cost;
//}
//
//bool Edge::operator==(Edge edge) const {
//    return vertex1 == edge.vertex1 && vertex2 == edge.vertex2;
//}


usi::const_iterator UndirectedGraph::out_vertex_begin(int vertex){
    if(!is_vertex(vertex))
        throw std::exception();
    return edges[vertex].begin();
}

usi::const_iterator UndirectedGraph::out_vertex_end(int vertex){
    if(!is_vertex(vertex))
        throw std::exception();
    return edges[vertex].end();
}


UndirectedGraph random_undirected_graph(int n, int m) {
    UndirectedGraph graph;
    if (m < 0 || m > n * (n + 1) / 2)
        throw std::exception();
    if (n < 0)
        throw std::exception();

    for (int i = 0; i < n; i++)
        graph.add_vertex(i);

    std::mt19937 rng(std::chrono::steady_clock::now().time_since_epoch().count());
    for (int i = 0; i < m; i++) {
        int vertex1, vertex2;
        do {
            vertex1 = int(rng() % intmax) % n;
            vertex2 = int(rng() % intmax) % n;
        } while (graph.is_edge(vertex1, vertex2));
        graph.add_edge(vertex1, vertex2);
    }
    return graph;
}
