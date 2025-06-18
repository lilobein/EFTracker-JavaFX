public class Indicator {
    private final int indicator_id;
    private String formula;
    private double value;
    private int currency_id;
    private int period_id;
    private int importance_constant;

    public Indicator(int indicator_id, String formula, double value, int currency_id, int period_id, int importance_constant) {
        this.currency_id = currency_id;
        this.indicator_id = indicator_id;
        this.formula = formula;
        this.value = value;
        this.period_id = period_id;
        this.importance_constant = importance_constant;
    }


    public int getIndicator_id() {
        return indicator_id;
    }

    public String getFormula() {
        return formula;
    }

    public double getValue() {
        return value;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public int getPeriod_id() {
        return period_id;
    }

    public int getImportance_constant() {
        return importance_constant;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }

    public void setImportance_constant(int importance_constant) {
        this.importance_constant = importance_constant;
    }
}