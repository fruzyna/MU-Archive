
# coding: utf-8

# In[4]:


import pandas as pd
import seaborn as sns
sns.set(color_codes=True)
df = pd.read_table('house_selling_prices.txt')
df.head()


# In[5]:


# Continous/Count Summary Stats
df['Price'].describe()
# fairly normal b/c mean and median are close


# In[6]:


# Nominal/Ordinal Data Summary Stats
bedroom_count = df['Bedrooms'].value_counts()
sns.countplot(df['Bedrooms']) # counts and plots


# In[7]:


sns.countplot(df['Baths'])


# In[8]:


# OLS Regression Results
import statsmodels.api as sm

X = df['Price']
Y = df['Taxes']

model = sm.OLS(Y,X).fit()
print(model.summary())


# In[9]:


Y = df['Price']
X = df[['Bedrooms', 'Taxes', 'Baths', 'Size', 'Lot']]

model = sm.OLS(Y,X).fit()
print(model.summary())
# 96% of the variance can be described by this model
# The currated model and ours are statistically indistinguishable e-65

