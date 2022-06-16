//
// Created by bogdan on 4/26/2022.
//

#include "bits/stdc++.h"
#include "directed_graph.h"

#define intmax 2147483647

using namespace std;

stack<int>result;
vector<bool>visited;
vector<int>topo;

void dfs(Graph g, int x){
    if(!visited[x]){
        for(auto it = g.out_vertex_begin(x); it != g.out_vertex_end(x); it++)
            dfs(g, *it);
        visited[x] = true;
        result.push(x);
    }
}

vector<int> Tarjan(Graph g){
    visited.assign(g.get_no_vertices(), false);
    topo.clear();
    for(int i = 0; i < g.get_no_vertices(); i++){
        if(!visited[i])
            dfs(g, i);
    }
    while (!result.empty())
        topo.push_back(result.top()), cout << result.top() << "\n", result.pop();

    return topo;
}

void scheduling_problem(){
    ifstream fin("test.in");
    int n;
    fin >> n;
    Graph g(n);
    vector<int> duration;
    for(int i = 0; i < n; i++){
        int prerequisites, times;
        fin >> prerequisites >> times;
        duration.push_back(times);
        for(int t = 0; t < prerequisites; t++){
            int j;
            fin >> j;
            g.add_edge(j, i);
        }
    }
    topo = Tarjan(g);
    if(topo.size() != g.get_no_vertices()) {
        cout << "This is not a DAG\n";
        return;
    }
    vector<int>earliest(g.get_no_vertices()), latest(g.get_no_vertices());
    int total = 0;
    for(auto x : topo){
        earliest[x] = 0;
        for(auto it = g.in_vertex_begin(x); it != g.in_vertex_end(x); it++)
            earliest[x] = max(earliest[x], earliest[*it] + duration[*it]);
    }

    for(auto x : topo)
        total = max(total, earliest[x] + duration[x]);

    for(auto it = topo.rbegin(); it != topo.rend(); it++){
        latest[*it] = total - duration[*it];
        for(auto x = g.out_vertex_begin(*it); x != g.out_vertex_end(*it); x++)
            latest[*it] = min(latest[*it], latest[*x] - duration[*it]);
    }

    for(auto i = 0; i < g.get_no_vertices(); i++)
        cout << "Activity " << i << ": Earliest time: " << earliest[i] << " | Latest time: " << latest[i] << "\n";
    fin.close();
}



vector<int>dist;
vector<bool>vis;

void bfs(Graph g, int start, int stop) {
    vis.assign(g.get_no_vertices(), false);
    dist.assign(g.get_no_vertices(), 0);
    queue<int> Q;
    vis[start] = true;
    dist[start] = 0;
    Q.push(start);
    while (!Q.empty()) {
        int x = Q.front();
        Q.pop();
        for (auto it = g.out_vertex_begin(x); it != g.out_vertex_end(x); it++) {
            if ((dist[*it] == 0 && !vis[*it]) || (dist[*it] < g.get_edge_cost(x, *it) + dist[x] && vis[*it])) {
                dist[*it] = g.get_edge_cost(x, *it) + dist[x];
                Q.push(*it);
                vis[*it] = true;
            }
        }
    }
    cout << "\nThe highest cost path between " << start << " and " << stop << " is ";
    cout << dist[stop];
}

struct node{
    int info;
    node *left, *right;
};

node* newNode(int info){
    node* nod = (node*) malloc(sizeof(node));
    nod->info = info;
    nod->left = nod->right = NULL;
    return (nod);
}

int search(int arr[], int start, int end, int val){
    int i;
    for(i = start; i <= end; i++)
        if(arr[i] == val)
            break;

    return i;
}

node* build_util(int in[], int post[], int in_str, int in_end, int *index){
    if(in_str > in_end)
        return NULL;

    node* nod = newNode(post[*index]);
    (*index)--;

    if(in_str == in_end)
        return nod;

    int idx = search(in, in_str, in_end, nod->info);

    nod->right = build_util(in, post, idx + 1, in_end, index);
    nod->right = build_util(in, post, in_str, idx - 1, index);

    return nod;
}

node* build_tree(int in[], int post[], int n){
    int index = n - 1;
    return build_util(in, post, 0, n - 1, &index);
}

node* build_tree2(int in[], int pre[], int start, int end){
    static int index = 0;

    if(start > end)
        return NULL;

    node* nod = newNode(pre[index++]);

    if(start == end)
        return nod;

    int in_index = search(in, start, end, nod->info);
    nod->left = build_tree2(in, pre, start, in_index - 1);
    nod->right = build_tree2(in, pre, in_index + 1, end);

    return nod;
}

node* construct(int pre[], int post[], int* pre_index, int l, int h, int n){
    if(*pre_index >= n || l > h)
        return NULL;

    node* root = newNode(pre[*pre_index]);
    ++*pre_index;

    if(l == h)
        return root;

    int i;
    for(i = l; i <= h; i++)
        if(pre[*pre_index] == post[i])
            break;

    if(i <= h){
        root->left = construct(pre, post, pre_index, l, i, n);
        root->right = construct(pre, post, pre_index, i + 1, h - 1, n);
    }

    return root;
}

node* build_tree3(int pre[], int post[], int n){
    int pre_index = 0;
    return construct(pre, post, &pre_index, 0, n - 1, n);
}

void bonus1(){
    int opt;
    cout << "What would you like to construct?\n"
            "1. From Postorder and Inorder\n"
            "2. From Inorder and Preorder\n"
            "3. From Preorder and Postorder\n>";
    cin >> opt;
    if(opt == 1){
        // From Postorder and Inorder
        int in[] = { 4, 8, 2, 5, 1, 6, 3, 7 };
        int post[] = { 8, 4, 5, 2, 6, 7, 3, 1 };
        int n = sizeof(in) / sizeof(in[0]);
        node* root = build_tree(in, post, n);

        assert(root->info == 1);
        cout << "\nThe root is " << root->info << "\n";
    }
    else if(opt == 2){
        // From Inorder and Preorder
        int in[] = { 3, 1, 4, 0, 5, 2};
        int pre[] = { 0, 1, 3, 4, 2, 5};
        int n = sizeof(in) / sizeof(in[0]);
        node* root = build_tree2(in, pre, 0, n - 1);

        assert(root->info == 0);
        cout << "\nThe root is " << root->info << "\n";
    }
    else {
        // From Preorder and Postorder
        int pre[] = {1, 2, 4, 8, 9, 5, 3, 6, 7};
        int post[] = {8, 9, 4, 5, 2, 6, 7, 3, 1};
        int n = sizeof(pre) / sizeof(pre[0]);
        node *root = build_tree3(pre, post, n);

        assert(root->info == 1);
        cout << "\nThe root is " << root->info << "\n";
    }
}

void bonus2(){
    Graph g(5);
    g.add_edge(0, 1);
    g.add_edge(0, 3);
    g.add_edge(0, 4);
    g.add_edge(1, 2);
    g.add_edge(3, 4);

    vector<int> dp(g.get_no_vertices(), 0);
    vector<int>srt = Tarjan(g);

    if(srt.size() != g.get_no_vertices()){
        cout << "\nNot a DAG\n";
        return;
    }

    int start, finish;
    int pos1, pos2;
    start = 0;
    finish = 4;
    for(int i = 0; i < g.get_no_vertices(); i++){
        if(srt[i] == start)
            pos1 = i;
        if(srt[i] == finish)
            pos2 = i;
    }
    dp[finish] = 1;
    for(int i = pos2 - 1; i >= pos1; i--){
        int x = srt[i];
        for(auto y = g.out_vertex_begin(x); y != g.out_vertex_end(x); y++)
            dp[x] += dp[*y];
    }
    cout << "\n\n\nThere are " << dp[start] << " paths\n";
}


void bonus3(){
    Graph g(6);
    g.add_edge(0, 1, 5);
    g.add_edge(0, 3, 2);
    g.add_edge(0, 4, 5);
    g.add_edge(1, 2, 7);
    g.add_edge(1, 4, 4);
    g.add_edge(3, 4, 3);

    vector<int>srt = Tarjan(g);
    if(srt.size() != g.get_no_vertices()){
        cout << "\nNot a DAG!\n";
        return;
    }
    int pos1 = 0, pos2 = 0;
    int start, finish;
    start = 0;
    finish = 4;

    for(int i = 0; i < g.get_no_vertices(); i++){
        if(start == srt[i])
            pos1 = i;
        if(finish == srt[i])
            pos2 = i;
    }

    vector<pair<int, int>> dp(g.get_no_vertices());
    dp[finish] = make_pair(0, 1);
    for(int i = pos2 - 1; i >= pos1; i--){
        int x = srt[i];
        dp[x] = make_pair(-1, 1);
        for(auto y = g.out_vertex_begin(x); y != g.out_vertex_end(x); y++){
            if(dp[*y].first != -1){
                if(dp[x].first == -1 || dp[x].first > dp[*y].first + g.get_edge_cost(x, *y))
                    dp[x] = make_pair(dp[*y].first + g.get_edge_cost(x, *y), dp[*y].second);
                else if(dp[*y].first + g.get_edge_cost(x, *y) == dp[x].first)
                    dp[x].second += dp[*y].second;
            }
        }
    }

    cout << "\n\nThere are " << dp[start].second << " paths that have the cost " << dp[start].first << "\n";
}


int main(){
    scheduling_problem();



    Graph g(6);
    g.add_edge(1, 2, 5);
    g.add_edge(1, 4, 22);
    g.add_edge(2, 4, 22);
    g.add_edge(2, 3, 3);
    g.add_edge(2, 5, 14);
    g.add_edge(3, 5, 11);

    bfs(g, 1, 4);
    bfs(g, 1, 5);

    Graph g2(g);
    cout << '\n';
    g2.add_edge(1, 5, 222);
    bfs(g2, 1, 5);

    bonus2();

    bonus3();

    bonus1();

    return 0;
}