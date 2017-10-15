import math
def MedianFinder(D1,D2,n,L1=0,L2=0):
    # Returns the median of the union of D1 and D2 in O(logn) time
    """
    :param D1: Database 1
    :param D2: Database 2
    :param n:  Number of elements in D1 and D2
    :param L1: Left pointer of Database 1, defaulted to 0
    :param L2: Left pointer of Database 2, defaulted to 0
    :return: The median of the two databases when combined
    """

    half = math.floor(n/2)
    # Assumes databases have some function query(k) which returns the kth smallest number
    #M1 = D1.query(L1+half)
    #M2 = D2.query(L2+half)

    # Assumes the lists are sorted
    M1 = D1[L1+half]
    M2 = D2[L2+half]
    if half < 1:
        return min(M1,M2)
    if M1<M2:
        return MedianFinder(D1,D2,half,L1+half+1,L2) # Query top half of D1 and bottom half of D2
    else:
        return MedianFinder(D1,D2,half,L1,L2+half+1) # Query bottom half of D1 and top half of D2

def MajorityFinder(A):
    # Return the majority of the A in O(nlogn) time
    if len(A) == 1:
        return A[0]
    # Divide array into two equal parts
    majority_left = MajorityFinder(A[0:math.floor(len(A)/2)])
    majority_right = MajorityFinder(A[math.floor(len(A)/2):len(A)])
    i = 0
    j = 0
    # If majority of both subproblems is the same, the majority of their union must also be the same
    if majority_left == majority_right:
        return majority_left
    # Iterate through the array to find which majority is maintained upon combination of subproblems
    for thing in A:
        if thing == majority_left:
            i += 1
        if thing == majority_right:
            j += 1
    if i>j:
        return majority_left
    elif i<j:
        return majority_right
    else:
        return "No Majority"

