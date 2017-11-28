from bisect import *
import stat
import time


def stopwatch(routine):
    def wrapper(*args):
        begin = time.time()
        return_value = routine(*args)
        end = time.time()
        return end-begin
    return wrapper

def index(a, x):
    'Locate the leftmost value exactly equal to x'
    i = bisect_left(a, x)
    if i != len(a) and a[i] == x:
        return i

@stopwatch
def brute_force(S, goal):
    for a in S:
        for b in S:
            if a+b == goal:
                return str(a) + " and " + str(b) + " add to " + str(goal)
    return "No matches"

@stopwatch
def binary_search(S,goal):
    S.sort()
    for number in S:
        if index(S,goal-number) is not None:
            return str(number) + ' and ' + str(goal-number)
    return "No matches"


def make_array_from_data(file):
    header = 'CollectionNumbers/listNumbers-' + file
    numbers = header + '.txt'
    goals = header + '-nsol.txt'

    f_n = open(numbers,'r')
    f_g = open(goals,'r')
    a=[]
    b=[]
    for line in f_n:
        a.append(int(line))
    for line in f_g:
        b.append(int(line))

    return a,b

lengths = ['10','100','1000','10000','100000']
for length in lengths:
    b= make_array_from_data(length)
    ave_brute = []
    ave_bin = []
    for number in b[1]:
        duration = brute_force(b[0],number)
        ave_brute.append(duration)
        duration = binary_search(b[0], number)
        ave_bin.append(duration)

    print("Brute       Binary")
    print(str(round(sum(ave_brute)/len(ave_brute),5)) + '      '+ str(round(sum(ave_bin)/len(ave_bin),5)))
