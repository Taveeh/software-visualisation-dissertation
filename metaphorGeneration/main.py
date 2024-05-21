from reader.Reader import Reader
from preparedata.Processor import Processor

if __name__ == '__main__':
    reader = Reader('../files/results-20_5-18_26.json')
    processor = Processor(reader.read())
    processor.draw_metrics()
