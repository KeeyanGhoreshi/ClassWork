import numpy as np
import time

def stopwatch(routine):
    def wrapper(*args,**kwargs):
        start = time.time()
        p = routine(*args,**kwargs)
        end = time.time()
        print(routine.__name__ + " took " + str(round((end-start),4)) + " seconds")
        return p
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
    def construct_matrix(self):
        observations = [x[0] for x in self.simulation]
        k = len(self.states)
        L = len(observations)
        # f[state, location]
        dynamic_matrix = np.zeros([k, L])
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
        for state in range(k):
            if state == begin_state:
                f[state, 0] = 1
            else:
                f[state, 0] = 0
        for i in range(1, L):
            observation = observations[i]

            for state in range(k):
                summation = sum([f[s2,i-1]*self.states[s2].transition_dict[self.states[state]] for s2 in range(k)])
                emission_prob = self.states[state].emission_dict[observation]
                summation = summation*emission_prob
                f[state, i] = summation
        return f
    @stopwatch
    def backward(self):
        """
        Computes probability of emitting the remaining sequence after being at some hidden state k at time t

        b_k(i) = P(X_i+1 ... X_L | pi_i = k)

        :return: Matrix of values, prints the probability of the full sequence
        """
        if len(self.simulation)==0:
            print("No simulation was run")
            return 0
        b, observations, k, L = self.construct_matrix()
        for state in range(k):
            # Given that the sequence is terminated based on length rather than probability, the model is
            # guaranteed to transition to end state on the Lth observation
            b[state,L-1] = 1
        # Recurrence
        for state in range(k):
            for observation in range(L-1)[::-1]:
                                # b_l(i+1) * a_kl * e_l(x_i+1)
                summation = sum([b[l,observation+1] * self.states[l].transition_dict[self.states[state]] * self.states[l].emission_dict[observations[observation+1]] for l in range(k)])
                b[state,observation] = summation
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
                possibilities = [self.states[k2].transition_dict[self.states[state]] * V[k2, observation-1] for k2 in range(k)]
                max_k = max(possibilities)
                # Argmax of a_kl * V_k(i-1)
                backpointer = np.argmax(possibilities)
                V[state,observation] = e_lxi*max_k
                V2[state,observation] = backpointer
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
        print('')
        print("--> Viterbi Accuracy: " + str(number_correct/len(x)))
        return z,number_correct/len(x)
    @stopwatch
    def posterior(self):
        f = self.forward(0)
        b = self.backward()
        p, observations, k, L = self.construct_matrix()
        for i in range(k):
            for j in range(L):
                p[i,j] = (f[i,j]*b[i,j])/(f[1,L-1]+f[0,L-1])
        counter = 0
        p = np.transpose(p)
        m = np.argmax(p,1)
        for i in range(len(self.simulation)):
            if m[i] == self.states.index(self.state_by_name[self.simulation[i][1]]):
                counter+=1
        accuracy = counter/len(self.simulation)
        print('')
        print("--> Posterior Accuracy: " + str(accuracy))
        return(p, accuracy)
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
        if(display):
            print(state_probability)
        mse = np.sum((np.square(state_probability*100-self.transition_matrix*100)))/np.size(state_probability)
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
HMM.simulate(max_iteration=300)
#print(HMM.forward(0))
#print(HMM.backward())
HMM.Viterbi(0)
HMM.posterior()
HMM.MLE()
