import streamlit as st
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

df = pd.read_csv("house_dataset.csv")

def show():
    st.title("Dashboard")
    st.write("\n")
    st.write("Here are the statistics of the house dataset")
    st.write(df.describe())
    st.write("Printing null values of the dataset present in each column")
    st.write(df.isnull().sum())

    # Display total number of unique values for each column
    # Create a DataFrame to store the results
    unique_counts = pd.DataFrame({
        'Column Name': df.columns,
        'Number of Unique Values': [df[col].nunique() for col in df.columns]
    })

    # Display the DataFrame in Streamlit
    st.write("Unique Value Counts per Column:")
    st.dataframe(unique_counts)

    # Visualizations -- boxplot, hisplot, and displot
    # additional visualization -- jointplot, scatterplot
    # jointplot is a powerful visualization tool that helps to 
    # check the correlation between two variables 

    # drop down menu for selecting a column
    plot_types = ['Boxplot', 'Displot', 'Histplot','Scatterplot','Jointplot']
    selected_plot = st.selectbox('Select a plot type', plot_types)
    
    numeric_columns = df.select_dtypes(include=['int64', 'float64']).columns

    # checks if it is boxplot, displot or histplot -- they only 
    # require one column for plotting
    if selected_plot in ['Boxplot', 'Displot', 'Histplot']:
        # stores the column selected by the user
        selected_column = st.selectbox('Select a column', numeric_columns)
    
        if selected_column:
            plt.figure(figsize=(10, 6))
            
            if selected_plot == 'Boxplot':
                sns.boxplot(y=df[selected_column])
                plt.title(f'Boxplot of {selected_column}')
            
            elif selected_plot == 'Displot':
                sns.histplot(df[selected_column], kde=True)
                plt.title(f'Displot of {selected_column}')
            
            elif selected_plot == 'Histplot':
                sns.histplot(df[selected_column], kde=False)
                plt.title(f'Histplot of {selected_column}')
            
            st.pyplot(plt)  # Display the plot in Streamlit
            plt.close()  # Close the plot to prevent display duplication

    # checks if it is scatterplot or jointplot 
    # both scatterplot and joinplot require the user 
    # to select two columns in order to check the correlation 
    # between these two columns.
    elif selected_plot in ['Scatterplot', 'Jointplot']:
        # Two columns for these plots
        x_col = st.selectbox('Select X-axis column', numeric_columns)
        y_col = st.selectbox('Select Y-axis column', numeric_columns)

        if x_col and y_col:
            plt.figure(figsize=(10, 6))
            
            if selected_plot == 'Scatterplot':
                sns.scatterplot(x=df[x_col], y=df[y_col])
                plt.title(f'Scatterplot of {x_col} vs {y_col}')
            
            elif selected_plot == 'Jointplot':
                sns.jointplot(x=df[x_col], y=df[y_col], kind='scatter')  
                plt.suptitle(f'Jointplot of {x_col} vs {y_col}', y=1.02) 

        st.pyplot(plt)  # Display the plot in Streamlit
        plt.close()  # Close the plot to prevent display duplication

    # additional visualization -- plotting correlation heatmap
    # Correlation Heatmap section

    numeric_columns = df.select_dtypes(include=['int64', 'float64']).columns

    plt.figure(figsize=(10, 8))
    correlation_matrix = df[numeric_columns].corr()  # Compute the correlation matrix
    sns.heatmap(correlation_matrix, annot=True, cmap='coolwarm', fmt='.2f', vmin=-1, vmax=1)
    plt.title('Correlation Heatmap')

    st.pyplot(plt)  # Display the plot in Streamlit
    plt.close()  # Close the plot to prevent display duplication