package gurobi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import gurobi.GRB.CharAttr;
import gurobi.GRB.DoubleAttr;
import gurobi.GRB.IntAttr;
import gurobi.GRB.StringAttr;
import logging.Logger;
import util.MyMath;

public class GRBHelper {

    public static String toString(GRBConstr c) {
        try {
            return c.get(StringAttr.ConstrName) + " " + c.get(CharAttr.Sense) + " " + c.get(DoubleAttr.RHS);
        } catch (GRBException e) {
            e.printStackTrace();
        }
        throw new Error();
    }

    public static void printCol(GRBModel m, GRBVar x) throws GRBException {
        GRBColumn column = m.getCol(x);
        Logger.writeln("obj = " + x.get(DoubleAttr.Obj));
        for (int j = 0; j < column.size(); j++) {
            double coeff = column.getCoeff(j);
            if (coeff != 0) {
                GRBConstr constr = column.getConstr(j);
                Logger.writeln(constr.get(StringAttr.ConstrName) + ", " + coeff);
            }
        }
    }

    public static void printPIs(GRBModel m) throws GRBException {
        for (GRBConstr constr : m.getConstrs()) {
            double val = constr.get(DoubleAttr.Pi);
            if (!MyMath.eq(0.0, val)) {
                Logger.writeln(constr.get(StringAttr.ConstrName) + ", " + val);
            }
        }
    }

    public static void assertVarsEqual(GRBVar a, GRBVar b) throws GRBException {
        // funktioniert nicht direkt nach Modell aufbauen
        // nach presolve geht es
        if (a == null && b != null) throw new Error();
        if (a != null && b == null) throw new Error();
        if (a.get(DoubleAttr.LB) != b.get(DoubleAttr.LB)) throw new Error();
        if (a.get(DoubleAttr.UB) != b.get(DoubleAttr.UB)) throw new Error();
        if (a.get(CharAttr.VType) != b.get(CharAttr.VType)) throw new Error();
    }

    public static void assertVarsEqualName(GRBVar a, GRBVar b) throws GRBException {
        if (!a.get(GRB.StringAttr.VarName).equals(b.get(GRB.StringAttr.VarName))) throw new Error();
    }

    public static void assertConstrEqual(GRBConstr a, GRBConstr b) throws GRBException {
        if (a == null && b != null) throw new Error();
        if (a != null && b == null) throw new Error();
        if (a.get(DoubleAttr.RHS) != b.get(DoubleAttr.RHS)) throw new Error();
        if (a.get(CharAttr.Sense) != b.get(CharAttr.Sense)) throw new Error();
    }

    public static void assertConstrEqualName(GRBConstr a, GRBConstr b) throws GRBException {
        if (!a.get(GRB.StringAttr.ConstrName).equals(b.get(GRB.StringAttr.ConstrName))) throw new Error();
    }

    public static void writeMat(GRBModel m, String path) {
        try {
            m.write(path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static void writeSol(GRBModel m, String path) {
        try {
            if (m.get(IntAttr.IsMIP) == 0)
                GRBHelper.writeSolLP(m, path);
            else
                GRBHelper.writeSolMIP(m, path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static void writeSolMIP(GRBModel m, String path) {
        try {
            File logFile = new File(path);
            logFile.delete();
            logFile.createNewFile();
            FileWriter fstream = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(fstream);

            out.write("objVal = " + m.get(GRB.DoubleAttr.ObjVal) + "\n");
            out.write("objConst = " + m.get(DoubleAttr.ObjCon) + "\n");

            out.write("\nvars\n");
            for (GRBVar var : m.getVars()) {
                double val = var.get(DoubleAttr.X);
                double objCoeff = var.get(DoubleAttr.Obj);
                if (MyMath.g(val, 0.0)) out.write(var.get(GRB.StringAttr.VarName) + " = "
                        + MyMath.round(val, 7) + ", objCoeff=" + objCoeff + "\n");
            }

            out.write("\nconstraints\n");
            for (GRBConstr c : m.getConstrs()) {
                String name = c.get(StringAttr.ConstrName);
                double slack = c.get(DoubleAttr.Slack);
                out.write(name + ": slack=" + MyMath.round(slack, 7) + "\n");
            }

            out.write("\nquality\n");
            out.write("MIPGap=" + m.get(DoubleAttr.MIPGap) + "\n");
            out.write("constrVio=" + m.get(DoubleAttr.ConstrVio) + "\n");
            out.write("boundVio=" + m.get(DoubleAttr.BoundVio) + "\n");
            out.write("intVio=" + m.get(DoubleAttr.IntVio) + "\n");

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static void writeSolLP(GRBModel m, String path) {
        try {
            File logFile = new File(path);
            logFile.delete();
            logFile.createNewFile();
            FileWriter fstream = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(fstream);

            out.write("objVal = " + m.get(GRB.DoubleAttr.ObjVal) + "\n");
            out.write("objConst = " + m.get(DoubleAttr.ObjCon) + "\n");

            out.write("\nvars\n");
            for (GRBVar var : m.getVars()) {
                double val = var.get(DoubleAttr.X);
                double objCoeff = var.get(DoubleAttr.Obj);
                if (val != 0.0) out.write(var.get(GRB.StringAttr.VarName) + " = " + MyMath.round(val, 7)
                        + ", objCoeff=" + objCoeff + "\n");
            }

            out.write("\nconstraints\n");
            for (GRBConstr c : m.getConstrs()) {
                String name = c.get(StringAttr.ConstrName);
                double slack = c.get(DoubleAttr.Slack);
                double pi = c.get(DoubleAttr.Pi);
                out.write(name + ": slack=" + MyMath.round(slack, 5) + ", pi=" + MyMath.round(pi, 7) + "\n");
            }

            out.write("\nquality\n");
            out.write("constrVio=" + m.get(DoubleAttr.ConstrVio) + "\n");
            out.write("constrResidual=" + m.get(DoubleAttr.ConstrResidual) + "\n");
            out.write("boundVio=" + m.get(DoubleAttr.BoundVio) + "\n");

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }

}
