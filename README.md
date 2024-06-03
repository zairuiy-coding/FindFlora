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

## 🌐 Data Processing in FindFlora

FindFlora employs sophisticated data processing techniques to ensure accurate and relevant flower recommendations. Our application meticulously manages and utilizes extensive flower data through a series of carefully designed procedures.

### Data Loading and Parsing

The `FlowerDatabase` class is essential for loading flower data from a CSV file, parsing each entry to extract names, descriptions, plant types, colors, hardiness zones, bloom seasons, sun needs, water needs, and maintenance requirements. Here’s how data is processed:

- **Name Parsing:** Flowers often have multiple names or aliases. Our system parses and maps each name (primary and aliases) to its primary name for consistent referencing throughout the application.
- **Color Extraction:** We standardize a broad range of color descriptions into recognized color names using a predefined mapping, aiding in accurate user preference matching and search functionality.

### Building the Attribute Matrix

`FlowerMatrixBuilder` constructs an attribute matrix representing flowers and their attributes. This matrix is pivotal for calculating similarity scores between flowers, fundamental to our recommendation engine. Each row represents a flower, and each column corresponds to an attribute, filled with binary values indicating the presence or absence of specific attributes.

### Dimensionality Reduction with t-SNE

The `TSNEProcessor` class reduces the dimensionality of the attribute data using t-Distributed Stochastic Neighbor Embedding (t-SNE), which is vital for visualizing complex datasets in a way that is interpretable and actionable:

1. **Data Conversion:** Converts the attribute matrix from integers to doubles, preparing it for t-SNE computation.
2. **t-SNE Computation:** Using the Smile library, it reduces the data to two dimensions, optimizing the balance between accuracy and computational efficiency.
3. **Scaling Results:** Post-reduction, the t-SNE coordinates are scaled to a uniform range (0 to 100), maintaining consistency in further quadtree construction and analysis.

## 🌟 Recommendation Engine Explained

### QuadTree Mechanics:
The QuadTree partitions space into distinct sections, each holding a different set of flower data points. Nodes distribute flowers based on their t-SNE scores, correlating to feature similarity.

### Insertion Process:
1. **Insertion:** Flowers are inserted into the QuadTree based on their t-SNE derived spatial scores, starting from the root and adhering to spatial boundaries.
2. **Boundary Checking:** Ensures coordinates are within node constraints. 

### Finding Nearest Neighbors:
A BFS-like traversal identifies similar flowers starting from a specific node and expanding outwards. This method captures similar flowers in nearby spatial partitions effectively.
- **Layered Search:** Thoroughly selects flower recommendations by examining neighboring nodes. The traversal begins with the node's descendants, which are within the same area and likely close to each other. It then moves upwards to the node's siblings, parent's siblings, and beyond.
- **Approximate Nearest Neighbors:** Efficiently finds similar flowers by prioritizing speed and practical relevance over exact accuracy, ensuring quick and relevant recommendations.

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

- 👩‍💻 **Zairui Yang:** [@zairuiy-coding](https://github.com/zairuiy-coding)
- 👩‍💻 **Hao Tan:** [@tanhaow](https://github.com/tanhaow)

---

Thank you for choosing FindFlora as your gardening companion. Happy gardening, and may your garden always bloom beautifully! 🌱💐
