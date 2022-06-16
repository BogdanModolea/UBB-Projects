//
// Created by bogdan on 4/1/2022.
//

#ifndef GRAPHS_UNDIRECTED_GRAPH_H
#define GRAPHS_UNDIRECTED_GRAPH_H

#include "bits/stdc++.h"

typedef std::pair<int, int>pi;
typedef std::unordered_set<int>usi;
typedef std::unordered_map<int, usi>umi;

class UndirectedGraph{
private:
    usi vertices;
    umi edges;
public:
    UndirectedGraph();

    UndirectedGraph(int n);

    UndirectedGraph(int n, int m);

    UndirectedGraph(const UndirectedGraph &g);

    int get_no_vertices();

    void add_vertex(int vertex);

    bool is_vertex(int vertex);

    void remove_vertex(int vertex);

    bool is_edge(int vertex1, int vertex2);

    bool is_edge(pi edge);

    void add_edge(int vertex1, int vertex2);

    void remove_edge(int vertex1, int vertex2);

    std::unordered_set<int>::const_iterator out_vertex_begin(int vertex);

    std::unordered_set<int>::const_iterator out_vertex_end(int vertex);
};

#endif //GRAPHS_UNDIRECTED_GRAPH_H
