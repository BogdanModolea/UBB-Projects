//
// Created by bogdan on 4/1/2022.
//

#include "bits/stdc++.h"
#include "directed_graph.h"
#include "undirected_graph.h"

#define ff first
#define ss second

using namespace std;

/*
     * The number of connected components
*/
void connected_components(UndirectedGraph g){
    vector<int>visited;
    visited.assign(g.get_no_vertices(), 0);
    int ans = 0;
    umi lst;
    for(int i = 0; i < g.get_no_vertices(); i++){
        if(!visited[i]){
            ans++;
            queue<int>Q;
            Q.push(i);
            visited[i] = 1;
            lst[ans] = usi();
            lst[ans].insert(i);
            while (!Q.empty()){
                int x = Q.front();
                Q.pop();
                for(auto j = g.out_vertex_begin(x); j != g.out_vertex_end(x); j++){
                    if(!visited[*j]){
                        visited[*j] = 1;
                        Q.push(*j);
                        lst[ans].insert(*j);
                    }
                }
            }
        }
    }
    cout << "There are " <<ans << " connected components:\n";
    for(int i = 1; i <= ans; i++) {
        for (auto j: lst[i])
            cout << j << " ";
        cout << "\n";
    }
}


/*
     * 2B: The biconnected components
*/


vector<vector<int>>sol;
stack<pi>st;
vector<int>niv, nivmin;
vector<int> vis;

void build(int a, int b) {
    vector<int> v;
    while (!st.empty()) {
        pi x = st.top();
        st.pop();
        v.push_back(x.ff);
        v.push_back(x.ss);

        if (x.ff == a && x.ss == b)
            break;
        if (x.ff == b && x.ss == a)
            break;
    }
    sort(v.begin(), v.end());
    v.erase(unique(v.begin(), v.end()), v.end());
    sol.push_back(v);
}


void dfs(UndirectedGraph graph, int node) {
    vis[node] = 1;
    if (niv[node] == 0)
        niv[node] = 1;
    nivmin[node] = niv[node];

    for (auto i = graph.out_vertex_begin(node); i != graph.out_vertex_end(node); i++) {
        if (!vis[*i]) {
            st.push({node, *i});
            niv[*i] = niv[node] + 1;
            dfs(graph, *i);

            if (nivmin[*i] >= niv[node])
                build(node, *i);
            nivmin[node] = min(nivmin[node], nivmin[*i]);
        } else
            nivmin[node] = min(nivmin[node], niv[*i]);
    }
}

void biconnected_components(UndirectedGraph graph) {
    vis.assign(graph.get_no_vertices(), 0);
    niv.assign(graph.get_no_vertices(), 0);
    nivmin.assign(graph.get_no_vertices(), 0);


    for (int i = 0; i < graph.get_no_vertices(); i++) {
        if (!vis[i])
            dfs(graph, i);
    }
    cout <<"There are " << sol.size() << " biconnected components:\n";
    for (const auto& i: sol) {
        for (auto j: i)
            cout << j << " ";
        cout << "\n";
    }
}

stack<int>S;
vector<int>been;
int ans_ctc;

void DFS(Graph g, int node){
    been[node] = 1;
    for(auto i = g.out_vertex_begin(node); i != g.out_vertex_end(node); i++){
        if(!been[*i])
            DFS(g, *i);
    }
    S.push(node);
}

vector<int>ctc[100001];

void dfst(Graph g, int node){
    been[node] = 2;
    ctc[ans_ctc].push_back(node);

    for(auto i = g.out_vertex_begin(node); i != g.out_vertex_end(node); i++)
        if(been[*i] == 1)
            dfst(g, *i);
}


void strongly_connected_components(Graph g, const Graph& greverse) {
    been.assign(g.get_no_vertices(), 0);
    for (int i = 0; i < g.get_no_vertices(); i++)
        if (!been[i])
            DFS(g, i);

    while (!S.empty()) {
        int node = S.top();
        if (been[node] == 1) {
            ans_ctc++;
            dfst(greverse, node);
        }
        S.pop();
    }


    cout << "There are " << ans_ctc << " strongly-connected components:\n";
    for (int i = 0; i < g.get_no_vertices(); i++) {
        for (int j : ctc[i])
            cout << j << " ";
        cout << "\n";
    }
}


int main(){
    /*
     * The number of connected components
     * */
    UndirectedGraph g1(8);
    g1.add_edge(1, 2);
    g1.add_edge(1, 3);
    g1.add_edge(2, 3);
    g1.add_edge(4, 5);
    g1.add_edge(5, 6);
    g1.add_edge(4, 6);
    g1.add_edge(1, 7);
    g1.add_edge(2, 7);

    cout << "Graph g1:\n";
    for(int i = 0; i < g1.get_no_vertices(); i++) {
        cout << i << ": ";
        for (auto j = g1.out_vertex_begin(i); j != g1.out_vertex_end(i); j++)
            cout << *j << " ";
        cout << "\n";
    }
    cout << "\n";
    connected_components(g1);
    cout << "\n\n";


    UndirectedGraph g2(5);
    g2.add_edge(0, 1);
    g2.add_edge(0, 2);
    g2.add_edge(0, 3);
    g2.add_edge(0, 4);
    g2.add_edge(1, 2);
    g2.add_edge(1, 3);
    g2.add_edge(1, 4);
    g2.add_edge(3, 4);

    cout << "Graph g2:\n";
    for(int i = 0; i < g2.get_no_vertices(); i++) {
        cout << i << ": ";
        for (auto j = g2.out_vertex_begin(i); j != g2.out_vertex_end(i); j++)
            cout << *j << " ";
        cout << "\n";
    }
    cout << "\n";
    connected_components(g2);
    cout << "\n\n\n";





    /*
     * 2B: The biconnected components
     * */
//    UndirectedGraph g3(8);
//    g3.add_edge(0, 1);
//    g3.add_edge(1, 2);
//    g3.add_edge(2, 3);
//    g3.add_edge(3, 0);
//    g3.add_edge(0, 4);
//    g3.add_edge(4, 5);
//    g3.add_edge(5, 6);
//    g3.add_edge(6, 4);
//    g3.add_edge(6, 7);
//
//    cout << "Graph g3:\n";
//    for(int i = 0; i < g3.get_no_vertices(); i++) {
//        cout << i << ": ";
//        for (auto j = g3.out_vertex_begin(i); j != g3.out_vertex_end(i); j++)
//            cout << *j << " ";
//        cout << "\n";
//    }
//    cout << "\n";
//    biconnected_components(g3);
//    cout << "\n\n";

    UndirectedGraph g4(12);
    g4.add_edge(0, 1);
    g4.add_edge(1, 2);
    g4.add_edge(1, 3);
    g4.add_edge(2, 3);;
    g4.add_edge(2, 4);
    g4.add_edge(3, 4);
    g4.add_edge(1, 5);
    g4.add_edge(0, 6);
    g4.add_edge(5, 6);
    g4.add_edge(5, 7);
    g4.add_edge(5, 8);
    g4.add_edge(7, 8);
    g4.add_edge(8, 9);
    g4.add_edge(10, 11);

    cout << "Graph g4:\n";
    for(int i = 0; i < g4.get_no_vertices(); i++) {
        cout << i << ": ";
        for (auto j = g4.out_vertex_begin(i); j != g4.out_vertex_end(i); j++)
            cout << *j << " ";
        cout << "\n";
    }
    cout << "\n";
    biconnected_components(g4);
    cout << "\n\n";




    /*
     * 3B: The strongly-connected components
     * */

    Graph g5(8);
    Graph g5reverse(8);
    g5.add_edge(0, 1);
    g5reverse.add_edge(1, 0);
    g5.add_edge(1, 5);
    g5reverse.add_edge(5, 1);
    g5.add_edge(5, 6);
    g5reverse.add_edge(6, 5);
    g5.add_edge(6, 5);
    g5reverse.add_edge(5, 6);
    g5.add_edge(2, 0);
    g5reverse.add_edge(0, 2);
    g5.add_edge(2, 3);
    g5reverse.add_edge(3, 2);
    g5.add_edge(1, 2);
    g5reverse.add_edge(2, 1);
    g5.add_edge(3, 4);
    g5reverse.add_edge(4, 3);
    g5.add_edge(4, 3);
    g5reverse.add_edge(3, 4);
    g5.add_edge(5, 4);
    g5reverse.add_edge(4, 5);
    g5.add_edge(4, 7);
    g5reverse.add_edge(7, 4);
    g5.add_edge(7, 6);
    g5reverse.add_edge(6, 7);

    cout << "Graph g5:\n";
    for(int i = 0; i < g5.get_no_vertices(); i++){
        cout << i << ": ";
        for(auto j = g5.out_vertex_begin(i); j != g5.out_vertex_end(i); j++)
            cout << *j << " ";
        cout << "\n";
    }
    cout << "\n";

    strongly_connected_components(g5, g5reverse);

    return 0;
}