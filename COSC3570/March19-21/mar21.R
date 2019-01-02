# March 21 In Class Notes

library(ggplot2)

# Plot price of all diamonds
with(diamonds, hist(price)) # long tailed distribution, very real world (prices, human data, any inequality)

# Add color
qplot(price, data=diamonds, color='red')

# Compare carats and price
with(diamonds, plot(carat,price))

# Plotted with color
qplot(carat, price, data=diamonds, color='red')
qplot(price, data=diamonds, geom='density', fill='red')

# Added labels
with(diamonds, plot(carat, price, xlab='Weight in Carats', ylab='Price in USD', main='Diamonds are Expensive'))

# Labels and color
qplot(carat, price, data=diamonds, color='red', xlab='Weight in Carats', ylab='Price in USD', main='Diamonds are Expensive')

# Small Multiples by color
qplot(carat, price, data=diamonds, facets=color~.)
# the higher the carat, the worse the quality will be and the more you will pay

# By clarity
qplot(carat, price, data=diamonds, facets=clarity~.)
# the higher the clarity, the higher the price that can be charged

# Compare both!
qplot(carat, price, data=diamonds, facets=clarity~color)

### End Diamonds --- Start Homeownership ###

library(dplyr)
library(data.table)

# Load in data
rawData <- fread('homeownership.txt')

# Graph homeownership over time
ggplot(data=rawData[state=='United States',], aes(x=year, y=value)) +
  geom_line(color='red', size=1) +
  theme_minimal() +
  scale_x_continuous(breaks=unique(rawData$year)) +
  scale_y_continuous(breaks=waiver()) +
  coord_cartesian(xlim=c(1895, 2020)) +
  labs(x='Year', y='Rate', title='Homeownership Rate (%)', subtitle='United States') +
  theme(plot.caption=element_text(hjust=0)) +
  theme(axis.text=element_text(size=7))

# Add a a single point
ggplot(data=rawData[state=="United States", ], aes(x=year, y=value)) +
  geom_line(color="red", size=1) +
  theme_minimal() +
  geom_text(data=rawData[state=="United States" & year==2015,],hjust=0,aes(label=paste(" ",round(value,1))))+
  geom_point(data=rawData[state=="United States" & year==2015,],color="blue",size=3)+
  scale_x_continuous(breaks=unique(rawData$year)) +
  scale_y_continuous(breaks = waiver()) +
  coord_cartesian(xlim = c(1895, 2020)) +
  labs(x="",y="", title="Homeownership rate (%)",
       subtitle = "United States") +
  theme(plot.caption=element_text(hjust=0)) +
  theme(axis.text=element_text(size=7))

# Compare all states
ggplot(data=rawData[! (state %in% c("United States","District of Columbia")),],aes(x=year,y=value))+
  geom_line(color="red",size=.95)+
  theme_minimal()+
  geom_text(data=rawData[! (state %in% c("United States","District of Columbia")) & year==2015,],hjust=0,aes(label=paste(" ",round(value,0))),size=2)+
  geom_point(data=rawData[! (state %in% c("United States","District of Columbia")) & year==2015,],color="blue",size=1)+
  scale_x_continuous(breaks=c(1900,2015))+
  scale_y_continuous(breaks=waiver())+
  coord_cartesian(xlim=c(1895,2025))+facet_wrap(~state,ncol=5)+
  labs(x="",y="",title="Homeownership rate (%)",
       subtitle="by state")+
  theme(plot.caption=element_text(hjust=0))+
  theme(axis.text=element_text(size=7))


# Compare a few states
ggplot(data=rawData[(state %in% c("Hawaii","California", "Wisconsin", "Michigan", "Texas", "New York")),],aes(x=year,y=value))+
  geom_line(color="red",size=1)+
  theme_minimal()+
  scale_x_continuous(breaks=c(1900,1925,1950,1975,2000,2025))+
  scale_y_continuous(breaks=waiver())+
  coord_cartesian(xlim=c(1895,2025))+facet_wrap(~state,ncol=2)+
  labs(x="",y="",title="Homeownership rate (%)",
       subtitle="by state")+
  theme(plot.caption=element_text(hjust=0))+
  theme(axis.text=element_text(size=7))