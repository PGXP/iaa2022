import math
import dists

def a_star(start, goal='Bucharest'):
    open_cities = set([start])
    closed_cities = set()

    f = {city: math.inf for city in dists.cities.keys()}
    g = {city: math.inf for city in dists.cities.keys()}

    f[start] = dists.straight_line_dists_from_bucharest[start]
    g[start] = 0

    came_from = dict()

    while open_cities:
        current_city = min(open_cities, key=lambda c: f[c])
    
        if current_city == goal:
            path = []
            city = current_city
            path.append(current_city)
            while city != start:
                path.append(came_from[city])
                city = came_from[city]

            path.reverse()
            return(path)

        open_cities.remove(current_city)
        closed_cities.add(current_city)

        neighbors = [(c, d) for c, d
                     in dists.cities[current_city]
                     if c not in closed_cities]

        for neighbor, dist in neighbors:
            new_g = g[current_city] + dist

            if new_g < g[neighbor]:
                g[neighbor] = new_g
                f[neighbor] = new_g + dists.straight_line_dists_from_bucharest[neighbor]
                came_from[neighbor] = current_city

            open_cities.add(neighbor)

a_star('Lugoj', 'Bucharest')