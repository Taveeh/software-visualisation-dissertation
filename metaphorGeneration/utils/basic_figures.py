import matplotlib.patches as patches


def draw_parallelogram(ax, x, y, point1, point2, point3, edge_color='b', face_color=None):
    x1, y1 = x, y
    x2, y2 = point1[0], point1[1]
    x3, y3 = point2[0], point2[1]
    x4, y4 = point3[0], point3[1]
    parallelogram = patches.Polygon(
        [(x1, y1), (x2, y2), (x3, y3), (x4, y4)],
        closed=True,
        fill=None,
        edgecolor=edge_color,
        facecolor=face_color
    )

    ax.add_patch(parallelogram)


def draw_rectangle(ax, x, y, width, height, edge_color='b', face_color=None):
    rectangle = patches.Rectangle((x, y), width, height, fill=face_color, edgecolor=edge_color,
                                  facecolor=face_color)
    ax.add_patch(rectangle)


def draw_circle(ax, x, y, radius, edge_color='b', face_color=None):
    circle = patches.Circle((x, y), radius, fill=face_color, edgecolor=edge_color,
                            facecolor=face_color)
    ax.add_patch(circle)


def draw_triangle(ax, x, y, vector1, vector2, edge_color='b', face_color=None):
    x1, y1 = x, y
    x2, y2 = x + vector1[0], y + vector1[1]
    x3, y3 = x + vector2[0], y + vector2[1]
    triangle = patches.Polygon([(x1, y1), (x2, y2), (x3, y3)], closed=True, fill=face_color, edgecolor=edge_color,
                               facecolor=face_color)
    ax.add_patch(triangle)


# fig = plt.figure()
# ax = fig.subplots()
# draw_parallelogram(ax, 0, 0, (2, 3), (4, 1), edge_color='r')
# draw_rectangle(ax, 0, 0, -2, -3, edge_color='g')
# draw_circle(ax, 0, 0, 2, edge_color='b')
# draw_triangle(ax, 0, 0, (5, 3), (2, 7), edge_color='y')
# ax.set_xlim(-3, 15)
# ax.set_ylim(-3, 15)
# ax.axis("off")
# plt.gca().set_aspect('equal', adjustable='box')
# plt.show()
