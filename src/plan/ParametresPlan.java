package plan;

import java.awt.Color;

public class ParametresPlan {
    private int pixelsParMetre;
    private Color drawingColor;
    private String inputMode;

    public ParametresPlan(int pixelsParMetre, Color drawingColor, String inputMode) {
        this.pixelsParMetre = pixelsParMetre;
        this.drawingColor = drawingColor;
        this.inputMode = inputMode;
    }

    public int getPixelsParMetre() {
        return pixelsParMetre;
    }

    public void setPixelsParMetre(int pixelsParMetre) {
        this.pixelsParMetre = pixelsParMetre;
    }

    public Color getDrawingColor() {
        return drawingColor;
    }

    public void setDrawingColor(Color drawingColor) {
        this.drawingColor = drawingColor;
    }

    public String getInputMode() {
        return inputMode;
    }

    public void setInputMode(String inputMode) {
        this.inputMode = inputMode;
    }

    @Override
    public String toString() {
        return "PlanParameters{" +
                "pixelsParMetre=" + pixelsParMetre +
                ", drawingColor=" + drawingColor +
                ", inputMode='" + inputMode + '\'' +
                '}';
    }
}
