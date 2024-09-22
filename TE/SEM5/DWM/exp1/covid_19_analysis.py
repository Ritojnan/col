import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from scipy.stats import zscore

# Load the data
data = pd.read_csv('covid_19_india.csv')

# Data cleaning: Handling missing values
# Replace '-' with NaN and convert columns to appropriate data types
data['ConfirmedIndianNational'] = data['ConfirmedIndianNational'].replace('-', np.nan).astype(float)
data['ConfirmedForeignNational'] = data['ConfirmedForeignNational'].replace('-', np.nan).astype(float)

# Fill missing numerical values with the median of the respective column
data['ConfirmedIndianNational'].fillna(data['ConfirmedIndianNational'].median(), inplace=True)
data['ConfirmedForeignNational'].fillna(data['ConfirmedForeignNational'].median(), inplace=True)

# Remove rows with any remaining missing values
data.dropna(inplace=True)

# Data cleaning: Removing outliers using Z-score
# Compute Z-scores
z_scores = np.abs(zscore(data[['ConfirmedIndianNational', 'ConfirmedForeignNational', 'Cured', 'Deaths', 'Confirmed']]))

# Define a threshold for identifying outliers
threshold = 3

# Remove rows with outliers
data = data[(z_scores < threshold).all(axis=1)]

# Data transformation: Min-max normalization
def min_max_normalize(column):
    return (column - column.min()) / (column.max() - column.min())

data['ConfirmedIndianNational_MinMax'] = min_max_normalize(data['ConfirmedIndianNational'])
data['ConfirmedForeignNational_MinMax'] = min_max_normalize(data['ConfirmedForeignNational'])
data['Cured_MinMax'] = min_max_normalize(data['Cured'])
data['Deaths_MinMax'] = min_max_normalize(data['Deaths'])
data['Confirmed_MinMax'] = min_max_normalize(data['Confirmed'])

# Data transformation: Z-score normalization
data['ConfirmedIndianNational_Zscore'] = zscore(data['ConfirmedIndianNational'])
data['ConfirmedForeignNational_Zscore'] = zscore(data['ConfirmedForeignNational'])
data['Cured_Zscore'] = zscore(data['Cured'])
data['Deaths_Zscore'] = zscore(data['Deaths'])
data['Confirmed_Zscore'] = zscore(data['Confirmed'])

# Data transformation: Decimal scaling
def decimal_scaling(column):
    return column / 10**np.ceil(np.log10(column.abs().max()))

data['ConfirmedIndianNational_Decimal'] = decimal_scaling(data['ConfirmedIndianNational'])
data['ConfirmedForeignNational_Decimal'] = decimal_scaling(data['ConfirmedForeignNational'])
data['Cured_Decimal'] = decimal_scaling(data['Cured'])
data['Deaths_Decimal'] = decimal_scaling(data['Deaths'])
data['Confirmed_Decimal'] = decimal_scaling(data['Confirmed'])

# Data discretization: Binning (equal-width binning)
data['Cured_Binned'] = pd.cut(data['Cured'], bins=5, labels=False)
data['Deaths_Binned'] = pd.cut(data['Deaths'], bins=5, labels=False)
data['Confirmed_Binned'] = pd.cut(data['Confirmed'], bins=5, labels=False)

# Data analysis and visualization

# Line Chart: Confirmed cases over time
plt.figure(figsize=(10, 6))
plt.plot(pd.to_datetime(data['Date']), data['Confirmed'], label='Confirmed Cases')
plt.xlabel('Date')
plt.ylabel('Number of Confirmed Cases')
plt.title('Confirmed Cases Over Time')
plt.legend()
plt.show()

# Bar Graph: Total confirmed cases per state
plt.figure(figsize=(12, 8))
data.groupby('State/UnionTerritory')['Confirmed'].sum().sort_values().plot(kind='barh')
plt.xlabel('Total Confirmed Cases')
plt.ylabel('State/UnionTerritory')
plt.title('Total Confirmed Cases per State/Union Territory')
plt.show()

# Histogram: Distribution of Cured cases
plt.figure(figsize=(10, 6))
plt.hist(data['Cured'], bins=20, edgecolor='k')
plt.xlabel('Number of Cured Cases')
plt.ylabel('Frequency')
plt.title('Distribution of Cured Cases')
plt.show()

# Box Plot: Confirmed cases by state
plt.figure(figsize=(12, 8))
sns.boxplot(x='State/UnionTerritory', y='Confirmed', data=data)
plt.xticks(rotation=90)
plt.xlabel('State/Union Territory')
plt.ylabel('Number of Confirmed Cases')
plt.title('Confirmed Cases by State/Union Territory')
plt.show()

# Scatter Plot: Cured vs Confirmed cases
plt.figure(figsize=(10, 6))
plt.scatter(data['Confirmed'], data['Cured'], alpha=0.5)
plt.xlabel('Number of Confirmed Cases')
plt.ylabel('Number of Cured Cases')
plt.title('Scatter Plot of Confirmed vs Cured Cases')
plt.show()

# Heat Map: Correlation between variables
plt.figure(figsize=(10, 8))
correlation_matrix = data[['ConfirmedIndianNational', 'ConfirmedForeignNational', 'Cured', 'Deaths', 'Confirmed']].corr()
sns.heatmap(correlation_matrix, annot=True, cmap='coolwarm')
plt.title('Correlation Heatmap')
plt.show()
