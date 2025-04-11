// using bitwise to improving performa (just experiment)
public class Utils {
    public static int getRed(int rgb) {
        return (rgb >> 16) & 0xff;        
    }

    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xff;        
    }

    public static int getBlue(int rgb) {
        return rgb & 0xff;        
    }

    public static int[] compareRed(int rgb, int[] redMaxMin) {
        if (Utils.getRed(rgb) > redMaxMin[0]) {
            redMaxMin[0] = Utils.getRed(rgb);
        } 
        if (Utils.getRed(rgb) < redMaxMin[1]) {
            redMaxMin[1] = Utils.getRed(rgb);
        } 
        return redMaxMin;        
    }
    
    public static int[] compareGreen(int rgb, int[] greenMaxMin) {
        if (Utils.getGreen(rgb) > greenMaxMin[0]) {
            greenMaxMin[0] = Utils.getGreen(rgb);
        } 
        if (Utils.getGreen(rgb) < greenMaxMin[1]) {
            greenMaxMin[1] = Utils.getGreen(rgb);
        }  
        return greenMaxMin;        
    }
    
    public static int[] compareBlue(int rgb, int[] blueMaxMin) {
        if (Utils.getBlue(rgb) > blueMaxMin[0]) {
            blueMaxMin[0] = Utils.getBlue(rgb);
        }         
        if (Utils.getBlue(rgb) < blueMaxMin[1]) {
            blueMaxMin[1] = Utils.getBlue(rgb);
        }          
        return blueMaxMin;        
    }
}