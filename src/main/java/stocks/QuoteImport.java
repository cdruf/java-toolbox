package stocks;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import org.json.JSONObject;

/**
 * 
 */
public class QuoteImport {

    // API-key www.alphavantage.co: 46MMSGHA8VICBAWQ
    private static final String URL_PART_1 = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=";
    private static final String URL_PART_2 = "&apikey=46MMSGHA8VICBAWQ&outputsize=full";

    // private static final String URL = "https://api.iextrading.com/1.0";

    @SuppressWarnings("deprecation")
    public static List<StockPriceInfoDaily> loadStockQuoteInfo(String symbol)
            throws MalformedURLException, IOException {
        // System.out.println("load " + symbol);
        List<StockPriceInfoDaily> ret = new ArrayList<StockPriceInfoDaily>();
        String url = URL_PART_1 + symbol + URL_PART_2;
        System.out.println(url);
        InputStream in = new URL(url).openStream();

        String json = IOUtils.toString(in);
        IOUtils.closeQuietly(in);

        JSONObject obj;
        try {
            obj = new JSONObject(json).getJSONObject("Time Series (Daily)");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String[] days = JSONObject.getNames(obj);
        for (String day : days) {
            JSONObject p = obj.getJSONObject(day);
            String openStr = p.getString("1. open");
            String highStr = p.getString("2. high");
            String lowStr = p.getString("3. low");
            String closeStr = p.getString("4. close");
            String volumeStr = p.getString("5. volume");

            String[] dateSplitted = day.split("-");
            StockPriceInfoDaily spid = new StockPriceInfoDaily(
                    new LocalDate(Integer.parseInt(dateSplitted[0]), Integer.parseInt(dateSplitted[1]),
                            Integer.parseInt(dateSplitted[2])),
                    Double.parseDouble(openStr), Double.parseDouble(highStr), Double.parseDouble(lowStr),
                    Double.parseDouble(closeStr), Integer.parseInt(volumeStr));
            ret.add(spid);
        }
        System.out.println("OK");
        Collections.sort(ret, new Comparator<StockPriceInfoDaily>() {
            @Override
            public int compare(StockPriceInfoDaily o1, StockPriceInfoDaily o2) {
                if (o1.datum.isEqual(o2.datum)) {
                    return 0;
                }
                if (o1.datum.isAfter(o2.datum)) return 1;
                return -1;
            }
        });
        return ret;
    }

}
