from enum import Enum


class Colour(Enum):
    PINK = 0
    YELLOW = 1
    LIGHTGREEN = 2
    GREEN = 3
    BLUE = 4
    BROWN = 5
    GREY = 6
    PURPLE = 7
    ORANGE = 8
    DARKORANGE = 9
    RED = 10
    BLACK = 11

    def __str__(self):
        return self.name

