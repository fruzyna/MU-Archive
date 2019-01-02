
# coding: utf-8

# In[1]:


import pandas as pd
import statsmodels.api as sm
import numpy as np


# In[3]:


# Data set on graduate school admissions
df = pd.read_csv('admissions.csv')
df.head()
# Prestige - nebulous measurement of the prestige of the (undergrad) school (1-4)
# Every 100 in USNWR top schools is a ranking number (1 is 1-100)


# In[4]:


# Want to predict the ability to get into a grad school given gre, gpa, and school prestige
df.describe()


# In[5]:


# Cross tabulate admit with prestige
pd.crosstab(df['admit'], df['prestige'], rownames=['admit'])
# People don't apply evenly, technically higher odds to get into a good school
# Grad school admission is not objective


# In[6]:


get_ipython().run_line_magic('matplotlib', 'inline')
import seaborn as sns
sns.set(color_codes=True)
import matplotlib.pyplot as plt

# Plot small multiples of prestige vs admit
multiples = sns.FacetGrid(df, col='prestige')
multiples = multiples.map(plt.hist, 'admit')


# In[8]:


# Show histograms of all columns
df.hist(figsize=(15,10))
plt.show()


# In[12]:


# Dummify prestige, dummy variable used to sort categorical variables into mutually exclusive categroies
dummy_ranks = pd.get_dummies(df['prestige'], prefix='prestige')
dummy_ranks.head()


# In[16]:


# Keep all columns besides prestige which is replaced with the dummy variables
cols_to_keep = ['admit', 'gre', 'gpa']
data = df[cols_to_keep].join(dummy_ranks.loc[:, 'prestige_2':])
data.head()


# In[18]:


data['intercept'] = 1.0
data.head()


# In[29]:


# Bug work-around for result.summary() below
from scipy import stats
stats.chisqprob = lambda chisq, df: stats.chi2.sf(chisq, df)


# In[30]:


# Ready data for input into model
xData = data[data.columns[1:]]
yData = data['admit']

# Fit the model, logrithmic
logit = sm.Logit(yData, xData)
result = logit.fit()
print(result.summary())
# They all affect probability, but not equally


# In[32]:


# Odds ratios, odds of happening : odds of not, >1 means more likely to happen
print(np.exp(result.params))
# GRE barely helps, a bad GRE will hurt but a good one doesn't help much
# But GRE's scale is still 2x that of GPA (800 vs 4(.)00)


# In[33]:


# Confidence intervals of each variable in the model (more detailed version of above)
print(result.conf_int())


# In[39]:


# Odds of a perfect student will getting into each tier of school
result.predict([[800, 4.0, 0, 0, 0, 1],
                [800, 4.0, 1, 0, 0, 1],
                [800, 4.0, 0, 1, 0, 1],
                [800, 4.0, 0, 0, 1, 1]])
# The better a school you went to the better your odds of getting into grad school

