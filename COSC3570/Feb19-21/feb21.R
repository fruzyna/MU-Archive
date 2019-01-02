# February 21 In Class Notes

library(mice)
library(VIM)
library(car)

raw_data <- Prestige
head(raw_data)
summary(raw_data)

# Plot all incomes and educations
marginplot(raw_data[, c('education', 'income')], pch=20, col=c('red'))

# Model linearly, want r^2 to be near 1, this is low (0.3336)
lm_model <- lm(income ~ education, data = raw_data)
abline(lm_model)
summary(lm_model)

mean(raw_data$income)

# Introduce some missing data
test_data <- raw_data
test_data[sample(1:nrow(test_data), 30), 'education'] <- NA
test_data[sample(1:nrow(test_data), 30), 'income'] <- NA

# Impute the data
imputed_data <- mice(test_data, m=30, maxit=40)
imp_1 <- complete(imputed_data, 1)

# Compare the original and new data
summary(raw_data)
summary(imp_1)
imputed_data$method

# Show each iteration of the imputation
xyplot(imputed_data, education~income | .imp, pch=20, cex=1.4)
densityplot(imputed_data)

# Imputed data to fit linear model
lm_imputed <- with(imputed_data, lm(income~education))
summary(lm_imputed)

combined_model <- pool(lm_imputed)
summary(combined_model)

# This is forcing the data to fit a linear model, even though it does
# This is inflicting our own bias on how the data should be linear
# It is not the fault of the computer that it wouldn't fit
# We should ensure we never add our own biases