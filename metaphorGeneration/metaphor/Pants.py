from matplotlib import pyplot as plt

from utils.basic_figures import draw_rectangle
from metaphor.Clothing import Clothing


class Pants(Clothing):
    def __init__(self, name: str, metrics_dict: dict):
        super().__init__(name, metrics_dict)

    def __str__(self):
        return f"Pants: {super().__str__()}"

    def get_max_height(self) -> int:
        return self.get_body_size() + max(self.get_left_sleeve_length(), self.get_right_sleeve_length())

    def get_max_width(self) -> int:
        return self.get_body_size()

    def draw(self, image: plt.Axes, starting_point_x: int, starting_point_y: int, index: int):
        body_width = self.get_body_size()
        body_height = self.get_body_size() / 3

        draw_rectangle(
            image,
            starting_point_x,
            starting_point_y,
            body_width,
            body_height,
            edge_color=self.get_color().name.lower(),
        )
        draw_rectangle(
            image,
            starting_point_x,
            starting_point_y + body_height,
            body_width / 2,
            self.get_left_sleeve_length(),
            edge_color=self.get_color().name.lower()
        )
        draw_rectangle(
            image,
            starting_point_x + body_width / 2,
            starting_point_y + body_height,
            body_width / 2,
            self.get_right_sleeve_length(),
            edge_color=self.get_color().name.lower()
        )

        image.text(
            starting_point_x + self.get_body_size() / 2,
            starting_point_y + self.get_max_height() + 1,
            str(index),
            fontsize=5,
            color='black',
            ha='center',
        )

        stripe_length = body_width / 12
        for i in range(self.get_design()):
            self.draw_stripes(
                image,
                starting_point_x + (3 * i + 1) * body_width / 6 + stripe_length / 2,
                starting_point_y,
                stripe_length,
                body_height + (self.get_left_sleeve_length() if i == 0 else self.get_right_sleeve_length()),
            )

    def draw_stripes(self, image: plt.Axes, starting_point_x: float, starting_point_y: float, length: float,
                     width: float):
        draw_rectangle(image, starting_point_x, starting_point_y, length, width,
                       edge_color=self.get_color().name.lower(), face_color=self.get_color().name.lower())
