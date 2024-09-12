import streamlit as st
import pandas as pd
import pickle as pkl

def show():
    st.title("Prediction")

    # Path to pickle file
    pickle_file = "MLR_practice/house_prediction_pipeline.pkl"

    @st.cache_resource  # Caching the model to improve performance
    def load_model(pickle_file):
        """Load the model from a pickle file."""
        try:
            with open(pickle_file, 'rb') as file:
                model = pkl.load(file)
            return model
        except FileNotFoundError:
            st.error(f"Error: The file {pickle_file} was not found.")
        except pkl.UnpicklingError:
            st.error("Error: The file could not be unpickled. It may be corrupted or not a valid pickle file.")
        except Exception as e:
            st.error(f"An unexpected error occurred: {e}")
        return None

    # Load the model
    model = load_model(pickle_file)

    if model is not None:
        # List of feature names 
        feature_names = ['level_0', 'index', 'bedrooms', 'bathrooms', 'sqft_lot', 'floors', 'condition', 'grade', 'sqft_above', 'sqft_basement', 'yr_built', 'lat', 'long', 'sqft_living15', 'sqft_lot15']

        user_inputs = {}
        for feature in feature_names:
            user_input = st.text_input(f'Enter value for {feature}', '')
            user_inputs[feature] = user_input

        # Check if all user inputs are filled
        if all(value.strip() for value in user_inputs.values()):
            # Convert user inputs to numerical values
            try:
                user_inputs = {feature: float(value) for feature, value in user_inputs.items()}
            except ValueError:
                st.error("Please enter valid numerical values.")
                user_inputs = {feature: None for feature in user_inputs}
            
            # Convert user inputs to a DataFrame
            input_df = pd.DataFrame([user_inputs], columns=feature_names)

            # Button to trigger prediction
            if st.button("Predict"):
                if not input_df.empty:
                    try:
                        # Predict using the loaded model
                        prediction = model.predict(input_df) # makes predictions based on user input
                        st.write(f"Prediction: {prediction[0]}")
                    except Exception as e:
                        st.error(f"An error occurred during prediction: {e}")
                else:
                    st.write("Please enter values for all features.")
        else:
            if st.button("Predict"):
                st.error("Please enter values for all features.")
    else:
        st.write("Model could not be loaded.")
