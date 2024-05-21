from math import sqrt

from matplotlib import pyplot as plt

from utils.basic_figures import *
from metaphor.Clothing import Clothing


class TShirt(Clothing):
    def __init__(self, name: str, metrics_dict: dict):
        super().__init__(name, metrics_dict)

    def __str__(self):
        return f"T-Shirt: {super().__str__()}"

    def get_max_height(self) -> int:
        return self.get_body_size()

    def get_max_width(self) -> int:
        return self.get_body_size() + self.get_left_sleeve_length() + self.get_right_sleeve_length()

    def draw(
            self,
            image,
            starting_point_x: int,
            starting_point_y: int,
            index: int
    ):

        draw_rectangle(
            image,
            starting_point_x,
            starting_point_y,
            self.get_body_size(),
            self.get_body_size(),
            edge_color=self.get_color().name.lower()
        )

        self.draw_left_sleeve(image, starting_point_x, starting_point_y)
        self.draw_right_sleeve(image, starting_point_x + self.get_body_size(), starting_point_y)
        for i in range(self.get_design()):
            self.draw_stripes(
                image,
                starting_point_x,
                starting_point_y + (2 * i + 1) * self.get_body_size() / 6 + self.get_body_size() / 12,
                self.get_body_size(),
                self.get_body_size() / 6
            )

        self.draw_collar(image, starting_point_x, starting_point_y)

        image.text(
            starting_point_x + self.get_body_size() / 2,
            starting_point_y + self.get_body_size() + 3,
            str(index),
            fontsize=5,
            color='black',
            ha='center',
        )

    def draw_left_sleeve(self, image, starting_point_x: int, starting_point_y: int):
        point_b_y = min(starting_point_y + self.get_body_size() / 3, starting_point_y + self.get_left_sleeve_length())
        length_on_left = abs(point_b_y - starting_point_y)
        draw_parallelogram(
            image,
            starting_point_x,
            starting_point_y,
            [starting_point_x - (sqrt(3) / 2) * self.get_left_sleeve_length(),
             starting_point_y + self.get_left_sleeve_length() / 2],
            self.compute_point_3_left(starting_point_x, starting_point_y,
                                      self.get_left_sleeve_length() - length_on_left / 2, point_b_y),
            [starting_point_x, point_b_y],
            edge_color=self.get_color().name.lower()
        )

    def draw_right_sleeve(self, image, starting_point_x: int, starting_point_y: int):
        point_b_y = min(starting_point_y + self.get_body_size() / 3, starting_point_y + self.get_right_sleeve_length())
        length_on_right = abs(point_b_y - starting_point_y)
        draw_parallelogram(
            image,
            starting_point_x,
            starting_point_y,
            [starting_point_x + (sqrt(3) / 2) * self.get_right_sleeve_length(),
             starting_point_y + self.get_right_sleeve_length() / 2],
            self.compute_point_3_right(
                starting_point_x,
                starting_point_y,
                self.get_right_sleeve_length() - (length_on_right / 2),
                point_b_y
            ),
            [starting_point_x, point_b_y],
            edge_color=self.get_color().name.lower()
        )

    @staticmethod
    def compute_point_3_left(starting_point_x: int, starting_point_y: int, length, point_b_y):
        return [
            starting_point_x - (sqrt(3) / 2) * length,
            point_b_y + length / 2
        ]

    @staticmethod
    def compute_point_3_right(starting_point_x: int, starting_point_y: int, length, point_b_y):
        return [
            starting_point_x + (sqrt(3) / 2) * length,
            point_b_y + length / 2
        ]

    def draw_collar(self, image: plt.Axes, starting_point_x: int, starting_point_y: int):
        triangle_side_size = self.get_body_size() / 4
        print("Triangle side size: ", triangle_side_size)
        print('Starting point x: ', starting_point_x)
        print('Starting point y: ', starting_point_y)
        draw_triangle(
            image,
            starting_point_x + self.get_body_size() / 2,
            starting_point_y,
            (starting_point_x + self.get_body_size() / 2 - triangle_side_size, starting_point_y),
            (starting_point_x + self.get_body_size() / 2 - triangle_side_size, starting_point_y - triangle_side_size),
            edge_color=self.get_color().name.lower()
        )
        draw_triangle(
            image,
            starting_point_x + self.get_body_size() / 2,
            starting_point_y,
            (starting_point_x + self.get_body_size() / 2 + triangle_side_size, starting_point_y),
            (starting_point_x + self.get_body_size() / 2 + triangle_side_size, starting_point_y - triangle_side_size),
            edge_color=self.get_color().name.lower()
        )


