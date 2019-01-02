# February 7th In Class Notes

# Starting with February 5 In Class Notes
library(reshape2)
library(plyr)
library(tidyverse)

tb = read.csv('tb.csv')
names(tb)[1] <- "country"
names(tb) <- str_replace(names(tb), "new_sp_", "")
raw_subset <- subset(tb, year ==2000)
raw_subset$new_sp <- NULL
raw_subset$m04 <- NULL
raw_subset$m514 <- NULL
raw_subset$f04 <- NULL
raw_subset$f514 <- NULL
clean_data <- melt(raw_subset, id = c("country", "year"))
names(clean_data)[3] <- "sex_age"
names(clean_data)[4] <- "frequency"
clean_data <- arrange(clean_data, country, sex_age, year)

# Start Feb 7

# Separate the sex_age column to sex and age columns

# By substitution
#clean_data$gender <- str_sub(clean_data$sex_age, 1, 1)
#clean_data$sex_age <- str_sub(clean_data$sex_age, 2)
#names(clean_data)[3] <- 'age_range'

# With separate()
clean_data <- separate(clean_data, col='sex_age', into=c('sex','age'), sep=1)

# Make age more readable

# By splitting into 2 columns
#clean_data <- separate(clean_data, col='age', into=c('min_age','max_age'), sep=-2) # splits into 2 columns

# Or by hyphonating

# Manaully with substitution
#clean_data$age <- paste(str_sub(clean_data$age, 0, -3), '-', str_sub(clean_data$age, -2, -1)) # put a - between min/max
#clean_data$age <- gsub('- 65', '65+', clean_data$age)
#clean_data$age <- gsub('- u', 'Unknown', clean_data$age)

# Automatically by factoring
ages <- c('014' = '0-14', '1524' = '15-24', '2534' = '25-34', '3544' = '35-44', '4554' = '45-54', '5564' = '55-64','65' = '65+', 'u' = 'NA')
clean_data$age <- factor(ages[str_sub(clean_data$age)], levels = ages)