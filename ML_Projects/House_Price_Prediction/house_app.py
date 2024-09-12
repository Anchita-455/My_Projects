import streamlit as st

st.title("House Price Prediction Model")

# Sidebar for navigation
st.sidebar.title("Navigation")
page = st.sidebar.radio("Select a Page", ["Dashboard", "Prediction"])

if page == "Dashboard":
    import dashboard
    dashboard.show()
elif page == "Prediction":
    import prediction
    prediction.show()