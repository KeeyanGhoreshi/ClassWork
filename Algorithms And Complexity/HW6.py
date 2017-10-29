import math
import numpy as np

def coin_change(coin_list,value):
    """
    Computes the smallest number of coins of necessary to make change for some value n

    :param coin_list: list of possible coin values
    :param value: Amount of money that change must be made for
    :return: The fewest number of coins needed to make change for "value"

    """
    # Init everything to 0, otherwise you could do C[i,0] and C[0,w] = 0
    k = len(coin_list)+1
    C = np.zeros([k,value+1])

    # Recurrence
    for di in range(1,k):
        for w in range(1,value+1):
            if w-coin_list[di-1] >= 0:
                C[di,w] = C[di,w-coin_list[di-1]]+1
            else:
                C[di,w] = C[di-1, w]
    # A return value of 0 indicates that change cannot be made for the value given the coins
    # Return corner, C[k,n]
    return min(C[1:, value])


# print(coin_change([1,10,25],30))


def job_stress(high_list, low_list):
    """
    Computes the best possible sequence of jobs to take given a list of high priority and low priority jobs, where
    high priority jobs require a week of rest beforehand.
    :param high_list: A list of values for the high priority jobs
    :param low_list: A list of values for the low priority jobs
    :return: Highest possible "score" given the jobs
    """
    n = len(high_list)

    #Init
    F = np.zeros([3,n+1])

    # Recurrence
    for week in range(1,n+1):
        # No job
        F[0,week] = max(F[:,week-1])
        # Low Stress
        F[1,week] = max(F[:,week-1]) + low_list[week-1]
        # High Stress
        F[2,week] = F[0,week-1] + high_list[week-1]

    return max(F[:,n])


#print(job_stress([5,50,5,1],[10,1,10,10]))




