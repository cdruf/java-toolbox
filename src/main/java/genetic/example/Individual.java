package genetic.example;

public class Individual {

    public static int defaultGeneLength = 64;

    public byte[]     genes             = new byte[defaultGeneLength];

    public int        fitness           = 0;

    /**
     * Create a random individual
     */
    public void generateIndividual() {
        for (int i = 0; i < genes.length; i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < genes.length; i++) {
            geneString += genes[i];
        }
        return geneString;
    }
}
