//
// Created by bogdan on 4/26/2022.
//

#include "bits/stdc++.h"
#include "directed_graph.h"

#define intmax 2147483647

using namespace std;

typedef vector< vector<int> > matrix;

matrix In(int n){
    matrix curr(n, vector<int>(n, intmax));
    for(int i = 0; i < n; i++)
        curr[i][i] = 0;
    return curr;
}

matrix graph_to_matrix(Graph g){
    int n = g.get_no_vertices();
    matrix a = In(n);
    for(auto it = g.edges_begin(); it != g.edges_end(); it++)
        a[it->vertex1][it->vertex2] = it->cost;
    return a;
}

void Roy(Graph g, int ss, int ff){
    int n = g.get_no_vertices();
    matrix a = graph_to_matrix(g);
    for(int k = 0; k < n; k++){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(i != j && a[i][k] && a[k][j] && (a[i][j] == 0 || a[i][j] > a[i][k] + a[k][j]))
                    a[i][j] = a[i][k] + a[k][j];
            }
        }
    }

    cout << "\nThe costs between all vetices:\n";

    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            cout << a[i][j] << " ";
        }
        cout << "\n";
    }
    cout << "\nThe cost between " << ss << " and " << ff << ":\n" << a[ss][ff] << "\n";
}


int main(){
    Graph g(5);
    g.add_edge(0, 1, 3);
    g.add_edge(0, 2, 9);
    g.add_edge(0, 3, 8);
    g.add_edge(0, 4, 3);

    g.add_edge(1, 0, 5);
    g.add_edge(1, 2, 1);
    g.add_edge(1, 3, 4);
    g.add_edge(1, 4, 2);

    g.add_edge(2, 0, 6);
    g.add_edge(2, 1, 6);
    g.add_edge(2, 3, 4);
    g.add_edge(2, 4, 5);

    g.add_edge(3, 0, 2);
    g.add_edge(3, 1, 9);
    g.add_edge(3, 2, 2);
    g.add_edge(3, 4, 7);

    g.add_edge(4, 0, 7);
    g.add_edge(4, 1, 9);
    g.add_edge(4, 2, 3);
    g.add_edge(4, 3, 2);

    matrix a;
    a = graph_to_matrix(g);

    for(int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++)
            cout << a[i][j] << " ";
        cout << "\n";
    }

    int start, stop;
    cout << "Enter the vertex to start with:";
    cin >> start;
    cout << "Enter the vertex to stop with:";
    cin >> stop;
    Roy(g, start, stop);
    return 0;
}