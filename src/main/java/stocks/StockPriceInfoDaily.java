package stocks;

import org.joda.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * Daily stock price information.
 */
@Getter
public class StockPriceInfoDaily {

    public final LocalDate datum;
    public final double    open;
    public final double    high;
    public final double    low;
    public final double    close;
    public final double    volume;

    @Setter
    private double         movingAvg38;
    @Setter
    private double         movingAvg100;
    @Setter
    private double         movingAvg200;

    public StockPriceInfoDaily(LocalDate datum, double open, double high, double low, double close,
            double volume) {
        super();
        this.datum = datum;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        movingAvg38 = -1;
        movingAvg100 = -1;
        movingAvg200 = -1;
    }

    @Override
    public String toString() {
        return "StockPriceInfoDaily [datum=" + datum + ", open=" + open + ", high=" + high + ", low=" + low
                + ", close=" + close + ", volume=" + volume + ", movingAvg38=" + movingAvg38
                + ", movingAvg100=" + movingAvg100 + ", movingAvg200=" + movingAvg200 + "]";
    }

}
