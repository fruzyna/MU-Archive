#February 12 In Class Work

# logistics
# group project
# 9, 4 member teams
# figure out group by monday
# make group on Piazza

library(reshape2)
library(plyr)
library(tidyverse)

w = read.csv('weather.csv')
names(w)[(1:3)] = c('id', 'year-month', 'measurement')
names(w)[-(1:3)] <- c(1:31)
w$id <- NULL
w <- separate(w, col='year-month', into=c('year','month'), sep=4)

y55 <- subset(w, year==1955)
y55[y55 == '-9999'] <- NA

y55 <- melt(y55, id=1:3)
names(y55)[4] <- 'day'
y55$date <- as.Date(ISOdate(y55$year, y55$month, y55$day))

y55$year <- NULL
y55$month <- NULL
y55$day <- NULL

tmin <- subset(y55, measurement == 'TMIN')
tmax <- subset(y55, measurement == 'TMAX')
prcp <- subset(y55, measurement == 'PRCP')

y55 <- y55[c('date', 'measurement', 'value')]

y55 <- dcast(y55, date ~ measurement, value.var='value')