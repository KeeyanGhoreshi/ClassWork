import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd

sns.set(style='whitegrid')
db = pd.read_csv('./data3.csv')

db['total_time'] = db['sys_time'] + db['user_time']
db['base_pairs'] = db['seq_len'] * db['Sequences']
clustal_db = db[db['alignment']=='clustal']
outlier_db = db[db['total_time']<1000]

g = sns.factorplot(x='seq_len',y='total_time',hue='alignment',data=outlier_db,kind='bar', size=8)
g.set_ylabels('Computer Time (s)')
g.set_titles('Sequence length vs Computer Time')
g.savefig('images/total_time_compare')

g = sns.factorplot(x='seq_len',y='real_time',hue='alignment',data=outlier_db,kind='bar', size=8)
g.set_ylabels('Real Time (s)')
g.set_titles('Sequence Length vs Real Time')
g.savefig('images/real_time_compare')


g = sns.factorplot(x='Sequences',y='total_time',hue='alignment',data=outlier_db,kind='bar', size=8)
g.set_titles('Number of Sequences vs Total Time')
g.set_ylabels('Total Time (s)')
g.savefig('images/sequence_number_compare')


droped = db[db['Consistency']!=0]


g = sns.factorplot(x='base_pairs',y='Consistency',hue='alignment',data=droped,kind='bar', size=10)
g.set_ylabels('Consistency')
g.set_titles('Accuracy')
g.savefig('images/accuracy_compare')


g = sns.factorplot(x='Name',y='Consistency',hue='alignment',data=droped,kind='bar', size=10)
g.set_ylabels('Consistency')
g.set_titles('Accuracy By Name')
g.savefig('images/accuracy_compare_name')

accuracy = db.groupby('alignment').mean()

acc= accuracy.transpose()


hep = droped[droped['SPS']!='-']
hep['SPS']=pd.to_numeric(hep['SPS'])
g = sns.factorplot(x='Name',y='SPS',hue='alignment',data=hep,kind='bar', size=10)
g.set_ylabels('Sum of Pairs')
g.set_titles('Accuracy By Name')
g.savefig('images/compare_SPS')

g = sns.clustermap(hep.corr(), cmap='vlag')
g.savefig('images/comparison')

g = sns.clustermap(hep[hep['alignment']=='clustal'].corr(), cmap='vlag')
g.savefig('images/comparison_clustal')
g = sns.clustermap(hep[hep['alignment']=='T-Coffee'].corr(), cmap='vlag')
g.savefig('images/comparison_coffee')
g = sns.clustermap(hep[hep['alignment']=='MAFFT'].corr(), cmap='vlag')
g.savefig('images/comparison_mafft')
a = hep[hep['alignment']=='OPAL']
a.drop('sys_time',axis=1,inplace=True)
a.drop('user_time',axis=1,inplace=True)
a.drop('total_time',axis=1,inplace=True)
print(a)
g = sns.clustermap(a.corr(), cmap='vlag')
g.savefig('images/comparison_OPAL')
plt.show()