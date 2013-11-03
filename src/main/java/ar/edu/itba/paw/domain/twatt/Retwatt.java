package ar.edu.itba.paw.domain.twatt;

import ar.edu.itba.paw.domain.twattuser.TwattUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Retwatt extends Twatt {

    @ManyToOne
    private Twatt original;

    Retwatt() {
        // needed by hibernate
    }

    public Retwatt(Twatt original, TwattUser retwatter) {
        super(original.getMessage(), retwatter);
        this.original = original;
    }

    public Twatt getOriginalTwatt() {
        return this.original;
    }
}
