//
// Created by bogdan on 3/27/2022.
//

#ifndef GRAPH_C___GRAPH_H
#define GRAPH_C___GRAPH_H

#include "fstream"
#include "vector"
#include "unordered_map"
#include "unordered_set"
#include "utility"
#include "random"
#include "chrono"

struct Edge{
    int vertex1, vertex2, cost;
    Edge(int vertex1, int vertex2, int cost);
    bool operator==(Edge edge)const;
};

namespace std {
    template<>
    struct hash<Edge> {
        std::size_t operator()(const Edge &edge) const {
            return (std::hash<int>()(edge.vertex1) ^ std::hash<int>()(edge.vertex2));
        }
    };
}


typedef std::pair<int, int>pi;
typedef std::unordered_set<int>usi;
typedef std::unordered_map<int, usi>umi;
typedef std::unordered_set<Edge>use;


class Graph{
private:
    usi vertices;
    umi edges, in_edges;
    use cost;

public:
    Graph();

    Graph(int n);

    Graph(int n, int m);

    Graph(const Graph &g);

    int get_no_edges();

    int get_no_vertices();

    void remove_edge(int vertex1, int vertex2);

    void add_edge(int vertex1, int vertex2, int edge_cost = 0);

    void remove_vertex(int vertex);

    void add_vertex(int vertex);

    int set_edge_cost(int vertex1, int vertex2, int new_cost);

    int get_edge_cost(int vertex1, int vertex2);

    int no_out_vertex(int vertex);

    int no_in_vertex(int vertex);

    bool is_vertex(int vertex);

    bool is_edge(pi edge);

    bool is_edge(int vertex1, int vertex2);

    std::unordered_set<int>::const_iterator vertices_begin();

    std::unordered_set<int>::const_iterator vertices_end();

    std::unordered_set<int>::const_iterator out_vertex_begin(int vertex);

    std::unordered_set<int>::const_iterator out_vertex_end(int vertex);

    std::unordered_set<int>::const_iterator in_vertex_begin(int vertex);

    std::unordered_set<int>::const_iterator in_vertex_end(int vertex);

    std::unordered_set<Edge>::const_iterator edges_begin();

    std::unordered_set<Edge>::const_iterator edges_end();
};

#endif //GRAPH_C___GRAPH_H
