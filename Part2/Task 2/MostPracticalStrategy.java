public class MostPracticalStrategy implements ChoiceStrategy {
    public Product chooseBetween(Product a, Product b){
        int APractical = a.practicality;
        int BPractical= b.practicality;
        if (APractical >= BPractical) {
            return a;
        } else{
            return b;
        }
    }
  	// Complete this with an implementation of
	// chooseBetween(Product a, Product b) which returns
	// Product a if its practicality is greater than or equal
	// to that of b; and returns Product b otherwise
}
   
