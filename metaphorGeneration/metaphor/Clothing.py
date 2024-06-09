from matplotlib import pyplot as plt

import utils.metric_names as metrics
from utils.ClothingType import ClothingType
from utils.Colour import Colour
from utils.basic_figures import draw_rectangle


class Clothing:
    def __init__(self, name: str, metrics_dict: dict):
        self._name = name
        self._metrics = metrics_dict

    @property
    def get_name(self):
        return self._name

    @property
    def get_metrics(self):
        return self._metrics

    def __str__(self):
        return f"Name: {self._name}, Metrics: {self._metrics}"

    def get_left_sleeve_length(self) -> int:
        return self._metrics[metrics.NUMBER_OF_CLASS_MEMBERS]

    def get_right_sleeve_length(self) -> int:
        return self._metrics[metrics.NUMBER_OF_METHODS]

    def get_color(self) -> Colour:
        return Colour(min(self._metrics[metrics.NUMBER_OF_CHILD_CLASSES], 11))

    def get_body_size(self) -> int:
        # # if self._metrics[metrics.COUPLING_BETWEEN_OBJECTS] == 0:
        # return self._metrics[metrics.DEPTH_OF_INHERITANCE_TREE]
        # # return self._metrics[metrics.COUPLING_BETWEEN_OBJECTS]
        if self._metrics[metrics.LINES_OF_CODE] < 300:
            return 10
        if 300 <= self._metrics[metrics.LINES_OF_CODE] < 500:
            return 20
        if 500 <= self._metrics[metrics.LINES_OF_CODE] < 1000:
            return 30
        return 45

    def get_max_height(self) -> int:
        pass

    def get_max_width(self) -> int:
        pass

    def get_design(self) -> int:
        if self._metrics[metrics.COUPLING_BETWEEN_OBJECTS] == 0:
            return 0
        if 0 < self._metrics[metrics.COUPLING_BETWEEN_OBJECTS] < 9:
            return 1
        return 2

    def has_collar(self):
        return self._metrics[metrics.DEPTH_OF_INHERITANCE_TREE] >= 6

    def draw(self, image: plt.Axes, starting_point_x: int, starting_point_y: int, index: int):
        pass

    def draw_stripes(self, image: plt.Axes, starting_point_x: float, starting_point_y: float, length: float,
                     width: float):
        draw_rectangle(
            image,
            starting_point_x,
            starting_point_y,
            length,
            width,
            edge_color=self.get_color().name.lower(),
            face_color=self.get_color().name.lower()
        )

    def draw_collar(self, image: plt.Axes, starting_point_x: int, starting_point_y: int):
        pass
