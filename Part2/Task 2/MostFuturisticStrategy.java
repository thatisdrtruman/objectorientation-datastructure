public class MostFuturisticStrategy implements ChoiceStrategy {
    public Product chooseBetween(Product a, Product b){
         int AFuture = a.futuristicness;
         int BFuture= b.futuristicness;
         if (AFuture >= BFuture) {
             return a;
         } else{
            return b;
        }
    }
 	 // Complete this with an implementation of
	// chooseBetween(Product a, Product b) which returns
	// Product a if its futuristicness is greater than or equal
	// to that of b; and returns Product b otherwise
}


