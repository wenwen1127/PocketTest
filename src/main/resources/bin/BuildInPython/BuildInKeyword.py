import difflib
import re
import time
from BuildInPython.util import *
import json
RERAISED_EXCEPTIONS = (KeyboardInterrupt, SystemExit, MemoryError)
def convert_to_integer(item):
    try:
        return int(item)
    except:
        raise RuntimeError("'%s' cannot be converted to an integer: %s")

def convert_to_binary(item, base=None, prefix=None, length=None):
    """Converts the given item to a binary string.
    The ``item``, with an optional ``base``, is first converted to an
    integer using `Convert To Integer` internally. After that it
    is converted to a binary number (base 2) represented as a
    string such as ``1011``.
    The returned value can contain an optional ``prefix`` and can be
    required to be of minimum ``length`` (excluding the prefix and a
    possible minus sign). If the value is initially shorter than
    the required length, it is padded with zeros.
    Examples:
    | ${result} = | Convert To Binary | 10 |         |           | # Result is 1010   |
    | ${result} = | Convert To Binary | F  | base=16 | prefix=0b | # Result is 0b1111 |
    | ${result} = | Convert To Binary | -2 | prefix=B | length=4 | # Result is -B0010 |
    See also `Convert To Integer`, `Convert To Octal` and `Convert To Hex`.
    """
    return _convert_to_bin_oct_hex(item,  prefix, length, 'b')

def _convert_to_bin_oct_hex(item, prefix, length, format_spec):
    ret = format(convert_to_integer(item), format_spec)
    prefix = prefix or ''
    if ret[0] == '-':
        prefix = '-' + prefix
        ret = ret[1:]
    if length:
        ret = ret.rjust(convert_to_integer(length), '0')
    return prefix + ret


def convert_to_string(item):
    """Converts the given item to a Unicode string.
    Strings are also [http://www.macchiato.com/unicode/nfc-faq|
    NFC normalized].
    Use `Encode String To Bytes` and `Decode Bytes To String` keywords
    in ``String`` library if you need to convert between Unicode and byte
    strings using different encodings. Use `Convert To Bytes` if you just
    want to create byte strings.
    """
    if isinstance(item, unicode):
        return item
    if isinstance(item, (bytes, bytearray)):
        try:
            return item.decode('ASCII')
        except UnicodeError:
            return u''.join(chr(b) if b < 128 else '\\x%x' % b
                            for b in bytearray(item))


def convert_to_boolean(item):
    """Converts the given item to Boolean true or false.
    Handles strings ``True`` and ``False`` (case-insensitive) as expected,
    otherwise returns item's
    [http://docs.python.org/library/stdtypes.html#truth|truth value]
    using Python's ``bool()`` method.
    """
    if is_string(item):
        if item.upper() == 'TRUE':
            return True
        if item.upper() == 'FALSE':
            return False
    return bool(item)

def create_dictionary(json_str):
    dict = json.load(json_str)
    return dict

def create_list(*items):
    """Returns a list containing given items.
    The returned list can be assigned both to ``${scalar}`` and ``@{list}``
    variables.
    Examples:
    | @{list} =   | Create List | a    | b    | c    |
    | ${scalar} = | Create List | a    | b    | c    |
    | ${ints} =   | Create List | ${1} | ${2} | ${3} |
    """
    return list(items)

def _is_true(condition):
    return convert_to_boolean(condition)

def should_not_be_true(condition, msg=None):
    """Fails if the given condition is true.
    See `Should Be True` for details about how ``condition`` is evaluated
    and how ``msg`` can be used to override the default error message.
    """
    if _is_true(condition):
        raise AssertionError(msg or "'%s' should not be true." % condition)

def should_be_true(condition, msg=None):
    if not _is_true(condition):
        raise AssertionError(msg or "'%s' should be true." % condition)

def should_be_equal(first, second):
    if type_name(first) == type_name(second):
        if is_string(first) and is_string(second):
            first = first.lower()
            second = second.lower()
        _should_be_equal(first, second)
    if type_name(first) == bool or type_name(second) == bool:
        first_bool = convert_to_boolean(first)
        second_bool = convert_to_boolean(second)
        _should_be_equal(first_bool,second_bool)
    elif type_name(first) != type_name(second):
        _should_be_equal(first, second)
def _should_be_equal(first, second):
    if first == second:
        return True
    else:
        raise AssertionError('"%s" and "%s" should be equal.' %(first, second))

def should_not_be_equal(first, second, msg=None):
    """Fails if the given objects are equal.
    See `Should Be Equal` for an explanation on how to override the default
    error message with ``msg`` and ``values``.
    If ``ignore_case`` is given a true value (see `Boolean arguments`) and
    both arguments are strings, comparison is done case-insensitively.
    New option in Robot Framework 3.0.1.
    """
    if type_name(first) == type_name(second):
        if is_string(first) and is_string(second):
            first = first.lower()
            second = second.lower()
        _should_not_be_equal(first, second, msg)
    if type_name(first) == bool or type_name(second) == bool:
        first_bool = convert_to_boolean(first)
        second_bool = convert_to_boolean(second)
        _should_not_be_equal(first_bool,second_bool)

def _should_not_be_equal(first, second, msg):
    if first != second:
        return True
    else:
        raise AssertionError(msg or '"%s" and "%s" should not be equal.' % (first, second))
def should_not_be_equal_as_integers(first, second, msg=None):
    """Fails if objects are equal after converting them to integers.
    See `Convert To Integer` for information how to convert integers from
    other bases than 10 using ``base`` argument or ``0b/0o/0x`` prefixes.
    See `Should Be Equal` for an explanation on how to override the default
    error message with ``msg`` and ``values``.
    See `Should Be Equal As Integers` for some usage examples.
    """
    return _should_not_be_equal(convert_to_integer(first),
                              convert_to_integer(second))

def should_be_equal_as_integers(first, second):
    """Fails if objects are unequal after converting them to integers.
    See `Convert To Integer` for information how to convert integers from
    other bases than 10 using ``base`` argument or ``0b/0o/0x`` prefixes.
    See `Should Be Equal` for an explanation on how to override the default
    error message with ``msg`` and ``values``.
    Examples:
    | Should Be Equal As Integers | 42   | ${42} | Error message |
    | Should Be Equal As Integers | ABCD | abcd  | base=16 |
    | Should Be Equal As Integers | 0b1011 | 11  |
    """
    return _should_be_equal(convert_to_integer(first),
                          convert_to_integer(second)
                          )


def should_not_be_equal_as_strings(first, second, msg=None):
    """Fails if objects are equal after converting them to strings.
    See `Should Be Equal` for an explanation on how to override the default
    error message with ``msg`` and ``values``.
    If ``ignore_case`` is given a true value (see `Boolean arguments`),
    comparison is done case-insensitively.
    Strings are always [http://www.macchiato.com/unicode/nfc-faq|
    NFC normalized].
    ``ignore_case`` is a new feature in Robot Framework 3.0.1.
    """
    first = convert_to_string(first)
    second = convert_to_string(second)
    first = first.lower()
    second = second.lower()
    return _should_not_be_equal(first, second, msg)

def should_be_equal_as_strings(first, second, msg=None, values=True):
    """Fails if objects are unequal after converting them to strings.
    See `Should Be Equal` for an explanation on how to override the default
    error message with ``msg``, ``values`` and ``formatter``.
    If ``ignore_case`` is given a true value (see `Boolean arguments`),
    comparison is done case-insensitively. If both arguments are
    multiline strings, this keyword uses `multiline string comparison`.
    Strings are always [http://www.macchiato.com/unicode/nfc-faq|
    NFC normalized].
    ``ignore_case`` and ``formatter`` are new features in Robot Framework
    3.0.1 and 3.1.2, respectively.
    """
    first = convert_to_string(first)
    second = convert_to_string(second)
    first = first.lower()
    second = second.lower()
    return _should_be_equal(first, second)

def should_not_contain(container, item,
                       ignore_case=False):
    """Fails if ``container`` contains ``item`` one or more times.
    Works with strings, lists, and anything that supports Python's ``in``
    operator.
    See `Should Be Equal` for an explanation on how to override the default
    error message with arguments ``msg`` and ``values``. ``ignore_case``
    has exactly the same semantics as with `Should Contain`.
    Examples:
    | Should Not Contain | ${some list} | value  |
    | Should Not Contain | ${output}    | FAILED | ignore_case=True |
    """
    # TODO: It is inconsistent that errors show original case in 'container'
    # 'item' is in lower case. Should rather show original case everywhere
    # and add separate '(case-insensitive)' not to the error message.
    # This same logic should be used with all keywords supporting
    # case-insensitive comparisons.
    orig_container = container
    if is_truthy(ignore_case) and is_string(item):
        item = item.lower()
        if is_string(container):
            container = container.lower()
        elif is_list_like(container):
            container = set(x.lower() if is_string(x) else x for x in container)
    if item in container:
        raise AssertionError("'%s' should not contain '%s'"%(container, item))
    else:
        return True

def should_contain(container, item, msg=None):
    """Fails if ``container`` does not contain ``item`` one or more times.
    Works with strings, lists, and anything that supports Python's ``in``
    operator.
    See `Should Be Equal` for an explanation on how to override the default
    error message with arguments ``msg`` and ``values``.
    If ``ignore_case`` is given a true value (see `Boolean arguments`) and
    compared items are strings, it indicates that comparison should be
    case-insensitive. If the ``container`` is a list-like object, string
    items in it are compared case-insensitively. New option in Robot
    Framework 3.0.1.
    Examples:
    | Should Contain | ${output}    | PASS  |
    | Should Contain | ${some list} | value | msg=Failure! | values=False |
    | Should Contain | ${some list} | value | ignore_case=True |
    """
    orig_container = container
    if is_string(item):
        item = item.lower()
        if is_string(container):
            container = container.lower()
        elif is_list_like(container):
            container = set(x.lower() if is_string(x) else x for x in container)
    if item not in container:
        raise AssertionError("'%s' should contain '%s'"%(container, item))
    else:
        return True
def get_length(item):
    """Returns and logs the length of the given item as an integer.
    The item can be anything that has a length, for example, a string,
    a list, or a mapping. The keyword first tries to get the length with
    the Python function ``len``, which calls the  item's ``__len__`` method
    internally. If that fails, the keyword tries to call the item's
    possible ``length`` and ``size`` methods directly. The final attempt is
    trying to get the value of the item's ``length`` attribute. If all
    these attempts are unsuccessful, the keyword fails.
    Examples:
    | ${length} = | Get Length    | Hello, world! |        |
    | Should Be Equal As Integers | ${length}     | 13     |
    | @{list} =   | Create List   | Hello,        | world! |
    | ${length} = | Get Length    | ${list}       |        |
    | Should Be Equal As Integers | ${length}     | 2      |
    See also `Length Should Be`, `Should Be Empty` and `Should Not Be
    Empty`.
    """
    length = _get_length(item)
    print 'Length is %d' % length
    return length

def _get_length(item):
    try:
        return len(item)
    except RERAISED_EXCEPTIONS:
        raise
    except:
        try:
            return item.length()
        except RERAISED_EXCEPTIONS:
            raise
        except:
            try:
                return item.size()
            except RERAISED_EXCEPTIONS:
                raise
            except:
                try:
                    return item.length
                except RERAISED_EXCEPTIONS:
                    raise
                except:
                    raise RuntimeError("Could not get length of '%s'." % item)

def set_variable(*values):
    """Returns the given values which can then be assigned to a variables.
    This keyword is mainly used for setting scalar variables.
    Additionally it can be used for converting a scalar variable
    containing a list to a list variable or to multiple scalar variables.
    It is recommended to use `Create List` when creating new lists.
    Examples:
    | ${hi} =   | Set Variable | Hello, world! |
    | ${hi2} =  | Set Variable | I said: ${hi} |
    | ${var1}   | ${var2} =    | Set Variable | Hello | world |
    | @{list} = | Set Variable | ${list with some items} |
    | ${item1}  | ${item2} =   | Set Variable  | ${list with 2 items} |
    Variables created with this keyword are available only in the
    scope where they are created. See `Set Global Variable`,
    `Set Test Variable` and `Set Suite Variable` for information on how to
    set variables so that they are available also in a larger scope.
    """
    if len(values) == 0:
        return ''
    elif len(values) == 1:
        return values[0]
    else:
        return list(values)

# def run_keyword_if(condition, name, *args):
#     args, branch = _split_elif_or_else_branch(args)
#     if _is_true(condition):
#         return run_keyword(name, *args)
#     return branch()
# def _split_elif_or_else_branch(args):
#     if 'ELSE IF' in args:
#         args, branch = _split_branch(args, 'ELSE IF', 2,
#                                           'condition and keyword')
#         return args, lambda: run_keyword_if(*branch)
#     if 'ELSE' in args:
#         args, branch = _split_branch(args, 'ELSE', 1, 'keyword')
#         return args, lambda: run_keyword(*branch)
#     return args, lambda: None

def run_keyword(name, *args):
    """Executes the given keyword with the given arguments.
    Because the name of the keyword to execute is given as an argument, it
    can be a variable and thus set dynamically, e.g. from a return value of
    another keyword or from the command line.
    """
    if not is_string(name):
        raise RuntimeError('Keyword name must be a string.')
    # kw = Keyword(name, args=args)
    # return kw.run(_context)

def _split_branch(args, control_word, required, required_error):
    index = list(args).index(control_word)
    # # branch = _variables.replace_list(args[index + 1:], required)
    # if len(branch) < required:
    #     raise AssertionError('%s requires %s.' % (control_word, required_error))
    # return args[:index], branch


if __name__ == '__main__':
    # print _Converter().convert_to_boolean('False')
    # _Verify().should_not_be_true([1])
    # print _Verify().should_contain({'1','q','3'},'1')
    print get_length({1,2,3,4,5})