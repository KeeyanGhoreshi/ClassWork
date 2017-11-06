import numpy as np
import random
def maximum_profit(p):
    P = np.empty(len(p), dtype=object)
    P[0] = (0,0,0)
    curr_min = 0
    # P[i] = day to buy the stock to make maximum profit
    for i in range(1,len(p)):
        placeholder = p[i]-p[curr_min]
        if(placeholder > P[i-1][0]):
            P[i] = (placeholder, curr_min, i)
            curr_max = i
        else:
            P[i] = P[i-1]
        if(p[i]<p[curr_min]):
            curr_min = i
    print(P[len(p)-1])


def max_consecutive_subarray(p):
    P = np.empty(len(p),dtype=object)

    P[0] = (p[0], [p[0]])
    current_max = 0
    max_pointer = 0
    for i in range(1,len(p)):
        if p[i] > P[i-1][0] + p[i]:
            P[i] = (p[i], [p[i]])
        else:

            placeholder = P[i-1][1][:]
            P[i] = (P[i-1][0] + p[i], placeholder + [p[i]])
        if P[i][0] > current_max:
            current_max = P[i][0]
            max_pointer = i
    print(P[max_pointer])


def freight_a_minute(S, r=1, c=10):
    """
    Algorithm Design - Jon Kleinberg, Eva Tardos.pdf: problem 11 of Chapter six (page 323)
    """
    # This is like weighted interval job scheduling except our jobs are either of length 4 with a fixed weight or of
    # length 1 with a weight of r*S[i]

    dynam = np.zeros(len(S), dtype=object)
    # Initialize, we keep track of the company sequence in the second index of the tuple
    dynam[0] = (S[0]*r, ['A'])
    for i in range(1, len(S)):
        if(i<4):
            dynam[i] = (S[i]*r + dynam[i - 1][0], dynam[i-1][1] + ['A'])
        else:
            # Recurrence
            if np.argmin((S[i]*r + dynam[i-1][0], c*4 + dynam[i-4][0]))==1:
                dynam[i] = (c*4 + dynam[i-4][0], dynam[i-4][1] + ['B']*4)
            else:
                dynam[i] = (S[i]*r + dynam[i-1][0], dynam[i-1][1] + ['A'])
    print(dynam[len(S)-1])


def interleaving_and_entering(s,x,y):
    states = [x, y]
    Q = np.zeros((len(s)+1,len(s)+1), dtype=int)
    # Q[i,j] describes whether s[0:i+j] is an interleaving of x&y
    cutoff = 0
    for i in range(len(s)):
        for j in range(len(s)-cutoff+1):
            if i == 0 and j == 0:
                Q[i,j] = True
            # elif i==0:
            #     Q[i,j] = Q[i,j-1] and (y[j%len(y)-1] == s[j-1])
            # elif j==0:
            #     Q[i, j] = Q[i-1, j] and x[i % len(x)-1] == s[i-1]
            else:
                Q[i,j] = (Q[i-1,j] and x[i%len(x)-1] == s[i+j-1]) or (Q[i,j-1] and y[j%len(y)-1] == s[i+j-1])
        cutoff+=1
    stack = []
    stack.append((0,0))
    while len(stack)>0:
        index = stack.pop(-1)
        if Q[index]:
            if(sum(index) == len(s)):
                return "Yes"

            b = (index[0] + 1, index[1])
            a = (index[0] , index[1] +1)
            if b:
                stack.append(b)
            if a:
                stack.append(a)
    return "No"


def data_generate(i, lower_bound = 5, upper_bound = 20):
    data = []
    for k in range(i):
        data.append(round(random.randrange(lower_bound,upper_bound)))
    print(data)
    return data
a = [11,9,9,12,12,12,12,9,9,11]
print(interleaving_and_entering('11111111111111', '11', '111'))
print(interleaving_and_entering('100010101','101','00'))

