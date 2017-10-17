import numpy as np


class HiddenMarkovModel():
    def __init__(self, states, end_state = "$"):
        self.states = states
        self.end_state = end_state
        self.current_state = states[0]
    def simulate(self, max_iteration=100):
        current_state = self.current_state
        for i in range(max_iteration):
            print(current_state.emit(include_state=True))
            current_state = current_state.transition()

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
            return (emission[0], self.name)
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
HMM.simulate()
