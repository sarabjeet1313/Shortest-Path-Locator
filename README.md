# Shortest-Path-Locator

The program maintains a map of a city comprising intersections and roads connecting
the intersections , in a graph structure and provide the functionality to find the shortest
path between any two inter sections in a city using Dijkstra’s algorithm
To add a new intersection in a city , the program offers a function. 
User provides the x, y coordinate for the intersection and the program will consider them
to add it in a new intersection list. The program also allows the functionality of adding
roads between the existing intersections by providing coordinates in the defineRoad function.
Once the connections between intersections are made, user can call the navigate
function with source and destination intersection coordinates to get the shortest route
between them. The path output will be printed on screen with each intersection on
separate line and coordinates as tab separated.
