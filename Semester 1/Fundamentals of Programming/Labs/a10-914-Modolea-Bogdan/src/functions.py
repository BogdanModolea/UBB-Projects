from copy import deepcopy

def sort(something, key = None, function = None, reverse = False):
    def default_key(obj):
        return obj

    def cmp(obj1, obj2):
        return obj1 <= obj2

    new_something = deepcopy(something)

    if key == None:
        key = default_key

    if function == None:
        function = cmp

    #shell sort

    gap = len(new_something) // 2

    while gap > 0:
        i = 0
        j = gap

        while j < len(new_something):
            if not function(key(new_something[i]), key(new_something[j])):
                new_something[i], new_something[j] = new_something[j], new_something[i]

            i += 1
            j += 1

            k = i
            while k - gap > -1:
                if not function(key(new_something[k - gap]), key(new_something[k])):
                    new_something[k - gap], new_something[k] = new_something[k], new_something[k - gap]
                k -= 1

        gap //= 2


    if reverse:
        i = 0
        j = len(new_something) - 1
        while i < j:
            new_something[i], new_something[j] = new_something[j], new_something[i]
            i += 1
            j -= 1


    return new_something


def some_filter(something, check):
    new_something = []
    for another in something:
        if check(another):
            new_something.append(another)

    return new_something


class Container:
    def __init__(self, new_dict = None):
        if new_dict == None:
            new_dict = {}
        self._something = new_dict

    def get_data(self):
        return self._something

    def __setitem__(self, key, value):
        self._something[key] = value

    def __getitem__(self, item):
        return self._something[item]

    def __delitem__(self, key):
        del self._something[key]

    def __next__(self):
        self.key += 1
        return self._something[self.key]

    def __iter__(self):
        self.key = 0
        return self

