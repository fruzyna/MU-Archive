
# coding: utf-8

# In[1]:


# Liam Fruzyna
# COSC 4610
# Assignment 4

import pandas as pd
import sklearn.cluster as sk
import matplotlib.pyplot as plt
import numpy as np

get_ipython().run_line_magic('matplotlib', 'notebook')


# In[2]:


df = pd.read_csv('bar_locations.csv')
df


# In[53]:


locs = df[['Longitude', 'Latitude']]
locs.plot.scatter('Longitude', 'Latitude', figsize=(12,9))


# In[52]:


kmeans = sk.KMeans(n_clusters=8, random_state=0).fit(locs)
plt.scatter(locs['Longitude'],locs['Latitude'],c=kmeans.labels_)


# In[27]:


kmeans = sk.KMeans(n_clusters=7, random_state=0).fit(locs)
plt.scatter(locs['Longitude'],locs['Latitude'],c=kmeans.labels_)


# In[28]:


kmeans = sk.KMeans(n_clusters=6, random_state=0).fit(locs)
plt.scatter(locs['Longitude'],locs['Latitude'],c=kmeans.labels_)


# In[29]:


kmeans = sk.KMeans(n_clusters=5, random_state=0).fit(locs)
plt.scatter(locs['Longitude'],locs['Latitude'],c=kmeans.labels_)

