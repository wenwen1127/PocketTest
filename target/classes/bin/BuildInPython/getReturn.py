def _return(args):
    print '\r\n'
    res = ''
    for item in args:
        if item is not args[-1]:
            res = res + str(convert_to_java_type(item)) + "-"
        else:
            res += str(convert_to_java_type(item))
    print "%" + res + "%"

def convert_to_java_type(value):
    if 'str' in str(type(value)):
        return "'"+value+"'"
    else:
        return value