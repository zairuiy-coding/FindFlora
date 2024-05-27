# FindFlora 💛👩‍🌾🌻

Welcome to FindFlora! If you're passionate about gardening and looking for a smart, efficient way to select the perfect flowers for your garden, you've come to the right place.💡

----

FindFlora is a Java-based application that leverages algorithms to provide tailored flower recommendations based on the unique conditions of your garden. Our system is designed to help both novice and expert gardeners discover and integrate the best blooms into their green spaces. 🌸✨

### 🌺 Key Features:

- **Tailored Recommendations:** Get personalized flower suggestions that fit the unique environmental conditions and aesthetic preferences of your garden.
- **Advanced Search Capabilities:** Quickly find flowers based on specific attributes using a robust, attribute-indexed search system.
- **Data-Driven Insights:** Our recommendations are powered by a robust dataset from Kaggle, which features detailed information on 200+ flowers.

### 🛠️ Data Structure

- **Recommendation Engine:** At the core of FindFlora is a quad-tree data structure, which utilizes t-SNE scores calculated from pairwise comparisons of flowers. This approach allows for nuanced and highly relevant flower recommendations.
- **Search Engine:** An inverted index efficiently maps various flower attributes to their corresponding entries, enabling fast and accurate search results.

### 🚀 Usage

1. **Fork This Repository**
2. **Open in an IDE**
3. **Build the Project**
4. **Run GardenFlowerSearchApp Class**

### 📘 References and Credits

- **Flower Dataset:** Our application is powered by a comprehensive flower dataset available on Kaggle, which includes extensive data on 200+ flower types and their attributes. [View Dataset](https://www.kaggle.com/datasets/kkhandekar/a-to-z-flowers-features-images)
- **Smile Machine Learning Library:** We utilize the open-source Smile machine learning library for Java to compute the t-SNE scores that form the basis of our recommendation engine. [Learn More about Smile](https://github.com/haifengl/smile)

### 🌟 Contribute

We welcome contributions from the community! Whether it's improving the recommendation algorithm, enhancing the user interface, or expanding the dataset, your input can help make FindFlora even better for gardeners everywhere.

----

Thank you for choosing FindFlora as your gardening companion. Happy gardening, and may your garden always bloom beautifully! 🌱💐