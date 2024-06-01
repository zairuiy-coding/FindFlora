# FindFlora 💛👩‍🌾🌻

Welcome to FindFlora! Are you passionate about gardening? Looking for a smart, efficient way to select the perfect flowers for your garden based on specific environmental conditions? If yes, FindFlora is here to help.💡 

Utilizing advanced t-SNE algorithms for dimensional reduction and quadtree structures for efficient spatial querying, FindFlora tailors flower recommendations to the unique conditions of your garden, ensuring optimal growth and aesthetic harmony. 🌸✨

----

FindFlora is a Java-based application that leverages advanced machine learning techniques and spatial data structures to provide tailored flower recommendations. Our system is designed for both novice and expert gardeners to discover and integrate the best blooms into their green spaces.

### 🌺 Key Features:

- **Tailored Recommendations:** Receive personalized flower suggestions that fit the specific environmental and aesthetic conditions of your garden.
- **Advanced Search Capabilities:** Quickly find flowers based on specific attributes using a robust, attribute-indexed search system.
- **Data-Driven Insights:** Our recommendations are powered by a detailed Kaggle dataset featuring information on over 200 flowers.

## 🛠️ Project Structure

- **Garden Model:** Represents your garden's characteristics, such as size and environmental conditions. Methods allow for updating garden details and managing flower types.
- **Flower Model:** Profiles each flower with key attributes like name, color, and bloom season which are crucial for recommendations and searches.
- **QuadTree:** At the heart of our recommendation engine, this data structure efficiently organizes flowers spatially. Each node in the QuadTree corresponds to a flower, with coordinates representing t-SNE scores to facilitate spatial queries for finding the nearest similar flowers.
- **Search Engine:** Built on an inverted index, this component ensures rapid retrieval of flowers by various attributes, enhancing the user's ability to find flowers by specific criteria.

![FindFlora_UML](FindFlora_UML_diagram.png)

## 🌟 Recommendation Engine Explained

### QuadTree Mechanics:
The QuadTree is a tree data structure used to partition a space into distinct sections, each holding a different set of flower data points. Each node in the tree can potentially represent a partition of space at a different granularity. The flower data points are distributed among the nodes based on their t-SNE scores which correlate with their similarity in features.

### Insertion Process:
1. **Insertion:** Flowers are inserted into the QuadTree based on their t-SNE derived spatial scores. The process begins at the root and traverses down to the appropriate leaf node adhering to spatial boundaries defined at each node.
2. **Boundary Checking:** Ensures that each flower's coordinates are within the spatial constraints of the node. If a flower does not fit, the tree is traversed upwards until a suitable insertion point is found or a new root is established.

### Finding Nearest Neighbors:
To recommend similar flowers, the application performs a BFS-like traversal from the node of interest. This method starts at a specific node (representing a flower's position in our t-SNE derived spatial context) and expands outwards in layers—first exploring all sibling nodes and their children. This layered, breadth-first search approach allows the system to effectively capture approximately similar flowers located in nearby spatial partitions. The benefits of this method include:
- **Layered Search:** Ensures a comprehensive examination of neighboring nodes, providing a thorough and relevant selection of flower recommendations.
- **Approximate Nearest Neighbors:** Offers an efficient way to find similar flowers without the need for exact nearest neighbor calculations, which can be computationally expensive. This is particularly useful when speed and practical relevance take precedence over absolute accuracy.

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

## ✨ Contributors

- 👩‍💻**Zairui Yang:** [Github Profile](https://github.com/zairuiy-coding)
- 👩‍💻**Hao Tan:** [Github Profile](https://github.com/tanhaow)

----

Thank you for choosing FindFlora as your gardening companion. Happy gardening, and may your garden always bloom beautifully! 🌱💐