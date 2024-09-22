#include <stdio.h>
#include <stdlib.h>

// Structure to represent an edge
struct Edge {
	int src, dest, weight;
};

// Structure to represent a subset for union-find
struct Subset {
	int parent;
	int rank;
};

// Function prototypes
int find(struct Subset subsets[], int i);
void Union(struct Subset subsets[], int x, int y);
int kruskalAlgo(int V, struct Edge edge[]);

// Comparator function for sorting edges based on weight
int compare(const void* a, const void* b) {
	struct Edge* edge1 = (struct Edge*)a;
	struct Edge* edge2 = (struct Edge*)b;
	return edge1->weight - edge2->weight;
}

// Kruskal's algorithm
int kruskalAlgo(int V, struct Edge edge[]) {
	int total_weight = 0; // Total weight of the minimum spanning tree
	struct Edge result[V]; // To store the result
	int e = 0; // Index variable, used for result[]

	// Sort all the edges in non-decreasing order of their weight
	qsort(edge, V, sizeof(edge[0]), compare);

	// Allocate memory for creating V subsets
	struct Subset* subsets = (struct Subset*)malloc(V * sizeof(struct Subset));

	// Initialize subsets
	for (int v = 0; v < V; v++) {
		subsets[v].parent = v;
		subsets[v].rank = 0;
	}

	// Number of edges to be taken is equal to V-1
	while (e < V - 1 && edge[e].weight != 0) {
		// Pick the smallest edge
		struct Edge next_edge = edge[e++];

		int x = find(subsets, next_edge.src);
		int y = find(subsets, next_edge.dest);

		// If including this edge does not cause cycle, include it
		if (x != y) {
			result[e - 1] = next_edge;
			Union(subsets, x, y);
		}
	}

	// Print the contents of result[] to display the built MST
	for (int i = 0; i < e - 1; ++i)
		total_weight += result[i].weight;
	printf("Total weight of the MST: %d\n", total_weight);

	free(subsets);
	return total_weight;
}

// A utility function to find the subset of an element i
int find(struct Subset subsets[], int i) {
	// Find root and make root as parent of i
	if (subsets[i].parent != i)
		subsets[i].parent = find(subsets, subsets[i].parent);

	return subsets[i].parent;
}

// A utility function to do union of two subsets
void Union(struct Subset subsets[], int x, int y) {
	int xroot = find(subsets, x);
	int yroot = find(subsets, y);

	// Attach smaller rank tree under root of high rank tree
	// (Union by Rank)
	if (subsets[xroot].rank < subsets[yroot].rank)
		subsets[xroot].parent = yroot;
	else if (subsets[xroot].rank > subsets[yroot].rank)
		subsets[yroot].parent = xroot;
	else {
		// If ranks are the same, then make one as root and
		// increment its rank by one
		subsets[yroot].parent = xroot;
		subsets[xroot].rank++;
	}
}

// Driver program
int main() {
	struct Edge edge[5] = { {0, 1, 10},
							{0, 2, 6},
							{0, 3, 5},
							{1, 3, 15},
							{2, 3, 4} };

	int V = 4; // Number of vertices in the graph

	kruskalAlgo(V, edge);

	return 0;
}
