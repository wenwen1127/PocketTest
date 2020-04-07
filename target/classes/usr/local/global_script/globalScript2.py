def getmaxPrice(arr1, arr2, x, y):
    maxcount = 0
    temp = [0 for i in range (len(arr1))]
    aCount = [0 for i in range(len(arr1))]
    bCount = [0 for j in range(len(arr1))]
    for i in range(len(arr1)):
        if arr1[i] > arr2[i] :
            aCount[i] = 1
            temp[i] = arr1[i] - arr2[i]
            maxcount = maxcount + arr1[i]
        else:
            bCount[i] = 1
            maxcount = maxcount + arr2[i]
            temp[i] = arr2[i] - arr1[i]
    # print(aCount)
    # print(bCount)
    # print(temp)
    aNum = 0
    bNum = 0
    for i in range (len(arr1)):
        aNum = aNum + aCount[i]
        bNum = bNum + aCount[i]
    while(aNum > x and bNum > y):
        index = getmin(temp)
        if aNum > x:
            maxcount = maxcount - arr1[index] + arr2[index]
            temp[index] = 0
            aNum = aNum - 1
        else:
            maxcount = maxcount - arr2[index] + arr1[index]
            temp[index] = 0
            bNum = bNum - 1
    # print(maxcount)
    return maxcount, aCount, bCount

def getmin(arr):
    min = 10000
    for i in range(len(arr)):
        if arr[i] < min :
            min = arr[i]
    # print(min)
    # print(arr.index(min))
    #print(str(arr.index(min))+"ahhahah")
    return arr.index(min)
if __name__ == '__main__':
    caseNum = input()
    for i in range(int(caseNum)):
        caseLine = input().split()
        caseLineNum = int(caseLine[0])
        x = int(caseLine[1])
        y = int(caseLine[2])
        line1 = input().split()
        arr1 = [int(i) for i in line1]
        line2 = input().split()
        arr2 = [int(i) for i in line2]
        # print(arr1)
        # print(arr2)
    print(getmaxPrice(arr1,arr2, 3, 3))