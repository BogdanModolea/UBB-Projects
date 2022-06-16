//
// Created by bogdan on 3/27/2022.
//

#include "tests.h"
#include "graph.h"
#include "cassert"
#include "iostream"

void test_vertex(){
    Graph g = Graph();
    assert(g.get_no_vertices() == 0);
    g.add_vertex(1);
    g.add_vertex(5);
    g.add_vertex(2);
    assert(g.get_no_vertices() == 3);
    g.remove_vertex(1);
    assert(g.get_no_vertices() == 2);

    bool except = false;
    try{
        g.add_vertex(5);
    }
    catch (...){
        except = true;
    }
    assert(except == true);

    except = false;
    try{
        g.remove_vertex(1);
    }
    catch (...){
        except = true;
    }
    assert(except == true);
}

void test_edges(){
    Graph g(6);
    g.add_edge(1, 4, 30);
    g.add_edge(1, 5, 25);
    g.add_edge(2, 3, 20);
    g.add_edge(2, 4, 15);
    g.add_edge(4, 5, 10);
    assert(g.get_no_edges() == 5);

    g.remove_edge(2, 3);
    assert(g.get_no_edges() == 4);

    bool except = false;
    try{
        g.add_edge(1, 4, 1);
    }
    catch (...){
        except = true;
    }
    assert(except == true);

    except = false;
    try{
        g.remove_edge(2, 3);
    }
    catch (...){
        except = true;
    }
    assert(except == true);
}

void test_is_edge(){
    Graph g(6);
    g.add_edge(1, 4, 30);
    g.add_edge(1, 5, 25);
    g.add_edge(2, 3, 20);
    g.add_edge(2, 4, 15);
    g.add_edge(4, 5, 10);
    g.add_edge(5, 3, 5);
    g.add_edge(3, 5);

    assert(g.is_edge(std::make_pair(1, 4)) == true);
    assert(g.is_edge(std::make_pair(4, 1)) == false);
}

void test_in_out(){
    Graph g(6);
    g.add_edge(1, 4, 30);
    g.add_edge(1, 5, 25);
    g.add_edge(2, 3, 20);
    g.add_edge(2, 4, 15);
    g.add_edge(4, 5, 10);
    g.add_edge(5, 3, 5);

    assert(g.no_in_vertex(1) == 0);
    assert(g.no_out_vertex(1) == 2);
    assert(g.no_in_vertex(4) == 2);
}

void test_cost(){
    Graph g(6);
    g.add_edge(1, 4, 30);
    g.add_edge(1, 5, 25);
    g.add_edge(2, 3, 20);
    g.add_edge(2, 4, 15);
    g.add_edge(4, 5, 10);
    g.add_edge(5, 3, 5);
    assert(g.get_edge_cost(1, 4) == 30);
    g.set_edge_cost(1, 4, 35);
    assert(g.get_edge_cost(1, 4) == 35);
}

void test_copy(){
    Graph g1(6);
    Graph g2 = g1;
    g1.add_vertex(6);
    assert(g2.get_no_vertices() != g1.get_no_vertices());
}

void test_all(){
    test_vertex();
    test_edges();
    test_is_edge();
    test_in_out();
    test_cost();
    test_copy();
}

