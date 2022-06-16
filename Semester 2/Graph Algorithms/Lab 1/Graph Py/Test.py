import unittest
from Graph import *

class GraphTest(unittest.TestCase):
    def setUp(self) -> None:
        pass

    def tearDown(self) -> None:
        pass


    """========================  VERTICES  ========================"""


    def test_vertex(self):
        g = Graph()
        self.assertEqual(g.get_no_vertices(), 0)
        g.add_vertex(1)
        g.add_vertex(5)
        g.add_vertex(2)
        self.assertEqual(g.get_no_vertices(), 3)
        g.remove_vertex(1)
        self.assertEqual(g.get_no_vertices(), 2)
        with self.assertRaises(Exception):
            g.add_vertex(5)
        with self.assertRaises(Exception):
            g.remove_vertex(1)

    def test_parse_vertex(self):
        g = Graph()
        g.add_vertex(1)
        g.add_vertex(2)
        g.add_vertex(3)
        v = set(g.vertex_iterator())
        self.assertEqual(v, {1, 2, 3})

    """========================  EDGES  ========================"""


    def test_edges(self):
        g = Graph(6)
        g.add_edge(1, 4, 30)
        g.add_edge(1, 5, 25)
        g.add_edge(2, 3, 20)
        g.add_edge(2, 4, 15)
        g.add_edge(4, 5, 10)
        #g.add_edge(5, 3, 5)
        self.assertEqual(g.get_no_edges(), 5)

        g.remove_edge(2, 3)
        self.assertEqual(g.get_no_edges(), 4)
        with self.assertRaises(Exception):
            g.add_edge(1, 4)
        with self.assertRaises(Exception):
            g.remove_edge(2, 3)

    def test_is_edge(self):
        g = Graph(6)
        g.add_edge(1, 4, 30)
        g.add_edge(1, 5, 25)
        g.add_edge(2, 3, 20)
        g.add_edge(2, 4, 15)
        g.add_edge(4, 5, 10)
        g.add_edge(5, 3, 5)

        self.assertTrue(g.is_edge(1, 4))
        self.assertFalse(g.is_edge(4, 1))

    def test_in_out(self):
        g = Graph(6)
        g.add_edge(1, 4, 30)
        g.add_edge(1, 5, 25)
        g.add_edge(2, 3, 20)
        g.add_edge(2, 4, 15)
        g.add_edge(4, 5, 10)
        g.add_edge(5, 3, 5)

        self.assertEqual(g.get_in(1), 0)
        self.assertEqual(g.get_out(1), 2)
        self.assertEqual(g.get_in(4), 2)

    def test_parse_edge(self):
        g = Graph(6)
        g.add_edge(1, 4, 30)
        g.add_edge(1, 5, 25)
        g.add_edge(2, 3, 20)
        g.add_edge(2, 4, 15)
        g.add_edge(4, 5, 10)
        g.add_edge(5, 3, 5)

        v = set(g.out_iterator(1))
        self.assertEqual(v, {4, 5})

        v = set(g.in_interator(4))
        self.assertEqual(v, {1, 2})

    def test_cost(self):
        g = Graph(6)
        g.add_edge(1, 4, 30)
        g.add_edge(1, 5, 25)
        g.add_edge(2, 3, 20)
        g.add_edge(2, 4, 15)
        g.add_edge(4, 5, 10)
        g.add_edge(5, 3, 5)
        self.assertEqual(g.get_edge_cost(1, 4), 30)
        g.set_edge_cost(1, 4, 35)
        self.assertEqual(g.get_edge_cost(1, 4), 35)

    def test_copy(self):
        g = Graph(3, 5)
        G = g.copy()
        G.remove_vertex(2)
        v = set(g.vertex_iterator())
        a = set(G.vertex_iterator())
        self.assertEqual(v, {0, 1, 2})
        self.assertEqual(a, {0, 1})