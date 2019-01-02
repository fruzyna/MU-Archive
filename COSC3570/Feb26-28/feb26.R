# Feb 26 In Class Notes

library(datasets)
library(mice)
library(VIM)

raw_data <- iris

par(mfrow=c(3,2))

# Plot Sepal.Width vs Sepal.Length
marginplot(raw_data[, c('Sepal.Length', 'Sepal.Width')], pch=20, col=c('red'), cex.numbers=1.3)

# Attempt to fit to a linear model
lm_model <- lm(Sepal.Width ~ Sepal.Length, data=raw_data)
abline(lm_model)
summary(lm_model)
# P Values of 0.152 > 0.05 so not near significant

# Attempt for all of them
marginplot(raw_data[, c('Petal.Length', 'Petal.Width')], pch=20, col=c('red'), cex.numbers=1.3)
lm_model <- lm(Petal.Width ~ Petal.Length, data=raw_data)
abline(lm_model)
summary(lm_model)

marginplot(raw_data[, c('Petal.Length', 'Sepal.Width')], pch=20, col=c('red'), cex.numbers=1.3)
lm_model <- lm(Sepal.Width ~ Petal.Length, data=raw_data)
abline(lm_model)
summary(lm_model)

marginplot(raw_data[, c('Sepal.Length', 'Petal.Width')], pch=20, col=c('red'), cex.numbers=1.3)
lm_model <- lm(Petal.Width ~ Sepal.Length, data=raw_data)
abline(lm_model)
summary(lm_model)

marginplot(raw_data[, c('Petal.Width', 'Sepal.Width')], pch=20, col=c('red'), cex.numbers=1.3)
lm_model <- lm(Sepal.Width ~ Petal.Width, data=raw_data)
abline(lm_model)
summary(lm_model)

marginplot(raw_data[, c('Sepal.Length', 'Petal.Length')], pch=20, col=c('red'), cex.numbers=1.3)
lm_model <- lm(Petal.Length ~ Sepal.Length, data=raw_data)
abline(lm_model)
summary(lm_model)