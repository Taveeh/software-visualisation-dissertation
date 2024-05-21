from matplotlib import pyplot as plt

from metaphor.Clothing import Clothing
from metaphor.TShirt import TShirt


class Shirt(TShirt):
    def __init__(self, name: str, metrics_dict: dict):
        super().__init__(name, metrics_dict)

    def __str__(self):
        return f"Shirt: {super().__str__()}"

    def __draw_buttons(self, image: plt.Axes, starting_point_x: int, starting_point_y: int):
        color = 'chartreuse'
        print(color)
        image.scatter(
            starting_point_x + self.get_body_size() / 2,
            starting_point_y + self.get_body_size() / 6,
            color=color,
            s=10
        )

        image.scatter(
            starting_point_x + self.get_body_size() / 2,
            starting_point_y + self.get_body_size() / 2,
            color=color,
            s=10
        )

        image.scatter(
            starting_point_x + self.get_body_size() / 2,
            starting_point_y + 5 * self.get_body_size() / 6,
            color=color,
            s=10
        )

    def draw(self, image: plt.Axes, starting_point_x: int, starting_point_y: int, index: int):
        print("Drawing Shirt --->", self._name)
        super().draw(image, starting_point_x, starting_point_y, index)
        self.__draw_buttons(image, starting_point_x, starting_point_y)



