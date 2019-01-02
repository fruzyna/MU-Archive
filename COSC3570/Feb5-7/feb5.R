# February 5 In Class Notes
library(reshape2)
library(plyr)
library(tidyverse)

# load in dataset of tb around the world
tb = read.csv('tb.csv')
tb

#Selecting a single column
tb$iso2

names(tb)[1] <- "country" # rename the first column, R is weird

names(tb) <- str_replace(names(tb), "new_sp_", "") # remove new_sp_ from all columns

#Looking at just a subset of a original dataframe
raw_subset <- subset(tb, year ==2000)

#Deleting the extraneous columns
#in R we do this by setting them to NULL
raw_subset$new_sp <- NULL
raw_subset$m04 <- NULL
raw_subset$m514 <- NULL
raw_subset$f04 <- NULL
raw_subset$f514 <- NULL

#*******************************************************************************************************************************************
#Problems with the dataset:
#1: A single variable (sex_age) is spread across multiple columns. Rule of tidy data: a single variable should belong to a single column
#2: Two variables are stored in the same column (sex_age should be split into the sex column and the age column).
#*******************************************************************************************************************************************

#MELTING
#Fixing problem #1 by Melting the data by using the country and the year as our Ids
clean_data <- melt(raw_subset, id = c("country", "year"))

#Changing column names to be more accurate/descriptive
names(clean_data)[3] <- "sex_age"
names(clean_data)[4] <- "frequency"

#Sorting the data; first by the country, then sex_age and then by year)
clean_data <- arrange(clean_data, country, sex_age, year)