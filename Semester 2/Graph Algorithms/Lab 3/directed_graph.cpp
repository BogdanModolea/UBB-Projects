//
// Created by bogdan on 4/26/2022.
//

#include "directed_graph.h"

#define intmax 2147483647

// constructors for graph
Graph::Graph() = default;

Graph::Graph(int n){
    if(n < 0)
        throw std::exception();
    for(int i = 0; i < n; i++)
        add_vertex(i);
}

Graph::Graph(int n, int m) {
    if(m < 0 || m > n * (n + 1) / 2)
        throw std::exception();
    if(n < 0)
        throw std::exception();

    for(int i = 0; i < n; i++)
        add_vertex(i);

    std::mt19937 rng(std::chrono::steady_clock::now().time_since_epoch().count());
    for(int i = 0; i < m; i++){
        int vertex1, vertex2, cost;
        do{
            vertex1 = int(rng() % intmax)%n;
            vertex2 = int(rng() % intmax)%n;
            cost = int(rng() % intmax) % 100000;
        } while (is_edge(vertex1, vertex2));
        add_edge(vertex1, vertex2, cost);
    }
}

Graph::Graph(const Graph &g){
    vertices = g.vertices;
    edges = g.edges;
    in_edges = g.in_edges;
    cost = g.cost;
}


/*========================  VERTICES  ========================*/

// returns the number of vertices
int Graph::get_no_vertices(){
    return vertices.size();
}

// adds a vertex
void Graph::add_vertex(int vertex) {
    if (is_vertex(vertex))
        throw std::exception();
    vertices.insert(vertex);
    edges[vertex] = usi();
    in_edges[vertex] = usi();
}

// checks if it is a vertex
bool Graph::is_vertex(int vertex) {
    return vertices.find(vertex) != vertices.end();
}

// returns the number of inbound vertices for a specific one
int Graph::no_in_vertex(int vertex){
    if(in_edges.find(vertex) != edges.end())
        return in_edges[vertex].size();
    throw std::exception();
}

// returns the number of outbound vertices for a specific one
int Graph::no_out_vertex(int vertex){
    if(edges.find(vertex) != edges.end())
        return edges[vertex].size();
}

// removes a vertex
void Graph::remove_vertex(int vertex) {
    if (!is_vertex(vertex))
        throw std::exception();
    std::vector<int> removed;

    for (auto x: edges[vertex])
        removed.emplace_back(x);

    for (auto x: removed)
        remove_edge(vertex, x);

    removed.clear();

    for (auto x: in_edges[vertex])
        removed.emplace_back(x);

    for (auto x: removed)
        remove_edge(x, vertex);

    edges.erase(vertex);
    in_edges.erase(vertex);
    vertices.erase(vertices.find(vertex));
}

usi::const_iterator Graph::vertices_begin(){
    return vertices.begin();
}

usi::const_iterator Graph::vertices_end(){
    return vertices.end();
}








/*========================  EDGES  ========================*/

// checks it there is an edge between 2 vertices
bool Graph::is_edge(int vertex1, int vertex2) {
    return edges.find(vertex1) != edges.end() && edges[vertex1].find(vertex2) != edges[vertex1].end();
}

bool Graph::is_edge(pi edge) {
    return edges.find(edge.first) != edges.end() && edges[edge.first].find(edge.second) != edges[edge.first].end();
}

// adds an edge between 2 vertices
void Graph::add_edge(int vertex1, int vertex2, int edge_cost) {
    if (is_edge(std::make_pair(vertex1, vertex2)))
        throw std::exception();
    if (!is_vertex(vertex1) || !is_vertex(vertex2))
        throw std::exception();

    edges[vertex1].insert(vertex2);
    in_edges[vertex2].insert(vertex1);
    cost.insert(Edge(vertex1, vertex2, edge_cost));
}

// returns the number of edges
int Graph::get_no_edges(){
    return cost.size();
}

// returns the cost for an edge between 2 vertices
int Graph::get_edge_cost(int vertex1, int vertex2) {
    auto it = cost.find(Edge(vertex1, vertex2, 0));
    if (it == cost.end())
        throw std::exception();
    return it->cost;
}

// sets the cost for an edge between 2 vertices
int Graph::set_edge_cost(int vertex1, int vertex2, int new_cost) {
    Edge new_edge(vertex1, vertex2, new_cost);
    auto it = cost.find(new_edge);
    if (it == cost.end())
        throw std::exception();
    cost.erase(new_edge);
    cost.insert(new_edge);
}

// removes an edge
void Graph::remove_edge(int vertex1, int vertex2) {
    if (!is_edge(std::make_pair(vertex1, vertex2)))
        throw std::exception();

    cost.erase(Edge(vertex1, vertex2, 0));
    edges[vertex1].erase(edges[vertex1].find(vertex2));
    in_edges[vertex2].erase(in_edges[vertex2].find(vertex1));
}


usi::const_iterator Graph::out_vertex_begin(int vertex){
    if(!is_vertex(vertex))
        throw std::exception();
    return edges[vertex].begin();
}

usi::const_iterator Graph::out_vertex_end(int vertex){
    if(!is_vertex(vertex))
        throw std::exception();
    return edges[vertex].end();
}

usi::const_iterator Graph::in_vertex_begin(int vertex){
    if(!is_vertex(vertex))
        throw std::exception();
    return in_edges[vertex].begin();
}

usi::const_iterator Graph::in_vertex_end(int vertex){
    if(!is_vertex(vertex))
        throw std::exception();
    return in_edges[vertex].end();
}

use::const_iterator Graph::edges_begin(){
    return cost.begin();
}

use::const_iterator Graph::edges_end(){
    return cost.end();
}

Edge::Edge(int node1, int node2, int new_cost) {
    vertex1 = node1;
    vertex2 = node2;
    cost = new_cost;
}

bool Edge::operator==(Edge edge) const {
    return vertex1 == edge.vertex1 && vertex2 == edge.vertex2;
}



Graph read(const char* filename){
    std::ifstream fin(filename);
    int n, m;
    fin >> n >> m;
    Graph graph(n);
    for(int i = 0; i < m; i++){
        int node1, node2, cost;
        fin >> node1 >> node2 >> cost;
        graph.add_edge(node1, node2, cost);
    }
    fin.close();
    return graph;
}

void write(const char* filename, Graph &graph){
    std::ofstream fout(filename);
    fout << graph.get_no_vertices() << " " << graph.get_no_edges() << "\n";

    for(auto it = graph.vertices_begin(); it != graph.vertices_end(); it++)
        for(auto x = graph.out_vertex_begin(*it); x != graph.out_vertex_end(*it); x++)
            fout << *it << " " << *x << graph.get_edge_cost(*it, *x) << "\n";

    fout.close();
}

Graph random_graph(int n, int m) {
    Graph graph;
    if (m < 0 || m > n * (n + 1) / 2)
        throw std::exception();
    if (n < 0)
        throw std::exception();

    for (int i = 0; i < n; i++)
        graph.add_vertex(i);

    std::mt19937 rng(std::chrono::steady_clock::now().time_since_epoch().count());
    for (int i = 0; i < m; i++) {
        int vertex1, vertex2, cost;
        do {
            vertex1 = int(rng() % intmax) % n;
            vertex2 = int(rng() % intmax) % n;
            cost = int(rng() % intmax) % 100000;
        } while (graph.is_edge(vertex1, vertex2));
        graph.add_edge(vertex1, vertex2, cost);
    }
    return graph;
}
