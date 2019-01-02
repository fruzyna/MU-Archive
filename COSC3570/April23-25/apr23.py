
# coding: utf-8

# In[ ]:


# Not even bothering basing this off the past notes anymore, but resuming April 16


# In[ ]:


# Normal line graphs don't show the opportunity for uncertainty
# Confidence interval or prediction interval show this
# Most useful for displaying the results of a model


# In[ ]:


# Get CI (of earch column)
model.conf_int(alpha=0.05, cols=None) # 95% confidence


# In[ ]:


# Cross Validation
from sklearn.cross_validation import cross_val_score, cross_val_predict
from sklearn import metrics


# In[ ]:


# Peform 10-fold cross validation
scores = cross_val_score(model, X, Y, cv=10)
print('Cross-validated scores: ', scores)


# In[ ]:


print('Accuracy: %0.2f (+/- %0.2f)' %(scores.mean(), scores.std()*2))


# In[ ]:


# Make cross validated predictions
predictions = cross_val_predict(model, X, Y, cv=10)
plt.figure(figsize=(10,10))
plt.scatter(Y, predictions)
plt.xlabel('Observered Prices')
plt.ylabel('Predicted Prices')


# In[ ]:


# Get accuracy of predictions
accuracy = metrics.r2_score(Y, predictions)
print('Cross-Predicted Accuracy: ', accuracy)

