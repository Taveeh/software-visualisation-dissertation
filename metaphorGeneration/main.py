from reader.Reader import Reader
from preparedata.Processor import Processor
import sys

if __name__ == '__main__':
    should_show = True
    print(sys.argv)
    if len(sys.argv) == 1:
        reader = Reader('../files/results-19_5-22_26.json')
    else:
        print('Arguments: ', sys.argv[1])
        reader = Reader(sys.argv[1])
        should_show = False

    processor = Processor(reader.read(), should_show)
    processor.draw_metrics()
