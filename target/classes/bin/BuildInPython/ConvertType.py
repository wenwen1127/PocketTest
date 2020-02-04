from collections import Mapping, MutableMapping
from UserDict import UserDict
from UserString import UserString
from types import ClassType, NoneType
import json

try:
    from java.lang import String
except ImportError:
    String = ()

def is_integer(item):
    return isinstance(item, (int, long))


def is_number(item):
    return isinstance(item, (int, long, float))


def is_bytes(item):
    return isinstance(item, (bytes, bytearray))


def is_string(item):
    # Returns False with `b'bytes'` on IronPython on purpose. Results of
    # `isinstance(item, basestring)` would depend on IronPython 2.7.x version.
    return isinstance(item, (str, unicode))

def is_unicode(item):
    return isinstance(item, unicode)

def is_pathlike(item):
    return False
def is_list_like(item):
    if isinstance(item, (str, unicode, bytes, bytearray, UserString, String,
                         file)):
        return False
    try:
        iter(item)
    except:
        return False
    else:
        return True


def is_dict_like(item):
    return isinstance(item, (Mapping, UserDict))


def type_name(item):
    cls = item.__class__ if hasattr(item, '__class__') else type(item)
    named_types = {str: 'string', unicode: 'string', bool: 'boolean',
                   int: 'integer', long: 'integer', NoneType: 'None',
                   dict: 'dictionary', type: 'class', ClassType: 'class'}
    return named_types.get(cls, cls.__name__)

def get_type(item):
    if not is_string(item) or len(item) < 4:
        return False
    if str(item)[0] == "$" and str(item)[1] == "{" and str(item)[-1] == "}":
        if str(item).replace(" ", '')[2:-1] .isdigit():
            return "int"
        else:
            return "variable"
    if not "{" in str(item) and not "}" in str(item):
        return "str"
    if str(item).strip()[0] == "@" and str(item).replace(" ", '')[1] == "{" and str(item).strip()[-1] == "}":
        return "list"
    if str(item).strip()[0] == "&" and str(item).replace(" ", '')[1] == "{" and str(item).strip()[-1] == "}":
        return "dict"

def convert_type(item, type):
    if type == "int":
        return int(item.strip("$|{|}"))
    if type == "str":
        return  str(item)
    if type == "variable" or type == "dict" or type == "list" :
        return eval(item)



if __name__ == '__main__':
    # print is_dict_like({"a":2, "b":"ww"})
    # print is_list_like({1,2,3})
    print type_name('True')
    # print get_type("${22345}")
    # aa = 12
    # list = {'a', 'b', 'c'}
    #
    # print convert_type('12' ,"int")
    # print convert_type("${aa}", "variable")
    # print create_dictionary()
    # print create_list(1,'2', 3)