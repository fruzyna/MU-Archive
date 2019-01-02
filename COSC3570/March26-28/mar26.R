# March 26 In Class Notes

# Data
library(data.tree)
hierarchy <- read.csv('Hierarchical_data.csv')

# Create a tree diagram
hierarchy$Path <- paste(
  'All',
  hierarchy$Continent,
  hierarchy$Country,
  sep ='/'
)

head(hierarchy$Path)

# Create a tree from a dataframe
tree <- as.Node(
   x=hierarchy,
   pathName='Path'
)

# Print the tree
print(tree, limit=10)

# Plot the tree
plot(tree)

# Create a tree graph
library(igraph)
treeGraph <- as.igraph(tree)

# Plot tree graph
plot(x=treeGraph, main='Geographic Distribution Hierarchy')

# Add row names for dendro labels
row.names(hierarchy) <- hierarchy$Country
head(hierarchy)

# Create a distance matrix
distance <- dist(hierarchy[,c(3,4)])
round(distance, 0)

# Create hierarchical clusters
clusters <- hclust(distance)

# Create dendrogram
plot(x=clusters, main='Heirarchical Clusters of Countries')

# Create a treemap
library(treemap)
treemap(
  dtf=hierarchy,
  index=c('Continent', 'Country'),
  vSize='Count',
  vColor='Continent',
  type='categorical',
  title='Count of Movies by Continent and Country',
  position.legend='none'
)

# Color based off of box office revenue
treemap(
  dtf=hierarchy,
  index=c('Continent', 'Country'),
  vSize='Count',
  vColor='Box.Office',
  type='value',
  title='Count of Movies by Continent and Country',
  position.legend='none'
)