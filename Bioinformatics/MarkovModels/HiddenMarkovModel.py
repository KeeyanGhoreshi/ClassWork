import numpy as np
import time
import hidden_markov


def stopwatch(routine):
    """
    Calculates and returns time it takes for a function to run
    """
    def wrapper(*args,**kwargs):
        start = time.time()
        p = routine(*args,**kwargs)
        end = time.time()
        # print(routine.__name__ + " took " + str(round((end-start),4)) + " seconds")
        return p, end-start
    return wrapper

class HiddenMarkovModel():
    def __init__(self, states, end_state = "$"):
        self.states = states
        self.end_state = end_state
        self.simulation = []
        self.state_by_name = {}
        self.transition_matrix = np.zeros([len(self.states),len(self.states)])
        for i in range(len(self.states)):
            state = states[i]
            for j in range(len(state.transition_dict)):
                self.transition_matrix[i,j] = state.transition_dict[states[j]]
            self.state_by_name[state.name] = state

    def simulate(self, max_iteration=1000):
        self.simulation = []
        current_state = self.states[0]
        for i in range(max_iteration):
            emission = current_state.emit(include_state=True)
            self.simulation.append(emission)
            current_state = current_state.transition()
        return self.simulation
    def construct_matrix(self):
        """
        Produces necessary variables for all dynamic programming algorithms
        """
        observations = [x[0] for x in self.simulation]
        k = len(self.states)
        L = len(observations)
        # f[state, location]
        dynamic_matrix = np.zeros([k, L], dtype=np.float128)

        return dynamic_matrix, observations, k, L
    @stopwatch
    def forward(self, begin_state):
        """
        Does the forward algorithm on the simulation data
        :param begin_state: The state of the first data point
        :return: An array of the probabilities of a state at a certain time given the previous data
        """
        if len(self.simulation)==0:
            print("No simulation was run")
            return 0
        f,observations,k,L = self.construct_matrix()
        # Main scale factor is hard coded for now but can be easily made dynamic
        scale_factors = [6]

        # Init
        for state in range(k):
            if state == begin_state:
                f[state, 0] = 1
            else:
                f[state, 0] = 0
        # Recurrence
        for i in range(1, L):
            observation = observations[i]
            for state in range(k):
                summation = sum([f[s2,i-1]*self.states[s2].transition_dict[self.states[state]] for s2 in range(k)])
                emission_prob = self.states[state].emission_dict[observation]
                summation = summation*emission_prob
                f[state, i] = summation
            # Scale results
            scale_factors.append(1/np.sum(f[:,i]))
            f[:,i] = f[:,i]/np.sum(f[:,i])
        return f, scale_factors


    @stopwatch
    def backward(self, scale_factors):
        """
        Computes probability of emitting the remaining sequence after being at some hidden state k at time t

        b_k(i) = P(X_i+1 ... X_L | pi_i = k)

        :return: Matrix of values, prints the probability of the full sequence
        """
        if len(self.simulation)==0:
            print("No simulation was run")
            return 0
        b, observations, k, L = self.construct_matrix()

        # Init
        for state in range(k):
            # Given that the sequence is terminated based on length rather than probability, the model is
            # guaranteed to transition to end state on the Lth observation
            b[state,L-1] = scale_factors[L-1]

        # Recurrence
        for observation in range(L-1)[::-1]:
            for state in range(k):
                # b_l(i+1) * a_kl * e_l(x_i+1)
                summation = np.sum([b[l,observation+1] * self.states[l].transition_dict[self.states[state]] * self.states[l].emission_dict[observations[observation+1]] for l in range(k)])

                # Scale data based on scale factors from forward algorithm
                b[state,observation] = summation*scale_factors[observation]


        # Given some known start state, b[k,0] will give the probability of the full sequence
        return b
    @stopwatch
    def Viterbi(self, start_state, display = False):
        """
        Uses Viterbi Decoding to find most likely path given some observations
        """


        if len(self.simulation)==0:
            print("No simulation was run")
            return 0
        V, observations, k, L = self.construct_matrix()
        V2 = V.copy()

        # Matrix is initialized to zero by default
        V[start_state,0] = 1*self.states[start_state].emission_dict[observations[0]]

        #V[start_state, 0] = 1

        for observation in range(1,L):
            for state in range(k):

                e_lxi = self.states[state].emission_dict[observations[observation]]
                possibilities = [np.multiply(np.multiply(self.states[k2].transition_dict[self.states[state]], V[k2, observation-1]),e_lxi) for k2 in range(k)]

                max_k = np.max(possibilities)

                # Argmax of a_kl * V_k(i-1)
                backpointer = np.argmax(possibilities)
                V[state,observation] = max_k
                V2[state,observation] = backpointer

            V[:,observation] = V[:,observation]/np.sum(V[:,observation])

        z = np.zeros(L, dtype=int)
        x = np.empty(L,dtype=object)
        # Find traceback to display most likely path
        final_state = np.argmax([V[state, L-1] for state in range(k)])
        z[L-1] = final_state
        x[L-1] = self.states[final_state].name

        for i in range(1,L)[::-1]:
            z[i-1] = V2[z[i],i]
            x[i-1] = self.states[z[i-1]].name
        number_correct = 0
        for i in range(len(x)):

            if(self.simulation[i][1] == x[i]):
                number_correct+=1
            if(display):
                print("[" + str(i) + "]   " + str(self.simulation[i]) + str([x[i]]))
        if(display):
            print('')
            print("--> Viterbi Accuracy: " + str(number_correct/len(x)))

        return number_correct/len(x)
    @stopwatch
    def posterior(self, display = False):

        results, duration = self.forward(0)
        f = results[0]
        scale_factors = results[1]
        b, duration = self.backward(scale_factors)
        p, observations, k, L = self.construct_matrix()
        p = np.multiply(f,b)/np.sum(f[:,L-1])
        counter = 0
        p = np.transpose(p)
        #Henlo
        m = np.argmax(p,1)
        for i in range(len(self.simulation)):
            if m[i] == self.states.index(self.state_by_name[self.simulation[i][1]]):
                counter+=1
        accuracy = counter/len(self.simulation)
        if(display):
            print('')
            print("--> Posterior Accuracy: " + str(accuracy))
        return accuracy
    @stopwatch
    def MLE(self, display = False):
        # Parameter Estimation
        state_matrix = np.zeros([len(self.states),len(self.states)])
        emit_list = []
        state_probability = state_matrix.copy()
        for state in self.states:
            emit_list.append({})
        for i in range(len(self.simulation)-1):
            data = self.simulation[i]
            next_point = self.simulation[i+1]
            current_state = self.states.index(self.state_by_name[data[1]])
            next_state = self.states.index(self.state_by_name[next_point[1]])
            state_matrix[current_state,next_state] += 1
            current_emit = data[0]
            try:
                emit_list[current_state][current_emit] += 1
            except KeyError:
                emit_list[current_state][current_emit] = 1
        if(display):
            print(state_matrix)
        # Pseudocount, avoids errors with small simluation lengths
        state_matrix = state_matrix+1
        for k in range(len(self.states)):
            for l in range(len(self.states)):
                summation = sum([state_matrix[k, i] for i in range(len(self.states))])
                probability = state_matrix[k,l]/summation
                state_probability[k,l] = probability
                if(display):
                    print(self.states[k].name + " --> " + self.states[l].name + ": " + str(probability))
        mse = np.sum((np.square(state_probability * 100 - self.transition_matrix * 100))) / np.size(state_probability)
        if(display):
            print(state_probability)
            print('')
            print("--> MLE Error: " + str(round(mse,8)))
        return mse










class State():
    def __init__(self, emission_dict, transition_dict=None, name="None"):
        self.emission_dict = emission_dict
        self.name = name
        if transition_dict is None:
            self.transition_dict = {}
        else:
            self.transition_dict = transition_dict

    def emit(self, include_state = False):
        keys = list(self.emission_dict.keys())
        probability_list = list(self.emission_dict.values())
        emission = np.random.choice(keys, 1, p=probability_list)
        if include_state:
            return [emission[0], self.name]
        else:
            return emission[0]

    def transition(self):
        keys = list(self.transition_dict.keys())
        probability_list = list(self.transition_dict.values())
        next_state = np.random.choice(keys, 1, p=probability_list)
        return next_state[0]

    def set_transition(self, next_state, probability):
        self.transition_dict[next_state] = probability

    def set_emission(self, emission, probability):
        self.emission_dict[emission] = probability


emission_fair = {1: 1/6, 2: 1/6, 3: 1/6, 4: 1/6, 5: 1/6, 6: 1/6}
emission_loaded = {1: .1, 2: .1, 3: .1, 4: .1, 5: .1, 6: .5}
Fair = State(emission_dict=emission_fair, name="Fair")
Loaded = State(emission_dict=emission_loaded, name="Loaded")
Fair.set_transition(Fair, .95)
Fair.set_transition(Loaded, .05)
Loaded.set_transition(Loaded, .9)
Loaded.set_transition(Fair,.1)
HMM = HiddenMarkovModel([Fair,Loaded])


sim_lengths = [300]
number_of_iterations = 5
with open('/home/keeyan/PycharmProjects/ClassWork/Bioinformatics/MarkovModels/results.txt','w') as file:
    for iterations in sim_lengths:
        V_acc = []
        V_time = []
        P_acc = []
        P_time = []
        MLE_acc = []
        MLE_time = []
        for i in range(number_of_iterations):
            sim = HMM.simulate(max_iteration=iterations)
            V_accuracy, V_duration = HMM.Viterbi(0)
            V_acc.append(V_accuracy)
            V_time.append(V_duration)
            P_accuracy, P_duration = HMM.posterior()
            P_acc.append(P_accuracy)
            P_time.append(P_duration)
            a,b = HMM.MLE()
            MLE_acc.append(a)
            MLE_time.append(b)


        file.write("--- " + str(iterations) + " data points --- (" + str(number_of_iterations) + " simulations) ---\n")
        file.write("Name       [Avg Accuracy] [Average Time]\n")
        file.write("Viterbi:      [" + str(round(np.average(V_acc),3)) + "]        [" + str(round(np.average(V_time),4)) + "]\n")
        file.write("Posterior:    [" + str(round(np.average(P_acc), 3)) + "]        [" + str(round(np.average(P_time), 4)) + "]\n")
        file.write("MLE:          [" + str(round(np.average(MLE_acc), 3)) + "]        [" + str(round(np.average(MLE_time), 4)) + "]\n")

