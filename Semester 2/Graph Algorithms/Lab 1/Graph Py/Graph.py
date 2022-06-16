import random
from copy import deepcopy

def read(file_name):
    """
    Reads a graph from the file
    """
    file = open(file_name, "r")
    n, m = map(int, file.readline().split())
    G = Graph(n)
    for j in range(m):
        vertex1, vertex2, cost = map(int, file.readline().split())
        G.add_edge(vertex1, vertex2, cost)

    file.close()
    return G


def write(file_name, G):
    """
        Writes a graph into a file
    """
    file = open(file_name, "w")
    file.write("{0} {1}\n".format(G.get_no_vertices(), G.get_no_edges()))

    for i in G.vertex_iterator():
        for j in G.out_iterator(i):
            file.write("{0} {1} {2}\n".format(i, j, G.get_edge_cost(i, j)))

    file.close()


def rand_graph(n, m):
    """
        Generates a random graph with n vertices and m edges
    """
    g = Graph()
    for i in range(n):
        g.add_vertex(i)

    for j in range(m):
        vertex1 = random.randint(0, n - 1)
        vertex2 = random.randint(0, n - 1)
        while g.is_edge(vertex1, vertex2):
            vertex1 = random.randint(0, n - 1)
            vertex2 = random.randint(0, n - 1)
        cost = random.randint(0, 10000)
        g.add_edge(vertex1, vertex2, cost)


class Graph:
    def __init__(self, n = 0, m = 0):
        self._vertices = list()
        self._outEdges = dict()
        self._inEdges = dict()
        self._costs = dict()

        for i in range(n):
            self.add_vertex(i)

        for j in range(m):
            vertex1 = random.randint(0, n - 1)
            vertex2 = random.randint(0, n - 1)
            while self.is_edge(vertex1, vertex2):
                vertex1 = random.randint(0, n - 1)
                vertex2 = random.randint(0, n - 1)
            cost = random.randint(0, 10000)
            self.add_edge(vertex1, vertex2, cost)



    def copy(self):
        return deepcopy(self)


    """========================  VERTICES  ========================"""


    def get_no_vertices(self):
        """
            Returns the total number of vertices
        """
        return len(self._vertices)

    def add_vertex(self, vertex):
        """
            Adds a vertex to the graph
        """
        if vertex in self._vertices:
            raise Exception()
        self._vertices.append(vertex)
        self._inEdges[vertex] = set()
        self._outEdges[vertex] = set()

    def is_vertex(self, vertex):
        """
            Checks if a vertex exists in the graph or not
        """
        return vertex in self._vertices


    def no_in_vertex(self, vertex):
        """
            Returns the number of inbound vertices
        """
        return len(self._inEdges[vertex])


    def no_out_vertex(self, vertex):
        """
            Returns the number of outbound vertices
        """
        return len(self._outEdges[vertex])


    def remove_vertex(self, vertex):
        """
            Removes a vertex from the graph
        """
        edges = []
        for node in self._outEdges[vertex]:
            edges.append(node)
        for node in edges:
            self.remove_edge(vertex, node)

        edges = []
        for node in self._inEdges[vertex]:
            edges.append(node)
        for node in edges:
            self.remove_edge(node, vertex)

        del self._inEdges[vertex]
        del self._outEdges[vertex]
        self._vertices.remove(vertex)


    def vertex_iterator(self):
        """
            Returns an interator to the set of vertices
        """
        for vertex in self._vertices:
            yield vertex



    """========================  EDGES  ========================"""


    def is_edge(self, vertex1, vertex2):
        """
            Checks if 2 vertices forms an edge
        """
        return vertex1 in self._outEdges and vertex2 in self._outEdges[vertex1]

    def add_edge(self, vertex1, vertex2, cost = 0):
        """
            Adds an edge to the graph
        """
        if self.is_edge(vertex1, vertex2):
            raise Exception()
        if not self.is_vertex(vertex1) or not self.is_vertex(vertex2):
            raise Exception()

        self._outEdges[vertex1].add(vertex2)
        self._inEdges[vertex2].add(vertex1)
        self._costs[(vertex1, vertex2)] = cost

    def get_no_edges(self):
        """
            Returns the number of edges
        """
        return len(self._costs)

    def get_edge_cost(self, vertex1, vertex2):
        """
            Returns the cost of an edge
        """
        return self._costs[(vertex1, vertex2)]

    def set_edge_cost(self, vertex1, vertex2, cost):
        """
            Sets the cost of an edge
        """
        if(vertex1, vertex2) not in self._costs:
            raise Exception()
        self._costs[(vertex1, vertex2)] = cost

    def remove_edge(self, vertex1, vertex2):
        """
            Removes an edge from the graph
        """
        del self._costs[(vertex1, vertex2)]
        self._outEdges[vertex1].remove(vertex2)
        self._inEdges[vertex2].remove(vertex1)

    def edge_iterator(self):
        """
            Returns an iterator to the set of edges
        """
        for key, value in self._costs.items():
            yield key[0], key[1], value

    def out_iterator(self, vertex):
        """
            Returns an iterator to the set of outbound vertices
        """
        if not self.is_vertex(vertex):
            raise Exception()

        for out in self._outEdges[vertex]:
            yield out

    def in_interator(self, vertex):
        """
            Returns an iterator to the set of inbound vertices
        """
        if not self.is_vertex(vertex):
            raise Exception()

        for IN in self._inEdges[vertex]:
            yield IN

    def get_in(self, vertex):
        """
            Returns the number of inbound vertices for a specific vertex
        """
        if vertex not in self._inEdges:
            raise Exception()

        return len(self._inEdges[vertex])

    def get_out(self, vertex):
        """
            Returns the number of outbound vertices for a specific vertex
        """
        if vertex not in self._outEdges:
            raise Exception()

        return len(self._outEdges[vertex])
