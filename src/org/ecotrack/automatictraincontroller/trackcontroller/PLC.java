package trackcontroller;

//package org.ecotrack.automatictraincontroller.trackcontroller;
//import org.ecotrack.automatictraincontroller.trackmodel.Block;

import javax.script.*;
import org.apache.commons.jexl2.*;

/**
 *
 * @author Christian Boni
 */
public class PLC implements PLCInterface {

    private final JexlEngine jexlExpressionEvaluator;
    private final ScriptEngine pythonExpressionEvaluator;
    private final String proceedExpression;
    private final String switchExpression;
    private final String maintenanceExpression;
    private final String crossingExpression;
    private final String crossingActivateExpression;
    private final String monitorExpression;
    private final String pythonProceedExpression;
    private final String pythonSwitchExpression;
    private final String pythonMaintenanceExpression;
    private final String pythonCrossingExpression;
    private final String pythonCrossingActivateExpression;
    private final String pythonMonitorExpression;
    private final int redundancy = 5;

    /**
     *
     * @param proceedExpression Vital boolean expression for proceed access to
     * be granted.
     * @param switchExpression Vital boolean expression for switch access to be
     * granted.
     * @param maintenanceExpression Vital boolean expression for maintenance
     * access to be granted.
     * @param crossingExpression Vital boolean expression for crossing access to
     * be granted.
     * @param crossingActivateExpression Vital boolean expression for crossing
     * activation to be granted.
     * @param monitorExpression Vital boolean expression for proceed access
     * to be granted during monitoring the track.
     */
    public PLC(String proceedExpression, String switchExpression, String maintenanceExpression, String crossingExpression, String crossingActivateExpression, String monitorExpression) {
        this.proceedExpression = proceedExpression;
        this.switchExpression = switchExpression;
        this.maintenanceExpression = maintenanceExpression;
        this.crossingExpression = crossingExpression;
        this.crossingActivateExpression = crossingActivateExpression;
        this.monitorExpression = monitorExpression;
        
        pythonProceedExpression = pythonExpressionBuilder(proceedExpression);
        pythonSwitchExpression = pythonExpressionBuilder(switchExpression);
        pythonMaintenanceExpression = pythonExpressionBuilder(maintenanceExpression);
        pythonCrossingExpression = pythonExpressionBuilder(crossingExpression);
        pythonCrossingActivateExpression = pythonExpressionBuilder(crossingActivateExpression);
        pythonMonitorExpression = pythonExpressionBuilder(monitorExpression);

        pythonExpressionEvaluator = new ScriptEngineManager().getEngineByName("python");
        jexlExpressionEvaluator = new JexlEngine();
    }

    /**
     * Simple helper function to convert a basic Java expression to a basic
     * Python expression.
     *
     * @param javaExpression The string of the Java expression
     * @return The string of the Python expression
     */
    private String pythonExpressionBuilder(String javaExpression) {
        StringBuilder s = new StringBuilder(javaExpression);
        for (int i = 0, n = (s.length() - 1); i < n; i++) {
            char c1 = s.charAt(i);
            char c2 = s.charAt(i + 1);
            if (c1 == '&' && c2 == '&') {
                String begin = s.substring(0, i);
                String middle = " and ";
                String end = s.substring(i + 2);
                s = new StringBuilder();
                s.append(begin);
                s.append(middle);
                s.append(end);
            } else if (c1 == '|' && c2 == '|') {
                String begin = s.substring(0, i);
                String middle = " or ";
                String end = s.substring(i + 2);
                s = new StringBuilder();
                s.append(begin);
                s.append(middle);
                s.append(end);
            } else if (c1 == '!') {
                String begin = s.substring(0, i);
                String middle = "not ";
                String end = s.substring(i + 1);
                s = new StringBuilder();
                s.append(begin);
                s.append(middle);
                s.append(end);
            }
            n = (s.length() - 1);
        }
        return s.toString();
    }

    @Override
    public boolean vitalProceed(Block nextBlock, Block upcomingBlock) {
        boolean vitalResult = false;
        boolean pythonResult = true;
        boolean jexlResult = true;

        if (proceedExpression == null || pythonProceedExpression == null || nextBlock == null || upcomingBlock == null) {
            return false;
        }

        boolean occupied1 = nextBlock.getOccupancy();
        boolean broken1 = !nextBlock.getState();
        boolean closed1 = !nextBlock.getOpen();
        boolean occupied2 = upcomingBlock.getOccupancy();
        boolean broken2 = !upcomingBlock.getState();
        boolean closed2 = !upcomingBlock.getOpen();

        try {
            for (int i = 0; i < redundancy; i++) {
                pythonExpressionEvaluator.put("nextBlockOccupied", occupied1);
                pythonExpressionEvaluator.put("nextBlockBroken", broken1);
                pythonExpressionEvaluator.put("nextBlockClosed", closed1);
                pythonExpressionEvaluator.put("upcomingBlockOccupied", occupied2);
                pythonExpressionEvaluator.put("upcomingBlockBroken", broken2);
                pythonExpressionEvaluator.put("upcomingBlockClosed", closed2);
                pythonResult = pythonResult && (boolean) pythonExpressionEvaluator.eval(pythonProceedExpression);
            }
        } catch (Exception ex) {
            return false;
        }

        try {
            for (int i = 0; i < redundancy; i++) {
                Expression expression = jexlExpressionEvaluator.createExpression(proceedExpression);
                JexlContext context = new MapContext();
                context.set("nextBlockOccupied", occupied1);
                context.set("nextBlockBroken", broken1);
                context.set("nextBlockClosed", closed1);
                context.set("upcomingBlockOccupied", occupied2);
                context.set("upcomingBlockBroken", broken2);
                context.set("upcomingBlockClosed", closed2);
                jexlResult = jexlResult && (boolean) expression.evaluate(context);
            }
        } catch (Exception ex) {
            return false;
        }

        vitalResult = pythonResult && jexlResult;

        return vitalResult;
    }

    @Override
    public boolean vitalSwitch(Block switchBlock, Block destinationBlock) {
        boolean vitalResult = false;
        boolean pythonResult = true;
        boolean jexlResult = true;

        if (switchExpression == null || pythonSwitchExpression == null || switchBlock == null || destinationBlock == null) {
            return false;
        }

        boolean occupied1 = switchBlock.getOccupancy();
        boolean occupied2 = destinationBlock.getOccupancy();
        boolean broken = !switchBlock.getState();

        try {
            for (int i = 0; i < redundancy; i++) {
                pythonExpressionEvaluator.put("switchBlockOccupied", occupied1);
                pythonExpressionEvaluator.put("destinationBlockOccupied", occupied2);
                pythonExpressionEvaluator.put("switchBroken", broken);
                pythonResult = pythonResult && (boolean) pythonExpressionEvaluator.eval(pythonSwitchExpression);
            }
        } catch (Exception ex) {
            return false;
        }

        try {
            for (int i = 0; i < redundancy; i++) {
                Expression expression = jexlExpressionEvaluator.createExpression(switchExpression);
                JexlContext context = new MapContext();
                context.set("switchBlockOccupied", occupied1);
                context.set("destinationBlockOccupied", occupied2);
                context.set("switchBroken", broken);
                jexlResult = jexlResult && (boolean) expression.evaluate(context);
            }
        } catch (Exception ex) {
            return false;
        }

        vitalResult = pythonResult && jexlResult;

        return vitalResult;
    }

    @Override
    public boolean vitalClose(Block previousBlock, Block closeBlock) {
        boolean vitalResult = false;
        boolean pythonResult = true;
        boolean jexlResult = true;

        if (maintenanceExpression == null || pythonMaintenanceExpression == null || previousBlock == null || closeBlock == null) {
            return false;
        }

        boolean occupied1 = previousBlock.getOccupancy();
        boolean occupied2 = closeBlock.getOccupancy();

        try {
            for (int i = 0; i < redundancy; i++) {
                pythonExpressionEvaluator.put("previousBlockOccupied", occupied1);
                pythonExpressionEvaluator.put("closeBlockBlockOccupied", occupied2);
                pythonResult = pythonResult && (boolean) pythonExpressionEvaluator.eval(pythonMaintenanceExpression);
            }
        } catch (Exception ex) {
            return false;
        }

        try {
            for (int i = 0; i < redundancy; i++) {
                Expression expression = jexlExpressionEvaluator.createExpression(maintenanceExpression);
                JexlContext context = new MapContext();
                context.set("previousBlockOccupied", occupied1);
                context.set("closeBlockBlockOccupied", occupied2);
                jexlResult = jexlResult && (boolean) expression.evaluate(context);
            }
        } catch (Exception ex) {
            return false;
        }

        vitalResult = pythonResult && jexlResult;

        return vitalResult;
    }

    @Override
    public boolean vitalCrossing(Block crossingBlock) {
        boolean vitalResult = false;
        boolean pythonResult = true;
        boolean jexlResult = true;

        if (crossingExpression == null || pythonCrossingExpression == null || crossingBlock == null) {
            return false;
        }

        boolean occupied = crossingBlock.getOccupancy();
        boolean broken = !crossingBlock.getState();
        boolean activated = crossingBlock.getCrossingActivated();

        try {
            for (int i = 0; i < redundancy; i++) {
                pythonExpressionEvaluator.put("crossingBlockOccupied", occupied);
                pythonExpressionEvaluator.put("crossingBroken", broken);
                pythonExpressionEvaluator.put("crossingActivated", activated);
                pythonResult = pythonResult && (boolean) pythonExpressionEvaluator.eval(pythonCrossingExpression);
            }
        } catch (Exception ex) {
            return false;
        }

        try {
            for (int i = 0; i < redundancy; i++) {
                Expression expression = jexlExpressionEvaluator.createExpression(crossingExpression);
                JexlContext context = new MapContext();
                context.set("crossingBlockOccupied", occupied);
                context.set("crossingBroken", broken);
                context.set("crossingActivated", activated);
                jexlResult = jexlResult && (boolean) expression.evaluate(context);
            }
        } catch (Exception ex) {
            return false;
        }

        vitalResult = pythonResult && jexlResult;

        return vitalResult;
    }

    @Override
    public boolean vitalCrossingState(Block previousBlock, Block crossingBlock) {
        boolean vitalResult = false;
        boolean pythonResult = true;
        boolean jexlResult = true;

        if (crossingActivateExpression == null || pythonCrossingActivateExpression == null || previousBlock == null || crossingBlock == null) {
            return false;
        }

        boolean occupied1 = previousBlock.getOccupancy();
        boolean occupied2 = crossingBlock.getOccupancy();

        try {
            for (int i = 0; i < redundancy; i++) {
                pythonExpressionEvaluator.put("previousBlockOccupied", occupied1);
                pythonExpressionEvaluator.put("crossingBlockOccupied", occupied2);
                pythonResult = pythonResult && (boolean) pythonExpressionEvaluator.eval(pythonCrossingActivateExpression);
            }
        } catch (Exception ex) {
            return false;
        }

        try {
            for (int i = 0; i < redundancy; i++) {
                Expression expression = jexlExpressionEvaluator.createExpression(crossingActivateExpression);
                JexlContext context = new MapContext();
                context.set("previousBlockOccupied", occupied1);
                context.set("crossingBlockOccupied", occupied2);
                jexlResult = jexlResult && (boolean) expression.evaluate(context);
            }
        } catch (Exception ex) {
            return false;
        }

        vitalResult = pythonResult && jexlResult;

        return vitalResult;
    }
    public boolean vitalTest() {
        boolean vitalResult = false;
        boolean pythonResult = true;
        boolean jexlResult = true;

        try {
            for (int i = 0; i < redundancy; i++) {
                pythonExpressionEvaluator.put("previousBlockOccupied", true);
                pythonExpressionEvaluator.put("crossingBlockOccupied", true);
                pythonResult = pythonResult && (boolean) pythonExpressionEvaluator.eval(pythonCrossingActivateExpression);
            }
        } catch (Exception ex) {
            return false;
        }

        try {
            for (int i = 0; i < redundancy; i++) {
                Expression expression = jexlExpressionEvaluator.createExpression(crossingActivateExpression);
                JexlContext context = new MapContext();
                context.set("previousBlockOccupied", true);
                context.set("crossingBlockOccupied", true);
                jexlResult = jexlResult && (boolean) expression.evaluate(context);
            }
        } catch (Exception ex) {
            return false;
        }

        vitalResult = pythonResult && jexlResult;

        return vitalResult;
    }
    
    @Override
    public boolean vitalMonitor(Block nextBlock, Block upcomingBlock) {
        boolean vitalResult = false;
        boolean pythonResult = true;
        boolean jexlResult = true;

        if (monitorExpression == null || pythonMonitorExpression == null || nextBlock == null || upcomingBlock == null) {
            return false;
        }

        boolean occupied1 = nextBlock.getOccupancy();
        boolean occupied2 = upcomingBlock.getOccupancy();

        try {
            for (int i = 0; i < redundancy; i++) {
                pythonExpressionEvaluator.put("nextBlockOccupied", occupied1);
                pythonExpressionEvaluator.put("upcomingBlockOccupied", occupied2);
                pythonResult = pythonResult && (boolean) pythonExpressionEvaluator.eval(pythonMonitorExpression);
            }
        } catch (Exception ex) {
            return false;
        }

        try {
            for (int i = 0; i < redundancy; i++) {
                Expression expression = jexlExpressionEvaluator.createExpression(monitorExpression);
                JexlContext context = new MapContext();
                context.set("nextBlockOccupied", occupied1);
                context.set("upcomingBlockOccupied", occupied2);
                jexlResult = jexlResult && (boolean) expression.evaluate(context);
            }
        } catch (Exception ex) {
            return false;
        }

        vitalResult = pythonResult && jexlResult;

        return vitalResult;
    }
}
