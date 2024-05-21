from reader.Reader import Reader
from preparedata.Processor import Processor

if __name__ == '__main__':
    reader = Reader('../files/results-19_5-22_26.json')
    processor = Processor(reader.read())
    processor.draw_metrics()
