package me.ouertani.fn2;



/**
 *
 * @author slim
 */
public class SumIteratee  implements Iteratee<Integer>{

    final Integer sum;

    public SumIteratee(Integer sum) {
        this.sum = sum;
    }

    public SumIteratee() {
        this(0);
    }
    

    @Override
    public Iteratee<Integer> handle(Input<Integer> e) {
    switch (e.state()) {
            case EL:
                Input.El<Integer> el = (Input.El) e;
                Integer elem = el.getE();
                 
                return new SumIteratee(sum +elem);
            default:
                return this;
        }
    }

    public Integer getSum() {
        return sum;
    }

   
    
}
