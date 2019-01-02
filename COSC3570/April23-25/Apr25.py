
# coding: utf-8

# In[1]:


import pandas as pd
from sklearn.datasets import load_digits
digits = load_digits()


# In[2]:


print("Image Data Shape", digits.data.shape)


# In[3]:


print("Image Target Shape", digits.target.shape)


# In[4]:


df = pd.DataFrame(digits.data)
df.head()


# In[7]:


import numpy as np
import matplotlib.pyplot as plt
get_ipython().run_line_magic('matplotlib', 'inline')

# load in images for 1 to 5
images = list(zip(digits.data[0:5], digits.target[0:5]))

# plot images
plt.figure(figsize=(20,4))
for index, (image, label) in enumerate(images):
    plt.subplot(1, 5, index + 1)
    plt.imshow(np.reshape(image, (8,8)), cmap=plt.cm.gray)
    plt.title('Training %i\n' % label, fontsize=20)
plt.show()


# In[9]:


# train on the 5 images
from sklearn.model_selection import train_test_split
x_train, x_test, y_train, y_test = train_test_split(digits.data, digits.target, test_size=0.25, random_state=0)


# In[10]:


print(x_train.shape)
print(y_train.shape)
print(x_test.shape)
print(y_test.shape)


# In[11]:


# Fit the logistic regression
from sklearn.linear_model import LogisticRegression
LogisticRegr = LogisticRegression()
LogisticRegr.fit(x_train, y_train)


# In[13]:


# Predict for one image
LogisticRegr.predict(x_test[0].reshape(1,-1))


# In[14]:


y_test[0]


# In[15]:


LogisticRegr.predict(x_test[0:10])


# In[16]:


y_test[0:10]


# In[17]:


predictions = LogisticRegr.predict(x_test)


# In[18]:


score = LogisticRegr.score(x_test, y_test)
print(score)


# In[ ]:


import seaborn as sns
import 


# In[ ]:


cm = metrics.confusion_matrix


# In[19]:


f, ax = plt.subplots(figsize=(12,21))
sns.heatmap(cm, annot=True, fmt='.3f', linewidth=0.5, square=True, cmap='Blues_r', ax=ax)
plt.show()


# In[ ]:


index = 0
mci = []
images = list(zip(predictions, y_test))
for predict, actual in images:
    if predict != acutal:
        mci.append(index)
    index += 1

