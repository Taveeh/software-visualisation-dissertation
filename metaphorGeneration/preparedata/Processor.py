import urllib.error

from matplotlib import pyplot as plt
import datetime
import os
from metaphor.Pants import Pants
from metaphor.Shirt import Shirt
from metaphor.TShirt import TShirt
from utils import metric_names as metrics

MAX_BODY_SIZE_PER_IMAGE = 130
MAX_CLASSES_PER_FIGURE = 50


class Processor:
    def __init__(self, metrics_json):
        self.__metrics: dict = {}

        for class_name in metrics_json:
            if len(metrics_json[class_name]) == 2:
                continue
            self.__metrics[class_name] = {}
            for metric in metrics_json[class_name]:
                self.__metrics[class_name][metric] = metrics_json[class_name][metric]
        self.__count = 1
        self.__current_date = datetime.datetime.now()
        print(self.__metrics)

    @property
    def get_metrics(self):
        return self.__metrics

    def define_clothes(self):
        clothes = []
        for class_name in self.__metrics:
            if self.__metrics[class_name][metrics.RESPONSE_FOR_CLASS] < 50:
                clothes.append(TShirt(class_name, self.__metrics[class_name]))
            if 50 <= self.__metrics[class_name][metrics.RESPONSE_FOR_CLASS] < 100:
                clothes.append(Shirt(class_name, self.__metrics[class_name]))
            if self.__metrics[class_name][metrics.RESPONSE_FOR_CLASS] > 100:
                clothes.append(Pants(class_name, self.__metrics[class_name]))
        return clothes

    def draw_one_figure(self, figure_clothes):
        fig, ax = plt.subplots(figsize=(20, 10))
        initial_x = 0
        initial_y = 0
        idx = 0
        legend = {}
        colours = {}
        max_x = 0
        max_body_size = 0
        for cloth in figure_clothes:
            initial_x += cloth.get_left_sleeve_length()
            idx += 1
            legend[idx] = cloth.get_name
            colours[idx] = cloth.get_color().name.lower()
            cloth.draw(ax, initial_x, initial_y, idx)
            if cloth.get_body_size() > max_body_size:
                max_body_size = cloth.get_max_height()
            initial_x += cloth.get_max_width() + 2

            if initial_x > MAX_BODY_SIZE_PER_IMAGE:
                if initial_x > max_x:
                    max_x = initial_x
                initial_x = 0
                initial_y += max_body_size + 10 + cloth.get_body_size() / 4
                max_body_size = 0

        if initial_x > max_x:
            max_x = initial_x
        initial_y += max_body_size + 10
        ax.set_xlim(-5, max_x)
        ax.set_ylim(initial_y, -10)
        legend_elements = [
            plt.Line2D([0], [0], color=colours[idx], lw=2, label=f'{idx}: {legend[idx]}') for idx in legend
        ]
        ax.legend(handles=legend_elements, loc='right', bbox_to_anchor=(-0.4, 0.5), ncol=2)
        ax.set_xlim(-5, max_x)
        ax.set_ylim(initial_y, -10)
        plt.subplots_adjust(left=0.5)
        plt.gca().set_aspect('equal', adjustable='box')

        file_path = f'C:\\Users\\ocustura\\Documents\\Dissertation\\software-visualisation-dissertation\\files\\images\\run-{self.__current_date.day}-{self.__current_date.month}-{self.__current_date.hour}_{self.__current_date.minute}_{self.__current_date.second}'
        os.makedirs(file_path, exist_ok=True)
        plt.savefig(
            f'{file_path}/image-{self.__count}.png')
        self.__count += 1
        if self.__count < 20:
            plt.show()

    def draw_metrics(self):
        clothes = self.define_clothes()
        for i in range(0, len(clothes), MAX_CLASSES_PER_FIGURE):
            self.draw_one_figure(clothes[i:i + MAX_CLASSES_PER_FIGURE])
