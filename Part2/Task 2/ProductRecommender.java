public class ProductRecommender {

    ChoiceStrategy myStrategy;

    public static void main(String args[]) {
        ProductRecommender recommender=new ProductRecommender();
        recommender.doExample();
    }

    public void setMostFuturisticStrategy() {
        myStrategy = new MostFuturisticStrategy();
    }
    public void setMostPracticalStrategy() {
        myStrategy = new MostPracticalStrategy();
    }

    public void doExample() {
        Product p1=new Product("DeLorean DMC-12", 5, 1);
        Product p2=new Product("LDV Maxus", 1, 5);

        // Add code here to create a MostFuturisticStrategy and
        // print out the chosen vehicle according to this strategy
        System.out.println("Current strategy: choose most futuristic");
        this.setMostFuturisticStrategy();
        Product bestFuturistic = myStrategy.chooseBetween(p1, p2);
        System.out.println("Chosen vehicle: " + bestFuturistic.name);

        // Add code here to create a MostPracticalStrategy and
        // print out the chosen vehicle according to this strategy
    	System.out.println("Strategy changed: choose most practical");
        this.setMostPracticalStrategy();
        Product bestPractical = myStrategy.chooseBetween(p1, p2);
        System.out.println("Chosen vehicle: " + bestPractical.name);
    }
}

