package org.zkoss.poi.ss.formula.functions;

import org.zkoss.poi.ss.formula.eval.*;

import java.util.Arrays;

/**
 * Abstract class to be inherited by select function within RelationalOperatorFunction
 * The inputs should be a region of cells and conditions (if any).
 * The first input arg[0] should be the region, and the rest of args are conditions
 * Created by Danny on 9/22/2016.
 */
public abstract class SelectFunction implements Function {

    public final ValueEval evaluate (ValueEval[] args, int srcCellRow, int srcCellCol) {
        
        try {
            
            if (args.length < 1) {
                
                return ErrorEval.VALUE_INVALID;
                
            }
            
            AreaEval range = convertRangeArg(args[0]);
            
            //no conditions
            if (args.length == 1) {
                
                return evaluate(range);
                
            }
            //evaluate with conditions
            else {
                
                ValueEval[] conditions = Arrays.copyOfRange(args, 1, args.length - 1);
                return evaluate(range, conditions);
                
            }           
            
        }
        catch (EvaluationException e) {
            return e.getErrorEval();
        }
        
    }
    
    
    protected abstract ValueEval evaluate(AreaEval range);    
    
    protected abstract ValueEval evaluate(AreaEval range, ValueEval[] args);


    /**
     * Function taken from Sumif.java
     * convertRangeArg takes a ValueEval and attempts to convert it to
     * an AreaEval or RefEval
     *
     * @param eval
     * @return
     * @throws EvaluationException
     */
    private static AreaEval convertRangeArg(ValueEval eval) throws EvaluationException {
        if (eval instanceof AreaEval) {
            return (AreaEval) eval;
        }
        if (eval instanceof RefEval) {
            return ((RefEval)eval).offset(0, 0, 0, 0);
        }
        throw new EvaluationException(ErrorEval.VALUE_INVALID);
    }
    
}
