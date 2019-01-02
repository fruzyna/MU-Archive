
# coding: utf-8

# In[2]:


# Running of April 4th notes

import pandas as pd
import seaborn as sns
sns.set(color_codes=True)
df = pd.read_table('../April2-4/house_selling_prices.txt')
df.head()

# Continous/Count Summary Stats
df['Price'].describe()
# fairly normal b/c mean and median are close

# Nominal/Ordinal Data Summary Stats
bedroom_count = df['Bedrooms'].value_counts()
sns.countplot(df['Bedrooms']) # counts and plots

sns.countplot(df['Baths'])

# OLS Regression Results
import statsmodels.api as sm

X = df['Price']
Y = df['Taxes']

model = sm.OLS(Y,X).fit()
print(model.summary())

Y = df['Price']
X = df[['Bedrooms', 'Taxes', 'Baths', 'Size', 'Lot']]

model = sm.OLS(Y,X).fit()
print(model.summary())
# 96% of the variance can be described by this model
# The currated model and ours are statistically indistinguishable e-65


# In[3]:


# April 11
import matplotlib.pyplot as plt
plt.plot(X, model.fittedvalues, 'o')
plt.figure(figsize=(20,10))
plt.show()


# In[ ]:


plt.plot(X, model.fittedvalues, 'o')
plt.figure(figsize=(20,10))
plt.show()


# In[4]:


# Compare actual values to model predictions
[predictions.head(), df['Price'].head()]

