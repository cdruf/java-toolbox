import org.apache.commons.math3.distribution.NormalDistribution;
import util.VMHelper;

public class QuickTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println(VMHelper.getVMStats());
        NormalDistribution di = new NormalDistribution();
    }

}
