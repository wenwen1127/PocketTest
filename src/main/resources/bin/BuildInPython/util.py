from BuildInPython.ConvertType import *
from BuildInPython.python3 import *

from collections import OrderedDict


TRUE_STRINGS = {'TRUE', 'YES', 'ON', '1'}
FALSE_STRINGS = {'FALSE', 'NO', 'OFF', '0', 'NONE', ''}

def roundup(number, ndigits=0, return_type=None):
    """Rounds number to the given number of digits.
    Numbers equally close to a certain precision are always rounded away from
    zero. By default return value is float when ``ndigits`` is positive and
    int otherwise, but that can be controlled with ``return_type``.
    With the built-in ``round()`` rounding equally close numbers as well as
    the return type depends on the Python version.
    """
    result = round(number, ndigits)
    if not return_type:
        return_type = float if ndigits > 0 else int
    return return_type(result)

def is_truthy(item):
    """Returns `True` or `False` depending is the item considered true or not.
    Validation rules:
    - If the value is a string, it is considered false if it is `'FALSE'`,
      `'NO'`, `'OFF'`, `'0'`, `'NONE'` or `''`, case-insensitively.
      Considering `'NONE'` false is new in RF 3.0.3 and considering `'OFF'`
      and `'0'` false is new in RF 3.1.
    - Other strings are considered true.
    - Other values are handled by using the standard `bool()` function.
    Designed to be used also by external test libraries that want to handle
    Boolean values similarly as Robot Framework itself. See also
    :func:`is_falsy`.
    """
    if isinstance(item, (str, unicode)):
        return item.upper() not in FALSE_STRINGS
    return bool(item)

def _get_base(self, item, base):
    if not is_string(item):
        return item, base
    item = normalize(item)
    if item.startswith(('-', '+')):
        sign = item[0]
        item = item[1:]
    else:
        sign = ''
    bases = {'0b': 2, '0o': 8, '0x': 16}
    if base or not item.startswith(tuple(bases)):
        return sign + item, base
    return sign + item[2:], bases[item[:2]]



class DotDict(OrderedDict):
    def __init__(self, *args, **kwds):
        args = [self._convert_nested_initial_dicts(a) for a in args]
        kwds = self._convert_nested_initial_dicts(kwds)
        OrderedDict.__init__(self, *args, **kwds)

    def _convert_nested_initial_dicts(self, value):
        items = value.items() if is_dict_like(value) else value
        return OrderedDict((key, self._convert_nested_dicts(value))
                           for key, value in items)

    def _convert_nested_dicts(self, value):
        if isinstance(value, DotDict):
            return value
        if is_dict_like(value):
            return DotDict(value)
        if isinstance(value, list):
            value[:] = [self._convert_nested_dicts(item) for item in value]
        return value

    def __getattr__(self, key):
        try:
            return self[key]
        except KeyError:
            raise AttributeError(key)

    def __setattr__(self, key, value):
        if not key.startswith('_OrderedDict__'):
            self[key] = value
        else:
            OrderedDict.__setattr__(self, key, value)

    def __delattr__(self, key):
        try:
            self.pop(key)
        except KeyError:
            OrderedDict.__delattr__(self, key)

    def __eq__(self, other):
        return dict.__eq__(self, other)

    def __ne__(self, other):
        return not self == other

    def __str__(self):
        return '{%s}' % ', '.join('%r: %r' % (key, self[key]) for key in self)

    # Must use original dict.__repr__ to allow customising PrettyPrinter.
    __repr__ = dict.__repr__

class VariableTableValueBase(object):

    def __init__(self, values, error_reporter=None):
        self._values = self._format_values(values)
        self._error_reporter = error_reporter
        self._resolving = False

    def _format_values(self, values):
        return values

    def resolve(self, variables):
        with self._avoid_recursion:
            return self._replace_variables(self._values, variables)

    def _avoid_recursion(self):
        if self._resolving:
            raise ('Recursive variable definition.')
        self._resolving = True
        try:
            yield
        finally:
            self._resolving = False

    def _replace_variables(self, value, variables):
        raise NotImplementedError
