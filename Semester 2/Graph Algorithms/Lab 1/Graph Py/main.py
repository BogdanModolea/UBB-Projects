from Graph import *

class UI:
    def __init__(self):
        self.graph = None

    def crete_empty(self):
        self.graph = Graph()

    def create_graph_vertex(self):
        n = int(input("Enter the number of vertices: "))
        try:
            self.graph = Graph(n)
        except Exception as ex:
            print(ex)

    def create_graph_edges(self):
        n = int(input("Enter the number of vertices: "))
        m = int(input("Enter the number of random edges: "))
        try:
            self.graph = Graph(n, m)
        except Exception as ex:
            print(ex)

    def add_vertex(self):
        node = int(input("Enter the vertex you want to add: "))
        try:
            self.graph.add_vertex(node)
        except Exception as ex:
            print(ex)

    def remove_vertex(self):
        node = int(input("Enter the vertex you want to remove: "))
        try:
            self.graph.remove_vertex(node)
        except Exception as ex:
            print(ex)

    def add_edge(self):
        node1 = int(input("Enter the first vertex: "))
        node2 = int(input("Enter the second vertex: "))
        cost = int(input("Enter the cost between those vertices: "))
        try:
            self.graph.add_edge(node1, node2, cost)
        except Exception as ex:
            print(ex)

    def remove_edge(self):
        node1 = int(input("Enter the first vertex: "))
        node2 = int(input("Enter the second vertex: "))
        try:
            self.graph.remove_edge(node1, node2)
        except Exception as ex:
            print(ex)

    def change_cost(self):
        node1 = int(input("Enter the first vertex: "))
        node2 = int(input("Enter the second vertex: "))
        cost = int(input("Enter the new cost between those vertices: "))
        try:
            self.graph.set_edge_cost(node1, node2, cost)
        except Exception as ex:
            print(ex)

    def print_cost_edge(self):
        node1 = int(input("Enter the first vertex: "))
        node2 = int(input("Enter the second vertex: "))
        try:
            print("The cost between the two vertices is {0}".format(self.graph.get_edge_cost(node1, node2)))
        except Exception as ex:
            print(ex)

    def IN(self):
        node = int(input("Enter the vertex: "))
        try:
            print(self.graph.get_in(node))
        except Exception as ex:
            print(ex)

    def out(self):
        node = int(input("Enter the vertex: "))
        try:
            print(self.graph.get_out(node))
        except Exception as ex:
            print(ex)

    def no_of_vertices(self):
        print("There are {0} vertices.".format(self.graph.get_no_vertices()))

    def no_of_edges(self):
        print("There are {0} edges.".format(self.graph.get_no_edges()))

    def is_vertex(self):
        node = int(input("Enter the vertex: "))
        try:
            if self.graph.is_vertex(node):
                print("Vertex {0} belongs to the graph.".format(node))
            else:
                print("Sorry, but it doesn't belong to the graph.")
        except Exception as ex:
            print(ex)

    def is_edge(self):
        node1 = int(input("Enter the first vertex: "))
        node2 = int(input("Enter the second vertex: "))
        try:
            if self.graph.is_edge(node1, node2):
                print("There is an edge between those vertices.")
            else:
                print("There is no edge between those vertices.")
        except Exception as ex:
            print(ex)

    def print_vertex(self):
        for node in self.graph.vertex_iterator():
            print(node, end=" ")
        print()

    def print_out(self):
        node = int(input("Enter the vertex: "))
        try:
            found = False
            for node in self.graph.out_iterator(node):
                print(node, end=" ")
                found = True
            if not found:
                print("Vertex {0} has no outbound edges.")
            else:
                print()
        except Exception as ex:
            print(ex)

    def print_in(self):
        node = int(input("Enter the vertex: "))
        try:
            found = False
            for node in self.graph.in_iterator(node):
                print(node, end=" ")
                found = True
            if not found:
                print("Vertex {0} has no inbound edges.")
            else:
                print()
        except Exception as ex:
            print(ex)

    def print_edges(self):
        found = False
        for edge in self.graph.edge_iterator():
            print("Edge ({0}, {1}) has cost {2}.".format(edge[0], edge[1], edge[2]))
            found = True
        if not found:
            print("There are no edges.")

    def read(self):
        try:
            self.graph = read("graph1k.txt")
        except Exception as ex:
            print(ex)

    def write(self):
        try:
            write("random_graph2.txt", self.graph)
        except Exception as ex:
            print(ex)

    def menu(self):
        cmd = {"1": self.crete_empty,
               "2": self.create_graph_vertex,
               "3": self.create_graph_edges,
               "4": self.add_vertex,
               "5": self.remove_vertex,
               "6": self.add_edge,
               "7": self.remove_edge,
               "8": self.change_cost,
               "9": self.print_cost_edge,
               "10": self.IN,
               "11": self.out,
               "12": self.no_of_vertices,
               "13": self.no_of_edges,
               "14": self.is_vertex,
               "15": self.is_edge,
               "16": self.print_vertex,
               "17": self.print_in,
               "18": self.print_out,
               "19": self.print_edges,
               "20": self.read,
               "21": self.write
               }

        while True:
            print("1. Generate an empty graph\n"
                  "2. Generate a graph with n vertices\n"
                  "3. Generate a graph with n vertices and m random edges\n"
                  "4. Add a vertex\n"
                  "5. Remove a vertex\n"
                  "6. Add an edge\n"
                  "7. Remove an edge\n"
                  "8. Change the cost of an edge\n"
                  "9. Print the cost of an edge\n"
                  "10. Print the in degree of a vertex\n"
                  "11. Print the out degree of a vertex\n"
                  "12. Print the number of vertices\n"
                  "13. Print the number of edges\n"
                  "14. Check if a vertex is in graph\n"
                  "15. Check if an edge is in graph\n"
                  "16. Print the list of vertices\n"
                  "17. Print the inbound vertices of a given vertex\n"
                  "18. Print the outbound vertices of a given vertex\n"
                  "19. Print the list of edges\n"
                  "20. Read a graph from the file\n"
                  "21. Write the graph in the file\n"
                  "0. Exit"
                  )
            com = input("Enter a command >")
            if com == "0":
                break
            elif com in cmd:
                cmd[com]()
            else:
                print("Invalid command")

            print()


UI().menu()