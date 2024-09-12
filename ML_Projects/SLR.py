import numpy as np
import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error, r2_score
import matplotlib.pyplot as plt

# loading the dataset
df = pd.read_csv("icecream_dataset.csv")

# extracting the independent and dependent variables and storing it seperately
X = df[["Temperature"]] # stores the SINGLE independent variable
Y = df["Revenue"] # stores the dependent variable

# splitting the data into training data and testing data
# training data -- for the model to know the relationship between temperature and icecream
# testing data -- model predicts based on unseen data
# test_size -> percentage that will be used for testing, rest will be used for training
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.3, random_state=0)

# creating and training the model 
model = LinearRegression() # creates a linear regression model 
model.fit(X_train,Y_train) # training the model to find the best fit line

# Make predictions
y_pred = model.predict(X_test) # predicts the y-value based on the unseen x-value

# Evaluating the model 
mse = mean_squared_error(Y_test, y_pred) # finds MSE
r2 = r2_score(Y_test, y_pred) # finds r^2

print("Mean Squared Error:", mse)
print("R-squared:", r2)
print("Slope (coefficient):", model.coef_[0])
print("Intercept:", model.intercept_)

# Plotting
plt.figure(figsize=(12, 8))

# Plot training data points
plt.scatter(X_train, Y_train, color='blue', label='Training data')

# Plot testing data points
plt.scatter(X_test, Y_test, color='green', label='Testing data')

# Create data for plotting the regression line
X_plot = np.linspace(X["Temperature"].min(), X["Temperature"].max(), 100).reshape(-1, 1)
y_plot = model.predict(X_plot)

# Plot the regression line
plt.plot(X_plot, y_plot, color='red', linewidth=2, label='Regression line')

# Plot predictions vs. actual values for the test set
plt.scatter(X_test, y_pred, color='orange', marker='x', label='Predictions')

# Add labels and title
plt.xlabel('Temperature in degree celsius')
plt.ylabel('Revenue')
plt.title('Simple Linear Regression with Training, Testing Data, and Predictions')
plt.legend()

# Show the plot
plt.show()