package trz.utils;

import trz.structure.Cell;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 05/01/18
 * Time: 10.27
 */
public class RightTextAligner {

    public int calculatePrintingPositionByCell(Cell cell){
        int retValue = cell.getPixelScreenXPos();
        if (cell instanceof Number) {
            retValue = this.calculatePositionByCell((Number) cell);
        }else if(cell instanceof Variable){
            retValue = this.calculatePositionByCell((Variable) cell);
        }
        return retValue;
    }
    private int calculatePositionByCell(Variable cell){
        String value = cell.printFormattedValue();
        int fontWidth = FontAndColorSelector.getNewInstance().getWidthForFont(cell.getFont(), "W");
        int deltaPos = value.length() * fontWidth;
        return cell.getPixelScreenXPos() - deltaPos;
    }
    private int calculatePositionByCell(Number cell){
        String value = cell.printFormattedValue();
        int fontWidth = FontAndColorSelector.getNewInstance().getWidthForFont(cell.getFont(), "W");
        int deltaPos = value.length() * fontWidth;
        return cell.getPixelScreenXPos() - deltaPos;
    }


}
