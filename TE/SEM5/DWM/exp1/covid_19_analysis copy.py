import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

# Load the dataset
df = pd.read_csv('covid_19_india.csv')  # Adjust the path accordingly

# Display the first 5 rows
print(df)

# Check for missing values
print("Missing values in each column:\n", df.isnull().sum())
print("Duplicate values in each column:\n", df.drop_duplicates())
print("Shape of the DataFrame:", df.shape)
# print("Stars removed :\n",df[df[]])

# Remove duplicates
df.drop_duplicates(inplace=True)
print("Shape after removing duplicates:", df.shape)

# Drop the 'Sno' column
df.drop('Sno', axis=1, inplace=True)
print("DataFrame after dropping 'Sno':\n", df.head(5))

# Calculate total confirmed cases for Kerala
kerala_confirmed = df[df['State/UnionTerritory'] == 'Kerala']['Confirmed'].sum()
print(f"Total confirmed cases in Kerala: {kerala_confirmed}")

# Calculate total deaths between specified dates
total_deaths = df[(df['Date'] >= '2020-01-01') & (df['Date'] <= '2021-12-31')]['Deaths'].sum()
print(f"Total deaths from 2020-01-01 to 2021-12-31: {total_deaths}")

# Group by state and sum the 'Cured' cases
state_cured_totals = df.groupby('State/UnionTerritory')['Cured'].sum()

# Find the state with the highest cured cases
highest_cured_cases = state_cured_totals.max()
state_with_most_cured = state_cured_totals.idxmax()
print(f"State with most cured cases: {state_with_most_cured} ({highest_cured_cases})")

# Group by state and sum the 'Confirmed' cases
state_confirmed_totals = df.groupby('State/UnionTerritory')['Confirmed'].sum()

# Find the state with the highest confirmed cases
highest_confirmed_cases = state_confirmed_totals.max()
state_with_most_confirmed = state_confirmed_totals.idxmax()
print(f"State with most confirmed cases: {state_with_most_confirmed} ({highest_confirmed_cases})")

# Plotting total confirmed cases in all states
plt.figure(figsize=(14, 8))
state_confirmed_totals.plot(kind='bar')
plt.title('Total Confirmed COVID-19 Cases in All States')
plt.xlabel('State/UnionTerritory')
plt.ylabel('Number of Confirmed Cases')
plt.xticks(rotation=90)
plt.show()

# Filter DataFrame for the years 2020 and 2021
df_2020_2021 = df[(df['Date'] >= '2020-01-01') & (df['Date'] <= '2021-12-31')]

# Group by date and sum the 'Confirmed' cases
daily_confirmed = df_2020_2021.groupby('Date')['Confirmed'].sum()

# Plotting daily confirmed cases
plt.figure(figsize=(12, 6))
daily_confirmed.plot()
plt.title('Distribution of COVID-19 Patients (2020-2021)')
plt.xlabel('Date')
plt.ylabel('Number of Confirmed Cases')
plt.grid(True)
plt.show()
