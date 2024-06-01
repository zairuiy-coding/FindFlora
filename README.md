# FindFlora 💛👩‍🌾🌻

Welcome to FindFlora! Are you passionate about gardening? Looking for a smart, efficient way to select the perfect flowers for your garden based on specific environmental conditions? If so, FindFlora is here to help. 💡

Utilizing advanced **t-SNE algorithms** for dimensional reduction and **quadtree structures** for efficient spatial querying, FindFlora delivers tailored flower recommendations to enhance the growth and aesthetic harmony of your garden. 🌸✨

---

FindFlora is a Java-based application that employs machine learning techniques and spatial data structures to offer personalized flower recommendations. Designed for both novice and expert gardeners, our system helps integrate the best blooms into your green spaces.

### 🌺 Key Features:

- **Tailored Recommendations:** Get personalized flower suggestions tailored to the specific environmental and aesthetic conditions of your garden.
- **Advanced Search Capabilities:** Quickly find flowers based on specific attributes using a **robust, attribute-indexed** search system.
- **Data-Driven Insights:** Powered by a detailed Kaggle dataset with information on over 200 flowers.

## 🛠️ Project Structure

- **Garden Model:** Represents your garden's characteristics, such as size and environmental conditions. Allows for updating garden details and managing flower types.
- **Flower Model:** Profiles each flower with key attributes like name, color, and bloom season, crucial for recommendations and searches.
- **QuadTree:** Central to our recommendation engine, this data structure efficiently organizes flowers spatially. Each node corresponds to a flower, with coordinates representing t-SNE scores to facilitate spatial queries for finding the nearest similar flowers.
- **Search Engine:** Built on an inverted index, this component ensures rapid retrieval of flowers by various attributes.

![FindFlora UML](FindFlora_UML_diagram.png)

## 🌟 Recommendation Engine Explained

### QuadTree Mechanics:
The QuadTree partitions space into distinct sections, each holding a different set of flower data points. Nodes distribute flowers based on their t-SNE scores, correlating to feature similarity.

### Insertion Process:
1. **Insertion:** Flowers are inserted into the QuadTree based on their t-SNE derived spatial scores, starting from the root and adhering to spatial boundaries.
2. **Boundary Checking:** Ensures coordinates are within node constraints. If not, the tree is traversed upwards until a fit is found or a new root is established.

### Finding Nearest Neighbors:
A BFS-like traversal identifies similar flowers starting from a specific node and expanding outwards. This method captures similar flowers in nearby spatial partitions effectively.
- **Layered Search:** Provides a thorough selection of flower recommendations by examining neighboring nodes.
- **Approximate Nearest Neighbors:** Efficiently finds similar flowers, prioritizing speed and practical relevance over absolute accuracy.

### 🚀 Usage

1. **Fork This Repository**
2. **Open in an IDE**
3. **Build the Project**
4. **Run the GardenFlowerSearchApp Class**

### 📘 References and Credits

- **Flower Dataset:** Powered by a comprehensive dataset from Kaggle with extensive data on 200+ flower types. [View Dataset](https://www.kaggle.com/datasets/kkhandekar/a-to-z-flowers-features-images)
- **Smile Machine Learning Library:** Utilizes Smile for Java to compute the t-SNE scores foundational to our recommendation engine. [Learn More about Smile](https://github.com/haifengl/smile)

### 🌟 Contribute

We welcome contributions from the community! Whether improving the algorithm, enhancing the UI, or expanding the dataset, your input can significantly enhance FindFlora.

## ✨ Contributors

- 👩‍💻 **Zairui Yang:** [Github Profile](https://github.com/zairuiy-coding)
- 👩‍💻 **Hao Tan:** [Github Profile](https://github.com/tanhaow)

---

Thank you for choosing FindFlora as your gardening companion. Happy gardening, and may your garden always bloom beautifully! 🌱💐
