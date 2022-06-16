//
// Created by bogdan on 4/26/2022.
//

#include "bits/stdc++.h"
#include "undirected_graph.h"
#include "directed_graph.h"

#define intmax 2147483647

using namespace std;

void solve(UndirectedGraph g){
    bool visited[g.get_no_vertices()];
    for(int i = 0; i < g.get_no_vertices(); i++)
        visited[i] = false;

    for(int x = 0; x < g.get_no_vertices(); x++){
        if(!visited[x]){
            for(auto it = g.out_vertex_begin(x); it != g.out_vertex_end(x); it++){
                int y = *it;
                if(!visited[y]){
                    visited[x] = true;
                    visited[y] = true;
                    break;
                }
            }
        }
    }

    for(int i = 0; i < g.get_no_vertices(); i++)
        if(visited[i])
            cout << i << " ";
}

int main(){
    UndirectedGraph g(7);
    g.add_edge(0, 1);
    g.add_edge(0, 2);
    g.add_edge(1, 3);
    g.add_edge(3, 4);
    g.add_edge(4, 5);
    g.add_edge(5, 6);
    solve(g);           // The optimal is {0, 3, 5} | sol: {0, 1, 2, 3, 4, 5}

    cout << "\n";
    UndirectedGraph g2(5);
    g2.add_edge(0, 2);
    g2.add_edge(2, 4);
    g2.add_edge(1, 4);
    g2.add_edge(3, 4);
    solve(g2);      // the optimal is {0, 4} | sol: {0, 1, 2, 4}

    cout << "\n";
    UndirectedGraph g3(4);
    g3.add_edge(0, 3);
    g3.add_edge(1, 3);
    g3.add_edge(2, 3);
    solve(g3);      // the optimal is {3} | sol: {0, 3}
    return 0;
}